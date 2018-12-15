package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.CallableCommand;
import org.blackdread.cameraframework.api.command.*;
import org.blackdread.cameraframework.api.command.GetPropertyCommand.ProductName;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.decorator.builder.CommandBuilderReusable;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Give access to commands creation with simple API (only methods) with auto dispatch and options/decorators to be applied.
 * <br>
 * Most methods are async, few are sync but only for most used ones, reason is to not duplicate too much methods and reduce boiler-plate code of sync methods of declaring checked exceptions.
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CanonCamera {

    private EdsCameraRef cameraRef;

    /**
     * Default decorator builder for all commands created by this canon camera.
     * If null then is not applied.
     */
    private CommandBuilderReusable commandBuilderReusable;
    /**
     * Lock for use and change of builder as builder itself is not thread-safe
     */
    private final Object lockBuilderUse = new Object();

    /**
     * Default timeout set to commands created by this canon camera.
     */
    private Duration defaultTimeout;

    private final Event event = new Event();

    private final Shoot shoot = new Shoot();

    private final LiveView liveView = new LiveView();

    private final Property property = new Property();

    protected <T extends CanonCommand<R>, R> T applyTarget(final T command) {
        if (cameraRef != null && !command.getTargetRef().isPresent()) {
            command.setTargetRef(cameraRef);
        }
        return command;
    }

    protected <T extends CanonCommand<R>, R> T applyExtraOptions(final T command) {
        if (defaultTimeout != null) {
            command.setTimeout(defaultTimeout);
        }
        return command;
    }

    @SuppressWarnings("unchecked")
    protected <T extends CanonCommand<R>, R> T applyDefaultCommandDecoration(final T command) {
        final CommandBuilderReusable builderReusable = this.commandBuilderReusable;
        if (builderReusable != null) {
            synchronized (lockBuilderUse) {
                return (T) builderReusable.setCanonCommand(command)
                    .build();
            }
        }
        return command;
    }

    /**
     * Dispatch a command with this camera as the owner and applies options/decorators of this camera.
     *
     * @param command command to dispatch, options and decorators maybe be applied to it before dispatched
     * @param <T>     command type
     * @param <R>     command return type
     * @return command that was dispatched and which has been applied to it options and decorators
     */
    public <T extends CanonCommand<R>, R> T dispatchCommand(T command) {
        command = applyTarget(command);
        command = applyExtraOptions(command);
        command = applyDefaultCommandDecoration(command);
        CanonFactory.commandDispatcher().scheduleCommand(cameraRef, command);
        return command;
    }

    public Optional<EdsCameraRef> getCameraRef() {
        return Optional.ofNullable(cameraRef);
    }

    /**
     * May be used to set the camera but should usually be set automatically by the OpenSession command
     *
     * @param cameraRef actual camera to use
     */
    public void setCameraRef(final EdsCameraRef cameraRef) {
        this.cameraRef = cameraRef;
    }

    public Optional<CommandBuilderReusable> getCommandBuilderReusable() {
        return Optional.ofNullable(commandBuilderReusable);
    }

    /**
     * @param commandBuilderReusable command builder to apply on commands before dispatch, may be null
     */
    public void setCommandBuilderReusable(final CommandBuilderReusable commandBuilderReusable) {
        this.commandBuilderReusable = commandBuilderReusable;
    }

    public Optional<Duration> getDefaultTimeout() {
        return Optional.ofNullable(defaultTimeout);
    }

    /**
     * Default timeout set to commands created by this canon camera.
     *
     * @param defaultTimeout default timeout, may be null
     */
    public void setDefaultTimeout(final Duration defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public Event getEvent() {
        return event;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public LiveView getLiveView() {
        return liveView;
    }

    public Property getProperty() {
        return property;
    }

    public <R> GenericCommand<R> sendGenericCommandAsync(final GenericCommand<R> command) {
        return dispatchCommand(command);
    }

    public <R> GenericCommand<R> sendGenericCommandAsync(final CallableCommand<R> callableCommand) {
        return dispatchCommand(new GenericCommand<>(callableCommand));
    }

    public CameraCommand sendCameraCommandAsync(final EdsCameraCommand cameraCommand) {
        return dispatchCommand(new CameraCommand(cameraCommand));
    }

    public CameraCommand sendCameraCommandAsync(final EdsCameraCommand cameraCommand, final NativeEnum<? extends Number> param) {
        return dispatchCommand(new CameraCommand(cameraCommand, param));
    }

    public CameraCommand sendCameraCommandAsync(final EdsCameraCommand cameraCommand, final long inParam) {
        return dispatchCommand(new CameraCommand(cameraCommand, inParam));
    }

    public StatusCommand sendStatusCommandAsync(final EdsCameraStatusCommand statusCommand) {
        return dispatchCommand(new StatusCommand(statusCommand));
    }

    public OpenSessionCommand openSession() {
        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setCamera(this)
            .build();
        return dispatchCommand(new OpenSessionCommand(option));
    }

    public OpenSessionCommand openSession(final OpenSessionOption option) {
        return dispatchCommand(new OpenSessionCommand(option));
    }

    public CloseSessionCommand closeSession() {
        return dispatchCommand(new CloseSessionCommand(new CloseSessionOptionBuilder().setCameraRef(cameraRef).build()));
    }

    public CloseSessionCommand closeSession(final CloseSessionOption option) {
        return dispatchCommand(new CloseSessionCommand(option));
    }

    /**
     * Event related commands
     */
    public class Event {

        /**
         * Added command here as a convenience but usually it should be called right after initialization of SDK
         *
         * @return command
         */
        public RegisterCameraAddedEventCommand registerCameraAddedEventCommand() {
            return dispatchCommand(new RegisterCameraAddedEventCommand());
        }

        public RegisterObjectEventCommand registerObjectEventCommand() {
            return dispatchCommand(new RegisterObjectEventCommand(cameraRef));
        }

        public RegisterPropertyEventCommand registerPropertyEventCommand() {
            return dispatchCommand(new RegisterPropertyEventCommand(cameraRef));
        }

        public RegisterStateEventCommand registerStateEventCommand() {
            return dispatchCommand(new RegisterStateEventCommand(cameraRef));
        }

        public UnRegisterObjectEventCommand unRegisterObjectEventCommand() {
            return dispatchCommand(new UnRegisterObjectEventCommand(cameraRef));
        }

        public UnRegisterPropertyEventCommand unRegisterPropertyEventCommand() {
            return dispatchCommand(new UnRegisterPropertyEventCommand(cameraRef));
        }

        public UnRegisterStateEventCommand unRegisterStateEventCommand() {
            return dispatchCommand(new UnRegisterStateEventCommand(cameraRef));
        }
    }

    /**
     * Shoot related commands
     */
    public class Shoot {

        public List<File> shoot() throws ExecutionException, InterruptedException {
            return dispatchCommand(new ShootCommand()).get();
        }

        public ShootCommand shootAsync() {
            return dispatchCommand(new ShootCommand());
        }

        public List<File> shoot(final ShootOption shootOption) throws ExecutionException, InterruptedException {
            return dispatchCommand(new ShootCommand(shootOption)).get();
        }

        public ShootCommand shootAsync(final ShootOption shootOption) {
            return dispatchCommand(new ShootCommand(shootOption));
        }

    }

    /**
     * LiveView related commands
     */
    public class LiveView {

        public LiveViewCommand.Begin beginLiveViewAsync() {
            return dispatchCommand(new LiveViewCommand.Begin());
        }

        public LiveViewCommand.End endLiveViewAsync() {
            return dispatchCommand(new LiveViewCommand.End());
        }

        public LiveViewCommand.Download downloadLiveViewAsync() {
            return dispatchCommand(new LiveViewCommand.Download());
        }

        public LiveViewCommand.DownloadBuffer downloadBufferLiveViewAsync() {
            return dispatchCommand(new LiveViewCommand.DownloadBuffer());
        }

        public LiveViewCommand.IsLiveViewEnabled isLiveViewEnabledAsync() {
            return dispatchCommand(new LiveViewCommand.IsLiveViewEnabled());
        }

        public LiveViewCommand.IsLiveViewActive isLiveViewActiveAsync() {
            return dispatchCommand(new LiveViewCommand.IsLiveViewActive());
        }

    }

    /**
     * Property related commands
     */
    public class Property {

        public ProductName getProductNameAsync() {
            return dispatchCommand(new ProductName());
        }


        public GetPropertyDescCommand.ApertureDesc getAvailableApertureAsync() {
            return dispatchCommand(new GetPropertyDescCommand.ApertureDesc());
        }

        public GetPropertyDescCommand.ColorTemperatureDesc getAvailableColorTemperatureAsync() {
            return dispatchCommand(new GetPropertyDescCommand.ColorTemperatureDesc());
        }

        public GetPropertyDescCommand.DriveModeDesc getAvailableDriveModeAsync() {
            return dispatchCommand(new GetPropertyDescCommand.DriveModeDesc());
        }

        public GetPropertyDescCommand.ExposureCompensationDesc getAvailableExposureCompensationAsync() {
            return dispatchCommand(new GetPropertyDescCommand.ExposureCompensationDesc());
        }

        public GetPropertyDescCommand.ImageQualityDesc getAvailableImageQualityAsync() {
            return dispatchCommand(new GetPropertyDescCommand.ImageQualityDesc());
        }

        public GetPropertyDescCommand.IsoSpeedDesc getAvailableIsoSpeedAsync() {
            return dispatchCommand(new GetPropertyDescCommand.IsoSpeedDesc());
        }

        public GetPropertyDescCommand.MeteringModeDesc getAvailableMeteringModeAsync() {
            return dispatchCommand(new GetPropertyDescCommand.MeteringModeDesc());
        }

        public GetPropertyDescCommand.PictureStyleDesc getAvailablePictureStyleAsync() {
            return dispatchCommand(new GetPropertyDescCommand.PictureStyleDesc());
        }

        public GetPropertyDescCommand.ShutterSpeedDesc getAvailableShutterSpeedAsync() {
            return dispatchCommand(new GetPropertyDescCommand.ShutterSpeedDesc());
        }

        public GetPropertyDescCommand.LiveViewAutoFocusModeDesc getAvailableLiveViewAutoFocusModeAsync() {
            return dispatchCommand(new GetPropertyDescCommand.LiveViewAutoFocusModeDesc());
        }

        public GetPropertyDescCommand.LiveViewColorTemperatureDesc getAvailableLiveViewColorTemperatureAsync() {
            return dispatchCommand(new GetPropertyDescCommand.LiveViewColorTemperatureDesc());
        }

        public GetPropertyDescCommand.LiveViewWhiteBalanceDesc getAvailableLiveViewWhiteBalanceAsync() {
            return dispatchCommand(new GetPropertyDescCommand.LiveViewWhiteBalanceDesc());
        }


        public SetPropertyCommand.IsoSpeed setIsoSpeedAsync(final EdsISOSpeed value) {
            return dispatchCommand(new SetPropertyCommand.IsoSpeed(value));
        }

    }
}
