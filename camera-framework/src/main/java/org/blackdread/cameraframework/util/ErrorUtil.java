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

import com.sun.jna.NativeLong;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/04</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 * @deprecated Might be moved to helper/logic to allow to override the default behavior
 */
public final class ErrorUtil {

    private static final Logger log = LoggerFactory.getLogger(ErrorUtil.class);

    /**
     * Convert a long value to it's corresponding EDSDK error.
     *
     * @param value native long that was returned by a method from {@link org.blackdread.camerabinding.jna.EdsdkLibrary}
     * @return edsdk error from native long
     */
    public static EdsdkError toEdsdkError(final NativeLong value) {
        for (EdsdkError error : EdsdkError.values()) {
            if (error.value().equals(value.intValue()))
                return error;
        }
        log.error("Unknown native error value: {}, {}", value.intValue(), value.longValue());
        // either we throw or maybe a default error like EDS_ERR_UNEXPECTED_EXCEPTION or another
        throw new IllegalArgumentException("Unknown native error value: " + value.intValue() + ", " + value.longValue());
    }

    /**
     * @param runnable runnable to execute
     * @deprecated not yet used and need to review, might use a better API to give more option (Interface with fluent)
     */
    public static void retryOnBusy(final Runnable runnable) {
        try {
            runnable.run();
        } catch (EdsdkErrorException e) {
            if (e.getEdsdkError() == EdsdkError.EDS_ERR_DEVICE_BUSY)
                runnable.run();
            else
                throw e;
        }
    }

    /**
     * @param runnable    runnable to execute
     * @param delayMillis delay between retries
     * @deprecated not yet used and need to review, might use a better API to give more option (Interface with fluent)
     */
    public static void retryOnBusy(final Runnable runnable, final long delayMillis) {
        try {
            runnable.run();
        } catch (EdsdkErrorException e) {
            if (e.getEdsdkError() == EdsdkError.EDS_ERR_DEVICE_BUSY) {
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
                runnable.run();
            } else
                throw e;
        }
    }


    private ErrorUtil() {
    }
}
