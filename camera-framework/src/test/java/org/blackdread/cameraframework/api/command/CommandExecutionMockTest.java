package org.blackdread.cameraframework.api.command;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
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
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInternalErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
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
class CommandExecutionMockTest extends AbstractMockTest {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutionMockTest.class);

    private EdsCameraRef fakeCamera;

    private EdsImageRef fakeImage;

    private final NativeLong noErrorLong = new NativeLong(0);

    private NativeLong expectedErrorLong;

    private EdsdkError expectedError;

    private Class<? extends EdsdkErrorException> expectedErrorType;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        fakeImage = new EdsImageRef();

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

        command.setTargetRef(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNotNull(o);
            final List o1 = (List) o;
            Assertions.assertTrue(o1.isEmpty());
            verify(CanonFactory.propertyDescShortcutLogic()).getPictureStyleDesc(fakeCamera);
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
