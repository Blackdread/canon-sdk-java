package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.GetPropertyCommand.ProductName;
import org.blackdread.cameraframework.api.command.GetPropertyDescCommand;
import org.blackdread.cameraframework.api.command.LiveViewCommand;
import org.blackdread.cameraframework.api.command.SetPropertyCommand;
import org.blackdread.cameraframework.api.command.ShootCommand;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.decorator.builder.CommandBuilderReusable;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Give access to commands creation with simple API (only methods) with auto dispatch and options/decorators to be applied.
 * <br>
 * Most methods are async, few are sync but only for most used ones, reason is to not duplicate too much methods and reduce boiler-plate code of sync methods of declaring checked exceptions.
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CanonCamera {

    private EdsCameraRef cameraRef;

    /**
     * Default decorator builder for all commands created by this canon camera.
     * If null then is not applied.
     */
    private CommandBuilderReusable commandBuilderReusable;

    private final Shoot shoot = new Shoot();

    private final LiveView liveView = new LiveView();

    private final Property property = new Property();

    protected <T extends CanonCommand<R>, R> T applyTarget(final T command) {
        if (!command.getTargetRef().isPresent()) {
            command.setTargetRef(cameraRef);
        }
        return command;
    }

    protected <T extends CanonCommand<R>, R> T applyExtraOptions(final T command) {

        return command;
    }

    @SuppressWarnings("unchecked")
    protected <T extends CanonCommand<R>, R> T applyDefaultCommandDecoration(final T command) {
        if (commandBuilderReusable != null) {
            return (T) commandBuilderReusable.setCanonCommand(command)
                .build();
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

    public CommandBuilderReusable getCommandBuilderReusable() {
        return commandBuilderReusable;
    }

    public void setCommandBuilderReusable(final CommandBuilderReusable commandBuilderReusable) {
        this.commandBuilderReusable = commandBuilderReusable;
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

    public final class Shoot {

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

    public final class LiveView {

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

    public final class Property {

        public String getProductName() throws ExecutionException, InterruptedException {
            return dispatchCommand(new ProductName()).get();
        }

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
