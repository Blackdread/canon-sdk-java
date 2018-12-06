package org.blackdread.cameraframework.api.helper.logic.event;

/**
 * <p>
 * Ref API says that threads other than main thread shall:
 * <br>
 * When creating applications that run under Windows, a COM initialization is required for each thread in order to access a camera from a thread other than the main thread. To create a user thread and access the camera from that thread, be sure to execute CoInitializeEx(NULL, COINIT_APARTMENTTHREADED) at the start of the thread and CoUnInitialize() at the end.</p>
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
 * <br><br>
 * <p><b><u>Important:</u></b> This class is completely optional, however if not used then user must handle himself the initialization of the SDK with proper thread and fetch events himself.
 * <br>
 * In case that {@link org.blackdread.cameraframework.api.helper.logic.CommandDispatcher} is used to initialize SDK and with the single thread implementation then commands may fetch events directly from implementation; also this interface should not be used.
 * </p>
 * <br>
 * <p>Created on 2018/12/05.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface EventFetcherLogic {

    /**
     * Start the running thread to fetch events.
     * <br>
     * <p>Calling this method many times has no effect if {@code stop()} was not called in between.</p>
     */
    void start();

    /**
     * Stop the running thread from fetching events.
     * <br>
     * If events need to fetched again, {@code start()} may be called again.
     * <br>
     * <p>Calling this method many times has no effect if {@code start()} was not called in between.</p>
     * <br>
     * <br>
     * <p><b><u>Important:</u></b> If this thread initialized the SDK, it is highly not recommended to call this method unless it is to stop completely, discard all references and restart again</p>
     */
    void stop();

    /**
     * @return true if thread is still "running" and fetching events
     */
    boolean isRunning();

}
