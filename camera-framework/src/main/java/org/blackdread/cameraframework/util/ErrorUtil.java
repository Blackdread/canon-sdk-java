package org.blackdread.cameraframework.util;

import com.sun.jna.NativeLong;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 04/10/2018</p>
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


    private ErrorUtil() {
    }
}
