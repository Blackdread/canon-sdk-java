package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsSaveTo;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.blackdread.cameraframework.api.TestShortcutUtil.getEvents;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/11/15.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class ShootLogicCameraTest {

    private static final Logger log = LoggerFactory.getLogger(ShootLogicCameraTest.class);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    @BeforeAll
    static void setUpClass() throws InterruptedException {
        Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
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
        Thread.sleep(100);
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled("Only run manually")
    @Test
    void testShoot() throws InterruptedException {
        log.warn("Camera: {} , {}, {}, {}", camera, camera.getPointer(), camera.getValue(), camera.getValue().getPointer());
        final EdsdkLibrary.EdsObjectEventHandler eventHandler = (inEvent, inRef, inContext) -> {
            log.warn("event {}, {}, {}", EdsObjectEvent.ofValue(inEvent.intValue()), inRef, inRef.getPointer());
            CanonFactory.edsdkLibrary().EdsDownloadCancel(new EdsDirectoryItemRef(inRef.getPointer()));
            return new NativeLong(0);
        };
        TestShortcutUtil.registerObjectEventHandler(camera.getValue(), eventHandler);

        CanonFactory.propertySetLogic().setPropertyData(camera.getValue(), EdsPropertyID.kEdsPropID_SaveTo, EdsSaveTo.kEdsSaveTo_Host);

        final AtomicBoolean init = new AtomicBoolean(false);
        for (int i = 0; i < 2; i++) {
            CanonFactory.cameraLogic().setCapacity(camera.getValue());

            final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsSendCommand(camera.getValue(), new NativeLong(EdsCameraCommand.kEdsCameraCommand_TakePicture.value()), new NativeLong(0)));

            log.warn("Error: {}", error);

            getEvents();
            // if too quick, camera will return error of busy
            Thread.sleep(800);
        }

        for (int i = 0; i < 50; i++) {
            CanonFactory.cameraLogic().setCapacity(camera.getValue());
            final Thread run_in = new Thread(() -> {
                if (!init.get()) {
                    init.set(true);
                    Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
                }
                log.warn("Run in");
                CanonFactory.edsdkLibrary().EdsSendCommand(camera.getValue(), new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value()), new NativeLong(EdsdkLibrary.EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely));
                log.warn("Middle Run in");
                CanonFactory.edsdkLibrary().EdsSendCommand(camera.getValue(), new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value()), new NativeLong(EdsdkLibrary.EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF));
                Ole32.INSTANCE.CoUninitialize();
                log.warn("End Run in");
            });
            run_in.setDaemon(true);
            run_in.start();
//        run_in.join();

            final Thread run_in2 = new Thread(() -> {
                Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
                log.warn("Run in 2");
                getEvents();
                getEvents();
                log.warn("end Run in 2");
                Ole32.INSTANCE.CoUninitialize();
            });
            run_in2.setDaemon(true);
            run_in2.start();

            Thread.sleep(10000);
            log.warn("End sleep");

            // only here I shoot from threads will happen... why...
            getEvents();

            Thread.sleep(1000);

            CanonFactory.edsdkLibrary().EdsSendCommand(camera.getValue(), new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value()), new NativeLong(EdsdkLibrary.EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF));
            CanonFactory.edsdkLibrary().EdsSendCommand(camera.getValue(), new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value()), new NativeLong(EdsdkLibrary.EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF));

            getEvents();
            Thread.sleep(1000);

            CanonFactory.edsdkLibrary().EdsSendCommand(camera.getValue(), new NativeLong(EdsCameraCommand.kEdsCameraCommand_TakePicture.value()), new NativeLong(0));

            getEvents();
            Thread.sleep(2000);
        }
    }

}
