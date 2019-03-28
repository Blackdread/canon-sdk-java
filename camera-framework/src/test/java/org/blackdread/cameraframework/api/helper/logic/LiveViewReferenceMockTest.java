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
