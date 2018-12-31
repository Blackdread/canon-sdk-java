/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
 * @since 1.0.0
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
