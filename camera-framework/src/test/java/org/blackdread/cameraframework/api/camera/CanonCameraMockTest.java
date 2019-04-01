package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.command.*;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
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
    }

    @Test
    void applyExtraOptions() {
    }

    @Test
    void applyDefaultCommandDecoration() {
    }

    @Test
    void dispatchCommand() {
    }

    @Test
    void getCameraRef() {
    }

    @Test
    void getCameraRefInternal() {
    }

    @Test
    void setCameraRef() {
    }

    @Test
    void getSerialNumber() {
    }

    @Test
    void setSerialNumber() {
    }

    @Test
    void getCommandBuilderReusable() {
    }

    @Test
    void setCommandBuilderReusable() {
    }

    @Test
    void getDefaultTimeout() {
    }

    @Test
    void setDefaultTimeout() {
    }

    @Test
    void getEvent() {
    }

    @Test
    void getShoot() {
    }

    @Test
    void getLiveView() {
    }

    @Test
    void getProperty() {
    }

    @Test
    void sendGenericCommandAsync() {
    }

    @Test
    void sendGenericCommandAsync1() {
    }

    @Test
    void sendCameraCommandAsync() {
    }

    @Test
    void sendCameraCommandAsync1() {
    }

    @Test
    void sendCameraCommandAsync2() {
    }

    @Test
    void sendStatusCommandAsync() {
    }

    @Test
    void isConnectedAsync() {
    }

    @Test
    void openSession() {
    }

    @Test
    void openSession1() {
    }

    @Test
    void closeSession() {
    }

    @Test
    void closeSession1() {
    }

    @Test
    void eventRequireCameraRefNotNull() {
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().registerObjectEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().registerPropertyEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().registerStateEventCommand());

        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().unRegisterObjectEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().unRegisterPropertyEventCommand());
        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.getEvent().unRegisterStateEventCommand());
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
