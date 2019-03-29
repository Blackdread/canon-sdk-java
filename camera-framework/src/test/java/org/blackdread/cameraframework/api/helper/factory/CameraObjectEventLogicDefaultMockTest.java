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
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonObjectEvent;
import org.blackdread.cameraframework.api.helper.logic.event.CanonObjectEventImpl;
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
class CameraObjectEventLogicDefaultMockTest extends AbstractMockTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraObjectListener cameraObjectListener;

    private CameraObjectListener cameraObjectListenerThrows;

    private final AtomicInteger countEvent = new AtomicInteger(0);

    private CameraObjectEventLogic spyCameraObjectEventLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsdkLibrary.EdsCameraRef();
        cameraObjectListener = (event) -> countEvent.incrementAndGet();
        cameraObjectListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };

        spyCameraObjectEventLogic = Mockito.spy(MockFactory.initialCanonFactory.getCameraObjectEventLogic());
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
        spyCameraObjectEventLogic.clearCameraObjectListeners();
    }

    @Test
    void handleEvent() {
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());

        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(3, countEvent.get());
    }

    @Test
    void handleEventIgnoreException() {
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());

        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListenerThrows);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(3, countEvent.get());
    }

    @Test
    void registerCameraObjectEvent() {
        when(edsdkLibrary().EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraObjectEventLogic.registerCameraObjectEvent(fakeCamera);

        verify(edsdkLibrary(), times(1)).EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void registerCameraObjectEventThrowsOnError() {
        when(edsdkLibrary().EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraObjectEventLogic.registerCameraObjectEvent(fakeCamera));

        verify(edsdkLibrary(), times(1)).EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void unregisterCameraObjectEvent() {
        when(edsdkLibrary().EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraObjectEventLogic.unregisterCameraObjectEvent(fakeCamera);

        verify(edsdkLibrary(), times(1)).EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void unregisterCameraObjectEventThrowsOnError() {
        when(edsdkLibrary().EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraObjectEventLogic.unregisterCameraObjectEvent(fakeCamera));

        verify(edsdkLibrary(), times(1)).EdsSetObjectEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void addCameraObjectListener() {
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);

        final List<WeakReference<CameraObjectListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraObjectListener, listeners.get(0).get());
        Assertions.assertEquals(cameraObjectListenerThrows, listeners.get(1).get());

        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void addCameraObjectListenerIgnoreDuplicate() {
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);

        final List<WeakReference<CameraObjectListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraObjectListener, listeners.get(0).get());
        Assertions.assertEquals(cameraObjectListenerThrows, listeners.get(1).get());

        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void addCameraObjectListenerSpecificCamera() {
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListenerThrows);

        final List<WeakReference<CameraObjectListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraObjectListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(2, cameraListeners.size());

        Assertions.assertEquals(cameraObjectListener, cameraListeners.get(0).get());
        Assertions.assertEquals(cameraObjectListenerThrows, cameraListeners.get(1).get());
    }

    @Test
    void addCameraObjectListenerSpecificCameraIgnoreDuplicate() {
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListenerThrows);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListenerThrows);

        final List<WeakReference<CameraObjectListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraObjectListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(2, cameraListeners.size());

        Assertions.assertEquals(cameraObjectListener, cameraListeners.get(0).get());
        Assertions.assertEquals(cameraObjectListenerThrows, cameraListeners.get(1).get());
    }

    @Test
    void removeCameraObjectListener() {
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);

        spyCameraObjectEventLogic.removeCameraObjectListener(cameraObjectListener);

        final List<WeakReference<CameraObjectListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(1, listeners.size());
        Assertions.assertEquals(cameraObjectListenerThrows, listeners.get(0).get());

        final List<WeakReference<CameraObjectListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(0, cameraListeners.size());

    }

    @Test
    void removeCameraObjectListenerIgnoreNotFound() {
        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraObjectEventLogic.removeCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.removeCameraObjectListener(cameraObjectListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void removeCameraObjectListenerSpecificCamera() {
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListenerThrows);

        spyCameraObjectEventLogic.removeCameraObjectListener(fakeCamera, cameraObjectListener);

        final List<WeakReference<CameraObjectListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraObjectListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(1, cameraListeners.size());

        Assertions.assertEquals(cameraObjectListenerThrows, cameraListeners.get(0).get());
    }

    @Test
    void removeCameraObjectListenerSpecificCameraIgnoreNotFound() {
        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraObjectEventLogic.removeCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.removeCameraObjectListener(fakeCamera, cameraObjectListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void clearCameraObjectListeners() {
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(cameraObjectListenerThrows);

        Assertions.assertEquals(2, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraObjectEventLogic.clearCameraObjectListeners();

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void clearCameraObjectListenersSpecificCamera() {
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListener);
        spyCameraObjectEventLogic.addCameraObjectListener(fakeCamera, cameraObjectListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(2, getCameraListeners(fakeCamera).size());

        spyCameraObjectEventLogic.clearCameraObjectListeners(fakeCamera);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }


    private void createEvent(final EdsdkLibrary.EdsCameraRef camera) {
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getCameraObjectEventLogic().getClass().getDeclaredMethod("handle", CanonObjectEvent.class);
            final CanonObjectEventImpl event = new CanonObjectEventImpl(camera, EdsObjectEvent.kEdsObjectEvent_DirItemCreated, new EdsdkLibrary.EdsBaseRef());
            handleMethod.setAccessible(true);
            handleMethod.invoke(MockFactory.initialCanonFactory.getCameraObjectEventLogic(), event);
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

    private List<WeakReference<CameraObjectListener>> getAnyCameraListeners() {
        try {
            final Field anyCameraListeners = MockFactory.initialCanonFactory.getCameraObjectEventLogic().getClass().getDeclaredField("anyCameraListeners");
            anyCameraListeners.setAccessible(true);
            return (List<WeakReference<CameraObjectListener>>) anyCameraListeners.get(MockFactory.initialCanonFactory.getCameraObjectEventLogic());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }
    }

    private List<WeakReference<CameraObjectListener>> getCameraListeners(final EdsdkLibrary.EdsCameraRef cameraRef) {
        try {
            final Field listenersMap = MockFactory.initialCanonFactory.getCameraObjectEventLogic().getClass().getDeclaredField("listenersMap");
            listenersMap.setAccessible(true);
            final WeakHashMap<EdsdkLibrary.EdsCameraRef, List<WeakReference<CameraObjectListener>>> map = (WeakHashMap<EdsdkLibrary.EdsCameraRef, List<WeakReference<CameraObjectListener>>>) listenersMap.get(MockFactory.initialCanonFactory.getCameraObjectEventLogic());
            final List<WeakReference<CameraObjectListener>> weakReferences = map.get(cameraRef);
            if (weakReferences != null)
                return weakReferences;
            return Collections.emptyList();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }
    }
}
