package org.blackdread.cameraframework.util;

import com.sun.jna.NativeLong;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/04</p>
 *
 * @author Yoann CAPLAIN
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
