package org.blackdread.cameraframework.api.helper.logic.event;

/**
 * <p><b><u>Important:</u></b> This class is completely optional, however if not used then user must handle himself the scheduling of fetch events.
 * <br>
 * In case that {@link org.blackdread.cameraframework.api.helper.logic.CommandDispatcher} is used to initialize SDK and with the single thread implementation then commands may fetch events directly from implementation; specific implementation of this interface may work in pair with dispatcher.
 * </p>
 * <br>
 * <p><b><u>Important:</u></b> This class is completely optional, however if not used then user must handle himself the scheduling of fetch events himself.
 * <p>Created on 2018/12/05.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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
