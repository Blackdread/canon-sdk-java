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
package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class LiveViewReferenceMockTest extends AbstractMockTest {

    private LiveViewReference liveViewReference;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        liveViewReference = new LiveViewReference(new EdsdkLibrary.EdsStreamRef.ByReference(), new EdsdkLibrary.EdsEvfImageRef.ByReference());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getStreamRef() {
        Assertions.assertNotNull(liveViewReference.getStreamRef());
    }

    @Test
    void getImageRef() {
        Assertions.assertNotNull(liveViewReference.getImageRef());
    }

    @Test
    void close() {
        final EdsdkLibrary.EdsStreamRef.ByReference refMock = mock(EdsdkLibrary.EdsStreamRef.ByReference.class);
        final EdsdkLibrary.EdsEvfImageRef.ByReference evfMock = mock(EdsdkLibrary.EdsEvfImageRef.ByReference.class);

        final EdsdkLibrary.EdsStreamRef streamRef = new EdsdkLibrary.EdsStreamRef();
        when(refMock.getValue()).thenReturn(streamRef);
        final EdsdkLibrary.EdsEvfImageRef evfImageRef = new EdsdkLibrary.EdsEvfImageRef();
        when(evfMock.getValue()).thenReturn(evfImageRef);

        when(CanonFactory.edsdkLibrary().EdsRelease(streamRef)).thenReturn(new NativeLong(0L));
        when(CanonFactory.edsdkLibrary().EdsRelease(evfImageRef)).thenReturn(new NativeLong(0L));

        try (LiveViewReference ref = new LiveViewReference(refMock, evfMock)) {
            // nothing
        }
//        verify(CanonFactory.edsdkLibrary(), times(1)).EdsRelease(streamRef);
//        verify(CanonFactory.edsdkLibrary(), times(1)).EdsRelease(evfImageRef);

        verify(CanonFactory.edsdkLibrary(), times(2)).EdsRelease(any());
    }
}
