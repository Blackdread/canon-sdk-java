package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
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

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
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
    void isLiveViewEnabledByDownloadingOneImageDoesNotThrow() {
        final boolean isOn = liveViewLogic().isLiveViewEnabledByDownloadingOneImage(camera.getValue());
        Assertions.assertFalse(isOn, "Expected lived view off");
    }

    @Test
    void getLiveViewImageThrowsIfNotRunning() {
        Assertions.assertThrows(EdsdkErrorException.class, () -> liveViewLogic().getLiveViewImage(camera.getValue()));
    }

    @Test
    void getLiveViewImageBufferThrowsIfNotRunning() {
        Assertions.assertThrows(EdsdkErrorException.class, () -> liveViewLogic().getLiveViewImageBuffer(camera.getValue()));
    }

    @Test
    void getLiveViewImageReferenceThrowsIfNotRunning() {
        Assertions.assertThrows(EdsdkErrorException.class, () -> liveViewLogic().getLiveViewImageReference(camera.getValue()));
    }
}
