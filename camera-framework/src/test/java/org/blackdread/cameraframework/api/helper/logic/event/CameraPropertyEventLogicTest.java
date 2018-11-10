package org.blackdread.cameraframework.api.helper.logic.event;

import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private CameraPropertyListener cameraPropertyListenerThrows;

    private final AtomicInteger countEvent = new AtomicInteger(0);

    @BeforeAll
    static void setUpClass() {
//        TestShortcutUtil.initLibrary();
    }

    @AfterAll
    static void tearDownClass() {
//        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
        fakeCamera = new EdsdkLibrary.EdsCameraRef();
        cameraPropertyListener = (event) -> countEvent.incrementAndGet();
        cameraPropertyListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
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
        cameraPropertyEventLogic().unregisterCameraPropertyEvent(fakeCamera);
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

    @Test
    void listenerExceptionAreSuppressed() {
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListenerThrows);
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());
    }

    @Test
    void getNotifiedFromAnyCamera() {
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
        cameraPropertyListener = null;
        for (int i = 0; i < 10; i++) {
            System.gc();
            sleep(10);
        }
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
    }

    @Test
    void getNotifiedFromSpecificCamera() {
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(2)));
        Assertions.assertEquals(1, countEvent.get());
        cameraPropertyEventLogic().addCameraPropertyListener(new EdsdkLibrary.EdsCameraRef(new Pointer(1)), cameraPropertyListener);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
    }


    private void createEvent(final EdsdkLibrary.EdsCameraRef camera) {
        try {
            final Method handleMethod = cameraPropertyEventLogic().getClass().getDeclaredMethod("handle", CanonPropertyEvent.class);
            final CanonPropertyEventImpl event = new CanonPropertyEventImpl(camera, EdsPropertyEvent.kEdsPropertyEvent_PropertyChanged, EdsPropertyID.kEdsPropID_ISOSpeed, 0);
            handleMethod.setAccessible(true);
            handleMethod.invoke(cameraPropertyEventLogic(), event);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
        }
    }
}
