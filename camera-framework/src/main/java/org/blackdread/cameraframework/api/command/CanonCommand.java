package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.command.contract.CopyCommand;
import org.blackdread.cameraframework.api.command.contract.ErrorLogicCommand;
import org.blackdread.cameraframework.api.command.contract.TargetRefCommand;
import org.blackdread.cameraframework.api.command.contract.TimeoutCommand;
import org.blackdread.cameraframework.util.TimeUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @param <R> Return type of command
 * @author Yoann CAPLAIN
 */
public interface CanonCommand<R> extends TargetRefCommand, CopyCommand<R>, TimeoutCommand, ErrorLogicCommand/*, RootDecoratorCommand<R>/*, Future<R>*/ {

    /**
     * Should be called only by command dispatcher thread(s).
     * Provided in interface to not require Reflection for each command execution.
     */
    void run();

    /**
     * @return time when command was created
     */
    Instant getCreateTime();

    /**
     * @return time when command was started
     * @throws IllegalStateException if called when command not ran yet
     */
    Instant getExecutionStartTime();

    /**
     * @return time when command was finished (with or without error)
     * @throws IllegalStateException if called when command not ran or not finished yet
     */
    Instant getExecutionEndTime();

    /**
     * Convenient method to know duration since creation of this command
     *
     * @return duration between create time and current time of command
     */
    default Duration getCreatedDurationSinceNow() {
        return Duration.between(getCreateTime(), TimeUtil.currentInstant()).abs();
    }

    /**
     * Convenient method to know duration since execution of this command
     *
     * @return duration of execution time of command
     * @throws IllegalStateException if called when command not ran yet
     */
    default Duration getExecutionDurationSinceNow() {
        return Duration.between(getExecutionStartTime(), TimeUtil.currentInstant()).abs();
    }

    /**
     * Duration between execution start time and end time of this command
     *
     * @return duration of execution time of command
     * @throws IllegalStateException if called when command not ran or not finished yet
     */
    default Duration getExecutionDuration() {
        return Duration.between(getExecutionStartTime(), getExecutionEndTime()).abs();
    }

    /**
     * Duration between creation time and execution end time of this command
     *
     * @return duration of execution time of command
     * @throws IllegalStateException if called when command not ran or not finished yet
     */
    default Duration getExecutionDurationTotal() {
        return Duration.between(getCreateTime(), getExecutionEndTime()).abs();
    }

    R get() throws InterruptedException, ExecutionException;

    Optional<R> getOpt() throws InterruptedException, ExecutionException;

}
