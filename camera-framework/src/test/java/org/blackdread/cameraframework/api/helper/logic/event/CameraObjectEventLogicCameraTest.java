package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraObjectEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class CameraObjectEventLogicCameraTest {

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    private CameraObjectListener cameraObjectListener;

    private final AtomicInteger countEvent = new AtomicInteger(0);

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
        cameraObjectEventLogic().registerCameraObjectEvent(camera.getValue());
        cameraObjectListener = (event) -> countEvent.incrementAndGet();
    }

    @AfterEach
    void tearDown() {
        cameraObjectEventLogic().unregisterCameraObjectEvent(camera.getValue());
        countEvent.set(0);
        cameraObjectEventLogic().clearCameraObjectListeners();
    }

    @Test
    void getNotifiedWhileRegisteringForAnyCamera() {
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);

        // TODO

        Assertions.assertEquals(1, countEvent.get());
    }

    @Test
    void getNotifiedWhileRegisteringForSpecificCamera() {
        cameraObjectEventLogic().addCameraObjectListener(camera.getValue(), cameraObjectListener);

        // TODO

        Assertions.assertEquals(1, countEvent.get());
    }

}
