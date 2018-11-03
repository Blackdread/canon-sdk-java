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

    @BeforeAll
    static void setUpClass() throws InterruptedException {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);

        liveViewLogic().beginLiveView(camera.getValue(), EdsEvfOutputDevice.kEdsEvfOutputDevice_PC);
        Thread.sleep(4000);
    }

    @AfterAll
    static void tearDownClass() {
        liveViewLogic().endLiveView(camera.getValue());
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
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(camera.getValue());
        Assertions.assertTrue(liveViewEnabled, "Live view mode should be enabled");
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImage() throws InterruptedException {
        final boolean isOn = liveViewLogic().isLiveViewEnabledByDownloadingOneImage(camera.getValue());
        Assertions.assertTrue(isOn, "Expected lived view ON");
    }

    @Test
    void getLiveViewImage() throws InterruptedException {
        final BufferedImage liveViewImage = liveViewLogic().getLiveViewImage(camera.getValue());
        Assertions.assertNotNull(liveViewImage);
    }

    @Test
    void getLiveViewImageBuffer() throws InterruptedException {
        final byte[] liveViewImageBuffer = liveViewLogic().getLiveViewImageBuffer(camera.getValue());
        Assertions.assertNotNull(liveViewImageBuffer);
    }

    @Test
    void getLiveViewImageReference() throws InterruptedException {
        final LiveViewReference liveViewImageReference = liveViewLogic().getLiveViewImageReference(camera.getValue());
        Assertions.assertNotNull(liveViewImageReference);
    }
}
