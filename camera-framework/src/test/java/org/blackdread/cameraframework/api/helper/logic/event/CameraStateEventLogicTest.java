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
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraStateEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
class CameraStateEventLogicTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraStateListener cameraStateListener;

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
        cameraStateListener = (event) -> countEvent.incrementAndGet();
    }

    @AfterEach
    void tearDown() {
        cameraStateEventLogic().clearCameraStateListeners();
    }

    @Test
    void registerCameraStateEvent() {
        cameraStateEventLogic().registerCameraStateEvent(fakeCamera);
    }

    @Test
    void registerCameraStateEventUseWeakReference() {
        final WeakReference<EdsdkLibrary.EdsCameraRef> weakReference = new WeakReference<>(fakeCamera);
        cameraStateEventLogic().registerCameraStateEvent(fakeCamera);
        fakeCamera = null;

        int i = 0;
        while (weakReference.get() != null && i < 100) {
            System.gc();
            sleep(10);
            i++;
        }

        Assertions.assertNull(weakReference.get(), "cameraStateEventLogic is holding a strong ref to camera");
    }

    @Test
    void registerCameraStateEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().registerCameraStateEvent(null));
    }

    @Test
    void unregisterCameraStateEvent() {
        cameraStateEventLogic().registerCameraStateEvent(fakeCamera);
    }

    @Test
    void unregisterCameraStateEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().unregisterCameraStateEvent(null));
    }

    @Test
    void addCameraStateListener() {
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
    }

    @Test
    void addCameraStateListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().addCameraStateListener(null));
    }

    @Test
    void addCameraStateListenerCamera() {
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
    }

    @Test
    void addCameraStateListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().addCameraStateListener(null, cameraStateListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().addCameraStateListener(fakeCamera, null));
    }


    @Test
    void addCameraStateListenerUseWeakReference() {
        final WeakReference<CameraStateListener> weakReference = new WeakReference<>(cameraStateListener);
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateListener = null;

        int i = 0;
        while (weakReference.get() != null && i < 100) {
            System.gc();
            sleep(10);
            i++;
        }

        Assertions.assertNull(weakReference.get(), "cameraStateEventLogic is holding a strong ref to listener");
    }

    @Test
    void removeCameraStateListener() {
        cameraStateEventLogic().removeCameraStateListener(cameraStateListener);
    }

    @Test
    void removeCameraStateListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().removeCameraStateListener(null, cameraStateListener));
    }

    @Test
    void removeCameraStateListenerCamera() {
        cameraStateEventLogic().removeCameraStateListener(fakeCamera, cameraStateListener);
    }

    @Test
    void removeCameraStateListenerCameraNonExisting() {
        cameraStateEventLogic().removeCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(new EdsdkLibrary.EdsCameraRef(), cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(new EdsdkLibrary.EdsCameraRef(), a -> {
        });
    }

    @Test
    void removeCameraStateListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().removeCameraStateListener(null, cameraStateListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().removeCameraStateListener(fakeCamera, null));
    }

    @Test
    void clearCameraStateListeners() {
        cameraStateEventLogic().clearCameraStateListeners();
    }

    @Test
    void clearCameraStateListenersWithCamera() {
        cameraStateEventLogic().clearCameraStateListeners(fakeCamera);
    }

    @Test
    void clearCameraStateListenersWithCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().clearCameraStateListeners(null));
    }
}
