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
package org.blackdread.cameraframework.util;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceBusyErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
public class NameToBeDefined implements Runnable {

    private static final int DEFAULT_DELAY_MILLIS = 200;

    private final Runnable runnable;

    public static NameToBeDefined wrap(final Runnable runnableToDecorate) {
        return new NameToBeDefined(runnableToDecorate);
    }

    protected NameToBeDefined(Runnable runnableToDecorate) {
        this.runnable = Objects.requireNonNull(runnableToDecorate);
    }

    public NameToBeDefined retryOnBusy() {
        // can be called many times to add many retries
        return retryOnBusy(DEFAULT_DELAY_MILLIS, 0);
    }

    public NameToBeDefined retryOnBusy(final long delayMillis) {
        // can be called many times to add many retries with different delay
        return retryOnBusy(delayMillis, 0);
    }

    public NameToBeDefined retryOnBusy(final long... delayMillis) {
        // can be called many times to add many retries with different delay
        NameToBeDefined tmp = this;
        for (final long delayMilli : delayMillis) {
            tmp = retryOnBusy(delayMilli, 0);
        }
        return tmp;
    }

    public NameToBeDefined retryOnBusy(final long delayMillis, final int retryTimes) {
        // can be called many times to add many retries with different delay
        if (delayMillis < 0)
            throw new IllegalArgumentException("Delay must be higher than 0");
        if (retryTimes < 0)
            throw new IllegalArgumentException("Retry times must be higher than 0");
        return new NameToBeDefined(() -> {
            int retry = -1;
            do {
                try {
                    this.runnable.run();
                    return;
                } catch (EdsdkDeviceBusyErrorException e) {
                    if (sleep(delayMillis))
                        return;
                    retry++;
                }
            } while (retry < retryTimes);
        });
    }

    public NameToBeDefined retryOnError(final long delayMillis, final EdsdkError errors) {
        return retryOnError(delayMillis, 0, Collections.singletonList(errors));
    }

    public NameToBeDefined retryOnError(final long delayMillis, final EdsdkError... errors) {
        if (errors == null)
            return this;
        return retryOnError(delayMillis, 0, Arrays.asList(errors));
    }

    public NameToBeDefined retryOnError(final long delayMillis, final int retryTimes, final EdsdkError errors) {
        return retryOnError(delayMillis, retryTimes, Collections.singletonList(errors));
    }

    public NameToBeDefined retryOnError(final long delayMillis, final int retryTimes, final EdsdkError... errors) {
        if (errors == null)
            return this;
        return retryOnError(delayMillis, retryTimes, Arrays.asList(errors));
    }

    public NameToBeDefined retryOnError(final long delayMillis, final int retryTimes, final List<EdsdkError> errors) {
        if (delayMillis < 0)
            throw new IllegalArgumentException("Delay must be higher than 0");
        if (retryTimes < 0)
            throw new IllegalArgumentException("Retry times must be higher than 0");
        errors.forEach(edsdkError -> {
            if (edsdkError == null)
                throw new IllegalArgumentException("Error cannot be null");
        });

        return new NameToBeDefined(() -> {
            int retry = -1;
            EdsdkErrorException lastException;
            do {
                try {
                    this.runnable.run();
                    return;
                } catch (EdsdkErrorException e) {
                    lastException = e;
                    if (errors.contains(e.getEdsdkError())) {
                        if (sleep(delayMillis))
                            return;
                        retry++;
                    } else {
                        throw e;
                    }
                }
            } while (retry < retryTimes);
            throw lastException;
        });
    }

    public NameToBeDefined runOnError(final Runnable runOnError, final EdsdkError error) {
        // can be called many times to add different handlers
        return runOnError(runOnError, Collections.singletonList(error));
    }

    public NameToBeDefined runOnError(final Runnable runOnError, final EdsdkError... errors) {
        if (errors == null)
            return this;
        return runOnError(runOnError, Arrays.asList(errors));
    }

    public NameToBeDefined runOnError(final Runnable runOnError, final List<EdsdkError> errors) {
        // can be called many times to add different handlers
        if (runOnError == null)
            throw new IllegalArgumentException("runOnError must not be null");
        errors.forEach(edsdkError -> {
            if (edsdkError == null)
                throw new IllegalArgumentException("Error cannot be null");
        });
        return new NameToBeDefined(() -> {
            try {
                this.runnable.run();
            } catch (EdsdkErrorException e) {
                if (errors.contains(e.getEdsdkError())) {
                    runOnError.run();
                } else {
                    throw e;
                }
            }
        });
    }

    @Override
    public void run() {
        runnable.run();
    }

    public void handle(Consumer<Throwable> consumer) {
        try {
            runnable.run();
        } catch (Throwable t) {
            consumer.accept(t);
        }
    }

    public <R> R handle(Function<Throwable, R> function) {
        try {
            runnable.run();
        } catch (Throwable t) {
            return function.apply(t);
        }
        return null;
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
