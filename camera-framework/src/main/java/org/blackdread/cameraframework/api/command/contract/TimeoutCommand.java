package org.blackdread.cameraframework.api.command.contract;

import java.time.Duration;
import java.util.Optional;

/**
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface TimeoutCommand {

    Optional<Duration> getTimeout();

}
