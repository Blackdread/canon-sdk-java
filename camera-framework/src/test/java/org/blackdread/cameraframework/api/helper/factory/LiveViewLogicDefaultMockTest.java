package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStreamRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.LiveViewReference;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceNotFoundErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/04/02.</p>
 *
 * @author Yoann CAPLAIN
 */
class LiveViewLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private EdsStreamRef.ByReference mockStreamByRef;
    private EdsEvfImageRef.ByReference mockEvfByRef;
    private LiveViewReference valueMockLiveViewReference;

    private LiveViewLogicDefaultExtended liveViewLogicDefaultExtended;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        mockStreamByRef = Mockito.mock(EdsStreamRef.ByReference.class);
        mockEvfByRef = Mockito.mock(EdsEvfImageRef.ByReference.class);

        when(mockStreamByRef.getValue()).thenReturn(new EdsStreamRef());
        when(mockEvfByRef.getValue()).thenReturn(new EdsEvfImageRef());

        valueMockLiveViewReference = new LiveViewReference(mockStreamByRef, mockEvfByRef);

        liveViewLogicDefaultExtended = new LiveViewLogicDefaultExtended();

        liveViewLogicDefaultExtended.setLiveViewReference(valueMockLiveViewReference);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void beginLiveView() {
    }

    @Test
    void endLiveView() {
    }

    @Test
    void isLiveViewEnabled() {
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImage() {
    }

    @Test
    void getLiveViewImage() {
    }

    @Test
    void getLiveViewImageBuffer() {
    }

    @Test
    void getLiveViewImageReference() {
        liveViewLogicDefaultExtended.setLiveViewReference(null);

        when(edsdkLibrary().EdsCreateMemoryStream(eq(0L), any(EdsStreamRef.ByReference.class)))
            .thenReturn(new NativeLong(0));
//        when(edsdkLibrary().EdsCreateEvfImageRef(any(EdsStreamRef.class), any(EdsEvfImageRef.ByReference.class)))
        when(edsdkLibrary().EdsCreateEvfImageRef(isNull(), any(EdsEvfImageRef.ByReference.class)))
            .thenReturn(new NativeLong(0));
//        when(edsdkLibrary().EdsDownloadEvfImage(eq(fakeCamera), any(EdsEvfImageRef.class)))
        when(edsdkLibrary().EdsDownloadEvfImage(eq(fakeCamera), isNull()))
            .thenReturn(new NativeLong(0));

        final LiveViewReference liveViewImageReference = liveViewLogicDefaultExtended.getLiveViewImageReference(fakeCamera);

        Assertions.assertNotNull(liveViewImageReference);
        Assertions.assertNotNull(liveViewImageReference.getStreamRef());
        Assertions.assertNotNull(liveViewImageReference.getImageRef());
    }

    @Test
    void getLiveViewImageReferenceThrowsOnError() {
        liveViewLogicDefaultExtended.setLiveViewReference(null);

        when(edsdkLibrary().EdsCreateMemoryStream(eq(0L), any(EdsStreamRef.ByReference.class)))
            .thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()))
            .thenReturn(new NativeLong(0));
        when(edsdkLibrary().EdsCreateEvfImageRef(isNull(), any(EdsEvfImageRef.ByReference.class)))
            .thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_NOT_FOUND.value()))
            .thenReturn(new NativeLong(0));
        when(edsdkLibrary().EdsDownloadEvfImage(eq(fakeCamera), isNull()))
            .thenReturn(new NativeLong(EdsdkError.EDS_ERR_COMM_USB_BUS_ERR.value()))
            .thenReturn(new NativeLong(0));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> liveViewLogicDefaultExtended.getLiveViewImageReference(fakeCamera));
        Assertions.assertThrows(EdsdkDeviceNotFoundErrorException.class, () -> liveViewLogicDefaultExtended.getLiveViewImageReference(fakeCamera));
        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> liveViewLogicDefaultExtended.getLiveViewImageReference(fakeCamera));

        final LiveViewReference liveViewImageReference = liveViewLogicDefaultExtended.getLiveViewImageReference(fakeCamera);

        Assertions.assertNotNull(liveViewImageReference);
        Assertions.assertNotNull(liveViewImageReference.getStreamRef());
        Assertions.assertNotNull(liveViewImageReference.getImageRef());
    }
}
