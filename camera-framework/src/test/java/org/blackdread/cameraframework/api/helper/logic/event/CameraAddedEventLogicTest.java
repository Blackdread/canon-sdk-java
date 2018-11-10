package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.cameraframework.DllOnPath;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import static org.blackdread.cameraframework.api.TestUtil.sleep;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraAddedEventLogic;

/**
 * <p>Created on 2018/11/10.</p>
 *
 * @author Yoann CAPLAIN
 */
@DllOnPath
class CameraAddedEventLogicTest {

    private CameraAddedListener cameraAddedListener;

    private CameraAddedListener cameraAddedListenerThrows;

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
        cameraAddedListener = (event) -> countEvent.incrementAndGet();
        cameraAddedListenerThrows = (event) -> {
            throw new IllegalStateException("Always throw");
        };
    }

    @AfterEach
    void tearDown() {
        countEvent.set(0);
        cameraAddedEventLogic().clearCameraAddedListeners();
    }

    @Test
    void registerCameraAddedEvent() {
        cameraAddedEventLogic().registerCameraAddedEvent();
    }

    @Test
    void addCameraAddedListener() {
        cameraAddedEventLogic().addCameraAddedListener(cameraAddedListener);
    }

    @Test
    void removeCameraAddedListener() {
        cameraAddedEventLogic().addCameraAddedListener(cameraAddedListener);
        cameraAddedEventLogic().removeCameraAddedListener(cameraAddedListener);
    }

    @Test
    void clearCameraAddedListeners() {
        cameraAddedEventLogic().clearCameraAddedListeners();
    }

    @Test
    void listenerExceptionAreSuppressed() {
        cameraAddedEventLogic().addCameraAddedListener(cameraAddedListenerThrows);
        cameraAddedEventLogic().addCameraAddedListener(cameraAddedListener);
        createEvent();
        Assertions.assertEquals(1, countEvent.get());
    }

    @Test
    void getNotifiedCameraAdded() {
        cameraAddedEventLogic().addCameraAddedListener(cameraAddedListener);
        createEvent();
        createEvent();
        Assertions.assertEquals(2, countEvent.get());
        cameraAddedListener = null;
        for (int i = 0; i < 10; i++) {
            System.gc();
            sleep(10);
        }
        createEvent();
        createEvent();
        Assertions.assertEquals(2, countEvent.get());
    }

    private void createEvent() {
        try {
            final Method handleMethod = cameraAddedEventLogic().getClass().getDeclaredMethod("handle", CanonEvent.class);
            final EmptyEvent event = new EmptyEvent();
            handleMethod.setAccessible(true);
            handleMethod.invoke(cameraAddedEventLogic(), event);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
        }
    }
}
