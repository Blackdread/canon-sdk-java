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
package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.liveViewLogic;

/**
 * Test when live view stays off or very few back and forth
 * <p>Created on 2018/10/22.<p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class LiveViewLogicCameraTest {

    private static final Logger log = LoggerFactory.getLogger(LiveViewLogicCameraTest.class);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    private static EdsdkLibrary.EdsCameraRef cameraRef;

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
        cameraRef = camera.getValue();
    }

    @AfterAll
    static void tearDownClass() {
        try {
            TestShortcutUtil.closeSession(camera);
        } finally {
            ReleaseUtil.release(camera);
        }
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        Thread.sleep(200);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void endLiveView() {
        liveViewLogic().enableLiveView(cameraRef);
        liveViewLogic().endLiveView(cameraRef);
    }

    @Test
    void endLiveViewFailsIfNotEnabledFirst() {
        try {
            liveViewLogic().endLiveView(cameraRef);
        } catch (EdsdkErrorException e) {
            Assertions.assertEquals(EdsdkError.EDS_ERR_DEVICE_BUSY, e.getEdsdkError());
            return;
        }
        Assertions.fail("Should have thrown");
    }

    @Test
    void liveViewDisabledAfterBeginAndEndLiveView() {
        liveViewLogic().beginLiveView(cameraRef);
        liveViewLogic().endLiveView(cameraRef);
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(cameraRef);
        Assertions.assertFalse(liveViewEnabled, "Live view mode should be disabled");
    }

    @Test
    void isLiveViewEnabledTrueWhenEnabled() {
        liveViewLogic().enableLiveView(cameraRef);
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(cameraRef);
        Assertions.assertTrue(liveViewEnabled, "Live view mode should be enabled");
    }

    @Test
    void isLiveViewEnabledFalseWhenDisabled() {
        liveViewLogic().disableLiveView(cameraRef);
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(cameraRef);
        Assertions.assertFalse(liveViewEnabled, "Live view mode should be disabled");
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImageDoesNotThrow() {
        final boolean isOn = liveViewLogic().isLiveViewEnabledByDownloadingOneImage(cameraRef);
        Assertions.assertFalse(isOn, "Expected lived view off");
    }

    @Test
    void getLiveViewImageThrowsIfNotRunning() {
        Assertions.assertThrows(EdsdkErrorException.class, () -> liveViewLogic().getLiveViewImage(cameraRef));
    }

    @Test
    void getLiveViewImageBufferThrowsIfNotRunning() {
        Assertions.assertThrows(EdsdkErrorException.class, () -> liveViewLogic().getLiveViewImageBuffer(cameraRef));
    }

    @Test
    void getLiveViewImageReferenceThrowsIfNotRunning() {
        Assertions.assertThrows(EdsdkErrorException.class, () -> liveViewLogic().getLiveViewImageReference(cameraRef));
    }
}
