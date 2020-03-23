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

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonObjectEventImplTest {

    private EdsCameraRef cameraRef;

    private EdsObjectEvent objectEvent;

    private EdsBaseRef baseRef;

    private CanonObjectEventImpl canonObjectEvent;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsCameraRef();
        objectEvent = EdsObjectEvent.kEdsObjectEvent_DirItemCreated;
        baseRef = new EdsBaseRef();

        canonObjectEvent = new CanonObjectEventImpl(cameraRef, objectEvent, baseRef);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructorThrowsOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> new CanonObjectEventImpl(null, objectEvent, baseRef));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonObjectEventImpl(cameraRef, null, baseRef));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonObjectEventImpl(cameraRef, objectEvent, null));
    }

    @Test
    void getCameraRef() {
        final EdsCameraRef ref = canonObjectEvent.getCameraRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(cameraRef, ref);
        Assertions.assertSame(cameraRef, canonObjectEvent.getCameraRef());
    }

    @Test
    void getObjectEvent() {
        final EdsObjectEvent event = canonObjectEvent.getObjectEvent();
        Assertions.assertNotNull(event);
        Assertions.assertEquals(objectEvent, event);
        Assertions.assertSame(objectEvent, canonObjectEvent.getObjectEvent());
    }

    @Test
    void getBaseRef() {
        final EdsBaseRef ref = canonObjectEvent.getBaseRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(baseRef, ref);
        Assertions.assertSame(baseRef, canonObjectEvent.getBaseRef());
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(canonObjectEvent.toString());
    }
}
