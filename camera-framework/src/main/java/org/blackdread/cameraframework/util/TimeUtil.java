package org.blackdread.cameraframework.util;

import java.time.Instant;

/**
 * Simple util class to return current time.
 * Is not customizable but UTC should fit any purpose.
 * <p>Created on 2018/11/03</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class TimeUtil {

    public static Instant currentInstant() {
        return Instant.now();
    }

    private TimeUtil() {
    }
}
