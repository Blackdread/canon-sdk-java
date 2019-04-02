package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStreamRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.LiveViewReference;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceNotFoundErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    void enableLiveView() {
        MockFactory.initialCanonFactory.getLiveViewLogic().enableLiveView(fakeCamera);

        verify(CanonFactory.propertySetLogic()).setPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_Mode, 1L);
    }

    @Test
    void disableLiveView() {
        MockFactory.initialCanonFactory.getLiveViewLogic().disableLiveView(fakeCamera);

        verify(CanonFactory.propertySetLogic()).setPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_Mode, 0L);
    }

    @Test
    void beginLiveView() {
        MockFactory.initialCanonFactory.getLiveViewLogic().beginLiveView(fakeCamera);

        verify(CanonFactory.propertySetLogic()).setPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, EdsEvfOutputDevice.kEdsEvfOutputDevice_PC);
    }

    @Test
    void beginLiveView1() {
        MockFactory.initialCanonFactory.getLiveViewLogic().beginLiveView(fakeCamera, EdsEvfOutputDevice.kEdsEvfOutputDevice_MOBILE);

        verify(CanonFactory.propertySetLogic()).setPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, EdsEvfOutputDevice.kEdsEvfOutputDevice_MOBILE);

    }

    @Test
    void endLiveView() {
        MockFactory.initialCanonFactory.getLiveViewLogic().endLiveView(fakeCamera);

        verify(CanonFactory.propertySetLogic()).setPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, EdsEvfOutputDevice.kEdsEvfOutputDevice_TFT);
    }

    @Test
    void isLiveViewEnabled() {
        when(CanonFactory.propertyGetLogic().getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_Mode))
            .thenReturn(0L)
            .thenReturn(2L)
            .thenReturn(-1L)
            .thenReturn(1L);

        Assertions.assertFalse(MockFactory.initialCanonFactory.getLiveViewLogic().isLiveViewEnabled(fakeCamera));
        Assertions.assertFalse(MockFactory.initialCanonFactory.getLiveViewLogic().isLiveViewEnabled(fakeCamera));
        Assertions.assertFalse(MockFactory.initialCanonFactory.getLiveViewLogic().isLiveViewEnabled(fakeCamera));
        Assertions.assertTrue(MockFactory.initialCanonFactory.getLiveViewLogic().isLiveViewEnabled(fakeCamera));
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImage() {
        final boolean result = liveViewLogicDefaultExtended.isLiveViewEnabledByDownloadingOneImage(fakeCamera);

        Assertions.assertTrue(result);

        verify(CanonFactory.edsdkLibrary(), times(2)).EdsRelease(any());
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImageCatchEdsdkError() {
        liveViewLogicDefaultExtended.setThrowOnGetLiveViewImageReference(new EdsdkErrorException(EdsdkError.EDS_ERR_OK));

        final boolean result = liveViewLogicDefaultExtended.isLiveViewEnabledByDownloadingOneImage(fakeCamera);

        Assertions.assertFalse(result);

        verify(CanonFactory.edsdkLibrary(), times(0)).EdsRelease(any());
    }

    @Test
    void isLiveViewEnabledByDownloadingOneImageDoesNotCatchOtherThanEdsdkError() {
        liveViewLogicDefaultExtended.setThrowOnGetLiveViewImageReference(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> liveViewLogicDefaultExtended.isLiveViewEnabledByDownloadingOneImage(fakeCamera));

        verify(CanonFactory.edsdkLibrary(), times(0)).EdsRelease(any());
    }

    @Test
    void getLiveViewImage() {
        final byte[] buffer = new byte[0];
        liveViewLogicDefaultExtended.setLiveViewBuffer(buffer);

        final BufferedImage liveViewImage = liveViewLogicDefaultExtended.getLiveViewImage(fakeCamera);

        Assertions.assertNull(liveViewImage);
    }

    @Test
    void getLiveViewImageBuffer() {
        when(edsdkLibrary().EdsGetLength(any(), any()))
            .thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()))
            .thenReturn(new NativeLong(0));
        when(edsdkLibrary().EdsGetPointer(any(), any()))
            .thenReturn(new NativeLong(EdsdkError.EDS_ERR_COMM_USB_BUS_ERR.value()))
            .thenReturn(new NativeLong(0));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> liveViewLogicDefaultExtended.getLiveViewImageBuffer(fakeCamera));
        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> liveViewLogicDefaultExtended.getLiveViewImageBuffer(fakeCamera));

        // NPE because cannot mock pointer without adding a protected method to instantiate the pointer
        Assertions.assertThrows(NullPointerException.class, () -> liveViewLogicDefaultExtended.getLiveViewImageBuffer(fakeCamera));
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
