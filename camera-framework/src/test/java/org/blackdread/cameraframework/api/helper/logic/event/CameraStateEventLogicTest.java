/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
import org.blackdread.cameraframework.api.constant.EdsStateEvent;
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
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraStateEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
@DllOnPath
class CameraStateEventLogicTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraStateListener cameraStateListener;

    private CameraStateListener cameraStateListenerThrows;

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
        cameraStateListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
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
        cameraStateEventLogic().unregisterCameraStateEvent(fakeCamera);
    }

    @Test
    void unregisterCameraStateEventWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().unregisterCameraStateEvent(null));
    }

    @Test
    void addCameraStateListener() {
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
        sendAndAssertCountEvent(3,3);
    }

    @Test
    void addCameraStateListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().addCameraStateListener(null));
        sendAndAssertCountEvent(5,0);
    }

    @Test
    void addCameraStateListenerCamera() {
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        sendAndAssertCountEvent(3,3);
    }

    @Test
    void addCameraStateListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().addCameraStateListener(null, cameraStateListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().addCameraStateListener(fakeCamera, null));
        sendAndAssertCountEvent(5,0);
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
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraStateListener() {
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(cameraStateListener);
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(cameraStateListener);
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraStateListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().removeCameraStateListener(null, cameraStateListener));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraStateListenerCamera() {
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(fakeCamera, cameraStateListener);
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraStateListenerCameraNonExisting() {
        cameraStateEventLogic().removeCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(new EdsdkLibrary.EdsCameraRef(), cameraStateListener);
        cameraStateEventLogic().removeCameraStateListener(new EdsdkLibrary.EdsCameraRef(), a -> {
        });
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraStateListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().removeCameraStateListener(null, cameraStateListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().removeCameraStateListener(fakeCamera, null));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void clearCameraStateListeners() {
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().clearCameraStateListeners();
        cameraStateEventLogic().clearCameraStateListeners();
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void clearCameraStateListenersWithCamera() {
        cameraStateEventLogic().clearCameraStateListeners(fakeCamera);
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        cameraStateEventLogic().clearCameraStateListeners(fakeCamera);
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void clearCameraStateListenersWithCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraStateEventLogic().clearCameraStateListeners(null));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void listenerExceptionAreSuppressed() {
        cameraStateEventLogic().addCameraStateListener(cameraStateListenerThrows);
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());
        sendAndAssertCountEvent(2,3);
    }

    @Test
    void getNotifiedFromAnyCamera() {
        cameraStateEventLogic().addCameraStateListener(cameraStateListener);
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
        cameraStateListener = null;
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
        cameraStateEventLogic().addCameraStateListener(fakeCamera, cameraStateListener);
        createEvent(fakeCamera);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(2)));
        Assertions.assertEquals(1, countEvent.get());
        cameraStateEventLogic().addCameraStateListener(new EdsdkLibrary.EdsCameraRef(new Pointer(1)), cameraStateListener);
        createEvent(new EdsdkLibrary.EdsCameraRef(new Pointer(1)));
        Assertions.assertEquals(2, countEvent.get());
    }


    private void createEvent(final EdsdkLibrary.EdsCameraRef camera) {
        try {
            final Method handleMethod = cameraStateEventLogic().getClass().getDeclaredMethod("handle", CanonStateEvent.class);
            final CanonStateEventImpl event = new CanonStateEventImpl(camera, EdsStateEvent.kEdsStateEvent_WillSoonShutDown, 0);
            handleMethod.setAccessible(true);
            handleMethod.invoke(cameraStateEventLogic(), event);
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
