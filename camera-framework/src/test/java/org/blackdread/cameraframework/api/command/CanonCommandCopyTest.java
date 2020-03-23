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
package org.blackdread.cameraframework.api.command;

import com.google.common.collect.Sets;
import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOptionBuilder;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.constant.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Generic test to test all commands can be copied
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonCommandCopyTest {

    @BeforeAll
    static void setUpClass() {
    }

    @AfterAll
    static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canCopyCommand() {
        final Set<CanonCommand> commands = getAllCommands();

        for (final CanonCommand command : commands) {
            final CanonCommand copy = command.copy();

            Assertions.assertNotEquals(copy, command);
            Assertions.assertNotSame(copy, command);

            Assertions.assertEquals(command.getClass(), copy.getClass());
        }
    }

    private static Set<CanonCommand> getAllCommands() {
        final EdsdkLibrary.EdsCameraRef cameraRef = new EdsdkLibrary.EdsCameraRef();

        return Sets.newHashSet(
            new CameraCommand(EdsCameraCommand.kEdsCameraCommand_TakePicture),
            new CameraCommand(EdsCameraCommand.kEdsCameraCommand_TakePicture, 0L),
            new CameraCommand(EdsCameraCommand.kEdsCameraCommand_TakePicture, EdsdkError.EDS_ERR_OK),
            new OpenSessionCommand(),
            new OpenSessionCommand(OpenSessionOption.DEFAULT_OPEN_SESSION_OPTION),
            new CloseSessionCommand(new CloseSessionOptionBuilder().setCameraRef(cameraRef).build()),
            new FetchEventCommand(),
            new FetchEventCommand(true, 2),
            new GenericCommand<>((() -> "any")),
            new InitializeSdkCommand(),
            new TerminateSdkCommand(),
            new IsConnectedCommand(cameraRef),
            new RegisterCameraAddedEventCommand(),
            new RegisterObjectEventCommand(cameraRef),
            new RegisterPropertyEventCommand(cameraRef),
            new RegisterStateEventCommand(cameraRef),
            new ReleaseCommand(cameraRef),
            new RetainCommand(cameraRef),
            new ShootCommand(),
            new ShootCommand(ShootOption.DEFAULT_SHOOT_OPTION),
            new StatusCommand(EdsCameraStatusCommand.kEdsCameraStatusCommand_UIUnLock),
            new UnRegisterObjectEventCommand(cameraRef),
            new UnRegisterPropertyEventCommand(cameraRef),
            new UnRegisterStateEventCommand(cameraRef),

            new LiveViewCommand.Begin(),
            new LiveViewCommand.Download(),
            new LiveViewCommand.DownloadBuffer(),
            new LiveViewCommand.IsLiveViewActive(),
            new LiveViewCommand.IsLiveViewEnabled(),
            new LiveViewCommand.End(),

            new SetPropertyCommand.ByValue(EdsPropertyID.kEdsPropID_ISOSpeed, 0x48),
            new SetPropertyCommand.ByEnum(EdsPropertyID.kEdsPropID_ISOSpeed, EdsISOSpeed.kEdsISOSpeed_100),
            new SetPropertyCommand.Aperture(EdsAv.kEdsAv_1),
            new SetPropertyCommand.Artist("Artist"),
            new SetPropertyCommand.Copyright("Copyright"),
            new SetPropertyCommand.ColorTemperature(1000),
            new SetPropertyCommand.ColorSpace(EdsColorSpace.kEdsColorSpace_sRGB),
            new SetPropertyCommand.DriveMode(EdsDriveMode.kEdsDriveMode_Continuous),
            new SetPropertyCommand.ExposureCompensation(EdsExposureCompensation.kEdsExposureCompensation_1),
            new SetPropertyCommand.HardDriveDirectoryStructure(""),
            new SetPropertyCommand.ImageQuality(EdsImageQuality.EdsImageQuality_LJF),
            new SetPropertyCommand.IsoSpeed(EdsISOSpeed.kEdsISOSpeed_100),
            new SetPropertyCommand.JpegQuality(1),
            new SetPropertyCommand.LiveViewZoomRatio(EdsEvfZoom.kEdsEvfZoom_x5),
            new SetPropertyCommand.LiveViewZoomPosition(new EdsPoint(new NativeLong(1), new NativeLong(1))),
            new SetPropertyCommand.LiveViewZoomPosition(1, 1),
            new SetPropertyCommand.LiveViewWhiteBalance(EdsWhiteBalance.kEdsWhiteBalance_ColorTemp),
            new SetPropertyCommand.LiveViewOutputDevice(EdsEvfOutputDevice.kEdsEvfOutputDevice_PC),
            new SetPropertyCommand.LiveViewColorTemperature(1000),
            new SetPropertyCommand.LiveViewAutoFocusMode(EdsEvfAFMode.Evf_AFMode_Live),
            new SetPropertyCommand.MeteringMode(EdsMeteringMode.kEdsMeteringMode_Evaluative),
            new SetPropertyCommand.OwnerName("OwnerName"),
            new SetPropertyCommand.PictureStyle(EdsPictureStyle.kEdsPictureStyle_Neutral),
            new SetPropertyCommand.PictureStyleDescription(new EdsPictureStyleDesc()),
            new SetPropertyCommand.ShutterSpeed(EdsTv.kEdsTv_1),
            new SetPropertyCommand.ShootingMode(EdsAEMode.kEdsAEMode_Av),
            new SetPropertyCommand.SaveTo(EdsSaveTo.kEdsSaveTo_Both),
            new SetPropertyCommand.WhiteBalance(EdsWhiteBalance.kEdsWhiteBalance_Auto),

            new GetPropertyCommand.ByProperty<>(EdsPropertyID.kEdsPropID_ISOSpeed),
            new GetPropertyCommand.ByProperty<>(EdsPropertyID.kEdsPropID_ISOSpeed, 0L),
            new GetPropertyCommand.ByEnum<>(EdsPropertyID.kEdsPropID_ISOSpeed, EdsISOSpeed.class),
            new GetPropertyCommand.Artist(),
            new GetPropertyCommand.AvailableShots(),
            new GetPropertyCommand.AutoFocusMode(),
            new GetPropertyCommand.Aperture(),
            new GetPropertyCommand.ApertureImage(),
            new GetPropertyCommand.Bracket(),
            new GetPropertyCommand.BodyIDEx(),
            new GetPropertyCommand.BatteryLevel(),
            new GetPropertyCommand.CurrentStorage(),
            new GetPropertyCommand.CurrentFolder(),
            new GetPropertyCommand.Copyright(),
            new GetPropertyCommand.ColorTemperature(),
            new GetPropertyCommand.ColorSpace(),
            new GetPropertyCommand.DateTime(),
            new GetPropertyCommand.DcLensBarrelState(),
            new GetPropertyCommand.DcStrobe(),
            new GetPropertyCommand.DcZoom(),
            new GetPropertyCommand.FocusInfo(),
            new GetPropertyCommand.FlashCompensation(),
            new GetPropertyCommand.FirmwareVersion(),
            new GetPropertyCommand.HardDriveDirectoryStructure(),
            new GetPropertyCommand.IsoSpeed(),
            new GetPropertyCommand.Artist(),
            new GetPropertyCommand.ImageQuality(),
            new GetPropertyCommand.IsoSpeed(),
            new GetPropertyCommand.JpegQuality(),
            new GetPropertyCommand.LiveViewZoomRectangle(),
            new GetPropertyCommand.LiveViewZoomPosition(),
            new GetPropertyCommand.LiveViewWhiteBalance(),
            new GetPropertyCommand.LiveViewOutputDevice(),
            new GetPropertyCommand.LiveViewImagePosition(),
            new GetPropertyCommand.LiveViewHistogram(),
            new GetPropertyCommand.LiveViewHistogramStatus(),
            new GetPropertyCommand.LiveViewHistogramG(),
            new GetPropertyCommand.LiveViewHistogramB(),
            new GetPropertyCommand.LiveViewCoordinateSystem(),
            new GetPropertyCommand.LiveViewColorTemperature(),
            new GetPropertyCommand.LiveViewAutoFocusMode(),
            new GetPropertyCommand.LiveViewHistogramR(),
            new GetPropertyCommand.LiveViewHistogramY(),
            new GetPropertyCommand.MeteringMode(),
            new GetPropertyCommand.OwnerName(),
            new GetPropertyCommand.PictureStyle(),
            new GetPropertyCommand.PictureStyleDescription(),
            new GetPropertyCommand.ProductName(),
            new GetPropertyCommand.ShutterSpeed(),
            new GetPropertyCommand.ShutterSpeedImage(),
            new GetPropertyCommand.ShootingMode(),
            new GetPropertyCommand.SerialNumber(),
            new GetPropertyCommand.SaveTo(),
            new GetPropertyCommand.WhiteBalance(),
            new GetPropertyCommand.WhiteBalanceShift(),
            new GetPropertyCommand.WhiteBalanceBracket(),

            new GetPropertyDescCommand.AEModeDesc(),
            new GetPropertyDescCommand.AEModeSelectDesc(),
            new GetPropertyDescCommand.IsoSpeedDesc(),
            new GetPropertyDescCommand.ColorTemperatureDesc(),
            new GetPropertyDescCommand.DriveModeDesc(),
            new GetPropertyDescCommand.ExposureCompensationDesc(),
            new GetPropertyDescCommand.MeteringModeDesc(),
            new GetPropertyDescCommand.LiveViewWhiteBalanceDesc(),
            new GetPropertyDescCommand.LiveViewColorTemperatureDesc(),
            new GetPropertyDescCommand.LiveViewAutoFocusModeDesc(),
            new GetPropertyDescCommand.WhiteBalanceDesc(),
            new GetPropertyDescCommand.ShutterSpeedDesc(),
            new GetPropertyDescCommand.PictureStyleDesc(),
            new GetPropertyDescCommand.ImageQualityDesc(),
            new GetPropertyDescCommand.ApertureDesc()
        );
    }
}
