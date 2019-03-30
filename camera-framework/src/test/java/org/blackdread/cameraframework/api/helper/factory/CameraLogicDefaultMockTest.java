package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsCustomFunction;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommBufferFullErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/29.</p>
 *
 * @author Yoann CAPLAIN
 */
class CameraLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private CameraLogic spyCameraLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        spyCameraLogic = Mockito.spy(MockFactory.initialCanonFactory.getCameraLogic());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setCapacity() {
        when(edsdkLibrary().EdsSetCapacity(eq(fakeCamera), any())).thenReturn(new NativeLong(0));

        spyCameraLogic.setCapacity(fakeCamera);
        spyCameraLogic.setCapacity(fakeCamera, 1000);
        spyCameraLogic.setCapacity(fakeCamera, 1000, 128);

        verify(edsdkLibrary(), times(3)).EdsSetCapacity(eq(fakeCamera), any());

        when(edsdkLibrary().EdsSetCapacity(eq(fakeCamera), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.setCapacity(fakeCamera));
        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.setCapacity(fakeCamera, 1000));
        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.setCapacity(fakeCamera, 1000, 128));
    }

    @Test
    void isMirrorLockupEnabled() {
        when(propertyGetLogic.getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value())).thenReturn(1L);

        Assertions.assertTrue(spyCameraLogic.isMirrorLockupEnabled(fakeCamera));

        when(propertyGetLogic.getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value())).thenReturn(0L);

        Assertions.assertFalse(spyCameraLogic.isMirrorLockupEnabled(fakeCamera));

        when(propertyGetLogic.getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value())).thenReturn(55L);

        Assertions.assertFalse(spyCameraLogic.isMirrorLockupEnabled(fakeCamera));
    }

    @Test
    void isMirrorLockupEnabledTreatSomeExceptionAsFalse() {
        when(propertyGetLogic.getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value())).thenThrow(IllegalArgumentException.class);

        Assertions.assertFalse(spyCameraLogic.isMirrorLockupEnabled(fakeCamera));
    }

    @Test
    void openSessionOpenSessionOnly() {
        when(edsdkLibrary().EdsOpenSession(fakeCamera)).thenReturn(new NativeLong(0));

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setOpenSessionOnly(true)
            .setRegisterObjectEvent(false)
            .setRegisterPropertyEvent(false)
            .setRegisterStateEvent(false)
            .setCameraRef(fakeCamera)
            .build();

        final EdsCameraRef edsCameraRef = spyCameraLogic.openSession(option);

        Assertions.assertEquals(fakeCamera, edsCameraRef);

        verify(edsdkLibrary()).EdsOpenSession(fakeCamera);
    }

    @Test
    void openSessionOpenSessionOnlyThrowsOnError() {
        when(edsdkLibrary().EdsOpenSession(fakeCamera)).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setOpenSessionOnly(true)
            .setRegisterObjectEvent(false)
            .setRegisterPropertyEvent(false)
            .setRegisterStateEvent(false)
            .setCameraRef(fakeCamera)
            .build();

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.openSession(option));

        verify(edsdkLibrary()).EdsOpenSession(fakeCamera);
    }


    @Test
    void openSessionThrows() {
        final String serial = "serial999";

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setRegisterObjectEvent(true)
            .setRegisterPropertyEvent(true)
            .setRegisterStateEvent(true)
            .setCameraBySerialNumber(serial)
            .build();

        when(edsdkLibrary().EdsGetCameraList(any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.openSession(option));

        // Second test

        when(edsdkLibrary().EdsGetCameraList(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_COMM_BUFFER_FULL.value()));

        Assertions.assertThrows(EdsdkCommBufferFullErrorException.class, () -> spyCameraLogic.openSession(option));

    }

    @Disabled("Cannot test")
    @Test
    void openSessionDefault() {
        when(edsdkLibrary().EdsGetCameraList(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), any())).thenReturn(new NativeLong(0));

        // Extra mocks

        spyCameraLogic.openSession();

        verify(cameraObjectEventLogic).registerCameraObjectEvent(fakeCamera);
        verify(cameraPropertyEventLogic).registerCameraPropertyEvent(fakeCamera);
        verify(cameraStateEventLogic).registerCameraStateEvent(fakeCamera);

    }

    @Disabled("Cannot test")
    @Test
    void openSessionByBodyIDEx() {
        when(edsdkLibrary().EdsGetCameraList(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), any())).thenReturn(new NativeLong(0));

        // Extra mocks

        final String serial = "serial999";

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setRegisterObjectEvent(true)
            .setRegisterPropertyEvent(true)
            .setRegisterStateEvent(true)
            .setCameraBySerialNumber(serial)
            .build();

        final EdsCameraRef edsCameraRef = spyCameraLogic.openSession(option);

        Assertions.assertEquals(fakeCamera, edsCameraRef);

        verify(cameraObjectEventLogic).registerCameraObjectEvent(fakeCamera);
        verify(cameraPropertyEventLogic).registerCameraPropertyEvent(fakeCamera);
        verify(cameraStateEventLogic).registerCameraStateEvent(fakeCamera);
    }

//    @Test
//    void setCameraRefToCamera() {
//    }

//    @Test
//    void registerEvents() {
//    }

    @Test
    void closeSession() {
        when(edsdkLibrary().EdsCloseSession(fakeCamera)).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsRelease(fakeCamera)).thenReturn(new NativeLong(0L));

        final CloseSessionOption option = new CloseSessionOptionBuilder()
            .setCameraRef(fakeCamera)
            .build();

        spyCameraLogic.closeSession(option);

        verify(edsdkLibrary()).EdsRelease(fakeCamera);
    }

    @Test
    void closeSessionThrowsOnError() {
        when(edsdkLibrary().EdsCloseSession(fakeCamera)).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        when(edsdkLibrary().EdsRelease(fakeCamera)).thenReturn(new NativeLong(0L));

        final CloseSessionOption option = new CloseSessionOptionBuilder()
            .setCameraRef(fakeCamera)
            .build();

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.closeSession(option));

        verify(edsdkLibrary(), times(0)).EdsRelease(fakeCamera);
    }
}
