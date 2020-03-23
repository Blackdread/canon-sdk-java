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
package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.api.TestUtil.assertNoError;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class TestShortcutUtil {

    private static final Logger log = LoggerFactory.getLogger(TestShortcutUtil.class);


    public static void initLibrary() {
        final EdsdkError error = toEdsdkError(edsdkLibrary().EdsInitializeSDK());
        Assertions.assertEquals(EdsdkError.EDS_ERR_OK, error);
    }

    public static void terminateLibrary() {
        final EdsdkError error = toEdsdkError(edsdkLibrary().EdsTerminateSDK());
        Assertions.assertEquals(EdsdkError.EDS_ERR_OK, error);
    }

    public static void reloadLibrary() {
        terminateLibrary();
        initLibrary();
    }

    /**
     * Do not forget to release the ref on fail
     *
     * @return Camera ref (not connected yet)
     */
    public static EdsdkLibrary.EdsCameraRef.ByReference getFirstCamera() {
        final EdsdkLibrary.EdsCameraListRef.ByReference cameraListRef = new EdsdkLibrary.EdsCameraListRef.ByReference();
        assertNoError(edsdkLibrary().EdsGetCameraList(cameraListRef));
        try {
            final NativeLongByReference outRef = new NativeLongByReference();
            assertNoError(edsdkLibrary().EdsGetChildCount(cameraListRef.getValue(), outRef));

            final long numCams = outRef.getValue().longValue();
            Assertions.assertTrue(numCams > 0, "No camera connected");

            final EdsdkLibrary.EdsCameraRef.ByReference cameraRef = new EdsdkLibrary.EdsCameraRef.ByReference();

            assertNoError(edsdkLibrary().EdsGetChildAtIndex(cameraListRef.getValue(), new NativeLong(0), cameraRef));
            return cameraRef;
        } finally {
            ReleaseUtil.release(cameraListRef);
        }
    }

    public static void registerCameraAddedHandler(final EdsdkLibrary.EdsCameraAddedHandler handler) {
        CanonFactory.edsdkLibrary().EdsSetCameraAddedHandler(handler, Pointer.NULL);
    }

    public static void registerObjectEventHandler(final EdsdkLibrary.EdsCameraRef cameraRef, final EdsdkLibrary.EdsObjectEventHandler handler) {
        CanonFactory.edsdkLibrary().EdsSetObjectEventHandler(cameraRef, new NativeLong(EdsObjectEvent.kEdsObjectEvent_All.value()), handler, Pointer.NULL);
    }

    public static void registerPropertyEventHandler(final EdsdkLibrary.EdsCameraRef cameraRef, final EdsdkLibrary.EdsPropertyEventHandler handler) {
        CanonFactory.edsdkLibrary().EdsSetPropertyEventHandler(cameraRef, new NativeLong(EdsPropertyEvent.kEdsPropertyEvent_All.value()), handler, Pointer.NULL);
    }

    public static void registerStateEventHandler(final EdsdkLibrary.EdsCameraRef cameraRef, final EdsdkLibrary.EdsStateEventHandler handler) {
        CanonFactory.edsdkLibrary().EdsSetCameraStateEventHandler(cameraRef, new NativeLong(EdsStateEvent.kEdsStateEvent_All.value()), handler, Pointer.NULL);
    }

    public static void openSession(final EdsdkLibrary.EdsCameraRef.ByReference camera) {
        assertNoError(edsdkLibrary().EdsOpenSession(camera.getValue()));
    }

    public static void closeSession(final EdsdkLibrary.EdsCameraRef.ByReference camera) {
        assertNoError(edsdkLibrary().EdsCloseSession(camera.getValue()));
    }

    public static void getEvents() {
        getEvents(50, 25);
    }

    public static void getEvents(final long sleepMillis, final int timeRetry) {
        for (int i = 0; i < timeRetry; i++) {
            CanonFactory.edsdkLibrary().EdsGetEvent();
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                Assertions.fail("Interrupted");
            }
        }
    }

    private TestShortcutUtil() {
    }
}
