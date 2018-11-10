package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

import static org.blackdread.cameraframework.api.TestUtil.sleep;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraPropertyEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
class CameraPropertyEventLogicTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraPropertyListener cameraPropertyListener;

    private final AtomicInteger countEvent = new AtomicInteger(0);

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
    }

    @AfterAll
    static void tearDownClass() {
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
        fakeCamera = new EdsdkLibrary.EdsCameraRef();
        cameraPropertyListener = (event) -> countEvent.incrementAndGet();
    }

    @AfterEach
    void tearDown() {
        cameraPropertyEventLogic().clearCameraPropertyListeners();
    }

    @Test
    void registerCameraPropertyEvent() {
        cameraPropertyEventLogic().registerCameraPropertyEvent(fakeCamera);
    }

    @Test
    void registerCameraPropertyEventUseWeakReference() {
        final WeakReference<EdsdkLibrary.EdsCameraRef> weakReference = new WeakReference<>(fakeCamera);
        cameraPropertyEventLogic().registerCameraPropertyEvent(fakeCamera);
        fakeCamera = null;

        int i = 0;
        while (weakReference.get() != null && i < 100) {
            System.gc();
            sleep(10);
            i++;
        }

        Assertions.assertNull(weakReference.get(), "cameraPropertyEventLogic is holding a strong ref to camera");
    }

    @Test
    void registerCameraPropertyEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().registerCameraPropertyEvent(null));
    }

    @Test
    void unregisterCameraPropertyEvent() {
        cameraPropertyEventLogic().registerCameraPropertyEvent(fakeCamera);
    }

    @Test
    void unregisterCameraPropertyEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().unregisterCameraPropertyEvent(null));
    }

    @Test
    void addCameraPropertyListener() {
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
    }

    @Test
    void addCameraPropertyListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().addCameraPropertyListener(null));
    }

    @Test
    void addCameraPropertyListenerCamera() {
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
    }

    @Test
    void addCameraPropertyListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().addCameraPropertyListener(null, cameraPropertyListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, null));
    }


    @Test
    void addCameraPropertyListenerUseWeakReference() {
        final WeakReference<CameraPropertyListener> weakReference = new WeakReference<>(cameraPropertyListener);
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyListener = null;

        int i = 0;
        while (weakReference.get() != null && i < 100) {
            System.gc();
            sleep(10);
            i++;
        }

        Assertions.assertNull(weakReference.get(), "cameraPropertyEventLogic is holding a strong ref to listener");
    }

    @Test
    void removeCameraPropertyListener() {
        cameraPropertyEventLogic().removeCameraPropertyListener(cameraPropertyListener);
    }

    @Test
    void removeCameraPropertyListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().removeCameraPropertyListener(null, cameraPropertyListener));
    }

    @Test
    void removeCameraPropertyListenerCamera() {
        cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
    }

    @Test
    void removeCameraPropertyListenerCameraNonExisting() {
        cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(new EdsdkLibrary.EdsCameraRef(), cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(new EdsdkLibrary.EdsCameraRef(), a -> {
        });
    }

    @Test
    void removeCameraPropertyListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().removeCameraPropertyListener(null, cameraPropertyListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, null));
    }

    @Test
    void clearCameraPropertyListeners() {
        cameraPropertyEventLogic().clearCameraPropertyListeners();
    }

    @Test
    void clearCameraPropertyListenersWithCamera() {
        cameraPropertyEventLogic().clearCameraPropertyListeners(fakeCamera);
    }

    @Test
    void clearCameraPropertyListenersWithCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().clearCameraPropertyListeners(null));
    }
}
