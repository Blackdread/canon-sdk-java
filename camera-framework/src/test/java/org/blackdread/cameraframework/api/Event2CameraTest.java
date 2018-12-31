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
package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created on 2018/11/03.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
public class Event2CameraTest {

    private static final Logger log = LoggerFactory.getLogger(Event2CameraTest.class);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    private final AtomicInteger cameraEventCalledCount = new AtomicInteger(0);
    private final AtomicInteger propertyEventCalledCount = new AtomicInteger(0);
    private final AtomicInteger objectEventCalledCount = new AtomicInteger(0);
    private final AtomicInteger stateEventCalledCount = new AtomicInteger(0);

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
    }

    @AfterAll
    static void tearDownClass() {
        try {
            TestShortcutUtil.closeSession(camera);
        } finally {
            ReleaseUtil.release(camera);
        }
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
        TestShortcutUtil.registerCameraAddedHandler(inContext -> {
            log.warn("Camera added called {}", inContext);
            cameraEventCalledCount.incrementAndGet();
            return new NativeLong(0);
        });

        TestShortcutUtil.registerPropertyEventHandler(camera.getValue(), (inEvent, inPropertyID, inParam, inContext) -> {
            log.warn("Camera property called {}, {}, {}", EdsPropertyEvent.ofValue(inEvent.intValue()), EdsPropertyID.ofValue(inPropertyID.intValue()), inContext);
            propertyEventCalledCount.incrementAndGet();
            return new NativeLong(0);
        });

        TestShortcutUtil.registerObjectEventHandler(camera.getValue(), (inEvent, inRef, inContext) -> {
            log.warn("Camera object called {}, {}, {}", inEvent, inRef, inContext);
            objectEventCalledCount.incrementAndGet();
            return new NativeLong(0);
        });

        TestShortcutUtil.registerStateEventHandler(camera.getValue(), (inEvent, inEventData, inContext) -> {
            log.warn("Camera state called {}, {}, {}", inEvent, inEventData, inContext);
            stateEventCalledCount.incrementAndGet();
            return new NativeLong(0);
        });
        resetCounts();
    }

    @AfterEach
    void tearDown() {
        resetCounts();
    }

    private void resetCounts() {
        cameraEventCalledCount.set(0);
        propertyEventCalledCount.set(0);
        objectEventCalledCount.set(0);
        stateEventCalledCount.set(0);
    }

    @Test
    @Disabled("Only run manually")
    void startLiveViewSendEvent() throws InterruptedException {
        CanonFactory.liveViewLogic().beginLiveView(camera.getValue());

        for (int i = 0; i < 100; i++) {
            Thread.sleep(50);
            CanonFactory.edsdkLibrary().EdsGetEvent();
        }

        final int count1 = propertyEventCalledCount.get();
        Assertions.assertTrue(count1 > 0);

        CanonFactory.liveViewLogic().endLiveView(camera.getValue());

        for (int i = 0; i < 100; i++) {
            Thread.sleep(50);
            CanonFactory.edsdkLibrary().EdsGetEvent();
        }
        final int count2 = propertyEventCalledCount.get();
        Assertions.assertTrue(count2 > count1);
    }

    @Test
    @Disabled("Only run manually")
    void changePropertyEvent() throws InterruptedException {
        final List<EdsISOSpeed> isoSpeeds = CanonFactory.propertyDescLogic().getPropertyDesc(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(50);
            CanonFactory.edsdkLibrary().EdsGetEvent();
        }

        resetCounts();

        CanonFactory.propertySetLogic().setPropertyData(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed, isoSpeeds.get(0));
        CanonFactory.propertySetLogic().setPropertyData(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed, isoSpeeds.get(1));

        for (int i = 0; i < 10; i++) {
            Thread.sleep(50);
            CanonFactory.edsdkLibrary().EdsGetEvent();
        }

        Assertions.assertTrue(propertyEventCalledCount.get() > 0);

    }
}
