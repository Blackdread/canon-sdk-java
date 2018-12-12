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
