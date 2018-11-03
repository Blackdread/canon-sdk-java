package org.blackdread.cameraframework.util;

import java.time.Instant;

/**
 * Simple util class to return current time.
 * Is not customizable but UTC should fit any purpose.
 * <p>Created on 03/11/2018</p>
 *
 * @author Yoann CAPLAIN
 */
public final class TimeUtil {

    public static Instant currentInstant() {
        return Instant.now();
    }

    private TimeUtil() {
    }
}
