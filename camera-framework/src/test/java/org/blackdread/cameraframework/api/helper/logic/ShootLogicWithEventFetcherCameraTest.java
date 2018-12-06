package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
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

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.*;

/**
 * <p>Created on 2018/11/15.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class ShootLogicWithEventFetcherCameraTest {

    private static final Logger log = LoggerFactory.getLogger(ShootLogicWithEventFetcherCameraTest.class);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    @BeforeAll
    static void setUpClass() throws InterruptedException {
    }

    @AfterAll
    static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled("Only run manually")
    @Test
    void testShootWithLogic() throws InterruptedException, ExecutionException, TimeoutException {
        /*
         * Edsdk has issues for events if it is not the thread that initialized that fetch but it does not matter for executing commands which thread is used...
         */

        eventFetcherLogic().start();

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

        final List<File> files = future.get(20, TimeUnit.SECONDS);
        log.info("Files: {}", files);

        Assertions.assertNotNull(files);
        Assertions.assertTrue(files.size() > 0);

        try {
            TestShortcutUtil.closeSession(camera);
        } finally {
            ReleaseUtil.release(camera);
        }

        eventFetcherLogic().stop();
    }

}
