package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>Created on 2018/11/03.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
public class EventCameraTest {

    private static final Logger log = LoggerFactory.getLogger(EventCameraTest.class);

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
    }

    @AfterAll
    static void tearDownClass() {
        TestShortcutUtil.terminateLibrary();
    }

    @Test
    @Disabled("Only run manually")
    void cameraConnectedIsSeen() throws InterruptedException {
        // This test demonstrate how to get events from library
        final AtomicBoolean cameraEventCalled = new AtomicBoolean(false);

        TestShortcutUtil.registerCameraAddedHandler(inContext -> {
            log.warn("Camera added called {}", inContext);
            cameraEventCalled.set(true);
            return new NativeLong(0);
        });

        for (int i = 0; i < 100; i++) {
            Thread.sleep(50);
            CanonFactory.edsdkLibrary().EdsGetEvent();
        }

        Assertions.assertTrue(cameraEventCalled.get());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    @Disabled("Only run manually")
    void cameraConnectedIsSeenWithUser32() throws InterruptedException {
        // This test demonstrate how to get events from library using User32 but better to use EdsGetEvent of library
        final User32 lib = User32.INSTANCE;

        final AtomicBoolean cameraEventCalled = new AtomicBoolean(false);

        TestShortcutUtil.registerCameraAddedHandler(inContext -> {
            log.warn("Camera added called {}", inContext);
            cameraEventCalled.set(true);
            return new NativeLong(0);
        });

        final WinUser.MSG msg = new WinUser.MSG();

        for (int i = 0; i < 100; i++) {
            Thread.sleep(50);
            final boolean hasMessage = lib.PeekMessage(msg, null, 0, 0, 1);
            if (hasMessage) {
                log.warn("Had message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }

        Assertions.assertTrue(cameraEventCalled.get());
    }

}
