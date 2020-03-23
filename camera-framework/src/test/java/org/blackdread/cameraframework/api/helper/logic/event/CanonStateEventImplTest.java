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

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonStateEventImplTest {

    private EdsCameraRef cameraRef;

    private EdsStateEvent stateEvent;

    private Long eventData;

    private CanonStateEventImpl canonStateEvent;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsCameraRef();
        stateEvent = EdsStateEvent.kEdsStateEvent_AfResult;
        eventData = 0L;

        canonStateEvent = new CanonStateEventImpl(cameraRef, stateEvent, eventData);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructorThrowsOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> new CanonStateEventImpl(null, stateEvent, eventData));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonStateEventImpl(cameraRef, null, eventData));
    }

    @Test
    void getCameraRef() {
        final EdsCameraRef ref = canonStateEvent.getCameraRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(cameraRef, ref);
        Assertions.assertSame(cameraRef, canonStateEvent.getCameraRef());
    }

    @Test
    void getObjectEvent() {
        final EdsStateEvent event = canonStateEvent.getStateEvent();
        Assertions.assertNotNull(event);
        Assertions.assertEquals(stateEvent, event);
        Assertions.assertSame(stateEvent, canonStateEvent.getStateEvent());
    }

    @Test
    void getEventData() {
        final long data = canonStateEvent.getEventData();
        Assertions.assertEquals(eventData, data);
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(canonStateEvent.toString());
    }
}
