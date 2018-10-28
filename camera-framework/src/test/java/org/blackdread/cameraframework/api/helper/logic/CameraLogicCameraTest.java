package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraLogic;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class CameraLogicCameraTest {

    private static final Logger log = LoggerFactory.getLogger(CameraLogicCameraTest.class);

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
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setCapacity() {
        cameraLogic().setCapacity(camera.getValue());
    }

    @Test
    void setCapacityWithCapacity() {
        cameraLogic().setCapacity(camera.getValue(), 10000);
        cameraLogic().setCapacity(camera.getValue(), 1000);
    }

    @Test
    void setCapacityWithBytesPerSector() {
        cameraLogic().setCapacity(camera.getValue(), 10000, 256);
        cameraLogic().setCapacity(camera.getValue(), 10000, 4096);
    }

    @Test
    void isMirrorLockupEnabled() {
        try {
            final boolean mirrorLockupEnabled = cameraLogic().isMirrorLockupEnabled(camera.getValue());
            log.info("mirrorLockupEnabled was {}", mirrorLockupEnabled);
        } catch (EdsdkErrorException ex) {
            log.warn("Might not be supported", ex);
        }
    }
}
