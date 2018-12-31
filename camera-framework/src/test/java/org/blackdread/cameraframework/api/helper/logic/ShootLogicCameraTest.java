/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsSaveTo;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.blackdread.cameraframework.api.TestShortcutUtil.getEvents;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.*;
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


    @Disabled("Only run manually")
    @Test
    void testShootWithShortcut() throws InterruptedException, ExecutionException {
        final EdsdkLibrary.EdsCameraRef cameraRef = camera.getValue();
        cameraObjectEventLogic().registerCameraObjectEvent(cameraRef);

        final CameraObjectListener cameraObjectListener = event -> {
            log.warn("Got event: {}", event);
        };
        cameraObjectEventLogic().addCameraObjectListener(cameraRef, cameraObjectListener);

        final CompletableFuture<List<File>> future = CompletableFuture.supplyAsync(() -> {
            try {
                return shootLogic().shoot(cameraRef);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        getEvents();
        getEvents();
        getEvents();
        getEvents();

        final List<File> files = future.get();
        log.info("Files: {}", files);

        Assertions.assertNotNull(files);
        Assertions.assertTrue(files.size() > 0);
    }

    @Disabled("Only run manually")
    @Test
    void testShootWithSelfFetched() throws InterruptedException {
        final EdsdkLibrary.EdsCameraRef cameraRef = camera.getValue();
        cameraObjectEventLogic().registerCameraObjectEvent(cameraRef);

        final CameraObjectListener cameraObjectListener = event -> {
            log.warn("Got event: {}", event);
        };
        cameraObjectEventLogic().addCameraObjectListener(cameraRef, cameraObjectListener);

        final ShootOption shootOption = new ShootOptionBuilder()
            .setFetchEvents(true)
            .build();

        final List<File> files = shootLogic().shoot(cameraRef, shootOption);

        log.info("Files: {}", files);

        Assertions.assertNotNull(files);
        Assertions.assertTrue(files.size() > 0);
    }

    @Disabled("Only run manually")
    @Test
    void testShootWithLogic() throws InterruptedException, ExecutionException, TimeoutException {
        // Since EventFetcherLogic on start will init the SDK, I need to terminate and let start
        // Doing so will make this test work but see further comment that says it would hang forever otherwise
        TestShortcutUtil.terminateLibrary();
        eventFetcherLogic().start();

        Thread.sleep(100);

        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);

        final EdsdkLibrary.EdsCameraRef cameraRef = camera.getValue();
        cameraObjectEventLogic().registerCameraObjectEvent(cameraRef);

        final CameraObjectListener cameraObjectListener = event -> {
            log.warn("Got event: {}", event);
        };
        cameraObjectEventLogic().addCameraObjectListener(cameraRef, cameraObjectListener);

        final CompletableFuture<List<File>> future = CompletableFuture.supplyAsync(() -> {
            try {
                return shootLogic().shoot(cameraRef);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // When we do not initialize the SDK in the EventFetcherLogic then it hangs forever as library was initialized by the main thread... and not the event fetcher
        final List<File> files = future.get(20, TimeUnit.SECONDS);
        log.info("Files: {}", files);

        Assertions.assertNotNull(files);
        Assertions.assertTrue(files.size() > 0);

    }

}
