/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.CallableCommand;
import org.blackdread.cameraframework.api.command.*;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.decorator.builder.CommandBuilderReusable;
import org.blackdread.cameraframework.api.constant.*;
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
@SuppressWarnings("unused")
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

    /**
     * Open a session with the first camera found and set this CanonCamera the EdsCameraRef
     *
     * @return command
     */
    public OpenSessionCommand openSession() {
        final OpenSessionOption option = new OpenSessionOptionBuilder()
            .setCamera(this)
            .build();
        return dispatchCommand(new OpenSessionCommand(option));
    }

    public OpenSessionCommand openSession(final OpenSessionOption option) {
        return dispatchCommand(new OpenSessionCommand(option));
    }

    /**
     * Close session and release EdsCameraRef
     *
     * @return command
     */
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
         * Added command here as a convenience but usually it should be called *once* right after initialization of SDK
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

        /*  ***************
         *      Getters
         *  ***************
         */

        public <T extends NativeEnum> GetPropertyCommand.ByProperty<T> getByPropertyAsync(final EdsPropertyID propertyID) {
            return dispatchCommand(new GetPropertyCommand.ByProperty<>(propertyID));
        }

        public <T extends NativeEnum> GetPropertyCommand.ByProperty<T> getByPropertyAsync(final EdsPropertyID propertyID, final long inParam) {
            return dispatchCommand(new GetPropertyCommand.ByProperty<>(propertyID, inParam));
        }

        public <T extends NativeEnum> GetPropertyCommand.ByEnum<T> getByEnumAsync(final EdsPropertyID propertyID, final Class<T> enumClass) {
            return dispatchCommand(new GetPropertyCommand.ByEnum<>(propertyID, enumClass));
        }

        public GetPropertyCommand.Aperture getApertureAsync() {
            return dispatchCommand(new GetPropertyCommand.Aperture());
        }

        public GetPropertyCommand.Artist getArtistAsync() {
            return dispatchCommand(new GetPropertyCommand.Artist());
        }

        public GetPropertyCommand.AutoFocusMode getAutoFocusModeAsync() {
            return dispatchCommand(new GetPropertyCommand.AutoFocusMode());
        }

        public GetPropertyCommand.AvailableShots getAvailableShotsAsync() {
            return dispatchCommand(new GetPropertyCommand.AvailableShots());
        }

        public GetPropertyCommand.BatteryLevel getBatteryLevelAsync() {
            return dispatchCommand(new GetPropertyCommand.BatteryLevel());
        }

        public GetPropertyCommand.BodyIDEx getBodyIDExAsync() {
            return dispatchCommand(new GetPropertyCommand.BodyIDEx());
        }

        public GetPropertyCommand.Bracket getBracketAsync() {
            return dispatchCommand(new GetPropertyCommand.Bracket());
        }

        public GetPropertyCommand.ColorSpace getColorSpaceAsync() {
            return dispatchCommand(new GetPropertyCommand.ColorSpace());
        }

        public GetPropertyCommand.ColorTemperature getColorTemperatureAsync() {
            return dispatchCommand(new GetPropertyCommand.ColorTemperature());
        }

        public GetPropertyCommand.Copyright getCopyrightAsync() {
            return dispatchCommand(new GetPropertyCommand.Copyright());
        }

        public GetPropertyCommand.CurrentFolder getCurrentFolderAsync() {
            return dispatchCommand(new GetPropertyCommand.CurrentFolder());
        }

        public GetPropertyCommand.CurrentStorage getCurrentStorageAsync() {
            return dispatchCommand(new GetPropertyCommand.CurrentStorage());
        }

        public GetPropertyCommand.DateTime getDateTimeAsync() {
            return dispatchCommand(new GetPropertyCommand.DateTime());
        }

        public GetPropertyCommand.FirmwareVersion getFirmwareVersionAsync() {
            return dispatchCommand(new GetPropertyCommand.FirmwareVersion());
        }

        public GetPropertyCommand.FlashCompensation getFlashCompensationAsync() {
            return dispatchCommand(new GetPropertyCommand.FlashCompensation());
        }

        public GetPropertyCommand.FocusInfo getFocusInfoAsync() {
            return dispatchCommand(new GetPropertyCommand.FocusInfo());
        }

        public GetPropertyCommand.HardDriveDirectoryStructure getHardDriveDirectoryStructureAsync() {
            return dispatchCommand(new GetPropertyCommand.HardDriveDirectoryStructure());
        }

        public GetPropertyCommand.ImageQuality getImageQualityAsync() {
            return dispatchCommand(new GetPropertyCommand.ImageQuality());
        }

        public GetPropertyCommand.IsoSpeed getIsoSpeedAsync() {
            return dispatchCommand(new GetPropertyCommand.IsoSpeed());
        }

        public GetPropertyCommand.JpegQuality getJpegQualityAsync() {
            return dispatchCommand(new GetPropertyCommand.JpegQuality());
        }

        public GetPropertyCommand.LensName getLensNameAsync() {
            return dispatchCommand(new GetPropertyCommand.LensName());
        }

        public GetPropertyCommand.LensStatus getLensStatusAsync() {
            return dispatchCommand(new GetPropertyCommand.LensStatus());
        }

        public GetPropertyCommand.LensStatusAsBoolean getLensStatusAsBooleanAsync() {
            return dispatchCommand(new GetPropertyCommand.LensStatusAsBoolean());
        }

        public GetPropertyCommand.LiveViewAutoFocusMode getLiveViewAutoFocusModeAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewAutoFocusMode());
        }

        public GetPropertyCommand.LiveViewColorTemperature getLiveViewColorTemperatureAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewColorTemperature());
        }

        public GetPropertyCommand.LiveViewCoordinateSystem getLiveViewCoordinateSystemAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewCoordinateSystem());
        }

        public GetPropertyCommand.LiveViewHistogram getLiveViewHistogramAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewHistogram());
        }

        public GetPropertyCommand.LiveViewHistogramB getLiveViewHistogramBAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewHistogramB());
        }

        public GetPropertyCommand.LiveViewHistogramG getLiveViewHistogramGAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewHistogramG());
        }

        public GetPropertyCommand.LiveViewHistogramR getLiveViewHistogramRAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewHistogramR());
        }

        public GetPropertyCommand.LiveViewHistogramY getLiveViewHistogramYAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewHistogramY());
        }

        public GetPropertyCommand.LiveViewHistogramStatus getLiveViewHistogramStatusAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewHistogramStatus());
        }

        public GetPropertyCommand.LiveViewImagePosition getLiveViewImagePositionAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewImagePosition());
        }

        public GetPropertyCommand.LiveViewOutputDevice getLiveViewOutputDeviceAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewOutputDevice());
        }

        public GetPropertyCommand.LiveViewWhiteBalance getLiveViewWhiteBalanceAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewWhiteBalance());
        }

        public GetPropertyCommand.LiveViewZoomPosition getLiveViewZoomPositionAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewZoomPosition());
        }

        public GetPropertyCommand.LiveViewZoomRectangle getLiveViewZoomRectangleAsync() {
            return dispatchCommand(new GetPropertyCommand.LiveViewZoomRectangle());
        }

        public GetPropertyCommand.MeteringMode getMeteringModeAsync() {
            return dispatchCommand(new GetPropertyCommand.MeteringMode());
        }

        public GetPropertyCommand.OwnerName getOwnerNameAsync() {
            return dispatchCommand(new GetPropertyCommand.OwnerName());
        }

        public GetPropertyCommand.ProductName getProductNameAsync() {
            return dispatchCommand(new GetPropertyCommand.ProductName());
        }

        public GetPropertyCommand.PictureStyle getPictureStyleAsync() {
            return dispatchCommand(new GetPropertyCommand.PictureStyle());
        }

        public GetPropertyCommand.PictureStyleDescription getPictureStyleDescriptionAsync() {
            return dispatchCommand(new GetPropertyCommand.PictureStyleDescription());
        }

        public GetPropertyCommand.SaveTo getSaveToAsync() {
            return dispatchCommand(new GetPropertyCommand.SaveTo());
        }

        public GetPropertyCommand.SerialNumber getSerialNumberAsync() {
            return dispatchCommand(new GetPropertyCommand.SerialNumber());
        }

        public GetPropertyCommand.ShootingMode getShootingModeAsync() {
            return dispatchCommand(new GetPropertyCommand.ShootingMode());
        }

        public GetPropertyCommand.ShutterSpeed getShutterSpeedAsync() {
            return dispatchCommand(new GetPropertyCommand.ShutterSpeed());
        }

        public GetPropertyCommand.WhiteBalance getWhiteBalanceAsync() {
            return dispatchCommand(new GetPropertyCommand.WhiteBalance());
        }

        public GetPropertyCommand.WhiteBalanceBracket getWhiteBalanceBracketAsync() {
            return dispatchCommand(new GetPropertyCommand.WhiteBalanceBracket());
        }

        public GetPropertyCommand.WhiteBalanceShift getWhiteBalanceShiftAsync() {
            return dispatchCommand(new GetPropertyCommand.WhiteBalanceShift());
        }

        public GetPropertyDescCommand.ApertureDesc getAvailableApertureAsync() {
            return dispatchCommand(new GetPropertyDescCommand.ApertureDesc());
        }

        /*  ***************
         *      Properties available
         *  ***************
         */

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

        /*  ***************
         *      Setters
         *  ***************
         */

        public SetPropertyCommand.ByValue setByValueAsync(final EdsPropertyID propertyID, final Object inParam) {
            return dispatchCommand(new SetPropertyCommand.ByValue(propertyID, inParam));
        }

        public SetPropertyCommand.ByEnum setByEnumAsync(final EdsPropertyID propertyID, final NativeEnum<? extends Number> value) {
            return dispatchCommand(new SetPropertyCommand.ByEnum(propertyID, value));
        }

        public SetPropertyCommand.Aperture setApertureAsync(final EdsAv value) {
            return dispatchCommand(new SetPropertyCommand.Aperture(value));
        }

        public SetPropertyCommand.Artist setArtistAsync(final String value) {
            return dispatchCommand(new SetPropertyCommand.Artist(value));
        }

        public SetPropertyCommand.ColorSpace setColorSpaceAsync(final EdsColorSpace value) {
            return dispatchCommand(new SetPropertyCommand.ColorSpace(value));
        }

        public SetPropertyCommand.ColorTemperature setColorTemperatureAsync(final long value) {
            return dispatchCommand(new SetPropertyCommand.ColorTemperature(value));
        }

        public SetPropertyCommand.Copyright setCopyrightAsync(final String value) {
            return dispatchCommand(new SetPropertyCommand.Copyright(value));
        }

        public SetPropertyCommand.DriveMode setDriveModeAsync(final EdsDriveMode value) {
            return dispatchCommand(new SetPropertyCommand.DriveMode(value));
        }

        public SetPropertyCommand.ExposureCompensation setExposureCompensationAsync(final EdsExposureCompensation value) {
            return dispatchCommand(new SetPropertyCommand.ExposureCompensation(value));
        }

        public SetPropertyCommand.HardDriveDirectoryStructure setHardDriveDirectoryStructureAsync(final String value) {
            return dispatchCommand(new SetPropertyCommand.HardDriveDirectoryStructure(value));
        }

        public SetPropertyCommand.IsoSpeed setIsoSpeedAsync(final EdsISOSpeed value) {
            return dispatchCommand(new SetPropertyCommand.IsoSpeed(value));
        }

        public SetPropertyCommand.ImageQuality setImageQualityAsync(final EdsImageQuality value) {
            return dispatchCommand(new SetPropertyCommand.ImageQuality(value));
        }

        public SetPropertyCommand.JpegQuality setJpegQualityAsync(final int value) {
            return dispatchCommand(new SetPropertyCommand.JpegQuality(value));
        }

        public SetPropertyCommand.LiveViewAutoFocusMode setLiveViewAutoFocusModeAsync(final EdsEvfAFMode value) {
            return dispatchCommand(new SetPropertyCommand.LiveViewAutoFocusMode(value));
        }

        public SetPropertyCommand.LiveViewColorTemperature setLiveViewColorTemperatureAsync(final long value) {
            return dispatchCommand(new SetPropertyCommand.LiveViewColorTemperature(value));
        }

        public SetPropertyCommand.LiveViewOutputDevice setLiveViewOutputDeviceAsync(final EdsEvfOutputDevice value) {
            return dispatchCommand(new SetPropertyCommand.LiveViewOutputDevice(value));
        }

        public SetPropertyCommand.LiveViewWhiteBalance setLiveViewWhiteBalanceAsync(final EdsWhiteBalance value) {
            return dispatchCommand(new SetPropertyCommand.LiveViewWhiteBalance(value));
        }

        public SetPropertyCommand.LiveViewZoomPosition setLiveViewZoomPositionAsync(final EdsPoint value) {
            return dispatchCommand(new SetPropertyCommand.LiveViewZoomPosition(value));
        }

        public SetPropertyCommand.LiveViewZoomPosition setLiveViewZoomPositionAsync(final long x, final long y) {
            return dispatchCommand(new SetPropertyCommand.LiveViewZoomPosition(x, y));
        }

        public SetPropertyCommand.LiveViewZoomRatio setLiveViewZoomRatioAsync(final EdsEvfZoom value) {
            return dispatchCommand(new SetPropertyCommand.LiveViewZoomRatio(value));
        }

        public SetPropertyCommand.MeteringMode setMeteringModeAsync(final EdsMeteringMode value) {
            return dispatchCommand(new SetPropertyCommand.MeteringMode(value));
        }

        public SetPropertyCommand.OwnerName setOwnerNameAsync(final String value) {
            return dispatchCommand(new SetPropertyCommand.OwnerName(value));
        }

        public SetPropertyCommand.PictureStyle setPictureStyleAsync(final EdsPictureStyle value) {
            return dispatchCommand(new SetPropertyCommand.PictureStyle(value));
        }

        public SetPropertyCommand.PictureStyleDescription setPictureStyleDescriptionAsync(final EdsPictureStyleDesc value) {
            return dispatchCommand(new SetPropertyCommand.PictureStyleDescription(value));
        }

        public SetPropertyCommand.SaveTo setSaveToAsync(final EdsSaveTo value) {
            return dispatchCommand(new SetPropertyCommand.SaveTo(value));
        }

        public SetPropertyCommand.ShootingMode setShootingModeAsync(final EdsAEMode value) {
            return dispatchCommand(new SetPropertyCommand.ShootingMode(value));
        }

        public SetPropertyCommand.ShutterSpeed setShutterSpeedAsync(final EdsTv value) {
            return dispatchCommand(new SetPropertyCommand.ShutterSpeed(value));
        }

        public SetPropertyCommand.WhiteBalance setWhiteBalanceAsync(final EdsWhiteBalance value) {
            return dispatchCommand(new SetPropertyCommand.WhiteBalance(value));
        }

    }
}
