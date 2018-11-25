package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.api.TestShortcutUtil.getEvents;
import static org.blackdread.cameraframework.api.TestUtil.sleep;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.*;

/**
 * Manual test for shoot with camera
 * <p>Created on 2018/10/22.<p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class ShootCameraTest {

    private static final Logger log = LoggerFactory.getLogger(ShootCameraTest.class);

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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled("Only run manually")
    void shootWithoutPausingLiveView() {
        final EdsdkLibrary.EdsCameraRef cameraRef = camera.getValue();
        TestShortcutUtil.registerObjectEventHandler(cameraRef, (inEvent, inRef, inContext) -> {
            log.warn("Camera object called {}, {}, {}", inEvent, inRef, inContext);
            return new NativeLong(0);
        });
        TestShortcutUtil.registerPropertyEventHandler(cameraRef, (inEvent, inPropertyID, inParam, inContext) -> {
            log.warn("Camera property called {}, {}, {}", EdsPropertyEvent.ofValue(inEvent.intValue()), EdsPropertyID.ofValue(inPropertyID.intValue()), inContext);
            return new NativeLong(0);
        });
        liveViewLogic().beginLiveView(cameraRef);

        // If not sleep then will get busy

        getEvents();
        sleep(1500);
        getEvents();

        cameraLogic().setCapacity(cameraRef);
        sleep(100);

//        shootLogic().shootV0(cameraRef);
        shootLogic().shootNoAF(cameraRef);
//        shootLogic().shootAF(cameraRef);
        getEvents();
        sleep(1400);

        liveViewLogic().endLiveView(cameraRef);
        getEvents();
    }

}
