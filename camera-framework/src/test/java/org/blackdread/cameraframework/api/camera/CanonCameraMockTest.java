package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.command.*;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
import org.blackdread.cameraframework.api.command.decorator.builder.CommandBuilderReusable;
import org.blackdread.cameraframework.api.command.decorator.impl.TimeoutCommandDecorator;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;

import static org.blackdread.cameraframework.api.TestUtil.throwUnchecked;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/04/01.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonCameraMockTest extends AbstractMockTest {

    private static final Logger log = LoggerFactory.getLogger(CanonCameraMockTest.class);

    private CanonCamera cameraDefaultConstructor;
    private CanonCamera cameraWithSerialNumber;

    private EdsCameraRef fakeCamera;

    @BeforeEach
    void setUp() {
        cameraDefaultConstructor = new CanonCamera();
        cameraWithSerialNumber = new CanonCamera("asdasd1651518");

        fakeCamera = new EdsCameraRef();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void applyTarget() {
        CanonCommand<String> command = new GenericCommand<>(() -> "value");
        command = cameraDefaultConstructor.applyTarget(command);
        Assertions.assertFalse(command.getTargetRef().isPresent());

        cameraDefaultConstructor.setCameraRef(fakeCamera);
        command = cameraDefaultConstructor.applyTarget(command);
        Assertions.assertTrue(command.getTargetRef().isPresent());
    }

    @Test
    void applyExtraOptions() {
        CanonCommand<String> command = new GenericCommand<>(() -> "value");
        command = cameraDefaultConstructor.applyExtraOptions(command);
        Assertions.assertTrue(command.getTimeout().isPresent()); // Commands have default timeouts

        final Duration defaultTimeout = Duration.ofSeconds(5);
        cameraDefaultConstructor.setDefaultTimeout(defaultTimeout);
        command = cameraDefaultConstructor.applyExtraOptions(command);
        Assertions.assertTrue(command.getTimeout().isPresent());
        Assertions.assertEquals(defaultTimeout, command.getTimeout().get());
    }

    @Test
    void applyDefaultCommandDecoration() {
        final CanonCommand<String> baseCommand = new GenericCommand<>(() -> "value");
        CanonCommand<String> commandDecoration = cameraDefaultConstructor.applyDefaultCommandDecoration(baseCommand);

        Assertions.assertEquals(baseCommand, commandDecoration);


        final Duration defaultTimeout = Duration.ofSeconds(5);
        final CommandBuilderReusable.ReusableBuilder<String> builder = new CommandBuilderReusable.ReusableBuilder<String>()
            .setCanonCommand(baseCommand)
            .timeout(defaultTimeout);

        cameraDefaultConstructor.setCommandBuilderReusable(builder);

        commandDecoration = cameraDefaultConstructor.applyDefaultCommandDecoration(baseCommand);
        Assertions.assertNotEquals(baseCommand, commandDecoration);

        Assertions.assertTrue(commandDecoration instanceof TimeoutCommandDecorator);
    }

    @Test
    void dispatchCommand() {
    }

    @Test
    void getCameraRef() {
        Assertions.assertFalse(cameraDefaultConstructor.getCameraRef().isPresent());
        cameraDefaultConstructor.setCameraRef(fakeCamera);
        Assertions.assertTrue(cameraDefaultConstructor.getCameraRef().isPresent());
    }

    @Test
    void getCameraRefInternal() {
        Assertions.assertThrows(IllegalStateException.class, () -> cameraDefaultConstructor.getCameraRefInternal());
        cameraDefaultConstructor.setCameraRef(fakeCamera);
        Assertions.assertNotNull(cameraDefaultConstructor.getCameraRefInternal());
        cameraDefaultConstructor.setCameraRef(null);
        Assertions.assertThrows(IllegalStateException.class, () -> cameraDefaultConstructor.getCameraRefInternal());
    }

    @Test
    void setCameraRef() {
        Assertions.assertFalse(cameraDefaultConstructor.getCameraRef().isPresent());
        cameraDefaultConstructor.setCameraRef(fakeCamera);
        Assertions.assertTrue(cameraDefaultConstructor.getCameraRef().isPresent());
        cameraDefaultConstructor.setCameraRef(null);
        Assertions.assertFalse(cameraDefaultConstructor.getCameraRef().isPresent());
    }

    @Test
    void getSerialNumber() {
        Assertions.assertFalse(cameraDefaultConstructor.getSerialNumber().isPresent());
        Assertions.assertTrue(cameraWithSerialNumber.getSerialNumber().isPresent());
    }

    @Test
    void setSerialNumber() {
        cameraDefaultConstructor.setSerialNumber("any");
        Assertions.assertEquals("any", cameraDefaultConstructor.getSerialNumber().get());
    }

    @Test
    void setSerialNumberCannotBeChanged() {
        cameraDefaultConstructor.setSerialNumber("any");
        Assertions.assertThrows(IllegalStateException.class, () -> cameraDefaultConstructor.setSerialNumber("any"));

        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.setSerialNumber("any"));
    }

    @Test
    void setSerialNumberThrowsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> cameraDefaultConstructor.setSerialNumber(null));
    }

    @Test
    void getAndSetCommandBuilderReusable() {
        Assertions.assertFalse(cameraDefaultConstructor.getCommandBuilderReusable().isPresent());
        cameraDefaultConstructor.setCommandBuilderReusable(new CommandBuilderReusable.ReusableBuilder());
        Assertions.assertTrue(cameraDefaultConstructor.getCommandBuilderReusable().isPresent());
    }

    @Test
    void getAndSetDefaultTimeout() {
        Assertions.assertFalse(cameraDefaultConstructor.getDefaultTimeout().isPresent());
        cameraDefaultConstructor.setDefaultTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(cameraDefaultConstructor.getDefaultTimeout().isPresent());
    }

    @Test
    void sendGenericCommandAsync() {
        final GenericCommand<Boolean> command = cameraWithSerialNumber.sendGenericCommandAsync(new GenericCommand<>(() -> true));

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void sendGenericCommandAsyncCallable() {
        final GenericCommand<Boolean> command = cameraWithSerialNumber.sendGenericCommandAsync(() -> {
            throw new InterruptedException("");
        });

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void sendCameraCommandAsync() {
        final CameraCommand command = cameraWithSerialNumber.sendCameraCommandAsync(EdsCameraCommand.kEdsCameraCommand_TakePicture);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void sendCameraCommandAsync1() {
        final CameraCommand command = cameraWithSerialNumber.sendCameraCommandAsync(EdsCameraCommand.kEdsCameraCommand_TakePicture, 0L);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void sendCameraCommandAsync2() {
        final CameraCommand command = cameraWithSerialNumber.sendCameraCommandAsync(EdsCameraCommand.kEdsCameraCommand_TakePicture, EdsISOSpeed.kEdsISOSpeed_100);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void sendStatusCommandAsync() {
        final StatusCommand command = cameraWithSerialNumber.sendStatusCommandAsync(EdsCameraStatusCommand.kEdsCameraStatusCommand_UILock);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void isConnectedAsync() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final IsConnectedCommand command = cameraWithSerialNumber.isConnectedAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void isConnectedAsyncRequireCameraRefNotNull() {
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.isConnectedAsync());

        verify(commandDispatcher, times(0)).scheduleCommand(any());
    }

    @Test
    void openSession() {
        final OpenSessionCommand command1 = cameraDefaultConstructor.openSession();
        final OpenSessionCommand command2 = cameraWithSerialNumber.openSession();

        verify(commandDispatcher).scheduleCommand(command1);
        verify(commandDispatcher).scheduleCommand(command2);
    }

    @Test
    void openSession1() {
        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setCameraBySerialNumber("any")
            .build();

        final OpenSessionCommand command = cameraWithSerialNumber.openSession(option);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void closeSession() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final CloseSessionCommand command = cameraWithSerialNumber.closeSession();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void closeSessionRequireCameraRefNotNull() {
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.closeSession());

        verify(commandDispatcher, times(0)).scheduleCommand(any());
    }

    @Test
    void closeSession1() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final CloseSessionOption option = new CloseSessionOptionBuilder()
            .setCamera(cameraWithSerialNumber)
            .setCameraRef(fakeCamera)
            .build();

        final CloseSessionCommand command = cameraWithSerialNumber.closeSession(option);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void eventRequireCameraRefNotNull() {
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().registerObjectEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().registerPropertyEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().registerStateEventCommand());

        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().unRegisterObjectEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().unRegisterPropertyEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().unRegisterStateEventCommand());

        verify(commandDispatcher, times(0)).scheduleCommand(any());
    }

    @Test
    void registerCameraAddedEventCommand() {
        final RegisterCameraAddedEventCommand command = cameraWithSerialNumber.getEvent().registerCameraAddedEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void registerObjectEventCommand() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final RegisterObjectEventCommand command = cameraWithSerialNumber.getEvent().registerObjectEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void registerPropertyEventCommand() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final RegisterPropertyEventCommand command = cameraWithSerialNumber.getEvent().registerPropertyEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void registerStateEventCommand() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final RegisterStateEventCommand command = cameraWithSerialNumber.getEvent().registerStateEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void unRegisterObjectEventCommand() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final UnRegisterObjectEventCommand command = cameraWithSerialNumber.getEvent().unRegisterObjectEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void unRegisterPropertyEventCommand() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final UnRegisterPropertyEventCommand command = cameraWithSerialNumber.getEvent().unRegisterPropertyEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void unRegisterStateEventCommand() {
        cameraWithSerialNumber.setCameraRef(fakeCamera);

        final UnRegisterStateEventCommand command = cameraWithSerialNumber.getEvent().unRegisterStateEventCommand();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(fakeCamera, command);
    }

    @Test
    void shoot() {
//        cameraWithSerialNumber.getShoot().shoot();
//
//        Assertions.assertNotNull(command);
//
//        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void shootAsync() {
        final ShootCommand command = cameraWithSerialNumber.getShoot().shootAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void shootAsyncCustom() {
        final ShootOption option = new ShootOptionBuilder()
            .build();

        final ShootCommand command = cameraWithSerialNumber.getShoot().shootAsync(option);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void beginLiveViewAsync() {
        final LiveViewCommand.Begin command = cameraWithSerialNumber.getLiveView().beginLiveViewAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void endLiveViewAsync() {
        final LiveViewCommand.End command = cameraWithSerialNumber.getLiveView().endLiveViewAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void downloadLiveViewAsync() {
        final LiveViewCommand.Download command = cameraWithSerialNumber.getLiveView().downloadLiveViewAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void downloadBufferLiveViewAsync() {
        final LiveViewCommand.DownloadBuffer command = cameraWithSerialNumber.getLiveView().downloadBufferLiveViewAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void isLiveViewActiveAsync() {
        final LiveViewCommand.IsLiveViewActive command = cameraWithSerialNumber.getLiveView().isLiveViewActiveAsync();

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void getByPropertyAsync() {
        final GetPropertyCommand.ByProperty<NativeEnum> command = cameraWithSerialNumber.getProperty().getByPropertyAsync(EdsPropertyID.kEdsPropID_ISOSpeed);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void getByPropertyInParamAsync() {
        final GetPropertyCommand.ByProperty<NativeEnum> command = cameraWithSerialNumber.getProperty().getByPropertyAsync(EdsPropertyID.kEdsPropID_ISOSpeed, 0L);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void getByEnumAsync() {
        final GetPropertyCommand.ByEnum<EdsISOSpeed> command = cameraWithSerialNumber.getProperty().getByEnumAsync(EdsPropertyID.kEdsPropID_ISOSpeed, EdsISOSpeed.class);

        Assertions.assertNotNull(command);

        verify(commandDispatcher).scheduleCommand(command);
    }

    @Test
    void sendAllGetCommands() throws InvocationTargetException, IllegalAccessException {
        final Method[] methods = cameraWithSerialNumber.getProperty().getClass().getMethods();
        int count = 0;
        for (final Method method : methods) {
            if (!method.getName().startsWith("get") || method.getName().equals("getClass")) {
                continue;
            }
            if (method.getParameterCount() > 0) {
                continue;
            }

            final CanonCommand command;
            try {
                command = (CanonCommand) method.invoke(cameraWithSerialNumber.getProperty());
            } catch (IllegalArgumentException | ClassCastException e) {
                log.warn("Fail: {}", method, e);
                throw e;
            }

            Assertions.assertNotNull(command);

            count++;

            verify(commandDispatcher).scheduleCommand(command);
            verify(commandDispatcher, times(count)).scheduleCommand(any());
        }

    }

    @Test
    void sendAllSetCommands() throws IllegalAccessException {
        final Method[] methods = cameraWithSerialNumber.getProperty().getClass().getMethods();
        for (final Method method : methods) {
            if (!method.getName().startsWith("set")) {
                continue;
            }
            final Object[] params = new Object[method.getParameterCount()];
            for (int i = 0; i < method.getParameterCount(); i++) {
                final Class<?> type = method.getParameters()[i].getType();
                log.warn("type {}: {}", type, method);
                if (type.equals(long.class) || type.equals(int.class)) {
                    params[i] = 0;
                }
            }

            try {
                final CanonCommand command = (CanonCommand) method.invoke(cameraWithSerialNumber.getProperty(), params);
            } catch (InvocationTargetException e) {
                final Throwable cause = e.getCause();
                if (cause instanceof NullPointerException) {
                    continue;
                }
                log.warn("Method ({}): {}", params, method);
                throwUnchecked(cause);
            } catch (IllegalArgumentException e) {
                log.warn("Method ({}): {}", params, method);
                throw e;
            }
        }
    }


}
