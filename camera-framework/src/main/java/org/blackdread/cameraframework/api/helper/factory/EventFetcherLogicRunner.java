/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.helper.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.event.EventFetcherLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ThreadFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>
 * Ref API says that threads other than main thread shall:
 * <br>
 * When creating applications that run under Windows, a COM Initialisation is required for each thread in order to access a camera from a thread other than the main thread. To create a user thread and access the camera from that thread, be sure to execute CoInitializeEx(NULL, COINIT_APARTMENTTHREADED) at the start of the thread and CoUnInitialize() at the end.</p>
 * <br>
 * <p>Implementation use COINIT_MULTITHREADED as during tests in {@code ShootLogicCameraTest}, it has been shown that it would work for events but not with COINIT_APARTMENTTHREADED</p>
 * <br>
 * <p>It is important to note that tests revealed that to fetch events from camera required the thread that loaded the Edsdk library to be used (and it called {@code EdsInitializeSDK}), therefore it might be advisable to call {@code start()} at the beginning of the program to let this implementation initialize the library.
 * <br>
 * In any case it has been shown that {@code User32} implementation seems to be also impacted by precedent problem.
 * <br>
 * <br>
 * One solution found is to call {@code CanonFactory.edsdkLibrary().EdsInitializeSDK();} before the fetch event loop (by the fetcher thread); if this solution is used then {@code stop()} should be handled with care as all cameras ref previously returned by library should be discarded.
 * </p>
 * <p>In case that {@link org.blackdread.cameraframework.api.helper.logic.CommandDispatcher} is used to initialize SDK and with the single thread implementation then commands may fetch events directly from implementation; also this implementation should not be used.</p>
 * <p>Created on 2018/12/05.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 * @deprecated Kept as is (will not remove) but EDSDK might not work well with multi-thread, framework supports to fetch events directly in commands to not be dependent on this EventFetcherLogic
 */
@ThreadSafe
@Deprecated
public class EventFetcherLogicRunner implements EventFetcherLogic {

    private static final Logger log = LoggerFactory.getLogger(EventFetcherLogicRunner.class);

    /**
     * Sleep time after fetch event(s)
     */
    private static final int LOOP_EVENT_SLEEP_MILLIS = 50;

    /**
     * Time in millis that calling thread will wait for event runner to stop
     */
    private static final int STOP_JOIN_MILLIS = 100;

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setNameFormat("event-runner-%d")
        .setDaemon(true)
        .setUncaughtExceptionHandler(this::uncaughtExceptionHandler)
        .build();

    private volatile Thread currentThread;

    private volatile boolean stopRun = false;

    private void runLogic() {
        initializeOle32Thread();
        initializeSdk();
        try {
            while (!stopRun) {
                try {
                    fetchEvent();
                } catch (Exception e) {
                    log.warn("Ignored exception in event logic runner", e);
                }
                try {
                    // To make sure we do not skip sleep if an exception is thrown in fetchEvent()
                    sleepAfterFetchEvent();
                } catch (Exception e) {
                    log.warn("Ignored exception in event logic runner", e);
                }
            }
        } finally {
            unInitializeOle32Thread();
            terminateSdk();
        }
        log.info("Event runner end");
    }

    /**
     * No checks are done (right now) if sdk was already initialized in another part of the program.
     * Use inheritance to change default behavior.
     */
    protected void initializeSdk() {
        final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsInitializeSDK());
        if (edsdkError != EdsdkError.EDS_ERR_OK) {
            log.error("Failed to initialize SDK");
        }
    }

    /**
     * No checks are done (right now) if sdk was already initialized by this thread or not.
     * Use inheritance to change default behavior.
     */
    protected void terminateSdk() {
        final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsTerminateSDK());
        if (edsdkError != EdsdkError.EDS_ERR_OK) {
            log.error("Failed to terminate SDK");
        }
    }

    /**
     * <p>
     * Ref API says that threads other than main thread shall:
     * <br>
     * When creating applications that run under Windows, a COM Initialisation is required for each thread in order to access a camera from a thread other than the main thread. To create a user thread and access the camera from that thread, be sure to execute CoInitializeEx(NULL, COINIT_APARTMENTTHREADED) at the start of the thread and CoUnInitialize() at the end.</p>
     * <br>
     * <p>Implementation use COINIT_MULTITHREADED as during tests in {@code ShootLogicCameraTest}, it has been shown that it would work for events but not with COINIT_APARTMENTTHREADED</p>
     * <br>
     * See {@code EventFetcherLogic} for full documentation
     * <br>
     * <br>
     * <p><b>Compatible only with windows!</b></p>
     */
    protected void initializeOle32Thread() {
        if (Platform.isWindows()) {
            final WinNT.HRESULT hresult = Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
            log.info("CoInitializeEx result: {}", hresult);
        }
    }

    /**
     * TODO Not sure this is good to call, we do not want to release DLLs loaded by the thread... more test are necessary in ShootLogicCameraTest class
     * <p>Default implementation does nothing, {@code Ole32.INSTANCE.CoUninitialize();} is commented.
     * <br>
     * See {@link EventFetcherLogicRunner#initializeOle32Thread()}
     * </p>
     * <br>
     * <p><b>Compatible only with windows!</b></p>
     */
    protected void unInitializeOle32Thread() {
        /*
        if (Platform.isWindows()) {
        Ole32.INSTANCE.CoUninitialize();
        }
        //*/
    }

    protected void fetchEvent() {
        fetchEventWithLibrary();
    }

    protected void fetchEventWithLibrary() {
        // this might throw if library is reloaded or failed to initialize, the exception is caught but it could be good to decide what to do in this situation
        final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetEvent());
        if (edsdkError != EdsdkError.EDS_ERR_OK) {
            log.warn("Error returned by camera while fetching event: {}", edsdkError);
        }
    }

    /**
     * <p><b>Compatible only with windows!</b></p>
     */
    protected void fetchEventWithUser32() {
        final User32 lib = User32.INSTANCE;
        final WinUser.MSG msg = new WinUser.MSG();
        final boolean hasMessage = lib.PeekMessage(msg, null, 0, 0, 1);
        if (hasMessage) {
            lib.TranslateMessage(msg);
            lib.DispatchMessage(msg);
        }
    }

    protected void sleepAfterFetchEvent() {
        try {
            Thread.sleep(LOOP_EVENT_SLEEP_MILLIS);
        } catch (InterruptedException ignored) {
            // If this thread should stop then stop method should be used
        }
    }

    private void uncaughtExceptionHandler(final Thread thread, final Throwable throwable) {
        // should not occur
        log.error("Event runner thread has terminated unexpectedly {} with {}", thread, throwable);
    }

    @Override
    public void start() {
        if (currentThread != null) {
            return;
        }
        synchronized (threadFactory) {
            if (currentThread == null) {
                stopRun = false;
                currentThread = threadFactory.newThread(this::runLogic);
                currentThread.start();
                try {
                    // Just to let the fetcher initialize the sdk for default behaviour
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void stop() {
        if (currentThread == null) {
            return;
        }
        synchronized (threadFactory) {
            if (currentThread != null) {
                stopRun = true;
                // This could be an option
//                currentThread.interrupt();
                try {
                    currentThread.join(STOP_JOIN_MILLIS);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                } finally {
                    currentThread = null;
                }
            }
        }
    }

    @Override
    public boolean isRunning() {
        return currentThread != null && !stopRun;
    }
}
