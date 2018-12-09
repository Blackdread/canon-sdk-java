package org.blackdread.cameraframework.api;

/**
 * Similar to {@link java.util.concurrent.Callable} but fits command exception contract
 * <p>Created on 2018/12/09.</p>
 *
 * @param <R> the result type of method {@code call}
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@FunctionalInterface
public interface CallableCommand<R> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws InterruptedException if interrupted
     */
    R call() throws InterruptedException;
}
