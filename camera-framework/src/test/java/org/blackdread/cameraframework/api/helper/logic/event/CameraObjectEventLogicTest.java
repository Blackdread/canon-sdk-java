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
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraObjectEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
class CameraObjectEventLogicTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraObjectListener cameraObjectListener;

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
        cameraObjectListener = (event) -> countEvent.incrementAndGet();
    }

    @AfterEach
    void tearDown() {
        cameraObjectEventLogic().clearCameraObjectListeners();
    }

    @Test
    void registerCameraObjectEvent() {
        cameraObjectEventLogic().registerCameraObjectEvent(fakeCamera);
    }

    @Test
    void registerCameraObjectEventUseWeakReference() {
        final WeakReference<EdsdkLibrary.EdsCameraRef> weakReference = new WeakReference<>(fakeCamera);
        cameraObjectEventLogic().registerCameraObjectEvent(fakeCamera);
        fakeCamera = null;

        int i = 0;
        while (weakReference.get() != null && i < 100) {
            System.gc();
            sleep(10);
            i++;
        }

        Assertions.assertNull(weakReference.get(), "cameraObjectEventLogic is holding a strong ref to camera");
    }

    @Test
    void registerCameraObjectEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().registerCameraObjectEvent(null));
    }

    @Test
    void unregisterCameraObjectEvent() {
        cameraObjectEventLogic().registerCameraObjectEvent(fakeCamera);
    }

    @Test
    void unregisterCameraObjectEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().unregisterCameraObjectEvent(null));
    }

    @Test
    void addCameraObjectListener() {
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
    }

    @Test
    void addCameraObjectListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().addCameraObjectListener(null));
    }

    @Test
    void addCameraObjectListenerCamera() {
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
    }

    @Test
    void addCameraObjectListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().addCameraObjectListener(null, cameraObjectListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().addCameraObjectListener(fakeCamera, null));
    }


    @Test
    void addCameraObjectListenerUseWeakReference() {
        final WeakReference<CameraObjectListener> weakReference = new WeakReference<>(cameraObjectListener);
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectListener = null;

        int i = 0;
        while (weakReference.get() != null && i < 100) {
            System.gc();
            sleep(10);
            i++;
        }

        Assertions.assertNull(weakReference.get(), "cameraObjectEventLogic is holding a strong ref to listener");
    }

    @Test
    void removeCameraObjectListener() {
        cameraObjectEventLogic().removeCameraObjectListener(cameraObjectListener);
    }

    @Test
    void removeCameraObjectListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().removeCameraObjectListener(null, cameraObjectListener));
    }

    @Test
    void removeCameraObjectListenerCamera() {
        cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, cameraObjectListener);
    }

    @Test
    void removeCameraObjectListenerCameraNonExisting() {
        cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(new EdsdkLibrary.EdsCameraRef(), cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(new EdsdkLibrary.EdsCameraRef(), a -> {
        });
    }

    @Test
    void removeCameraObjectListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().removeCameraObjectListener(null, cameraObjectListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, null));
    }

    @Test
    void clearCameraObjectListeners() {
        cameraObjectEventLogic().clearCameraObjectListeners();
    }

    @Test
    void clearCameraObjectListenersWithCamera() {
        cameraObjectEventLogic().clearCameraObjectListeners(fakeCamera);
    }

    @Test
    void clearCameraObjectListenersWithCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().clearCameraObjectListeners(null));
    }
}
