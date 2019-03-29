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
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonEvent;
import org.blackdread.cameraframework.api.helper.logic.event.EmptyEvent;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/29.</p>
 *
 * @author Yoann CAPLAIN
 */
class CameraAddedEventLogicDefaultMockTest extends AbstractMockTest {

    private CameraAddedListener cameraAddedListener;

    private CameraAddedListener cameraAddedListenerThrows;

    private final AtomicInteger countEvent = new AtomicInteger(0);

    private CameraAddedEventLogic spyCameraAddedEventLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        cameraAddedListener = (event) -> countEvent.incrementAndGet();
        cameraAddedListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };

        spyCameraAddedEventLogic = Mockito.spy(MockFactory.initialCanonFactory.getCameraAddedEventLogic());
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
        spyCameraAddedEventLogic.clearCameraAddedListeners();
    }

    @Test
    void handleEventWhenNoListener() {
        createEvent();
    }

    @Test
    void handleEvent() {
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        createEvent();
        Assertions.assertEquals(1, countEvent.get());
    }

    @Test
    void handleEventIgnoreException() {
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListenerThrows);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        createEvent();
        Assertions.assertEquals(1, countEvent.get());
    }

    @Test
    void registerCameraAddedEvent() {
        when(edsdkLibrary().EdsSetCameraAddedHandler(any(), (Pointer) isNull())).thenReturn(new NativeLong(0));

        spyCameraAddedEventLogic.registerCameraAddedEvent();

        verify(edsdkLibrary(), times(1)).EdsSetCameraAddedHandler(any(), (Pointer) isNull());
    }

    @Test
    void registerCameraAddedEventThrowsOnError() {
        when(edsdkLibrary().EdsSetCameraAddedHandler(any(), (Pointer) isNull())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraAddedEventLogic.registerCameraAddedEvent());

        verify(edsdkLibrary(), times(1)).EdsSetCameraAddedHandler(any(), (Pointer) isNull());
    }

    @Test
    void addCameraAddedListener() {
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListenerThrows);

        final List<WeakReference<CameraAddedListener>> listeners = getListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraAddedListener, listeners.get(0).get());
        Assertions.assertEquals(cameraAddedListenerThrows, listeners.get(1).get());
    }

    @Test
    void addCameraAddedListenerIgnoreDuplicates() {
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListenerThrows);

        final List<WeakReference<CameraAddedListener>> listeners = getListeners();
        Assertions.assertEquals(2, listeners.size());

        Assertions.assertEquals(cameraAddedListener, listeners.get(0).get());
        Assertions.assertEquals(cameraAddedListenerThrows, listeners.get(1).get());
    }

    @Test
    void removeCameraAddedListener() {
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListenerThrows);

        spyCameraAddedEventLogic.removeCameraAddedListener(cameraAddedListener);

        final List<WeakReference<CameraAddedListener>> listeners = getListeners();
        Assertions.assertEquals(1, listeners.size());

        Assertions.assertEquals(cameraAddedListenerThrows, listeners.get(0).get());
    }

    @Test
    void removeCameraAddedListenerIgnoreNotFound() {
        Assertions.assertEquals(0, getListeners().size());

        spyCameraAddedEventLogic.removeCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.removeCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.removeCameraAddedListener(cameraAddedListenerThrows);

        Assertions.assertEquals(0, getListeners().size());
    }

    @Test
    void clearCameraAddedListeners() {
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListener);
        spyCameraAddedEventLogic.addCameraAddedListener(cameraAddedListenerThrows);

        spyCameraAddedEventLogic.clearCameraAddedListeners();

        final List<WeakReference<CameraAddedListener>> listeners = getListeners();
        Assertions.assertEquals(0, listeners.size());
    }

    private List<WeakReference<CameraAddedListener>> getListeners() {
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getCameraAddedEventLogic().getClass().getDeclaredMethod("getListeners");
            handleMethod.setAccessible(true);
            return (List<WeakReference<CameraAddedListener>>) handleMethod.invoke(MockFactory.initialCanonFactory.getCameraAddedEventLogic());
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("do not reach");
        }
    }

    private void createEvent() {
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getCameraAddedEventLogic().getClass().getDeclaredMethod("handle", CanonEvent.class);
            final EmptyEvent event = new EmptyEvent();
            handleMethod.setAccessible(true);
            handleMethod.invoke(MockFactory.initialCanonFactory.getCameraAddedEventLogic(), event);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
        }
    }
}
