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
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.liveViewLogic;

/**
 * Test when live view stays on
 * <p>Created on 2018/10/22.<p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class LiveViewLogicOnCameraTest {

    private static final Logger log = LoggerFactory.getLogger(LiveViewLogicOnCameraTest.class);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    private static EdsdkLibrary.EdsCameraRef cameraRef;

    @BeforeAll
    static void setUpClass() throws InterruptedException {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
        cameraRef = camera.getValue();

        liveViewLogic().beginLiveView(cameraRef, EdsEvfOutputDevice.kEdsEvfOutputDevice_PC);
        Thread.sleep(4000);
    }

    @AfterAll
    static void tearDownClass() {
        liveViewLogic().endLiveView(cameraRef);
        try {
            TestShortcutUtil.closeSession(camera);
        } finally {
            ReleaseUtil.release(camera);
        }
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        Thread.sleep(100);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void liveViewEnabledAfterBeginLiveView() {
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(cameraRef);
        Assertions.assertTrue(liveViewEnabled, "Live view mode should be enabled");
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImage() throws InterruptedException {
        final boolean isOn = liveViewLogic().isLiveViewEnabledByDownloadingOneImage(cameraRef);
        Assertions.assertTrue(isOn, "Expected lived view ON");
    }

    @Test
    void getLiveViewImage() throws InterruptedException {
        final BufferedImage liveViewImage = liveViewLogic().getLiveViewImage(cameraRef);
        Assertions.assertNotNull(liveViewImage);
    }

    @Test
    void getLiveViewImageBuffer() throws InterruptedException {
        final byte[] liveViewImageBuffer = liveViewLogic().getLiveViewImageBuffer(cameraRef);
        Assertions.assertNotNull(liveViewImageBuffer);
    }

    @Test
    void getLiveViewImageReference() throws InterruptedException {
        final LiveViewReference liveViewImageReference = liveViewLogic().getLiveViewImageReference(cameraRef);
        Assertions.assertNotNull(liveViewImageReference);
    }
}
