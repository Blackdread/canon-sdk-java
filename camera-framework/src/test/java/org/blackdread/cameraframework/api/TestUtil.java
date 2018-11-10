package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

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

    public static void assertNoError(final NativeLong error) {
        Assertions.assertEquals(EdsdkError.EDS_ERR_OK, toEdsdkError(error));
    }

    public static void assertNoError(final EdsdkError error) {
        Assertions.assertEquals(EdsdkError.EDS_ERR_OK, error);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Assertions.fail("Sleep interrupted");
        }
    }

    private TestUtil() {

    }
}
