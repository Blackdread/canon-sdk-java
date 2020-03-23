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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonStateEvent;
import org.blackdread.cameraframework.api.helper.logic.event.CanonStateEventImpl;
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
class CameraStateEventLogicDefaultMockTest extends AbstractMockTest {

    private EdsdkLibrary.EdsCameraRef fakeCamera;

    private CameraStateListener cameraStateListener;

    private CameraStateListener cameraStateListenerThrows;

    private final AtomicInteger countEvent = new AtomicInteger(0);

    private CameraStateEventLogic spyCameraStateEventLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsdkLibrary.EdsCameraRef();
        cameraStateListener = (event) -> countEvent.incrementAndGet();
        cameraStateListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };

        spyCameraStateEventLogic = Mockito.spy(MockFactory.initialCanonFactory.getCameraStateEventLogic());
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
        spyCameraStateEventLogic.clearCameraStateListeners();
    }

    @Test
    void buildHandlerUseWeakReference() throws InterruptedException {
        final EdsdkLibrary.EdsStateEventHandler handler;
        try {
            final Method buildHandler = MockFactory.initialCanonFactory.getCameraStateEventLogic().getClass().getDeclaredMethod("buildHandler", EdsdkLibrary.EdsCameraRef.class);
            buildHandler.setAccessible(true);
            handler = (EdsdkLibrary.EdsStateEventHandler) buildHandler.invoke(MockFactory.initialCanonFactory.getCameraStateEventLogic(), fakeCamera);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }

        final NativeLong apply = handler.apply(new NativeLong(EdsStateEvent.kEdsStateEvent_Shutdown.value()), new NativeLong(0L), new Pointer(0));

        fakeCamera = null;

        for (int i = 0; i < 50; i++) {
            System.gc();
            Thread.sleep(50);
            System.gc();
        }

        // Let's hope gc actually happened...

        Assertions.assertThrows(IllegalStateException.class, () -> handler.apply(new NativeLong(EdsStateEvent.kEdsStateEvent_Shutdown.value()), new NativeLong(0L), new Pointer(0)));
    }

    @Test
    void handleEvent() {
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());

        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(3, countEvent.get());
    }

    @Test
    void handleEventIgnoreException() {
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(1, countEvent.get());

        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListenerThrows);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        createEvent(fakeCamera);
        Assertions.assertEquals(3, countEvent.get());
    }

    @Test
    void registerCameraStateEvent() {
        when(edsdkLibrary().EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraStateEventLogic.registerCameraStateEvent(fakeCamera);

        verify(edsdkLibrary(), times(1)).EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void registerCameraStateEventThrowsOnError() {
        when(edsdkLibrary().EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraStateEventLogic.registerCameraStateEvent(fakeCamera));

        verify(edsdkLibrary(), times(1)).EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void unregisterCameraStateEvent() {
        when(edsdkLibrary().EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraStateEventLogic.unregisterCameraStateEvent(fakeCamera);

        verify(edsdkLibrary(), times(1)).EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void unregisterCameraStateEventThrowsOnError() {
        when(edsdkLibrary().EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraStateEventLogic.unregisterCameraStateEvent(fakeCamera));

        verify(edsdkLibrary(), times(1)).EdsSetCameraStateEventHandler(eq(fakeCamera), any(), any(), (Pointer) isNull());
    }

    @Test
    void addCameraStateListener() {
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);

        final List<WeakReference<CameraStateListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraStateListener, listeners.get(0).get());
        Assertions.assertEquals(cameraStateListenerThrows, listeners.get(1).get());

        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void addCameraStateListenerIgnoreDuplicate() {
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);

        final List<WeakReference<CameraStateListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraStateListener, listeners.get(0).get());
        Assertions.assertEquals(cameraStateListenerThrows, listeners.get(1).get());

        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void addCameraStateListenerSpecificCamera() {
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListenerThrows);

        final List<WeakReference<CameraStateListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraStateListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(2, cameraListeners.size());

        Assertions.assertEquals(cameraStateListener, cameraListeners.get(0).get());
        Assertions.assertEquals(cameraStateListenerThrows, cameraListeners.get(1).get());
    }

    @Test
    void addCameraStateListenerSpecificCameraIgnoreDuplicate() {
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListenerThrows);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListenerThrows);

        final List<WeakReference<CameraStateListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraStateListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(2, cameraListeners.size());

        Assertions.assertEquals(cameraStateListener, cameraListeners.get(0).get());
        Assertions.assertEquals(cameraStateListenerThrows, cameraListeners.get(1).get());
    }

    @Test
    void removeCameraStateListener() {
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);

        spyCameraStateEventLogic.removeCameraStateListener(cameraStateListener);

        final List<WeakReference<CameraStateListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(1, listeners.size());
        Assertions.assertEquals(cameraStateListenerThrows, listeners.get(0).get());

        final List<WeakReference<CameraStateListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(0, cameraListeners.size());

    }

    @Test
    void removeCameraStateListenerIgnoreNotFound() {
        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraStateEventLogic.removeCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.removeCameraStateListener(cameraStateListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void removeCameraStateListenerSpecificCamera() {
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListenerThrows);

        spyCameraStateEventLogic.removeCameraStateListener(fakeCamera, cameraStateListener);

        final List<WeakReference<CameraStateListener>> listeners = getAnyCameraListeners();
        Assertions.assertEquals(0, listeners.size());

        final List<WeakReference<CameraStateListener>> cameraListeners = getCameraListeners(fakeCamera);
        Assertions.assertEquals(1, cameraListeners.size());

        Assertions.assertEquals(cameraStateListenerThrows, cameraListeners.get(0).get());
    }

    @Test
    void removeCameraStateListenerSpecificCameraIgnoreNotFound() {
        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraStateEventLogic.removeCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.removeCameraStateListener(fakeCamera, cameraStateListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void clearCameraStateListeners() {
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(cameraStateListenerThrows);

        Assertions.assertEquals(2, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());

        spyCameraStateEventLogic.clearCameraStateListeners();

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }

    @Test
    void clearCameraStateListenersSpecificCamera() {
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListener);
        spyCameraStateEventLogic.addCameraStateListener(fakeCamera, cameraStateListenerThrows);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(2, getCameraListeners(fakeCamera).size());

        spyCameraStateEventLogic.clearCameraStateListeners(fakeCamera);

        Assertions.assertEquals(0, getAnyCameraListeners().size());
        Assertions.assertEquals(0, getCameraListeners(fakeCamera).size());
    }


    private void createEvent(final EdsdkLibrary.EdsCameraRef camera) {
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getCameraStateEventLogic().getClass().getDeclaredMethod("handle", CanonStateEvent.class);
            final CanonStateEventImpl event = new CanonStateEventImpl(camera, EdsStateEvent.kEdsStateEvent_Shutdown, 0L);
            handleMethod.setAccessible(true);
            handleMethod.invoke(MockFactory.initialCanonFactory.getCameraStateEventLogic(), event);
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

    private List<WeakReference<CameraStateListener>> getAnyCameraListeners() {
        try {
            final Field anyCameraListeners = MockFactory.initialCanonFactory.getCameraStateEventLogic().getClass().getDeclaredField("anyCameraListeners");
            anyCameraListeners.setAccessible(true);
            return (List<WeakReference<CameraStateListener>>) anyCameraListeners.get(MockFactory.initialCanonFactory.getCameraStateEventLogic());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }
    }

    private List<WeakReference<CameraStateListener>> getCameraListeners(final EdsdkLibrary.EdsCameraRef cameraRef) {
        try {
            final Field listenersMap = MockFactory.initialCanonFactory.getCameraStateEventLogic().getClass().getDeclaredField("listenersMap");
            listenersMap.setAccessible(true);
            final WeakHashMap<EdsdkLibrary.EdsCameraRef, List<WeakReference<CameraStateListener>>> map = (WeakHashMap<EdsdkLibrary.EdsCameraRef, List<WeakReference<CameraStateListener>>>) listenersMap.get(MockFactory.initialCanonFactory.getCameraStateEventLogic());
            final List<WeakReference<CameraStateListener>> weakReferences = map.get(cameraRef);
            if (weakReferences != null)
                return weakReferences;
            return Collections.emptyList();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("can not reach");
        }
    }
}
