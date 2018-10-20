package org.blackdread.cameraframework.api;

import com.sun.jna.Platform;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class TestUtil {

    private static final Logger log = LoggerFactory.getLogger(TestUtil.class);

    public static void assertIsWindows() {
        Assertions.assertTrue(Platform.isWindows(), "Platform is not windows");
    }

    private TestUtil() {

    }
}
