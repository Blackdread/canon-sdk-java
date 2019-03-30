package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsShutterButton;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/30.</p>
 *
 * @author Yoann CAPLAIN
 */
class ShootLogicDefaultTest extends AbstractMockTest {

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

        verify(cameraLogic).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
    }

    @Test
    void shootAF() {
        spyShootLogic.shootAF(fakeCamera);

        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

    @Test
    void shootNoAF() {
        spyShootLogic.shootNoAF(fakeCamera);

        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        verify(cameraLogic).sendCommand(fakeCamera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

//    @Test
//    void shoot() {
//    }

//    @Test
//    void handleObjectEvent() {
//    }

//    @Test
//    void shootLoopLogic() {
//    }

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
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getShootLogic().getClass().getDeclaredMethod(methodName, parameterTypes);
            handleMethod.setAccessible(true);
            handleMethod.invoke(MockFactory.initialCanonFactory.getShootLogic(), args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
        }
    }

    private <T> T callReturnMethod(final String methodName, @Nullable Class<?>[] parameterTypes, Object... args) {
        try {
            final Method handleMethod = MockFactory.initialCanonFactory.getShootLogic().getClass().getDeclaredMethod(methodName, parameterTypes);
            handleMethod.setAccessible(true);
            return (T) handleMethod.invoke(MockFactory.initialCanonFactory.getShootLogic(), args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Assertions.fail("Failed reflection", e);
            throw new IllegalStateException("do no reach");
        }
    }
}
