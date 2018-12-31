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
@DllOnPath
class CameraPropertyEventLogicTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraPropertyListener cameraPropertyListener;

    private CameraPropertyListener cameraPropertyListenerThrows;

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
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
        sendAndAssertCountEvent(2,2);
    }

    @Test
    void addCameraPropertyListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().addCameraPropertyListener(null));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void addCameraPropertyListenerCamera() {
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        sendAndAssertCountEvent(2,2);
    }

    @Test
    void addCameraPropertyListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().addCameraPropertyListener(null, cameraPropertyListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, null));
        sendAndAssertCountEvent(2,0);
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
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraPropertyListener() {
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(cameraPropertyListener);
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(cameraPropertyListener);
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraPropertyListenerWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().removeCameraPropertyListener(null, cameraPropertyListener));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraPropertyListenerCamera() {
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraPropertyListenerCameraNonExisting() {
        cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(new EdsdkLibrary.EdsCameraRef(), cameraPropertyListener);
        cameraPropertyEventLogic().removeCameraPropertyListener(new EdsdkLibrary.EdsCameraRef(), a -> {
        });
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void removeCameraPropertyListenerCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().removeCameraPropertyListener(null, cameraPropertyListener));
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().removeCameraPropertyListener(fakeCamera, null));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void clearCameraPropertyListeners() {
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().clearCameraPropertyListeners();
        cameraPropertyEventLogic().clearCameraPropertyListeners();
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void clearCameraPropertyListenersWithCamera() {
        cameraPropertyEventLogic().clearCameraPropertyListeners(fakeCamera);
        cameraPropertyEventLogic().addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        cameraPropertyEventLogic().clearCameraPropertyListeners(fakeCamera);
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void clearCameraPropertyListenersWithCameraWithNullThrows() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraPropertyEventLogic().clearCameraPropertyListeners(null));
        sendAndAssertCountEvent(2,0);
    }

    @Test
    void listenerExceptionAreSuppressed() {
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListenerThrows);
        cameraPropertyEventLogic().addCameraPropertyListener(cameraPropertyListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());
        sendAndAssertCountEvent(2,3);
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

    private void sendAndAssertCountEvent(final int countSendEvent,final int expectedCount) {
        for (int i = 0; i < countSendEvent; i++) {
            createEvent(fakeCamera);
        }
        Assertions.assertEquals(expectedCount, countEvent.get());
    }
}
