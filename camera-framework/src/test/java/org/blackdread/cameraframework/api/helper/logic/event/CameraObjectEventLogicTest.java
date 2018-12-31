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
package org.blackdread.cameraframework.api.helper.logic.event;

import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.DllOnPath;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
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
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraObjectEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
@DllOnPath
class CameraObjectEventLogicTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraObjectListener cameraObjectListener;

    private CameraObjectListener cameraObjectListenerThrows;

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
        cameraObjectListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
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
        cameraObjectEventLogic().unregisterCameraObjectEvent(fakeCamera);
    }

    @Test
    void unregisterCameraObjectEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().unregisterCameraObjectEvent(null));
    }

    @Test
    void addCameraObjectListener() {
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        sendAndAssertCountEvent(2, 2);
    }

    @Test
    void addCameraObjectListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().addCameraObjectListener(null));
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void addCameraObjectListenerCamera() {
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        sendAndAssertCountEvent(2, 2);
    }

    @Test
    void addCameraObjectListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().addCameraObjectListener(null, cameraObjectListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().addCameraObjectListener(fakeCamera, null));
        sendAndAssertCountEvent(2, 0);
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
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void removeCameraObjectListener() {
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(cameraObjectListener);
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(cameraObjectListener);
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void removeCameraObjectListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().removeCameraObjectListener(null, cameraObjectListener));
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void removeCameraObjectListenerCamera() {
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, cameraObjectListener);
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void removeCameraObjectListenerCameraNonExisting() {
        cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(new EdsdkLibrary.EdsCameraRef(), cameraObjectListener);
        cameraObjectEventLogic().removeCameraObjectListener(new EdsdkLibrary.EdsCameraRef(), a -> {
        });
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void removeCameraObjectListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().removeCameraObjectListener(null, cameraObjectListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().removeCameraObjectListener(fakeCamera, null));
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void clearCameraObjectListeners() {
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().clearCameraObjectListeners();
        cameraObjectEventLogic().clearCameraObjectListeners();
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void clearCameraObjectListenersWithCamera() {
        cameraObjectEventLogic().clearCameraObjectListeners(fakeCamera);
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        cameraObjectEventLogic().clearCameraObjectListeners(fakeCamera);
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void clearCameraObjectListenersWithCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraObjectEventLogic().clearCameraObjectListeners(null));
        sendAndAssertCountEvent(2, 0);
    }

    @Test
    void listenerExceptionAreSuppressed() {
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListenerThrows);
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());
        sendAndAssertCountEvent(2, 3);
    }

    @Test
    void getNotifiedFromAnyCamera() {
        cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
        cameraObjectListener = null;
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
        cameraObjectEventLogic().addCameraObjectListener(fakeCamera, cameraObjectListener);
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(2)));
        Assertions.assertEquals(1, countEvent.get());
        cameraObjectEventLogic().addCameraObjectListener(new EdsdkLibrary.EdsCameraRef(new Pointer(1)), cameraObjectListener);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
    }


    private void createEvent(final EdsdkLibrary.EdsCameraRef camera) {
        try {
            final Method handleMethod = cameraObjectEventLogic().getClass().getDeclaredMethod("handle", CanonObjectEvent.class);
            final CanonObjectEventImpl event = new CanonObjectEventImpl(camera, EdsObjectEvent.kEdsObjectEvent_DirItemCreated, new EdsdkLibrary.EdsBaseRef());
            handleMethod.setAccessible(true);
            handleMethod.invoke(cameraObjectEventLogic(), event);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
        }
    }

    private void sendAndAssertCountEvent(final int countSendEvent,final int expectedCount) {
        for (int i = 0; i < countSendEvent; i++) {
            createEvent(fakeCamera);
        }
        Assertions.assertEquals(expectedCount, countEvent.get());
    }
}
