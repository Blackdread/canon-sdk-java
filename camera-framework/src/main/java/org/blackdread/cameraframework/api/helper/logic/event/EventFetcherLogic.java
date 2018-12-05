package org.blackdread.cameraframework.api.helper.logic.event;

/**
 * <p>Created on 2018/12/05.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface EventFetcherLogic {

    /**
     * Start the running thread to fetch events.
     * <br>
     * Calling this method many times has no effect if {@code stop()} was not called in between.
     */
    void start();

    /**
     * Stop the running thread from fetching events.
     * <br>
     * If events need to fetched again, {@code start()} may be called again.
     * <br>
     * Calling this method many times has no effect if {@code start()} was not called in between.
     */
    void stop();

    /**
     * @return true if thread is still "running" and fetching events
     */
    boolean isRunning();

}
