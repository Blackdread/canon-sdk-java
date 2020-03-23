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
package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.command.contract.CopyCommand;
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
 * @since 1.0.0
 */
public interface CanonCommand<R> extends TargetRefCommand<R>, CopyCommand<R>, TimeoutCommand<R>/*, RootDecoratorCommand<R>/*, Future<R>*/ {

    /**
     * Should be called only by command dispatcher thread(s).
     * <br>
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
     * If true is returned then {@link #getExecutionStartTime()} and {@link #getExecutionDurationSinceNow()} can be safely called.
     *
     * @return true if execution has been started (run method called)
     */
    boolean hasExecutionStarted();

    /**
     * @return time when command was finished (with or without error)
     * @throws IllegalStateException if called when command not ran or not finished yet
     */
    Instant getExecutionEndTime();

    /**
     * If true is returned then {@link #getExecutionEndTime()}, {@link #getExecutionDuration()}, {@link #getExecutionDurationTotal()} and {@link #getExecutionDurationSinceNow()} can be safely called.
     * <br>
     * <br>
     * <p>Completion may be due to normal termination, an exception -- in all
     * of these cases, this method will return {@code true}.</p>
     *
     * @return true if execution has been ended (run method ended)
     */
    boolean hasExecutionEnded();

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

    /**
     * Waits if necessary for this command to complete, and then returns its result.
     *
     * @return the result value
     * @throws ExecutionException   if this command completed exceptionally
     * @throws InterruptedException if the current thread was interrupted
     *                              while waiting
     */
    R get() throws InterruptedException, ExecutionException;

    /**
     * Waits if necessary for this command to complete, and then returns its result.
     *
     * @return the result value or empty if none
     * @throws ExecutionException   if this command completed exceptionally
     * @throws InterruptedException if the current thread was interrupted
     *                              while waiting
     */
    default Optional<R> getOpt() throws InterruptedException, ExecutionException {
        return Optional.ofNullable(get());
    }

    /*
     * Waits if necessary for at most the given time for the command
     * to complete, and then retrieves its result, if available.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return the computed result
     * @throws ExecutionException   if this command completed exceptionally
     * @throws InterruptedException if the current thread was interrupted
     *                              while waiting
     * @throws TimeoutException     if the wait timed out
     */
//    R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

}
