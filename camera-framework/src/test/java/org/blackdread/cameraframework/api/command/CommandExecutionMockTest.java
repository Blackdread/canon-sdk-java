package org.blackdread.cameraframework.api.command;

import com.google.common.collect.ImmutableSet;
import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsVolumeRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.TestUtil;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsAv;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.UnsupportedTargetTypeException;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInternalErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

/**
 * Test execution of commands, make sure logic stays unchanged and match expected behavior
 * <p>Created on 2019/04/02.</p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings("unchecked")
class CommandExecutionMockTest extends AbstractMockTest {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutionMockTest.class);

    private static final Set<TargetRefType> ALL_TARGET_REF_TYPES = ImmutableSet.copyOf(TargetRefType.values());

    private EdsCameraRef fakeCamera;

    private EdsImageRef fakeImage;

    private EdsEvfImageRef fakeEvfImage;

    private EdsVolumeRef fakeVolume;

    private EdsDirectoryItemRef fakeDirItem;

    private final NativeLong noErrorLong = new NativeLong(0);

    private NativeLong expectedErrorLong;

    private EdsdkError expectedError;

    private Class<? extends EdsdkErrorException> expectedErrorType;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();
        fakeImage = new EdsImageRef();
        fakeEvfImage = new EdsEvfImageRef();
        fakeVolume = new EdsVolumeRef();
        fakeDirItem = new EdsDirectoryItemRef();

        expectedErrorLong = new NativeLong(EdsdkError.EDS_ERR_DEVICE_INTERNAL_ERROR.value());
        expectedError = EdsdkError.EDS_ERR_DEVICE_INTERNAL_ERROR;
        expectedErrorType = EdsdkDeviceInternalErrorException.class;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRegisterCameraAddedEventCommand() {
        final CanonCommand command = new RegisterCameraAddedEventCommand();

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraAddedEventLogic()).registerCameraAddedEvent();
        });
    }

    @Test
    void testRegisterObjectEventCommand() {
        final CanonCommand command = new RegisterObjectEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraObjectEventLogic()).registerCameraObjectEvent(fakeCamera);
        });
    }

    @Test
    void testRegisterPropertyEventCommand() {
        final CanonCommand command = new RegisterPropertyEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraPropertyEventLogic()).registerCameraPropertyEvent(fakeCamera);
        });
    }

    @Test
    void testRegisterStateEventCommand() {
        final CanonCommand command = new RegisterStateEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraStateEventLogic()).registerCameraStateEvent(fakeCamera);
        });
    }

    @Test
    void testUnRegisterObjectEventCommand() {
        final CanonCommand command = new UnRegisterObjectEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraObjectEventLogic()).unregisterCameraObjectEvent(fakeCamera);
        });
    }

    @Test
    void testUnRegisterPropertyEventCommand() {
        final CanonCommand command = new UnRegisterPropertyEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraPropertyEventLogic()).unregisterCameraPropertyEvent(fakeCamera);
        });
    }

    @Test
    void testUnRegisterStateEventCommand() {
        final CanonCommand command = new UnRegisterStateEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraStateEventLogic()).unregisterCameraStateEvent(fakeCamera);
        });
    }

    @Test
    void testInitializeSdkCommand() {
        when(CanonFactory.edsdkLibrary().EdsInitializeSDK()).thenReturn(noErrorLong);

        final CanonCommand command = new InitializeSdkCommand();

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary()).EdsInitializeSDK();
        });
    }

    @Test
    void testInitializeSdkCommandThrowsOnError() {
        when(CanonFactory.edsdkLibrary().EdsInitializeSDK()).thenReturn(expectedErrorLong);

        final CanonCommand command = new InitializeSdkCommand();

        runAndAssertThrows(command, expectedErrorType, (canonCommand, throwable) -> {
            verify(CanonFactory.edsdkLibrary()).EdsInitializeSDK();
        });
    }

    @Test
    void testTerminateSdkCommand() {
        when(CanonFactory.edsdkLibrary().EdsTerminateSDK()).thenReturn(noErrorLong);

        final CanonCommand command = new TerminateSdkCommand();

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary()).EdsTerminateSDK();
        });
    }

    @Test
    void testTerminateSdkCommandThrowsOnError() {
        when(CanonFactory.edsdkLibrary().EdsTerminateSDK()).thenReturn(expectedErrorLong);

        final CanonCommand command = new TerminateSdkCommand();

        runAndAssertThrows(command, expectedErrorType, (canonCommand, throwable) -> {
            verify(CanonFactory.edsdkLibrary()).EdsTerminateSDK();
        });
    }

    @Test
    void testCameraCommand() {
        final CanonCommand command = new CameraCommand(EdsCameraCommand.kEdsCameraCommand_TakePicture);

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraLogic()).sendCommand(fakeCamera, EdsCameraCommand.kEdsCameraCommand_TakePicture, 0);
        });
    }

    @Test
    void testOpenSessionCommand() {
        final CanonCommand command = new OpenSessionCommand();

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraLogic()).openSession();
        });
    }

    @Test
    void testOpenSessionCommandWithOption() {
        final CanonCommand command = new OpenSessionCommand(OpenSessionOption.DEFAULT_OPEN_SESSION_OPTION);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraLogic()).openSession(OpenSessionOption.DEFAULT_OPEN_SESSION_OPTION);
        });
    }

    @Test
    void testCloseSessionCommand() {
        final CloseSessionOption option = new CloseSessionOptionBuilder().setCameraRef(fakeCamera).build();
        final CanonCommand command = new CloseSessionCommand(option);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraLogic()).closeSession(option);
        });
    }

    @Test
    void testFetchEventCommand() {
        when(CanonFactory.edsdkLibrary().EdsGetEvent()).thenReturn(noErrorLong);

        final CanonCommand command = new FetchEventCommand();

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary()).EdsGetEvent();
        });
    }

    @Test
    void testFetchEventCommandMany() {
        when(CanonFactory.edsdkLibrary().EdsGetEvent()).thenReturn(noErrorLong);

        final CanonCommand command = new FetchEventCommand(true, 5);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary(), times(5)).EdsGetEvent();
        });
    }

    @Test
    void testFetchEventCommandSkipError() {
        when(CanonFactory.edsdkLibrary().EdsGetEvent()).thenReturn(noErrorLong);

        when(CanonFactory.edsdkLibrary().EdsGetEvent()).thenReturn(expectedErrorLong);

        final CanonCommand command = new FetchEventCommand(false, 3);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary(), times(3)).EdsGetEvent();
        });
    }

    @Test
    void testFetchEventCommandThrowsOnError() {
        when(CanonFactory.edsdkLibrary().EdsGetEvent()).thenReturn(expectedErrorLong);

        final CanonCommand command = new FetchEventCommand();

        runAndAssertThrows(command, expectedErrorType, (canonCommand, throwable) -> {
            verify(CanonFactory.edsdkLibrary()).EdsGetEvent();
        });
    }

    @Test
    void testIsConnectedCommand() {
        when(CanonFactory.cameraLogic().isConnected(fakeCamera)).thenReturn(true);

        final CanonCommand command = new IsConnectedCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            Assertions.assertTrue((boolean) o);
            verify(CanonFactory.cameraLogic()).isConnected(fakeCamera);
        });
    }

    @Test
    void testReleaseCommand() {
        when(CanonFactory.edsdkLibrary().EdsRelease(fakeCamera)).thenReturn(noErrorLong);

        final CanonCommand command = new ReleaseCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary()).EdsRelease(fakeCamera);
        });
    }

    @Test
    void testReleaseCommandThrowsOnError() {
        when(CanonFactory.edsdkLibrary().EdsRelease(fakeCamera)).thenReturn(expectedErrorLong);

        final CanonCommand command = new ReleaseCommand(fakeCamera);

        runAndAssertThrows(command, expectedErrorType, (canonCommand, throwable) -> {
            verify(CanonFactory.edsdkLibrary()).EdsRelease(fakeCamera);
        });
    }

    @Test
    void testRetainCommand() {
        when(CanonFactory.edsdkLibrary().EdsRetain(fakeCamera)).thenReturn(noErrorLong);

        final CanonCommand command = new RetainCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.edsdkLibrary()).EdsRetain(fakeCamera);
        });
    }

    @Test
    void testRetainCommandThrowsOnError() {
        when(CanonFactory.edsdkLibrary().EdsRetain(fakeCamera)).thenReturn(expectedErrorLong);

        final CanonCommand command = new RetainCommand(fakeCamera);

        runAndAssertThrows(command, expectedErrorType, (canonCommand, throwable) -> {
            verify(CanonFactory.edsdkLibrary()).EdsRetain(fakeCamera);
        });
    }

    @Test
    void testShootCommand() throws InterruptedException {
        when(CanonFactory.shootLogic().shoot(fakeCamera)).thenReturn(Collections.emptyList());

        final CanonCommand command = new ShootCommand();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            try {
                verify(CanonFactory.shootLogic()).shoot(fakeCamera);
                verify(CanonFactory.shootLogic(), times(0)).shoot(eq(fakeCamera), any());
            } catch (InterruptedException e) {
                Assertions.fail(e);
            }
        });
    }

    @Test
    void testShootCommandWithOption() throws InterruptedException {
        final ShootOption option = new ShootOptionBuilder()
            .build();

        when(CanonFactory.shootLogic().shoot(fakeCamera, option)).thenReturn(Collections.emptyList());

        final CanonCommand command = new ShootCommand(option);

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            try {
                verify(CanonFactory.shootLogic(), times(0)).shoot(fakeCamera);
                verify(CanonFactory.shootLogic()).shoot(fakeCamera, option);
            } catch (InterruptedException e) {
                Assertions.fail(e);
            }
        });
    }

    @Test
    void testStatusCommand() {
        final CanonCommand command = new StatusCommand(EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock);

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraLogic()).sendStatusCommand(fakeCamera, EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock);
        });
    }

    @Test
    void testSetPropertyByNativeEnum() {
        final CanonCommand command = new SetPropertyCommand.Aperture(EdsAv.kEdsAv_1_1);

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.propertySetLogic()).setPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Av, EdsAv.kEdsAv_1_1);
        });
    }

    @Test
    void testSetPropertyByValue() {
        final CanonCommand command = new SetPropertyCommand.JpegQuality(5);

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.propertySetLogic()).setPropertyDataAdvanced(fakeCamera, EdsPropertyID.kEdsPropID_JpegQuality, 5);
        });
    }

    @Test
    void testBegin() {
        final CanonCommand command = new LiveViewCommand.Begin();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.liveViewLogic()).beginLiveView(fakeCamera);
        });
    }

    @Test
    void testEnd() {
        final CanonCommand command = new LiveViewCommand.End();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.liveViewLogic()).endLiveView(fakeCamera);
        });
    }

    @Test
    void testDownload() {
        when(CanonFactory.liveViewLogic().getLiveViewImage(fakeCamera))
            .thenReturn(Mockito.mock(BufferedImage.class));

        final CanonCommand command = new LiveViewCommand.Download();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            verify(CanonFactory.liveViewLogic()).getLiveViewImage(fakeCamera);
        });
    }

    @Test
    void testDownloadBuffer() {
        when(CanonFactory.liveViewLogic().getLiveViewImageBuffer(fakeCamera))
            .thenReturn(new byte[0]);

        final CanonCommand command = new LiveViewCommand.DownloadBuffer();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            verify(CanonFactory.liveViewLogic()).getLiveViewImageBuffer(fakeCamera);
        });
    }

    @Test
    void testIsLiveViewEnabled() {
        when(CanonFactory.liveViewLogic().isLiveViewEnabled(fakeCamera))
            .thenReturn(true);

        final CanonCommand command = new LiveViewCommand.IsLiveViewEnabled();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            Assertions.assertTrue((Boolean) o);
            verify(CanonFactory.liveViewLogic()).isLiveViewEnabled(fakeCamera);
        });
    }

    @Test
    void testIsLiveViewActive() {
        when(CanonFactory.liveViewLogic().isLiveViewEnabledByDownloadingOneImage(fakeCamera))
            .thenReturn(true);

        final CanonCommand command = new LiveViewCommand.IsLiveViewActive();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            Assertions.assertTrue((Boolean) o);
            verify(CanonFactory.liveViewLogic()).isLiveViewEnabledByDownloadingOneImage(fakeCamera);
        });
    }

    @Test
    void testApertureDesc() {
        final CanonCommand command = new GetPropertyDescCommand.ApertureDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getAvDesc(fakeCamera);
        });
    }

    @Test
    void testAEModeDesc() {
        final CanonCommand command = new GetPropertyDescCommand.AEModeDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getAEModeDesc(fakeCamera);
        });
    }

    @Test
    void testAEModeSelectDesc() {
        final CanonCommand command = new GetPropertyDescCommand.AEModeSelectDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getAEModeSelectDesc(fakeCamera);
        });
    }

    @Test
    void testColorTemperatureDesc() {
        final CanonCommand command = new GetPropertyDescCommand.ColorTemperatureDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getColorTemperatureDesc(fakeCamera);
        });
    }

    @Test
    void testDriveModeDesc() {
        final CanonCommand command = new GetPropertyDescCommand.DriveModeDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getDriveModeDesc(fakeCamera);
        });
    }

    @Test
    void testExposureCompensationDesc() {
        final CanonCommand command = new GetPropertyDescCommand.ExposureCompensationDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getExposureCompensationDesc(fakeCamera);
        });
    }

    @Test
    void testImageQualityDesc() {
        final CanonCommand command = new GetPropertyDescCommand.ImageQualityDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getImageQualityDesc(fakeCamera);
        });
    }

    @Test
    void testIsoSpeedDesc() {
        final CanonCommand command = new GetPropertyDescCommand.IsoSpeedDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getISOSpeedDesc(fakeCamera);
        });
    }

    @Test
    void testLiveViewAutoFocusModeDesc() {
        final CanonCommand command = new GetPropertyDescCommand.LiveViewAutoFocusModeDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getEvfAFModeDesc(fakeCamera);
        });
    }

    @Test
    void testLiveViewColorTemperatureDesc() {
        final CanonCommand command = new GetPropertyDescCommand.LiveViewColorTemperatureDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getEvfColorTemperatureDesc(fakeCamera);
        });
    }

    @Test
    void testLiveViewWhiteBalanceDesc() {
        final CanonCommand command = new GetPropertyDescCommand.LiveViewWhiteBalanceDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getEvfWhiteBalanceDesc(fakeCamera);
        });
    }

    @Test
    void testMeteringModeDesc() {
        final CanonCommand command = new GetPropertyDescCommand.MeteringModeDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getMeteringModeDesc(fakeCamera);
        });
    }

    @Test
    void testPictureStyleDesc() {
        final CanonCommand command = new GetPropertyDescCommand.PictureStyleDesc();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (canonCommand, o) -> {
            for (final Object result : o) {
                Assertions.assertNotNull(result);
                final List o1 = (List) result;
                Assertions.assertTrue(o1.isEmpty());
            }
            verify(CanonFactory.propertyDescShortcutLogic()).getPictureStyleDesc(fakeCamera);
            verify(CanonFactory.propertyDescShortcutLogic()).getPictureStyleDesc(fakeImage);
        });
    }

    @Test
    void testShutterSpeedDesc() {
        final CanonCommand command = new GetPropertyDescCommand.ShutterSpeedDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getTvDesc(fakeCamera);
        });
    }

    @Test
    void testWhiteBalanceDesc() {
        final CanonCommand command = new GetPropertyDescCommand.WhiteBalanceDesc();

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getWhiteBalanceDesc(fakeCamera);
        });
    }

    @Test
    void testAperture() {
        final CanonCommand command = new GetPropertyCommand.Aperture();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getAv(fakeCamera);
        });
    }

    @Test
    void testApertureImage() {
        final CanonCommand command = new GetPropertyCommand.ApertureImage();

        runAndAssertCommand(TargetRefType.IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getAv(fakeImage);
        });
    }

    @Test
    void testArtist() {
        final CanonCommand command = new GetPropertyCommand.Artist();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getArtist(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getArtist(fakeImage);
        });
    }

    @Test
    void testAutoFocusMode() {
        final CanonCommand command = new GetPropertyCommand.AutoFocusMode();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getAFMode(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getAFMode(fakeImage);
        });
    }

    @Test
    void testAvailableShots() {
        final CanonCommand command = new GetPropertyCommand.AvailableShots();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getAvailableShots(fakeCamera);
        });
    }

    @Test
    void testBatteryLevel() {
        final CanonCommand command = new GetPropertyCommand.BatteryLevel();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getBatteryLevel(fakeCamera);
        });
    }

    @Test
    void testBodyIDEx() {
        final CanonCommand command = new GetPropertyCommand.BodyIDEx();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getBodyIDEx(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getBodyIDEx(fakeImage);
        });
    }

    @Test
    void testBracket() {
        final CanonCommand command = new GetPropertyCommand.Bracket();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getBracket(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getBracket(fakeImage);
        });
    }

    @Test
    void testColorSpace() {
        final CanonCommand command = new GetPropertyCommand.ColorSpace();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getColorSpace(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getColorSpace(fakeImage);
        });
    }

    @Test
    void testColorTemperature() {
        final CanonCommand command = new GetPropertyCommand.ColorTemperature();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getColorTemperature(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getColorTemperature(fakeImage);
        });
    }

    @Test
    void testCopyright() {
        final CanonCommand command = new GetPropertyCommand.Copyright();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getCopyright(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getCopyright(fakeImage);
        });
    }

    @Test
    void testCurrentFolder() {
        final CanonCommand command = new GetPropertyCommand.CurrentFolder();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getCurrentFolder(fakeCamera);
        });
    }

    @Test
    void testCurrentStorage() {
        final CanonCommand command = new GetPropertyCommand.CurrentStorage();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getCurrentStorage(fakeCamera);
        });
    }

    @Test
    void testDateTime() {
        final CanonCommand command = new GetPropertyCommand.DateTime();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getDateTime(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getDateTime(fakeImage);
        });
    }

    @Test
    void testFlashCompensation() {
        final CanonCommand command = new GetPropertyCommand.FlashCompensation();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getFlashCompensation(fakeCamera);
        });
    }

    @Test
    void testFirmwareVersion() {
        final CanonCommand command = new GetPropertyCommand.FirmwareVersion();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getFirmwareVersion(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getFirmwareVersion(fakeImage);
        });
    }

    @Test
    void testFocusInfo() {
        final CanonCommand command = new GetPropertyCommand.FocusInfo();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getFocusInfo(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getFocusInfo(fakeImage);
        });
    }

    @Test
    void testHardDriveDirectoryStructure() {
        final CanonCommand command = new GetPropertyCommand.HardDriveDirectoryStructure();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getHDDirectoryStructure(fakeCamera);
        });
    }

    @Test
    void testImageQuality() {
        final CanonCommand command = new GetPropertyCommand.ImageQuality();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getImageQuality(fakeCamera);
        });
    }

    @Test
    void testIsoSpeed() {
        final CanonCommand command = new GetPropertyCommand.IsoSpeed();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getISOSpeed(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getISOSpeed(fakeImage);
        });
    }

    @Test
    void testJpegQuality() {
        final CanonCommand command = new GetPropertyCommand.JpegQuality();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getJpegQuality(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getJpegQuality(fakeImage);
        });
    }

    @Test
    void testLensName() {
        final CanonCommand command = new GetPropertyCommand.LensName();

        runAndAssertCommand(TargetRefType.IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getLensName(fakeImage);
        });
    }

    @Test
    void testLensStatus() {
        final CanonCommand command = new GetPropertyCommand.LensStatus();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getLensStatus(fakeCamera);
        });
    }

    @Test
    void testLensStatusAsBoolean() {
        final CanonCommand command = new GetPropertyCommand.LensStatusAsBoolean();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getLensStatus(fakeCamera);
        });
    }

    @Test
    void testLiveViewAutoFocusMode() {
        final CanonCommand command = new GetPropertyCommand.LiveViewAutoFocusMode();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfAFMode(fakeCamera);
        });
    }

    @Test
    void testLiveViewColorTemperature() {
        final CanonCommand command = new GetPropertyCommand.LiveViewColorTemperature();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfColorTemperature(fakeCamera);
        });
    }

    @Test
    void testLiveViewCoordinateSystem() {
        final CanonCommand command = new GetPropertyCommand.LiveViewCoordinateSystem();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfCoordinateSystem(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewImagePosition() {
        final CanonCommand command = new GetPropertyCommand.LiveViewImagePosition();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfImagePosition(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewHistogram() {
        final CanonCommand command = new GetPropertyCommand.LiveViewHistogram();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetLogic()).getPropertyData(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_Histogram);
        });
    }

    @Test
    void testLiveViewHistogramB() {
        final CanonCommand command = new GetPropertyCommand.LiveViewHistogramB();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfHistogramB(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewHistogramG() {
        final CanonCommand command = new GetPropertyCommand.LiveViewHistogramG();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfHistogramG(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewHistogramR() {
        final CanonCommand command = new GetPropertyCommand.LiveViewHistogramR();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfHistogramR(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewHistogramY() {
        final CanonCommand command = new GetPropertyCommand.LiveViewHistogramY();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfHistogramY(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewHistogramStatus() {
        final CanonCommand command = new GetPropertyCommand.LiveViewHistogramStatus();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfHistogramStatus(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewOutputDevice() {
        when(CanonFactory.propertyGetLogic().getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, 0))
            .thenReturn(EdsEvfOutputDevice.kEdsEvfOutputDevice_TFT.value().longValue());

        final CanonCommand command = new GetPropertyCommand.LiveViewOutputDevice();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetLogic()).getPropertyData(fakeCamera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, 0);
        });
    }

    @Test
    void testLiveViewWhiteBalance() {
        final CanonCommand command = new GetPropertyCommand.LiveViewWhiteBalance();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfWhiteBalance(fakeCamera);
        });
    }

    @Test
    void testLiveViewZoomPosition() {
        final CanonCommand command = new GetPropertyCommand.LiveViewZoomPosition();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvfZoomPosition(fakeEvfImage);
        });
    }

    @Test
    void testLiveViewZoomRectangle() {
        final CanonCommand command = new GetPropertyCommand.LiveViewZoomRectangle();

        runAndAssertCommand(TargetRefType.EVF_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getEvf_ZoomRect(fakeEvfImage);
        });
    }

    @Test
    void testMeteringMode() {
        final CanonCommand command = new GetPropertyCommand.MeteringMode();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getMeteringMode(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getMeteringMode(fakeImage);
        });
    }

    @Test
    void testOwnerName() {
        final CanonCommand command = new GetPropertyCommand.OwnerName();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getOwnerName(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getOwnerName(fakeImage);
        });
    }

    @Test
    void testPictureStyle() {
        final CanonCommand command = new GetPropertyCommand.PictureStyle();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getPictureStyle(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getPictureStyle(fakeImage);
        });
    }

// TODO test later, not sure if correct
//    @Test
//    void testPictureStyleDescription() {
//        final CanonCommand command = new GetPropertyCommand.PictureStyleDescription();
//
//        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
//            verify(CanonFactory.propertyGetShortcutLogic()).getPictureStyleDesc(fakeCamera);
//            verify(CanonFactory.propertyGetShortcutLogic()).getPictureStyleDesc(fakeImage);
//        });
//    }

    @Test
    void testProductName() {
        final CanonCommand command = new GetPropertyCommand.ProductName();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getProductName(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getProductName(fakeImage);
        });
    }

    @Test
    void testSaveTo() {
        final CanonCommand command = new GetPropertyCommand.SaveTo();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getSaveTo(fakeCamera);
        });
    }

    @Test
    void testShootingMode() {
        final CanonCommand command = new GetPropertyCommand.ShootingMode();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getAEMode(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getAEMode(fakeImage);
        });
    }

    @Test
    void testShutterSpeed() {
        final CanonCommand command = new GetPropertyCommand.ShutterSpeed();

        runAndAssertCommand(TargetRefType.CAMERA_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getTv(fakeCamera);
        });
    }

    @Test
    void testShutterSpeedImage() {
        final CanonCommand command = new GetPropertyCommand.ShutterSpeedImage();

        runAndAssertCommand(TargetRefType.IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getTv(fakeImage);
        });
    }

    @Test
    void testWhiteBalance() {
        final CanonCommand command = new GetPropertyCommand.WhiteBalance();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getWhiteBalance(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getWhiteBalance(fakeImage);
        });
    }

    @Test
    void testWhiteBalanceBracket() {
        final CanonCommand command = new GetPropertyCommand.WhiteBalanceBracket();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getWhiteBalanceBracket(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getWhiteBalanceBracket(fakeImage);
        });
    }

    @Test
    void testWhiteBalanceShift() {
        final CanonCommand command = new GetPropertyCommand.WhiteBalanceShift();

        runAndAssertCommand(TargetRefType.CAMERA_IMAGE_ONLY, command, (commands, results) -> {
            verify(CanonFactory.propertyGetShortcutLogic()).getWhiteBalanceShift(fakeCamera);
            verify(CanonFactory.propertyGetShortcutLogic()).getWhiteBalanceShift(fakeImage);
        });
    }

    /*
    @Test
    void test() {
        final CanonCommand command = new RegisterObjectEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraObjectEventLogic()).registerCameraObjectEvent(fakeCamera);
        });
    }
    //*/

    private static <T extends CanonCommand<R>, R> void runAndAssertCommand(final T command, final Consumer<T> assertConsumer) {
        command.run();

        final R ignored = getResult(command);

        assertConsumer.accept(command);
    }

    private static <T extends CanonCommand<R>, R> void runAndAssertCommand(final T command, final BiConsumer<T, R> assertConsumer) {
        command.run();

        final R result = getResult(command);

        assertConsumer.accept(command, result);
    }

    private <T extends CanonCommand<R>, R> void runAndAssertCommand(final Set<TargetRefType> supportedType, final T command, final BiConsumer<List<T>, List<R>> assertConsumer) {
        final ArrayList<T> commands = new ArrayList<>(supportedType.size());
        final ArrayList<R> results = new ArrayList<>(supportedType.size());
        for (final TargetRefType targetRefType : supportedType) {
            final T copy = (T) command.copy();
            setTargetRef(copy, targetRefType);

            copy.run();

            final R result = getResult(copy);

            commands.add(copy);
            results.add(result);
        }

        assertConsumer.accept(commands, results);

        // test not supported types
        for (final TargetRefType targetRefType : ALL_TARGET_REF_TYPES) {
            if (supportedType.contains(targetRefType))
                continue;
            final T copy = (T) command.copy();
            setTargetRef(copy, targetRefType);

            runAndAssertThrows(copy, UnsupportedTargetTypeException.class, (t, throwable) -> {
                // do nothing
            });
        }
    }

    private void setTargetRef(final CanonCommand command, final TargetRefType targetRefType) {
        switch (targetRefType) {
            case CAMERA:
                command.setTargetRef(fakeCamera);
                break;
            case IMAGE:
                command.setTargetRef(fakeImage);
                break;
            case EVF_IMAGE:
                command.setTargetRef(fakeEvfImage);
                break;
            case VOLUME:
                command.setTargetRef(fakeVolume);
                break;
            case DIRECTORY_ITEM:
                command.setTargetRef(fakeDirItem);
                break;
            default:
                throw new IllegalStateException("Missing switch");
        }
    }

    private static <T extends CanonCommand<R>, R> void runAndAssertThrows(final T command, final Class<? extends Throwable> expectedType, final BiConsumer<T, Throwable> assertConsumer) {
        try {
            command.run();
            getResult(command);
            Assertions.fail(String.format("Expected %s but nothing was thrown", expectedType.getSimpleName()));
        } catch (Throwable t) {
            if (ExecutionException.class.isInstance(t)) {
                t = t.getCause();
            }

            if (AssertionError.class.isInstance(t)) {
                TestUtil.throwUnchecked(t);
            }
            if (expectedType.isInstance(t)) {
                assertConsumer.accept(command, t);
                return;
            }
            Assertions.fail(String.format("Unexpected exception type thrown: %s", t.getClass().getSimpleName()));
        }
    }

    private static <R> R getResult(final CanonCommand<R> command) {
        try {
            return command.get();
        } catch (InterruptedException | ExecutionException e) {
            TestUtil.throwUnchecked(e);
            throw new RuntimeException("do not reach");
        }
    }
}
