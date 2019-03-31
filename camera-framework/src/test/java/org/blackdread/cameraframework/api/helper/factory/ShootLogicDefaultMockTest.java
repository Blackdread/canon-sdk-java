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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.TestUtil;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsShutterButton;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonObjectEventImpl;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/30.</p>
 *
 * @author Yoann CAPLAIN
 */
class ShootLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private ShootLogic spyShootLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        spyShootLogic = spy(MockFactory.initialCanonFactory.getShootLogic());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shootV0() {
        spyShootLogic.shootV0(fakeCamera);

        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
    }

    @Test
    void shootAF() {
        spyShootLogic.shootAF(fakeCamera);

        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

    @Test
    void shootNoAF() {
        spyShootLogic.shootNoAF(fakeCamera);

        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

//    @Test
//    void shoot() {
//    }

    @Test
    void handleObjectEvent() throws InvocationTargetException, IllegalAccessException {
        final File fileJpg = new File("fake.jpg");
        final File fileRaw = new File("fake.cr2");

        final ShootOption option = new ShootOptionBuilder()
            .build();

        final int expectedFileCount = 0;

        final List<File> filesSavedOnPc = new ArrayList<>();

        final AtomicReference<RuntimeException> cameraObjectListenerException = new AtomicReference<>();

        final Method handleObjectEvent = Arrays.stream(MockFactory.initialCanonFactory.getShootLogic().getClass().getDeclaredMethods())
            .filter(e -> e.getName().equals("handleObjectEvent"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("not found"));
        handleObjectEvent.setAccessible(true);
        final CameraObjectListener cameraObjectListener = (CameraObjectListener) handleObjectEvent.invoke(MockFactory.initialCanonFactory.getShootLogic(), option, expectedFileCount, filesSavedOnPc, cameraObjectListenerException);

        final CanonObjectEventImpl canonObjectEvent = new CanonObjectEventImpl(fakeCamera, EdsObjectEvent.kEdsObjectEvent_DirItemCreated, new EdsdkLibrary.EdsBaseRef());
        final CanonObjectEventImpl canonObjectEvent2 = new CanonObjectEventImpl(fakeCamera, EdsObjectEvent.kEdsObjectEvent_DirItemRequestTransfer, new EdsdkLibrary.EdsBaseRef());

        // ==========================
        // First event

        when(fileLogic.download(any(), isNull(), isNull())).thenReturn(fileJpg);

        cameraObjectListener.handleCameraObjectEvent(canonObjectEvent);

        Assertions.assertNull(cameraObjectListenerException.get());
        verify(fileLogic, times(0)).downloadCancel(any());

        // ==========================
        // Second event

        when(fileLogic.download(any(), isNull(), isNull())).thenReturn(fileRaw);

        cameraObjectListener.handleCameraObjectEvent(canonObjectEvent2);

        Assertions.assertNull(cameraObjectListenerException.get());

        Assertions.assertEquals(fileJpg, filesSavedOnPc.get(0));
        Assertions.assertEquals(fileRaw, filesSavedOnPc.get(1));
        verify(fileLogic, times(0)).downloadCancel(any());

        // ==========================
        // Test with exception

        when(fileLogic.download(any(), isNull(), isNull())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> cameraObjectListener.handleCameraObjectEvent(canonObjectEvent));

        Assertions.assertEquals(RuntimeException.class, cameraObjectListenerException.get().getClass());
        verify(fileLogic).downloadCancel(any());
    }

    @Test
    void shootLoopLogicV0() {
        final ShootOption option = new ShootOptionBuilder()
            .setShootWithV0(true)
            .setShootWithAF(false)
            .setShootWithNoAF(false)
            .build();

        callMethod("shootLoopLogic", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option);

        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
    }

    @Test
    void shootLoopLogicAF() {
        final ShootOption option = new ShootOptionBuilder()
            .setShootWithV0(false)
            .setShootWithAF(true)
            .setShootWithNoAF(false)
            .build();

        callMethod("shootLoopLogic", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option);

        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

    @Test
    void shootLoopLogicNoAF() {
        final ShootOption option = new ShootOptionBuilder()
            .setShootWithV0(false)
            .setShootWithAF(false)
            .setShootWithNoAF(true)
            .build();

        callMethod("shootLoopLogic", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option);

        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
        verify(cameraLogic, times(0)).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

    @Test
    void shootLoopLogicThrowsAfterAllFails() {
        doThrow(new EdsdkDeviceInvalidErrorException()).when(cameraLogic).sendCommand(eq(fakeCamera), (EdsCameraCommand) any());
        doThrow(new EdsdkDeviceInvalidErrorException()).when(cameraLogic).sendCommand(eq(fakeCamera), (EdsShutterButton) any());

        final int shootAttempts = 4;

        final ShootOption option = new ShootOptionBuilder()
            .setShootWithV0(true)
            .setShootWithAF(true)
            .setShootWithNoAF(true)
            .setShootAttemptCount(shootAttempts)
            .build();

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> callMethod("shootLoopLogic", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option));

        verify(cameraLogic, times(shootAttempts)).sendCommand(eq(fakeCamera), eq(EdsCameraCommand.kEdsCameraCommand_TakePicture));
        verify(cameraLogic, times(shootAttempts)).sendCommand(eq(fakeCamera), eq(EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely));
        verify(cameraLogic, times(shootAttempts)).sendCommand(eq(fakeCamera), eq(EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF));
        // 0 because the first call with EdsShutterButton throws
        verify(cameraLogic, times(0)).sendCommand(eq(fakeCamera), eq(EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF));
    }

    @Test
    void checkLiveViewState() {
        when(liveViewLogic.isLiveViewEnabledByDownloadingOneImage(fakeCamera)).thenReturn(true);

        final ShootOption option = new ShootOptionBuilder()
            .setCheckLiveViewState(true)
            .build();

        final boolean checkLiveViewState = callReturnMethod("checkLiveViewState", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option);

        Assertions.assertTrue(checkLiveViewState);

        verify(liveViewLogic).isLiveViewEnabledByDownloadingOneImage(fakeCamera);
        verify(liveViewLogic).disableLiveView(fakeCamera);
    }

    @Test
    void checkLiveViewStateNoChangeOnNotEnabled() {
        when(liveViewLogic.isLiveViewEnabledByDownloadingOneImage(fakeCamera)).thenReturn(false);

        final ShootOption option = new ShootOptionBuilder()
            .setCheckLiveViewState(true)
            .build();

        final boolean checkLiveViewState = callReturnMethod("checkLiveViewState", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option);

        Assertions.assertFalse(checkLiveViewState);

        verify(liveViewLogic).isLiveViewEnabledByDownloadingOneImage(fakeCamera);
        verify(liveViewLogic, times(0)).disableLiveView(fakeCamera);
    }

    @Test
    void checkLiveViewStateDoesNotCheckStateIfNotRequired() {
        final ShootOption option = new ShootOptionBuilder()
            .setCheckLiveViewState(false)
            .build();

        final boolean checkLiveViewState = callReturnMethod("checkLiveViewState", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, option);

        Assertions.assertFalse(checkLiveViewState);

        verify(liveViewLogic, times(0)).isLiveViewEnabledByDownloadingOneImage(fakeCamera);
        verify(liveViewLogic, times(0)).disableLiveView(fakeCamera);
    }

    @Test
    void restoreLiveViewState() {
        callMethod("restoreLiveViewState", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        verify(liveViewLogic).enableLiveView(fakeCamera);
    }

    @Test
    void shootWithV0() {
        final ShootLogicDefault.ShootResultWrapper resultWrapper = callReturnMethod("shootWithV0", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        Assertions.assertTrue(resultWrapper.isShootSuccess());
        Assertions.assertNotNull(resultWrapper.toString());
    }

    @Test
    void shootWithV0HasExceptionInWrapper() {
        doThrow(new EdsdkDeviceInvalidErrorException()).when(cameraLogic).sendCommand(eq(fakeCamera), (EdsCameraCommand) any());

        final ShootLogicDefault.ShootResultWrapper resultWrapper = callReturnMethod("shootWithV0", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        Assertions.assertFalse(resultWrapper.isShootSuccess());
        Assertions.assertTrue(resultWrapper.getException().isPresent());
        Assertions.assertEquals(EdsdkError.EDS_ERR_DEVICE_INVALID, resultWrapper.getException().get().getEdsdkError());
        Assertions.assertNotNull(resultWrapper.toString());
    }

    @Test
    void shootWithNoAF() {
        final ShootLogicDefault.ShootResultWrapper resultWrapper = callReturnMethod("shootWithNoAF", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        Assertions.assertTrue(resultWrapper.isShootSuccess());
    }

    @Test
    void shootWithNoAFHasExceptionInWrapper() {
        doThrow(new EdsdkDeviceInvalidErrorException()).when(cameraLogic).sendCommand(eq(fakeCamera), (EdsShutterButton) any());

        final ShootLogicDefault.ShootResultWrapper resultWrapper = callReturnMethod("shootWithNoAF", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        Assertions.assertFalse(resultWrapper.isShootSuccess());
        Assertions.assertTrue(resultWrapper.getException().isPresent());
        Assertions.assertEquals(EdsdkError.EDS_ERR_DEVICE_INVALID, resultWrapper.getException().get().getEdsdkError());
    }

    @Test
    void shootWithAF() {
        final ShootLogicDefault.ShootResultWrapper resultWrapper = callReturnMethod("shootWithAF", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        Assertions.assertTrue(resultWrapper.isShootSuccess());
    }

    @Test
    void shootWithAFHasExceptionInWrapper() {
        doThrow(new EdsdkDeviceInvalidErrorException()).when(cameraLogic).sendCommand(eq(fakeCamera), (EdsShutterButton) any());

        final ShootLogicDefault.ShootResultWrapper resultWrapper = callReturnMethod("shootWithAF", new Class[]{EdsCameraRef.class, ShootOption.class}, fakeCamera, null);

        Assertions.assertFalse(resultWrapper.isShootSuccess());
        Assertions.assertTrue(resultWrapper.getException().isPresent());
        Assertions.assertEquals(EdsdkError.EDS_ERR_DEVICE_INVALID, resultWrapper.getException().get().getEdsdkError());
    }

    @Test
    void fetchEvents() {
        when(edsdkLibrary().EdsGetEvent()).thenReturn(new NativeLong(0));

        callMethod("fetchEvents", null);

        verify(edsdkLibrary()).EdsGetEvent();
    }

    @Test
    void fetchEventsIgnoreReturnErrorCode() {
        when(edsdkLibrary().EdsGetEvent()).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        callMethod("fetchEvents", null);

        verify(edsdkLibrary()).EdsGetEvent();
    }

    private void callMethod(final String methodName, @Nullable Class<?>[] parameterTypes, Object... args) {
        TestUtil.callMethod(MockFactory.initialCanonFactory.getShootLogic(), methodName, parameterTypes, args);
    }

    private <T> T callReturnMethod(final String methodName, @Nullable Class<?>[] parameterTypes, Object... args) {
        return TestUtil.callReturnMethod(MockFactory.initialCanonFactory.getShootLogic(), methodName, parameterTypes, args);
    }
}
