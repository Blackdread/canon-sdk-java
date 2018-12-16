/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
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
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.FetchEventCommand;
import org.blackdread.cameraframework.api.helper.logic.event.EventFetcherLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ThreadFactory;

/**
 * Event fetcher by creating commands and scheduling it to the dispatcher
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@ThreadSafe
public class EventFetcherCommandLogicDefault implements EventFetcherLogic {

    private static final Logger log = LoggerFactory.getLogger(EventFetcherCommandLogicDefault.class);

    /**
     * Sleep time after a fetch event command is done
     */
    private int eventSleepMillis = 25;

    private int fetchTimesByOneCommand = 2;

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setNameFormat("event-schedule-%d")
        .setDaemon(true)
        .setUncaughtExceptionHandler(this::uncaughtExceptionHandler)
        .build();

    private volatile Thread currentThread;

    private volatile boolean stopRun = false;

    private void uncaughtExceptionHandler(final Thread thread, final Throwable throwable) {
        // should not occur
        log.error("Event scheduler thread has terminated unexpectedly {} with {}", thread, throwable);
    }

    private void runLogic() {
        while (!stopRun) {
            try {
                fetchEvent().get();
            } catch (Exception e) {
                log.warn("Ignored exception in event logic scheduler", e);
            }
            try {
                sleepAfterFetchEvent();
            } catch (Exception e) {
                log.warn("Ignored exception in event logic scheduler", e);
            }
        }
        log.info("Event scheduler end");
    }

    protected CanonCommand<Void> fetchEvent() {
        return fetchEventWithLibrary();
    }

    protected CanonCommand<Void> fetchEventWithLibrary() {
        final FetchEventCommand command = new FetchEventCommand(true, fetchTimesByOneCommand);
        CanonFactory.commandDispatcher().scheduleCommand(command);
        return command;
    }

    /*
     * <p><b>Compatible only with windows!</b></p>
     */
    /*
    protected void fetchEventWithUser32() {
    }
    //*/

    protected void sleepAfterFetchEvent() {
        try {
            Thread.sleep(eventSleepMillis);
        } catch (InterruptedException ignored) {
            // If this thread should stop then stop method should be used
        }
    }

    public int getEventSleepMillis() {
        return eventSleepMillis;
    }

    public void setEventSleepMillis(final int eventSleepMillis) {
        this.eventSleepMillis = eventSleepMillis;
    }

    public int getFetchTimesByOneCommand() {
        return fetchTimesByOneCommand;
    }

    public void setFetchTimesByOneCommand(final int fetchTimesByOneCommand) {
        this.fetchTimesByOneCommand = fetchTimesByOneCommand;
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
                currentThread.interrupt();
                currentThread = null;
            }
        }
    }

    @Override
    public boolean isRunning() {
        return currentThread != null && !stopRun;
    }

}
