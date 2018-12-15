package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

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

    public static <T extends Throwable> T assertExecutionThrows(Class<T> expectedType, Executable executable) {
        try {
            executable.execute();
        } catch (Throwable actualException) {
            if (actualException instanceof ExecutionException) {
                if (expectedType.isInstance(actualException.getCause())) {
                    return (T) actualException.getCause();
                } else {
                    throw new AssertionFailedError(String.format("expected: %s but was: %s", expectedType, actualException.getCause()));
                }
            } else {
                Assertions.fail(String.format("Unexpected exception thrown: %s", actualException));
            }
        }
        String message = String.format("Expected %s to be thrown, but nothing was thrown.", expectedType.getCanonicalName());
        throw new AssertionFailedError(message);
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
