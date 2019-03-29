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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonPropertyEvent;
import org.blackdread.cameraframework.api.helper.logic.event.CanonPropertyEventImpl;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/29.</p>
 *
 * @author Yoann CAPLAIN
 */
class CameraPropertyEventLogicDefaultMockTest extends AbstractMockTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraPropertyListener cameraPropertyListener;

    private CameraPropertyListener cameraPropertyListenerThrows;

    private final AtomicInteger countEvent = new AtomicInteger(0);

    private CameraPropertyEventLogic spyCameraPropertyEventLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsdkLibrary.EdsCameraRef();
        cameraPropertyListener = (event) -> countEvent.incrementAndGet();
        cameraPropertyListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };

        spyCameraPropertyEventLogic = Mockito.spy(MockFactory.initialCanonFactory.getCameraPropertyEventLogic());
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
        spyCameraPropertyEventLogic.clearCameraPropertyListeners();
    }

    @Test
    void handleEvent() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());

        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(3, countEvent.get());
    }

    @Test
    void handleEventIgnoreException() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());

        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(3, countEvent.get());
    }

    @Test
    void registerCameraPropertyEvent() {
        when(edsdkLibrary().EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraPropertyEventLogic.registerCameraPropertyEvent(fakeCamera);

        verify(edsdkLibrary(), times(1)).EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void registerCameraPropertyEventThrowsOnError() {
        when(edsdkLibrary().EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraPropertyEventLogic.registerCameraPropertyEvent(fakeCamera));

        verify(edsdkLibrary(), times(1)).EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void unregisterCameraPropertyEvent() {
        when(edsdkLibrary().EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraPropertyEventLogic.unregisterCameraPropertyEvent(fakeCamera);

        verify(edsdkLibrary(), times(1)).EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void unregisterCameraPropertyEventThrowsOnError() {
        when(edsdkLibrary().EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraPropertyEventLogic.unregisterCameraPropertyEvent(fakeCamera));

        verify(edsdkLibrary(), times(1)).EdsSetPropertyEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void addCameraPropertyListener() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);

        final List<WeakReference<CameraPropertyListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraPropertyListener, listeners.get(0).get());
        Assertions.assertEquals(cameraPropertyListenerThrows, listeners.get(1).get());

        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void addCameraPropertyListenerIgnoreDuplicate() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);

        final List<WeakReference<CameraPropertyListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraPropertyListener, listeners.get(0).get());
        Assertions.assertEquals(cameraPropertyListenerThrows, listeners.get(1).get());

        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void addCameraPropertyListenerSpecificCamera() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);

        final List<WeakReference<CameraPropertyListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraPropertyListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(2, cameraListeners.size());

        Assertions.assertEquals(cameraPropertyListener, cameraListeners.get(0).get());
        Assertions.assertEquals(cameraPropertyListenerThrows, cameraListeners.get(1).get());
    }

    @Test
    void addCameraPropertyListenerSpecificCameraIgnoreDuplicate() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);

        final List<WeakReference<CameraPropertyListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraPropertyListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(2, cameraListeners.size());

        Assertions.assertEquals(cameraPropertyListener, cameraListeners.get(0).get());
        Assertions.assertEquals(cameraPropertyListenerThrows, cameraListeners.get(1).get());
    }

    @Test
    void removeCameraPropertyListener() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);

        spyCameraPropertyEventLogic.removeCameraPropertyListener(cameraPropertyListener);

        final List<WeakReference<CameraPropertyListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(1, listeners.size());
        Assertions.assertEquals(cameraPropertyListenerThrows, listeners.get(0).get());

        final List<WeakReference<CameraPropertyListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(0, cameraListeners.size());

    }

    @Test
    void removeCameraPropertyListenerIgnoreNotFound() {
        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraPropertyEventLogic.removeCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.removeCameraPropertyListener(cameraPropertyListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void removeCameraPropertyListenerSpecificCamera() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);

        spyCameraPropertyEventLogic.removeCameraPropertyListener(fakeCamera, cameraPropertyListener);

        final List<WeakReference<CameraPropertyListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraPropertyListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(1, cameraListeners.size());

        Assertions.assertEquals(cameraPropertyListenerThrows, cameraListeners.get(0).get());
    }

    @Test
    void removeCameraPropertyListenerSpecificCameraIgnoreNotFound() {
        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraPropertyEventLogic.removeCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.removeCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void clearCameraPropertyListeners() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(cameraPropertyListenerThrows);

        Assertions.assertEquals(2, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraPropertyEventLogic.clearCameraPropertyListeners();

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void clearCameraPropertyListenersSpecificCamera() {
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListener);
        spyCameraPropertyEventLogic.addCameraPropertyListener(fakeCamera, cameraPropertyListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(2, getCameraListeners(fakeCamera).size());

        spyCameraPropertyEventLogic.clearCameraPropertyListeners(fakeCamera);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }


    private void createEvent(final EdsdkLibrary.EdsCameraRef camera) {
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getCameraPropertyEventLogic().getClass().getDeclaredMethod("handle", CanonPropertyEvent.class);
            final CanonPropertyEventImpl event = new CanonPropertyEventImpl(camera, EdsPropertyEvent.kEdsPropertyEvent_PropertyChanged, EdsPropertyID.kEdsPropID_ISOSpeed, 0L);
            handleMethod.setAccessible(true);
            handleMethod.invoke(MockFactory.initialCanonFactory.getCameraPropertyEventLogic(), event);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
        }
    }

    private void sendAndAssertCountEvent(final int countSendEvent, final int expectedCount) {
        for (int i = 0; i < countSendEvent; i++) {
            createEvent(fakeCamera);
        }
        Assertions.assertEquals(expectedCount, countEvent.get());
    }

    private List<WeakReference<CameraPropertyListener>> getAnyCameraListeners() {
        try {
            final Field anyCameraListeners = MockFactory.initialCanonFactory.getCameraPropertyEventLogic().getClass().getDeclaredField("anyCameraListeners");
            anyCameraListeners.setAccessible(true);
            return (List<WeakReference<CameraPropertyListener>>) anyCameraListeners.get(MockFactory.initialCanonFactory.getCameraPropertyEventLogic());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }
    }

    private List<WeakReference<CameraPropertyListener>> getCameraListeners(final EdsdkLibrary.EdsCameraRef cameraRef) {
        try {
            final Field listenersMap = MockFactory.initialCanonFactory.getCameraPropertyEventLogic().getClass().getDeclaredField("listenersMap");
            listenersMap.setAccessible(true);
            final WeakHashMap<EdsdkLibrary.EdsCameraRef, List<WeakReference<CameraPropertyListener>>> map = (WeakHashMap<EdsdkLibrary.EdsCameraRef, List<WeakReference<CameraPropertyListener>>>) listenersMap.get(MockFactory.initialCanonFactory.getCameraPropertyEventLogic());
            final List<WeakReference<CameraPropertyListener>> weakReferences = map.get(cameraRef);
            if (weakReferences != null)
                return weakReferences;
            return Collections.emptyList();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }
    }
}
