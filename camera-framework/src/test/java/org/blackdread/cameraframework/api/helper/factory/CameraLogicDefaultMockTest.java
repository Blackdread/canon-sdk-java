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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.TestUtil;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.constant.EdsCustomFunction;
import org.blackdread.cameraframework.api.constant.EdsDcRemoteShootingMode;
import org.blackdread.cameraframework.api.constant.EdsEvfAf;
import org.blackdread.cameraframework.api.constant.EdsEvfDriveLens;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsShutterButton;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommBufferFullErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommDisconnectedErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceNotFoundErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private CameraLogicDefaultExtended cameraLogicDefaultExtended;

    private EdsdkLibrary.EdsCameraListRef.ByReference mockCameraListRefByRef;
    private NativeLongByReference mockNativeLongByReference;
    private EdsdkLibrary.EdsCameraRef.ByReference mockCameraRefByRef;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        spyCameraLogic = spy(MockFactory.initialCanonFactory.getCameraLogic());

        cameraLogicDefaultExtended = new CameraLogicDefaultExtended();

        mockCameraListRefByRef = mock(EdsdkLibrary.EdsCameraListRef.ByReference.class);
        mockNativeLongByReference = mock(NativeLongByReference.class);
        mockCameraRefByRef = mock(EdsdkLibrary.EdsCameraRef.ByReference.class);
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
    void sendCommand() {
        when(edsdkLibrary().EdsSendCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_DoEvfAf));
        Assertions.assertThrows(IllegalArgumentException.class, () -> spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_DriveLensEvf));
        Assertions.assertThrows(IllegalArgumentException.class, () -> spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_DoClickWBEvf));
        Assertions.assertThrows(IllegalArgumentException.class, () -> spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_PressShutterButton));
        Assertions.assertThrows(IllegalArgumentException.class, () -> spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_SetRemoteShootingMode));

        spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
        spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_BulbStart);
        spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_BulbEnd);

        verify(edsdkLibrary(), times(3)).EdsSendCommand(eq(fakeCamera), any(), any());
    }

    @Test
    void sendCommandEdsShutterButton() {
        when(edsdkLibrary().EdsSendCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(0));

        spyCameraLogic.sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);

        verify(edsdkLibrary()).EdsSendCommand(eq(fakeCamera), eq(new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value())), eq(new NativeLong(EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely.value())));
    }

    @Test
    void sendCommandEdsDcRemoteShootingMode() {
        when(edsdkLibrary().EdsSendCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(0));

        spyCameraLogic.sendCommand(fakeCamera, EdsDcRemoteShootingMode.kDcRemoteShootingModeStart);

        verify(edsdkLibrary()).EdsSendCommand(eq(fakeCamera), eq(new NativeLong(EdsCameraCommand.kEdsCameraCommand_SetRemoteShootingMode.value())), eq(new NativeLong(EdsDcRemoteShootingMode.kDcRemoteShootingModeStart.value())));
    }

    @Test
    void sendCommandEdsEvfAf() {
        when(edsdkLibrary().EdsSendCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(0));

        spyCameraLogic.sendCommand(fakeCamera, EdsEvfAf.kEdsCameraCommand_EvfAf_ON);

        verify(edsdkLibrary()).EdsSendCommand(eq(fakeCamera), eq(new NativeLong(EdsCameraCommand.kEdsCameraCommand_DoEvfAf.value())), eq(new NativeLong(EdsEvfAf.kEdsCameraCommand_EvfAf_ON.value())));
    }

    @Test
    void sendCommandEdsEvfDriveLens() {
        when(edsdkLibrary().EdsSendCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(0));

        spyCameraLogic.sendCommand(fakeCamera, EdsEvfDriveLens.kEdsEvfDriveLens_Far1);

        verify(edsdkLibrary()).EdsSendCommand(eq(fakeCamera), eq(new NativeLong(EdsCameraCommand.kEdsCameraCommand_DriveLensEvf.value())), eq(new NativeLong(EdsEvfDriveLens.kEdsEvfDriveLens_Far1.value())));
    }

    @Test
    void sendCommandThrowsOnError() {
        when(edsdkLibrary().EdsSendCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture));

        verify(edsdkLibrary()).EdsSendCommand(eq(fakeCamera), any(), any());
    }

    @Test
    void sendStatusCommand() {
        when(edsdkLibrary().EdsSendStatusCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(0));

        spyCameraLogic.sendStatusCommand(fakeCamera, EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock);

        verify(edsdkLibrary()).EdsSendStatusCommand(eq(fakeCamera), eq(new NativeLong(EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock.value())), eq(new NativeLong(0)));
    }

    @Test
    void sendStatusCommandThrowsOnError() {
        when(edsdkLibrary().EdsSendStatusCommand(eq(fakeCamera), any(), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.sendStatusCommand(fakeCamera, EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock));

        verify(edsdkLibrary()).EdsSendStatusCommand(eq(fakeCamera), eq(new NativeLong(EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock.value())), eq(new NativeLong(0)));
    }

    @Test
    void getCameraConnectedCount() {
        when(edsdkLibrary().EdsGetCameraList(any(EdsdkLibrary.EdsCameraListRef.ByReference.class))).thenReturn(new NativeLong(0));
        when(edsdkLibrary().EdsGetChildCount(any(), any(NativeLongByReference.class))).thenReturn(new NativeLong(0));

        final int cameraConnectedCount = spyCameraLogic.getCameraConnectedCount();

        Assertions.assertEquals(0, cameraConnectedCount);
    }

    @Test
    void getCameraConnectedCountThrowsOnError() {
        when(edsdkLibrary().EdsGetCameraList(any(EdsdkLibrary.EdsCameraListRef.ByReference.class))).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.getCameraConnectedCount());

        // Second test

        when(edsdkLibrary().EdsGetCameraList(any(EdsdkLibrary.EdsCameraListRef.ByReference.class))).thenReturn(new NativeLong(0));
        when(edsdkLibrary().EdsGetChildCount(any(), any(NativeLongByReference.class))).thenReturn(new NativeLong(EdsdkError.EDS_ERR_COMM_USB_BUS_ERR.value()));

        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> spyCameraLogic.getCameraConnectedCount());

        verify(edsdkLibrary(), times(2)).EdsGetCameraList(any(EdsdkLibrary.EdsCameraListRef.ByReference.class));
        verify(edsdkLibrary(), times(1)).EdsGetChildCount(any(), any(NativeLongByReference.class));
    }

    @Test
    void isConnectedTrue() {
        when(propertyGetShortcutLogic.getBodyIDEx(fakeCamera)).thenReturn("any");

        Assertions.assertTrue(spyCameraLogic.isConnected(fakeCamera));
    }

    @Test
    void isConnectedFalse() {
        when(propertyGetShortcutLogic.getBodyIDEx(fakeCamera)).thenThrow(EdsdkErrorException.class);

        Assertions.assertFalse(spyCameraLogic.isConnected(fakeCamera));
    }

    @Test
    void isConnectedCatchOnlyEdsdkErrors() {
        when(propertyGetShortcutLogic.getBodyIDEx(fakeCamera)).thenThrow(IllegalStateException.class);

        Assertions.assertThrows(IllegalStateException.class, () -> spyCameraLogic.isConnected(fakeCamera));
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

        verify(edsdkLibrary()).EdsOpenSession(same(fakeCamera));
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

    @Test
    void openSessionDefaultGetsFirstCamera() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);
        cameraLogicDefaultExtended.setCameraRefByRef(mockCameraRefByRef);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(3));

        when(mockCameraRefByRef.getValue()).thenReturn(fakeCamera);

        when(edsdkLibrary().EdsGetChildAtIndex(eq(cameraListRef), any(NativeLong.class), same(mockCameraRefByRef))).thenReturn(new NativeLong(0));

        when(CanonFactory.propertyGetShortcutLogic().getBodyIDEx(fakeCamera))
            .thenThrow(EdsdkCommDisconnectedErrorException.class)
            .thenReturn("any");

        when(edsdkLibrary().EdsOpenSession(same(fakeCamera))).thenReturn(new NativeLong(0));

        final EdsCameraRef result = cameraLogicDefaultExtended.openSession();

        Assertions.assertSame(fakeCamera, result);
        Assertions.assertEquals(fakeCamera, result);

        verify(edsdkLibrary()).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(CanonFactory.propertyGetShortcutLogic(), times(2)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary()).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic).registerCameraStateEvent(same(fakeCamera));
    }


    @Test
    void openSessionThrowsIfBodyIDExIsNull() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);
        cameraLogicDefaultExtended.setCameraRefByRef(mockCameraRefByRef);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(3));

        when(mockCameraRefByRef.getValue()).thenReturn(fakeCamera);

        when(edsdkLibrary().EdsGetChildAtIndex(eq(cameraListRef), any(NativeLong.class), same(mockCameraRefByRef))).thenReturn(new NativeLong(0));

        when(CanonFactory.propertyGetShortcutLogic().getBodyIDEx(fakeCamera))
            .thenThrow(EdsdkCommDisconnectedErrorException.class)
            .thenReturn(null);

        when(edsdkLibrary().EdsOpenSession(same(fakeCamera))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(IllegalStateException.class, () -> cameraLogicDefaultExtended.openSession());

        verify(edsdkLibrary(), times(2)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary()).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(2)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary()).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(1)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void openSessionRethrowOnSecondGetBodyIDEx() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);
        cameraLogicDefaultExtended.setCameraRefByRef(mockCameraRefByRef);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(3));

        when(mockCameraRefByRef.getValue()).thenReturn(fakeCamera);

        when(edsdkLibrary().EdsGetChildAtIndex(eq(cameraListRef), any(NativeLong.class), same(mockCameraRefByRef))).thenReturn(new NativeLong(0));

        when(CanonFactory.propertyGetShortcutLogic().getBodyIDEx(fakeCamera))
            .thenThrow(EdsdkCommDisconnectedErrorException.class)
            .thenThrow(EdsdkDeviceInvalidErrorException.class);

        when(edsdkLibrary().EdsOpenSession(same(fakeCamera))).thenReturn(new NativeLong(0));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> cameraLogicDefaultExtended.openSession());

        verify(edsdkLibrary(), times(2)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary()).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(2)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary()).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void openSessionByBodyIDEx() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);
        cameraLogicDefaultExtended.setCameraRefByRef(mockCameraRefByRef);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(3));

        when(mockCameraRefByRef.getValue()).thenReturn(fakeCamera);

        when(edsdkLibrary().EdsGetChildAtIndex(eq(cameraListRef), any(NativeLong.class), same(mockCameraRefByRef))).thenReturn(new NativeLong(0));

        final String serial = "serial999";

        when(CanonFactory.propertyGetShortcutLogic().getBodyIDEx(fakeCamera))
            .thenThrow(EdsdkCommDisconnectedErrorException.class)
            .thenReturn("firstCameraIsOther")
            .thenReturn("serial999");

        when(edsdkLibrary().EdsOpenSession(same(fakeCamera))).thenReturn(new NativeLong(0));


        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setRegisterObjectEvent(true)
            .setRegisterPropertyEvent(true)
            .setRegisterStateEvent(true)
            .setCameraBySerialNumber(serial)
            .build();

        final EdsCameraRef result = cameraLogicDefaultExtended.openSession(option);

        Assertions.assertSame(fakeCamera, result);
        Assertions.assertEquals(fakeCamera, result);

        verify(edsdkLibrary(), times(2)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary(), times(1)).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(4)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary()).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(1)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void openSessionByBodyIDExThrowsIfNotFoundAfterAll() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);
        cameraLogicDefaultExtended.setCameraRefByRef(mockCameraRefByRef);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(3));

        when(mockCameraRefByRef.getValue()).thenReturn(fakeCamera);

        when(edsdkLibrary().EdsGetChildAtIndex(eq(cameraListRef), any(NativeLong.class), same(mockCameraRefByRef))).thenReturn(new NativeLong(0));

        when(CanonFactory.propertyGetShortcutLogic().getBodyIDEx(fakeCamera))
            .thenThrow(EdsdkCommDisconnectedErrorException.class)
            .thenReturn("anyThatWillNotBeFound");

        when(edsdkLibrary().EdsOpenSession(same(fakeCamera))).thenReturn(new NativeLong(0));

        final String serial = "serial999";

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setRegisterObjectEvent(true)
            .setRegisterPropertyEvent(true)
            .setRegisterStateEvent(true)
            .setCameraBySerialNumber(serial)
            .build();

        Assertions.assertThrows(IllegalStateException.class, () -> cameraLogicDefaultExtended.openSession(option));

        verify(edsdkLibrary(), times(4)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary(), times(3)).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(6)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary(), times(1)).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(1)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void openSessionThrowsIfCameraCountIsZero() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(0));

        Assertions.assertThrows(EdsdkDeviceNotFoundErrorException.class, () -> cameraLogicDefaultExtended.openSession());

        verify(edsdkLibrary(), times(1)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary(), times(0)).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(0)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void openSessionThrowsIfIndexDesiredIsTooBig() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(5));

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setCameraByIndex(5)
            .build();

        Assertions.assertThrows(EdsdkDeviceNotFoundErrorException.class, () -> cameraLogicDefaultExtended.openSession(option));

        verify(edsdkLibrary(), times(1)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary(), times(0)).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(0)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));

    }

    @Test
    void openSessionThrowsWhenFailToOpenSession() {
        cameraLogicDefaultExtended.setCameraListRefByRef(mockCameraListRefByRef);
        cameraLogicDefaultExtended.setNativeLongByReference(mockNativeLongByReference);
        cameraLogicDefaultExtended.setCameraRefByRef(mockCameraRefByRef);

        when(edsdkLibrary().EdsRelease(any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetCameraList(same(mockCameraListRefByRef))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsGetChildCount(any(), same(mockNativeLongByReference))).thenReturn(new NativeLong(0));

        final EdsdkLibrary.EdsCameraListRef cameraListRef = new EdsdkLibrary.EdsCameraListRef();
        when(mockCameraListRefByRef.getValue()).thenReturn(cameraListRef);

        when(mockNativeLongByReference.getValue()).thenReturn(new NativeLong(3));

        when(mockCameraRefByRef.getValue()).thenReturn(fakeCamera);

        when(edsdkLibrary().EdsGetChildAtIndex(eq(cameraListRef), any(NativeLong.class), same(mockCameraRefByRef))).thenReturn(new NativeLong(0));

        when(CanonFactory.propertyGetShortcutLogic().getBodyIDEx(fakeCamera))
            .thenThrow(EdsdkCommDisconnectedErrorException.class);

        when(edsdkLibrary().EdsOpenSession(same(fakeCamera))).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> cameraLogicDefaultExtended.openSession());

        verify(edsdkLibrary(), times(2)).EdsRelease(any());
        verify(edsdkLibrary()).EdsRelease(same(cameraListRef));
        verify(edsdkLibrary(), times(1)).EdsRelease(same(fakeCamera));
        verify(CanonFactory.propertyGetShortcutLogic(), times(1)).getBodyIDEx(same(fakeCamera));
        verify(edsdkLibrary(), times(1)).EdsOpenSession(same(fakeCamera));
        verify(edsdkLibrary(), times(0)).EdsCloseSession(same(fakeCamera));
        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void setCameraRefToCamera() {
        final CanonCamera canonCamera = new CanonCamera();

        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setCamera(canonCamera)
            .build();

        Assertions.assertFalse(canonCamera.getCameraRef().isPresent());

        TestUtil.callMethod(MockFactory.initialCanonFactory.getCameraLogic(), "setCameraRefToCamera", new Class[]{EdsCameraRef.class, OpenSessionOption.class}, fakeCamera, option);

        Assertions.assertTrue(canonCamera.getCameraRef().isPresent());
        Assertions.assertEquals(fakeCamera, canonCamera.getCameraRef().get());
    }

    @Test
    void setCameraRefToCameraIgnoresOnCameraNotSet() {
        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .build();

        TestUtil.callMethod(MockFactory.initialCanonFactory.getCameraLogic(), "setCameraRefToCamera", new Class[]{EdsCameraRef.class, OpenSessionOption.class}, fakeCamera, option);
    }

    @Test
    void registerEvents() {
        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setRegisterObjectEvent(false)
            .setRegisterPropertyEvent(false)
            .setRegisterStateEvent(false)
            .build();

        TestUtil.callMethod(MockFactory.initialCanonFactory.getCameraLogic(), "registerEvents", new Class[]{EdsCameraRef.class, OpenSessionOption.class}, fakeCamera, option);

        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(0)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(0)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void registerEventsFew() {
        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setRegisterObjectEvent(false)
            .setRegisterPropertyEvent(true)
            .setRegisterStateEvent(true)
            .build();

        TestUtil.callMethod(MockFactory.initialCanonFactory.getCameraLogic(), "registerEvents", new Class[]{EdsCameraRef.class, OpenSessionOption.class}, fakeCamera, option);

        verify(cameraObjectEventLogic, times(0)).registerCameraObjectEvent(same(fakeCamera));
        verify(cameraPropertyEventLogic, times(1)).registerCameraPropertyEvent(same(fakeCamera));
        verify(cameraStateEventLogic, times(1)).registerCameraStateEvent(same(fakeCamera));
    }

    @Test
    void closeSession() {
        when(edsdkLibrary().EdsCloseSession(fakeCamera)).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsRelease(fakeCamera)).thenReturn(new NativeLong(0L));

        final CloseSessionOption option = new CloseSessionOptionBuilder()
            .setCameraRef(fakeCamera)
            .build();

        spyCameraLogic.closeSession(option);

        verify(edsdkLibrary()).EdsRelease(same(fakeCamera));
    }

    @Test
    void closeSessionThrowsOnError() {
        when(edsdkLibrary().EdsCloseSession(fakeCamera)).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        when(edsdkLibrary().EdsRelease(fakeCamera)).thenReturn(new NativeLong(0L));

        final CloseSessionOption option = new CloseSessionOptionBuilder()
            .setCameraRef(fakeCamera)
            .build();

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyCameraLogic.closeSession(option));

        verify(edsdkLibrary(), times(0)).EdsRelease(same(fakeCamera));
    }
}
