package org.blackdread.cameraframework.util;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceBusyErrorException;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Internal class to reduce duplicate code for commands errors with very simple API.
 * Main usage of this class is retryOnBusy methods, rest is there but might never be used and not really "good".
 * It is not made to be chained like callbacks, neither a replacement of {@link java.util.concurrent.CompletableFuture}
 * <p>Created on 2018/11/17.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class NameToBeDefined2<V> implements Callable<V> {

    private static final int DEFAULT_DELAY_MILLIS = 200;

    private final Callable<V> callable;

    public static <V> NameToBeDefined2<V> wrap(final Callable<V> callableToDecorate) {
        return new NameToBeDefined2<>(callableToDecorate);
    }

    protected NameToBeDefined2(Callable<V> callableToDecorate) {
        this.callable = callableToDecorate;
    }

    public NameToBeDefined2<V> retryOnBusy() {
        // can be called many times to add many retries
        return retryOnBusy(DEFAULT_DELAY_MILLIS, 0);
    }

    public NameToBeDefined2<V> retryOnBusy(final long delayMillis) {
        // can be called many times to add many retries with different delay
        return retryOnBusy(delayMillis, 0);
    }

    public NameToBeDefined2<V> retryOnBusy(final long... delayMillis) {
        // can be called many times to add many retries with different delay
        NameToBeDefined2<V> tmp = this;
        for (final long delayMilli : delayMillis) {
            tmp = retryOnBusy(delayMilli, 0);
        }
        return tmp;
    }

    public NameToBeDefined2<V> retryOnBusy(final long delayMillis, final int retryTimes) {
        // can be called many times to add many retries with different delay
        if (delayMillis < 0)
            throw new IllegalArgumentException("Delay must be higher than 0");
        if (retryTimes < 0)
            throw new IllegalArgumentException("Retry times must be higher than 0");
        return new NameToBeDefined2<>(() -> {
            int retry = -1;
            do {
                try {
                    return this.callable.call();
                } catch (EdsdkDeviceBusyErrorException e) {
                    if (sleep(delayMillis))
                        return null;
                    retry++;
                }
            } while (retry < retryTimes);
            return null;
        });
    }

    public NameToBeDefined2<V> retryOnError(final long delayMillis, final EdsdkError errors) {
        return retryOnError(delayMillis, 0, Collections.singletonList(errors));
    }

    public NameToBeDefined2<V> retryOnError(final long delayMillis, final EdsdkError... errors) {
        return retryOnError(delayMillis, 0, Arrays.asList(errors));
    }

    public NameToBeDefined2<V> retryOnError(final long delayMillis, final int retryTimes, final EdsdkError errors) {
        return retryOnError(delayMillis, retryTimes, Collections.singletonList(errors));
    }

    public NameToBeDefined2<V> retryOnError(final long delayMillis, final int retryTimes, final EdsdkError... errors) {
        return retryOnError(delayMillis, retryTimes, Arrays.asList(errors));
    }

    public NameToBeDefined2<V> retryOnError(final long delayMillis, final int retryTimes, final List<EdsdkError> errors) {
        if (delayMillis < 0)
            throw new IllegalArgumentException("Delay must be higher than 0");
        if (retryTimes < 0)
            throw new IllegalArgumentException("Retry times must be higher than 0");
        return new NameToBeDefined2<V>(() -> {
            int retry = -1;
            EdsdkErrorException lastException;
            do {
                try {
                    return this.callable.call();
                } catch (EdsdkErrorException e) {
                    lastException = e;
                    if (errors.contains(e.getEdsdkError())) {
                        if (sleep(delayMillis))
                            return null;
                        retry++;
                    } else {
                        throw e;
                    }
                }
            } while (retry < retryTimes);
            throw lastException;
        });
    }

    public NameToBeDefined2<V> runOnError(final Callable<V> runOnError, final EdsdkError error) {
        // can be called many times to add different handlers
        return runOnError(runOnError, Collections.singletonList(error));
    }

    public NameToBeDefined2<V> runOnError(final Callable<V> runOnError, final EdsdkError... errors) {
        return runOnError(runOnError, Arrays.asList(errors));
    }

    public NameToBeDefined2<V> runOnError(final Callable<V> runOnError, final List<EdsdkError> errors) {
        // can be called many times to add different handlers
        return new NameToBeDefined2<V>(() -> {
            try {
                return this.callable.call();
            } catch (EdsdkErrorException e) {
                if (errors.contains(e.getEdsdkError())) {
                    return runOnError.call();
                } else {
                    throw e;
                }
            }
        });
    }

    @Override
    public V call() throws Exception {
        return callable.call();
    }

    public void handle(Consumer<Throwable> consumer) {
        try {
            callable.call();
        } catch (Throwable t) {
            consumer.accept(t);
        }
    }

    public V handle(Function<Throwable, V> function) {
        try {
            return callable.call();
        } catch (Throwable t) {
            return function.apply(t);
        }
    }

    public <R> R handle(BiFunction<V, Throwable, R> function) {
        try {
            return function.apply(callable.call(), null);
        } catch (Throwable t) {
            return function.apply(null, t);
        }
    }

    private boolean sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return true;
        }
        return false;
    }
}
