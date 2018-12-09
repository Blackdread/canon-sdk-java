package org.blackdread.cameraframework.api.command.contract;

import java.time.Duration;
import java.util.Optional;

/**
 * A command should be interrupted if its execution exceeded timeout duration.
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface TimeoutCommand {

    /**
     * @return timeout of command
     */
    Optional<Duration> getTimeout();

    /**
     * As timeout is one of the most used option, it is provided directly without need of decorator (even if one is provided by the framework).
     * <br>
     * <p>If this method is called after command has started execution, there is no guarantee it will be taken into account (no exception is thrown)</p>
     *
     * @param timeout timeout to set
     */
    void setTimeout(final Duration timeout);

}
