package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.liveViewLogic;

/**
 * <p>Created on 2018/10/22.<p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class LiveViewLogicCameraTest {

    static EdsdkLibrary edsdkLibrary;

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
    }

    @AfterAll
    static void tearDownClass() {
        TestShortcutUtil.closeSession(camera);
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void beginLiveView() throws InterruptedException {
        liveViewLogic().beginLiveView(camera.getValue(), EdsEvfOutputDevice.kEdsEvfOutputDevice_PC);

        Thread.sleep(10000);

        liveViewLogic().endLiveView(camera.getValue());
    }

    @Test
    void endLiveView() {
        liveViewLogic().enableLiveView(camera.getValue());
        liveViewLogic().endLiveView(camera.getValue());
    }

    @Test
    void endLiveViewFailsIfNotEnabledFirst() {
        try {
            liveViewLogic().endLiveView(camera.getValue());
        } catch (EdsdkErrorException ex) {
            Assertions.assertEquals(EdsdkError.EDS_ERR_DEVICE_BUSY, ex.getEdsdkError());
            return;
        }
        Assertions.fail("Should have thrown");
    }

    @Test
    void liveViewEnabledAfterBeginLiveView() {
        liveViewLogic().enableLiveView(camera.getValue());
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(camera.getValue());
        Assertions.assertTrue(liveViewEnabled, "Live view mode should be enabled");
        liveViewLogic().endLiveView(camera.getValue());
    }

    @Test
    void liveViewDisabledAfterBeginAndEndLiveView() {
        liveViewLogic().beginLiveView(camera.getValue());
        liveViewLogic().endLiveView(camera.getValue());
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(camera.getValue());
        Assertions.assertFalse(liveViewEnabled, "Live view mode should be disabled");
    }

    @Test
    void isLiveViewEnabledTrueWhenEnabled() {
        liveViewLogic().enableLiveView(camera.getValue());
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(camera.getValue());
        Assertions.assertTrue(liveViewEnabled, "Live view mode should be enabled");
    }

    @Test
    void isLiveViewEnabledFalseWhenDisabled() {
        liveViewLogic().disableLiveView(camera.getValue());
        final boolean liveViewEnabled = liveViewLogic().isLiveViewEnabled(camera.getValue());
        Assertions.assertFalse(liveViewEnabled, "Live view mode should be disabled");
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImage() {
    }

    @Test
    void getLiveViewImageReference() {
    }

    @Test
    void getLiveViewImage() {
    }

    @Test
    void getLiveViewImageBuffer() {
    }
}
