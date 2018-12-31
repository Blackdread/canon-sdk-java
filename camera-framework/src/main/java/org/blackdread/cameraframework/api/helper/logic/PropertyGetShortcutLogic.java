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
package org.blackdread.cameraframework.api.helper.logic;

import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.camerabinding.jna.EdsFocusInfo;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsRational;
import org.blackdread.camerabinding.jna.EdsRect;
import org.blackdread.camerabinding.jna.EdsSize;
import org.blackdread.camerabinding.jna.EdsTime;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.constant.custom.ImageOrientation;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import java.time.LocalDate;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyGetLogic;

/**
 * Shortcut to get properties data, simplify also the returned type as it matches the property.
 * <br>
 * It allows to not copy/paste this logic every time a property is queried
 * <br>
 * It also strongly type the reference that receives the command (EdsCameraRef, EdsImageRef, etc)
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface PropertyGetShortcutLogic {

    /**
     * Indicates the name of the remote camera.
     *
     * @param camera ref of camera
     * @return A string representing the product name
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getProductName(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ProductName);
    }

    /**
     * Indicates the name of the camera used to shoot the image.
     *
     * @param image ref of image
     * @return A string representing the product name
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getProductName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ProductName);
    }

    /**
     * Indicates the product serial number.
     *
     * @param camera ref of camera
     * @return the serial number of the remote camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getBodyIDEx(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_BodyIDEx);
    }

    /**
     * Indicates the serial number of the camera used to shoot the image.
     *
     * @param image ref of image
     * @return the serial number of the camera used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getBodyIDEx(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_BodyIDEx);
    }

    /**
     * Indicates a string identifying the owner as registered on the camera.
     *
     * @param camera ref of camera
     * @return the owner name for the remote camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getOwnerName(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_OwnerName);
    }

    /**
     * Indicates a string identifying the owner as registered on the camera used to shoot the image.
     *
     * @param image ref of image
     * @return the owner name for the camera used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getOwnerName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_OwnerName);
    }

    /**
     * Indicates a string identifying the photographer as registered on the camera.
     *
     * @param camera ref of camera
     * @return the photographer owner name as registered on the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getArtist(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Artist);
    }

    /**
     * Indicates a string identifying the photographer as registered on the camera.
     *
     * @param image ref of image
     * @return the photographer owner name for the camera used to shoot the image.
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getArtist(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Artist);
    }

    /**
     * Indicates a string identifying the copyright information as registered on the camera.
     *
     * @param camera ref of camera
     * @return the copyright information as registered on the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getCopyright(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Copyright);
    }

    /**
     * Indicates a string identifying the copyright information as registered on the camera used to shoot the image.
     *
     * @param image ref of image
     * @return the copyright information as registered on the camera used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getCopyright(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Copyright);
    }

    /**
     * Indicates a string identifying the manufacturer.
     *
     * @param image ref of image
     * @return a string identifying the manufacturer
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getMakerName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_MakerName);
    }

    /**
     * Indicates the time and date set on the camera
     *
     * @param camera ref of camera
     * @return the time and date set on the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsTime getDateTime(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_DateTime);
    }

    /**
     * Indicates the time and date of the shooting date and time of images.
     *
     * @param image ref of image
     * @return the time and date of shooting of the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsTime getDateTime(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_DateTime);
    }

    /**
     * Indicates the camera's firmware version.
     *
     * @param camera ref of camera
     * @return the camera's firmware version
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getFirmwareVersion(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_FirmwareVersion);
    }

    /**
     * Indicates the camera's firmware version used to shoot the image.
     *
     * @param image ref of image
     * @return the camera's firmware version used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getFirmwareVersion(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_FirmwareVersion);
    }

    /**
     * Indicates the camera battery level.
     * <br>
     * When the battery reaches a particular level, a kEdsPropertyEvent_PropertyChanged event is generated.
     * <br>
     * The battery level that triggers the event is model-dependent.
     *
     * @param camera ref of camera
     * @return the camera's battery level
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsBatteryLevel2 getBatteryLevel(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_BatteryLevel);
        return EdsBatteryLevel2.ofValue(value.intValue());
    }

    /**
     * Gets the level of degradation of the battery.
     *
     * @param camera ref of camera
     * @return the level of degradation of the battery
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsBatteryQuality getBatteryQuality(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_BatteryQuality);
        return EdsBatteryQuality.ofValue(value.intValue());
    }

    /**
     * Indicates the ICC profile data embedded in an image.
     * <br>
     * An error is returned if you use EdsGetPropertyData to attempt to get the ICC profile of an image without an embedded ICC profile.
     * TODO Might return Optional for this one so no exception when ICC profile not available
     *
     * @param image ref of image
     * @return the ICC profile data embedded in an image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @deprecated Not yet finished  design
     */
    default Object getICCProfile(final EdsImageRef image) {
        final Object value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ICCProfile);
        throw new NotImplementedException("");
    }

    /**
     * Indicates focus information for image data at the time of shooting.
     * <br>
     * This property does not depend on the AF mode at the time of shooting. AF frames in focus are indicated by JustFocus, even during manual shooting.
     * <br>
     * The EOS 50D or EOS 5D Mark II or later cameras obtain the AF frame from EdsCameraRef. The value obtained during live view operations is different.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Live View</th><th>AF Frame</th></tr></thead>
     * <tbody>
     * <tr><td>When operating</td><td>The AF frame depending on the AF mode during live view set for the camera</td></tr>
     * <tr><td>When stopped</td><td>The AF frame during Quick Mode</td></tr>
     * </tbody>
     * </table>
     *
     * @param camera ref of camera
     * @return focus information for image data at the time of shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @deprecated
     */
    default EdsFocusInfo getFocusInfo(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_FocusInfo);
    }

    default EdsFocusInfo getFocusInfo(final EdsImageRef image) {
        // TODO to check
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_FocusInfo);
    }

    default EdsImageQuality getImageQuality(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ImageQuality);
        // TODO TO check, the values return can be used to extract more information
        return EdsImageQuality.ofValue(value.intValue());
    }

    /**
     * Indicates the JPEG compression.
     * <br>
     * This property is <b>valid for the EOS 1 series only</b>.
     *
     * @param camera ref of camera
     * @return an integer value of 0–10 (0 if uncompressed)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int getJpegQuality(final EdsCameraRef camera) {
        final Integer value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_JpegQuality);
        return value;
    }

    /**
     * Indicates the JPEG compression of the image.
     * <br>
     * This property is <b>valid for the EOS 1 series only</b>.
     *
     * @param image ref of image
     * @return an integer value of 0–10 (0 if uncompressed)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int getJpegQuality(final EdsImageRef image) {
        final Integer value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_JpegQuality);
        return value;
    }

    /**
     * Indicates image rotation information.
     * <br>
     * Rotation information is retrieved from images' Exif information. Thus, images rotated by means of a software tool of computer may be displayed differently from how they would appear using the actual rotation information.
     * <br>
     * This property can be read or written, regardless of the image compression format (RAW, JPEG, and so on); the access type is Read/Write.
     *
     * @param image ref of image
     * @return image rotation information
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default ImageOrientation getOrientation(final EdsImageRef image) {
        final int value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Orientation);
        return ImageOrientation.ofValue(value);
    }

    /**
     * Indicates image rotation information.
     *
     * @param image ref of image
     * @return image rotation information as int
     * @see #getOrientation(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef)
     */
    default int getOrientationAsInt(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Orientation);
    }

    /**
     * Indicates settings values of the camera in shooting mode.
     * <br>
     * When the AE Mode Dial is set to camera user settings, you will get the AE mode which is been registered to the selected camera user setting.
     *
     * @param camera ref of camera
     * @return setting value of the camera in shooting mode
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsAEMode getAEMode(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_AEMode);
        return EdsAEMode.ofValue(value.intValue());
    }

    /**
     * Indicates settings values of the camera in shooting mode used to shoot the image.
     * <br>
     * When the AE Mode Dial is set to camera user settings, you will get the AE mode which is been registered to the selected camera user setting.
     *
     * @param image ref of image
     * @return setting value of the camera in shooting mode used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsAEMode getAEMode(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_AEMode);
        return EdsAEMode.ofValue(value.intValue());
    }

    /**
     * Indicates settings values of the camera in shooting mode.
     * <br>
     * The shooting mode is in either an applied or simple shooting zone. When a camera is in a shooting mode of the simple shooting zone, a variety of capture-related properties (such as for auto focus, drive mode, and metering mode) are automatically set to the optimal values. Thus, when the camera is in a shooting mode of a simple shooting zone, capture-related properties cannot be set on the camera.
     * <br>
     * Values defined in Enum {@link EdsAEMode}. From EOS 5DMarkIII, in addition to {@link EdsAEMode} we added {@link EdsAEModeSelect}. For the models before EOS 60D, you cannot get the AE mode which is registered to camera user settings.
     *
     * @param camera ref of camera
     * @return setting value of the camera in shooting mode
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsAEModeSelect getAEModeSelect(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_AEModeSelect);
        return EdsAEModeSelect.ofValue(value.intValue());
    }

    /**
     * Indicates settings values of the camera in drive mode.
     *
     * @param camera ref of camera
     * @return drive mode of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsDriveMode getDriveMode(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_DriveMode);
        return EdsDriveMode.ofValue(value.intValue());
    }

    /**
     * Indicates settings values of the camera in drive mode.
     *
     * @param image ref of image
     * @return drive mode of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsISOSpeed getDriveMode(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_DriveMode);
        return EdsISOSpeed.ofValue(value.intValue());
    }

    /**
     * Indicates ISO sensitivity setting value of the camera.
     *
     * @param camera ref of camera
     * @return ISO sensitivity setting value of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsISOSpeed getISOSpeed(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ISOSpeed);
        return EdsISOSpeed.ofValue(value.intValue());
    }

    /**
     * Indicates ISO sensitivity setting value of the camera used to shoot the image.
     *
     * @param image ref of image
     * @return ISO sensitivity setting value of the camera used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsISOSpeed getISOSpeed(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ISOSpeed);
        return EdsISOSpeed.ofValue(value.intValue());
    }

    /**
     * Indicates the metering mode.
     *
     * @param camera ref of camera
     * @return the metering mode of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsMeteringMode getMeteringMode(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_MeteringMode);
        return EdsMeteringMode.ofValue(value.intValue());
    }

    /**
     * Indicates the metering mode.
     *
     * @param image ref of image
     * @return the metering mode
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsMeteringMode getMeteringMode(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_MeteringMode);
        return EdsMeteringMode.ofValue(value.intValue());
    }

    /**
     * Indicates AF mode settings values.
     *
     * @param camera ref of camera
     * @return AF mode setting value
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsAFMode getAFMode(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_AFMode);
        return EdsAFMode.ofValue(value.intValue());
    }

    /**
     * Indicates AF mode settings values.
     *
     * @param image ref of image
     * @return AF mode setting value of image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsAFMode getAFMode(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_AFMode);
        return EdsAFMode.ofValue(value.intValue());
    }

    /**
     * Indicates the camera's aperture value.
     *
     * @param camera ref of camera
     * @return the camera's aperture value
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsAv getAv(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Av);
        return EdsAv.ofValue(value.intValue());
    }

    /**
     * Indicates the image's aperture value.
     *
     * @param image ref of image
     * @return the image's aperture value
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getAv(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Av);
    }

    /**
     * Indicates the shutter speed.
     *
     * @param camera ref of camera
     * @return the shutter speed of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsTv getTv(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Tv);
        return EdsTv.ofValue(value.intValue());
    }

    /**
     * Indicates the shutter speed.
     *
     * @param image ref of image
     * @return the shutter speed of the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getTv(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Tv);
    }

    /**
     * Indicates the exposure compensation.
     * <br>
     * Exposure compensation refers to compensation relative to the position of the standard exposure mark (in the center of the exposure gauge).
     *
     * @param camera ref of camera
     * @return the exposure compensation of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsExposureCompensation getExposureCompensation(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ExposureCompensation);
        return EdsExposureCompensation.ofValue(value.intValue());
    }

    /**
     * Indicates the exposure compensation.
     * <br>
     * Exposure compensation refers to compensation relative to the position of the standard exposure mark (in the center of the exposure gauge).
     *
     * @param image ref of image
     * @return the exposure compensation of the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getExposureCompensation(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ExposureCompensation);
    }

    /**
     * Indicates the digital exposure compensation at the time of shooting.
     * <br>
     * As the digital exposure compensation, a value is returned representing the compensation for brightness. This is equivalent to the exposure at the time of shooting as adjusted for the aperture plus or minus several steps.
     *
     * @param image ref of image
     * @return the digital exposure compensation at the time of shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getDigitalExposure(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_DigitalExposure);
    }

    /**
     * Indicates the flash compensation.
     * <br>
     * Note that flash compensation cannot be retrieved for an external flash.
     * <br>
     * The flash compensation is the same value as the exposure compensation property kEdsPropID_ExposureCompensation
     *
     * @param camera ref of camera
     * @return the flash compensation of the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsExposureCompensation getFlashCompensation(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_FlashCompensation);
        return EdsExposureCompensation.ofValue(value.intValue());
    }

    /**
     * Indicates the focal length of the lens.
     * <br>
     * When a single-focus lens is used, the same value is returned for the Wide and Tele focal length.
     * <br>
     * You can obtain three items of information at once by using EdsGetPropertyData to get this property: the focal length at the time of shooting, the focal length of Wide, and the focal length of Tele. In this case, the buffer storing this property data is passed in three parts. However, if you prefer to get only the focal length at the time of shooting, you can get only that single part of the buffer.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Array number</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>Focal length at the time of shooting</td></tr>
     * <tr><td>1</td><td>Wide focal length</td></tr>
     * <tr><td>1</td><td>Tele focal length</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of camera
     * @return the focal length of the lens
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational[] getFocalLength(final EdsImageRef image) {
        // TODO to test and check
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_FocalLength);
    }

    /**
     * Indicates the number of shots available on a camera.
     * <br>
     * The number of shots left on the camera based on the available disk capacity of the host computer they are connected to.
     * <br>
     * To be used with {@link CameraLogic#setCapacity(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef)}
     *
     * @param camera ref of camera
     * @return the number of shots available on a camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getAvailableShots(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_AvailableShots);
    }

    /**
     * Indicates the current bracket type.
     * <br>
     * If multiple brackets have been set on the camera, you can get the bracket type as a logical sum.
     * <br>
     * This property cannot be used to get bracket compensation. Compensation is collected separately because there are separate properties for each bracket type.
     *
     * @param camera ref of camera
     * @return the current bracket type
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsBracket getBracket(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Bracket);
        return EdsBracket.ofValue(value.intValue());
    }

    /**
     * Indicates the bracket type used to shoot the image.
     * <br>
     * If multiple brackets have been set on the camera, you can get the bracket type as a logical sum.
     * <br>
     * This property cannot be used to get bracket compensation. Compensation is collected separately because there are separate properties for each bracket type.
     *
     * @param image ref of image
     * @return the bracket type used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsBracket getBracket(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Bracket);
        return EdsBracket.ofValue(value.intValue());
    }

    /**
     * Indicates the AE bracket compensation of image data.
     *
     * @param image ref of image
     * @return the AE bracket compensation of image data
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getAEBracket(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_AEBracket);
    }

    /**
     * Indicates the FE bracket compensation at the time of shooting of image data.
     *
     * @param image ref of image
     * @return the FE bracket compensation at the time of shooting of image data
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getFEBracket(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_FEBracket);
    }

    /**
     * Indicates the white balance bracket amount.
     * <br>
     * Note: "AB" means the bracket toward amber-blue and "GM" toward green-magenta
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Array number</th><th>Description</th><th>Value</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>BracketMode</td><td>0 = OFF<br>1 = Mode AB<br>2 = Mode GM<br>0xFFFFFFFF = Not Supported</td></tr>
     * <tr><td>1</td><td>BracketValueAB<br>The bracket amount from the WhiteBalanceShift position toward AB</td><td>0 to +9</td></tr>
     * <tr><td>2</td><td>BracketValueGM<br>The bracket amount from the WhiteBalanceShift position toward GM</td><td>0 to +9</td></tr>
     * </tbody>
     * </table>
     *
     * @param camera ref of camera
     * @return the white balance bracket amount
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getWhiteBalanceBracket(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_WhiteBalanceBracket);
    }

    /**
     * Indicates the white balance bracket amount.
     * <br>
     * Note: "AB" means the bracket toward amber-blue and "GM" toward green-magenta
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Array number</th><th>Description</th><th>Value</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>BracketMode</td><td>0 = OFF<br>1 = Mode AB<br>2 = Mode GM<br>0xFFFFFFFF = Not Supported</td></tr>
     * <tr><td>1</td><td>BracketValueAB<br>The bracket amount from the WhiteBalanceShift position toward AB</td><td>–9 to +9<br>(B direction–A direction)</td></tr>
     * <tr><td>2</td><td>BracketValueGM<br>The bracket amount from the WhiteBalanceShift position toward GM</td><td>–9 to +9<br>(G direction–M direction)</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return the white balance bracket amount
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getWhiteBalanceBracket(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_WhiteBalanceBracket);
    }

    /**
     * Indicates the ISO bracket compensation at the time of shooting of image data.
     *
     * @param camera ref of camera
     * @return the ISO bracket compensation at the time of shooting of image data
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getISOBracket(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ISOBracket);
    }

    /**
     * Indicates the white balance type.
     * <br>
     * If the white balance type is "Color Temperature," to know the actual color temperature you must reference another property {@link PropertyGetShortcutLogic#getColorTemperature(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef)}.
     *
     * @param camera ref of camera
     * @return the white balance type
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsWhiteBalance getWhiteBalance(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_WhiteBalance);
        return EdsWhiteBalance.ofValue(value.intValue());
    }

    /**
     * Indicates the white balance type used to shoot the image.
     * <br>
     * If the white balance type is "Color Temperature," to know the actual color temperature you must reference another property {@link PropertyGetShortcutLogic#getColorTemperature(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef)}.
     *
     * @param image ref of image
     * @return the white balance type used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsWhiteBalance getWhiteBalance(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_WhiteBalance);
        return EdsWhiteBalance.ofValue(value.intValue());
    }

    /**
     * Indicates the color temperature setting value. (Units: Kelvin)
     * <br>
     * Valid only when the white balance is set to Color Temperature.
     *
     * @param camera ref of camera
     * @return the color temperature setting value
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getColorTemperature(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ColorTemperature);
    }

    /**
     * Indicates the color temperature setting value used to shoot the image. (Units: Kelvin)
     * <br>
     * Valid only when the white balance is set to Color Temperature.
     *
     * @param image ref of image
     * @return the color temperature setting value used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getColorTemperature(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ColorTemperature);
    }

    /**
     * Indicates the white balance compensation.
     * <br>
     * Note: "AB" means compensation toward amber-blue and "GM" toward green-magenta.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th></th><th></th><th></th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>ValueAB</td><td>–9 to +9<br>0x7FFFFFFF = invalid value<br>Note: 0 means no compensation, (–) means compensation toward blue, and (+) means compensation toward amber.</td></tr>
     * <tr><td>1</td><td>ValueGM</td><td>–9 to +9<br>0x7FFFFFFF = invalid value<br>Note: 0 means no compensation, (–) means compensation toward green, and (+) means compensation toward magenta.</td></tr>
     * </tbody>
     * </table>
     *
     * @param camera ref of camera
     * @return the white balance compensation
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getWhiteBalanceShift(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_WhiteBalanceShift);
    }

    /**
     * Indicates the white balance compensation used to shoot the image.
     * <br>
     * Note: "AB" means compensation toward amber-blue and "GM" toward green-magenta.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th></th><th></th><th></th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>ValueAB</td><td>–9 to +9<br>0x7FFFFFFF = invalid value<br>Note: 0 means no compensation, (–) means compensation toward blue, and (+) means compensation toward amber.</td></tr>
     * <tr><td>1</td><td>ValueGM</td><td>–9 to +9<br>0x7FFFFFFF = invalid value<br>Note: 0 means no compensation, (–) means compensation toward green, and (+) means compensation toward magenta.</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return the white balance compensation used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getWhiteBalanceShift(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_WhiteBalanceShift);
    }

    /**
     * Indicates the white balance value used to shoot the image.
     *
     * @param image ref of image
     * @return the white balance value used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default byte[] getWBCoeffs(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_WBCoeffs);
    }

    /**
     * Indicates if linear processing is activated or not.
     * <br>
     * This property is valid only if 16-bit TIFF or 16-bit RGB has been set for image processing.
     *
     * @param image ref of image
     * @return true linear processing is activated
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default boolean getLinear(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Linear);
    }

    /**
     * Indicates the color space.
     *
     * @param camera ref of camera
     * @return the color space
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsColorSpace getColorSpace(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ColorSpace);
        return EdsColorSpace.ofValue(value.intValue());
    }

    /**
     * Indicates the color space used to shoot the image.
     *
     * @param image ref of image
     * @return the color space used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsColorSpace getColorSpace(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ColorSpace);
        return EdsColorSpace.ofValue(value.intValue());
    }

    /**
     * Indicates the tone curve.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value of inParam</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>Standard</td></tr>
     * <tr><td>1</td><td>Set 1</td></tr>
     * <tr><td>2</td><td>Set 2</td></tr>
     * <tr><td>3</td><td>Set 3</td></tr>
     * </tbody>
     * </table>
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0x00000000</td><td>Standard</td></tr>
     * <tr><td>0x00000011</td><td>User setting</td></tr>
     * <tr><td>0x00000080</td><td>Custom setting</td></tr>
     * <tr><td>0x00000001</td><td>TCD1</td></tr>
     * <tr><td>0x00000002</td><td>TCD2</td></tr>
     * <tr><td>0x00000003</td><td>TCD3</td></tr>
     * </tbody>
     * </table>
     *
     * @param camera  ref of camera
     * @param inParam param
     * @return the tone curve
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @deprecated not sure available
     */
    default long getToneCurve(final EdsCameraRef camera, final int inParam) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ToneCurve);
        // TODO to check
        return value;
    }

    /**
     * Indicates the tone curve.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0x00000000</td><td>Standard</td></tr>
     * <tr><td>0x00000011</td><td>User setting</td></tr>
     * <tr><td>0x00000080</td><td>Custom setting</td></tr>
     * <tr><td>0x00000001</td><td>TCD1</td></tr>
     * <tr><td>0x00000002</td><td>TCD2</td></tr>
     * <tr><td>0x00000003</td><td>TCD3</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return the tone curve
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getToneCurve(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ToneCurve);
        // TODO to check
        return value;
    }

    /**
     * Indicates the picture style.
     * This property is valid only for models supporting picture styles.
     *
     * @param camera ref of camera
     * @return the picture style
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsPictureStyle getPictureStyle(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_PictureStyle);
        return EdsPictureStyle.ofValue(value.intValue());
    }

    /**
     * Indicates the picture style used to shoot the image.
     * This property is valid only for models supporting picture styles.
     *
     * @param image ref of image
     * @return the picture style used to shoot the image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsPictureStyle getPictureStyle(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_PictureStyle);
        return EdsPictureStyle.ofValue(value.intValue());
    }

    /**
     * Indicates if the flash was on at the time of shooting.
     *
     * @param image ref of camera
     * @return true if the flash was on at the time of shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default boolean getFlashOn(final EdsImageRef image) {
        // TODO API ref says EdsUInt32 -> maybe get long here then return boolean
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_FlashOn);
    }

    /**
     * Indicates the flash type at the time of shooting.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Array number</th><th>Description</th><th>Value</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>Flash type</td><td>0 = None (the "flash type" item itself is not displayed)<br>1 = Internal<br>2 = external E-TTL<br>3 = external A-TTL<br>0xFFFFFFFF = Invalid value</td></tr>
     * <tr><td>1</td><td>Synchro timing</td><td>0 = 1st Curtain Synchro<br>1 = 2nd Curtain Synchro<br>0xFFFFFFFF = Invalid value</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return the flash type at the time of shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getFlashMode(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_FlashMode);
    }

    /**
     * Indicates red-eye reduction.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>OFF</td></tr>
     * <tr><td>1</td><td>ON</td></tr>
     * <tr><td>0xFFFFFFFF</td><td>Invalid value</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return red-eye reduction
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int getRedEye(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_RedEye);
    }

    /**
     * Indicates noise reduction.
     * <br>
     * Values 1–3 vary depending on the camera model.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>OFF</td></tr>
     * <tr><td>1</td><td>ON 1</td></tr>
     * <tr><td>2</td><td>ON 2</td></tr>
     * <tr><td>3</td><td>ON</td></tr>
     * <tr><td>4</td><td>Auto</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return Indicates noise reduction
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getNoiseReduction(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_NoiseReduction);
    }

    /**
     * Returns the user-specified picture style caption name at the time of shooting.
     * <br>
     * This property is valid only for models supporting picture styles.
     * <br>
     * User-specified picture styles refer to picture styles for which picture style files are read on a host computer and set on a camera.
     *
     * @param image ref of image
     * @return the user-specified picture style caption name at the time of shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getPictureStyleCaption(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_PictureStyleCaption);
    }

    /**
     * Indicates the destination of images after shooting.
     * <br>
     * If kEdsSaveTo_Host or kEdsSaveTo_Both is used, the camera caches the image data to be transferred until DownloadComplete or CancelDownload APIs are executed on the host computer (by an application).
     * The application creates a callback function to receive camera events. If kEdsObjectEvent_DirItemRequestTransfer or kEdsObjectEvent_DirItemRequestTransferDT events are received, the application must execute DownloadComplete (after downloading) or CancelDownload (if images are not needed) for the camera.
     *
     * @param camera ref of camera
     * @return the destination of images after shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsSaveTo getSaveTo(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_SaveTo);
        return EdsSaveTo.ofValue(value.intValue());
    }

    /**
     * Returns the camera state of whether the lens attached to the camera.
     * <br>
     * This property can only be retrieved from images shot using models the EOS 50D or EOS 5D Mark II or later.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>The lens is not attached</td></tr>
     * <tr><td>1</td><td>The lens is attached</td></tr>
     * </tbody>
     * </table>
     *
     * @param camera ref of camera
     * @return the camera state of whether the lens attached to the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getLensStatus(final EdsCameraRef camera) {
        // TODO give method with boolean or enum...
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_LensStatus);
    }

    /**
     * Returns the lens name at the time of shooting.
     * <br>
     * This property can only be retrieved from images shot using models supporting picture styles.
     *
     * @param image ref of image
     * @return the lens name at the time of shooting
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getLensName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_LensName);
    }

    /**
     * Gets the current storage media for the camera.
     * <br>
     * Current media name（“CF”,”SD”,”HDD”）
     *
     * @param camera ref of camera
     * @return the current storage media for the camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getCurrentStorage(final EdsCameraRef camera) {
        // TODO define custom enum
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_CurrentStorage);
    }

    /**
     * Gets the current folder for the camera (Current folder name).
     *
     * @param camera ref of camera
     * @return the current folder for the camera (Current folder name)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getCurrentFolder(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_CurrentFolder);
    }

    /**
     * Gets the directory structure information for USB storage (USB storage directory name).
     *
     * @param camera ref of camera
     * @return the directory name currently targeted (USB storage directory name)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getHDDirectoryStructure(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_HDDirectoryStructure);
    }

    /**
     * Current output device to receive evf output (live view)
     *
     * @param camera ref of camera
     * @return current output device to receive evf output (live view)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsEvfOutputDevice getEvfOutputDevice(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_OutputDevice);
        return EdsEvfOutputDevice.ofValue(value.intValue());
    }

    /**
     * Get information if live view mode is enabled
     *
     * @param camera ref of camera
     * @return true if live view mode is enabled, false otherwise
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default boolean getEvfMode(final EdsCameraRef camera) {
        final long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode);
        return (value == 0) ? false : true;
    }

    /**
     * Get the white balance of the live view image.
     *
     * @param camera ref of camera
     * @return the white balance of the live view image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsWhiteBalance getEvfWhiteBalance(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_WhiteBalance);
        return EdsWhiteBalance.ofValue(value.intValue());
    }

    /**
     * Get the color temperature of the live view image.
     * <br>
     * This is applied to the live view image only when the live view white balance is set to Color temperature.
     *
     * @param camera ref of camera
     * @return the color temperature of the live view image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default long getEvfColorTemperature(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_ColorTemperature);
    }


    /**
     * Get the depth of field ON/OFF during Preview mode.
     * <br>
     * If kEdsEvfOutputDevice is set to KEdsEvfOutputDevice_PC and depth of field is being used, the camera will be put in UI Lock status.
     *
     * @param camera ref of camera
     * @return true if depth of field is enabled during Preview mode
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default boolean getEvfDepthOfFieldPreview(final EdsCameraRef camera) {
        final long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_DepthOfFieldPreview);
        return (value == 0) ? false : true;
    }

    /**
     * Get the zoom ratio for the live view.
     * <br>
     * The zoom ratio is set using EdsCameraRef, but obtained using live view image data, in other words, by using EdsEvfImageRef.
     *
     * @param evfImage ref of evfImage
     * @return the zoom ratio for the live view
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsEvfZoom getEvfZoom(final EdsEvfImageRef evfImage) {
        final Long value = propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_Zoom);
        return EdsEvfZoom.ofValue(value.intValue());
    }

    /**
     * Get the focus and zoom border position for live view.
     * <br>
     * The focus and zoom border is set using EdsCameraRef, but obtained using live view image data, in other words, by using EdsEvfImageRef.
     * <br>
     * The coordinates are the upper left coordinates of the focus and zoom border. These values expressed in a coordinate system of kEdsPropID_Evf_CoordinateSystem.
     * <br>
     * The size of the focus and zoom border is one fifth the size of kEdsPropID_Evf_CoordinateSystem when 5x zoom or the entire screen is used, and one tenth the size of kEdsPropID_Evf_CoordinateSystem when 10x zoom is used.
     *
     * @param evfImage ref of evfImage
     * @return the focus and zoom border position for live view
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsPoint getEvfZoomPosition(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_ZoomPosition);
    }

    /**
     * Get the focus and zoom border rectangle for live view.
     * <br>
     * The “point” member is the upper left coordinates of the focus and zoom border. And the “size” member is the rectangle of focus border size. These values expressed in a coordinate system of kEdsPropID_Evf_CoordinateSystem.
     *
     * @param evfImage ref of evfImage
     * @return the focus and zoom border rectangle for live view.
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRect getEvf_ZoomRect(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_ZoomRect);
    }

    /**
     * Gets the cropping position of the enlarged live view image.
     * <br>
     * The coordinates used are the upper left coordinates of the enlarged image. These values expressed in a coordinate system of kEdsPropID_Evf_CoordinateSystem.
     *
     * @param evfImage ref of evfImage
     * @return the cropping position of the enlarged live view image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsPoint getEvfImagePosition(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_ImagePosition);
    }

    /**
     * Get the coordinate system of the live view image.
     * <br>
     * The coordinate system is used to express each value of the live view image.
     *
     * @param evfImage ref of evfImage
     * @return the coordinate system of the live view image
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsSize getEvfCoordinateSystem(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_CoordinateSystem);
    }

    /**
     * Gets the histogram for live view image data.
     * <br>
     * The histogram can be used to obtain Y.
     * <br>
     * The histogram stores data in the form Y(0)…Y(n) (0{@literal <}=n{@literal <}=255).
     * Cumulative values in the histogram differ from the total number of pixels in the image data.
     *
     * @param evfImage ref of evfImage
     * @return the histogram for live view image data, for Y
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getEvfHistogramY(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_HistogramY);
    }

    /**
     * Gets the histogram for live view image data.
     * <br>
     * The histogram can be used to obtain R.
     * <br>
     * The histogram stores data in the form R(0)…R(n) (0{@literal <}=n{@literal <}=255).
     * Cumulative values in the histogram differ from the total number of pixels in the image data.
     *
     * @param evfImage ref of evfImage
     * @return the histogram for live view image data, for R
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getEvfHistogramR(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_HistogramR);
    }

    /**
     * Gets the histogram for live view image data.
     * <br>
     * The histogram can be used to obtain G.
     * <br>
     * The histogram stores data in the form G(0)…G(n) (0{@literal <}=n{@literal <}=255).
     * Cumulative values in the histogram differ from the total number of pixels in the image data.
     *
     * @param evfImage ref of evfImage
     * @return the histogram for live view image data, for G
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getEvfHistogramG(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_HistogramG);
    }

    /**
     * Gets the histogram for live view image data.
     * <br>
     * The histogram can be used to obtain B.
     * <br>
     * The histogram stores data in the form B(0)…B(n) (0{@literal <}=n{@literal <}=255).
     * Cumulative values in the histogram differ from the total number of pixels in the image data.
     *
     * @param evfImage ref of evfImage
     * @return the histogram for live view image data, for B
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int[] getEvfHistogramB(final EdsEvfImageRef evfImage) {
        return propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_HistogramB);
    }

    /**
     * Gets the display status of the histogram.
     * <br>
     * The display status of the histogram varies depending on settings such as whether live view exposure simulation is ON/OFF, whether strobe shooting is used, whether bulb shooting is used, etc.
     *
     * @param evfImage ref of evfImage
     * @return the display status of the histogram
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsEvfHistogramStatus getEvfHistogramStatus(final EdsEvfImageRef evfImage) {
        final Long value = propertyGetLogic().getPropertyData(evfImage, EdsPropertyID.kEdsPropID_Evf_HistogramStatus);
        return EdsEvfHistogramStatus.ofValue(value.intValue());
    }

    /**
     * Get the AF mode for the live view.
     * <br>
     * This property can be set/get from the EOS 50D or EOS 5D Mark II or later.
     *
     * @param camera ref of camera
     * @return the AF mode for the live view
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsEvfAFMode getEvfAFMode(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_AFMode);
        return EdsEvfAFMode.ofValue(value.intValue());
    }

    /**
     * Get status of recording (movie shooting)
     *
     * @param camera ref of camera
     * @return status of recording (movie shooting)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default boolean getRecordStatus(final EdsCameraRef camera) {
        // TODO return custom enum?
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Record);
        return (value == 0) ? false : true;
    }

    // TODO below
//    default String getGPSVersionID(final EdsImageRef image) {
//    }

    /**
     * Indicates the version of GPSInfoIFD. The version is given as 2.2.0.0
     *
     * @param image ref of image
     * @return the version of GPSInfoIFD
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int getGPSVersionIDAsInt(final EdsImageRef image) {
        final Byte value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSVersionID);
        // TODO check if could return in string format directly
        return value.intValue();
    }

    // TODO below
//    default Enum getGPSLatitudeRef(final EdsImageRef image) {
//    }

    /**
     * Indicates whether the latitude is north or south latitude.
     * <br>
     * The value 'N' indicates north latitude,and 'S' is south latitude.
     *
     * @param image ref of image
     * @return whether the latitude is north or south latitude
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getGPSLatitudeRefAsString(final EdsImageRef image) {
        // TODO return custom enum?
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSLatitudeRef);
    }

    // TODO below
//    default Object getGPSLatitude(final EdsImageRef image) {
//    }

    /**
     * Indicates the latitude.
     * <br>
     * The latitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively.
     * TODO create POJO class instead of array
     *
     * @param image ref of image
     * @return the latitude
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational[] getGPSLatitudeAsRational(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSLatitude);
    }

    // TODO below
//    default Enum getGPSLongitudeRef(final EdsImageRef image) {
//    }

    /**
     * Indicates whether the longitude is east or west longitude.
     * <br>
     * The value 'E' indicates east longitude, and 'W' is west longitude
     *
     * @param image ref of image
     * @return whether the longitude is east or west longitude
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getGPSLongitudeRefAsString(final EdsImageRef image) {
        // TODO return custom enum?
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSLongitudeRef);
    }


    // TODO below
//    default Object getGPSLongitude(final EdsImageRef image) {
//    }

    /**
     * Indicates the longitude.
     * <br>
     * The longitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively.
     * TODO create POJO class instead of array
     *
     * @param image ref of image
     * @return the longitude
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational[] getGPSLongitudeAsRational(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSLongitude);
    }

    // TODO below
//    default Enum getGPSAltitudeRef(final EdsImageRef image) {
//    }

    /**
     * Indicates the altitude used as the reference altitude.
     * <br>
     * If the reference is sea level and the altitude is above sea level, 0 is given.
     * <br>
     * If the altitude is below sea level, a value of 1 is given and the altitude is indicated as an absolute value in the GPSAltitude.
     * <br>
     * The reference unit is meters.
     * <br>
     * <table style="border:1px solid">
     * <caption>none</caption>
     * <thead><tr><th>Value</th><th>Description</th></tr></thead>
     * <tbody>
     * <tr><td>0</td><td>Sea level</td></tr>
     * <tr><td>1</td><td>Sea level reference (negative value)</td></tr>
     * </tbody>
     * </table>
     *
     * @param image ref of image
     * @return the altitude used as the reference altitude
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int getGPSAltitudeRefAsInt(final EdsImageRef image) {
        // TODO return custom enum?
        final Byte value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSAltitudeRef);
        return value.intValue();
    }

    /**
     * Indicates the altitude based on the reference in GPSAltitudeRef.
     * <br>
     * Altitude is expressed as one RATIONAL value.
     * <br>
     * The reference unit is meters.
     *
     * @param image ref of image
     * @return the altitude based on the reference in GPSAltitudeRef
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational getGPSAltitude(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSAltitude);
    }

    // TODO below
//    default Object getGPSTimeStamp(final EdsImageRef image) {
//        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSTimeStamp);
//    }

    /**
     * Indicates the time as UTC (Coordinated Universal Time).
     * <br>
     * TimeStamp is expressed as three RATIONAL values giving the hour, minute, and second.
     * TODO create POJO class instead of array
     *
     * @param image ref of image
     * @return the time as UTC
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsRational[] getGPSTimeStampAsRational(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSTimeStamp);
    }

    /**
     * Indicates the GPS satellites used for measurements.
     *
     * @param image ref of image
     * @return the GPS satellites used for measurements
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getGPSSatellites(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSSatellites);
    }

    /**
     * Indicates the geodetic survey data used by the GPS receiver.
     *
     * @param image ref of image
     * @return the geodetic survey data used by the GPS receiver
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getGPSMapDatum(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSMapDatum);
    }

    /**
     * Date information relative to UTC (Coordinated Universal Time).
     *
     * @param image ref of image
     * @return date information relative to UTC
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default LocalDate getGPSDateStamp(final EdsImageRef image) {
        final String dateStamp = getGPSDateStampAsString(image);
//        return LocalDate.of()
        throw new NotImplementedException("");
    }

    /**
     * A character string recording date and time information relative to UTC (Coordinated Universal Time).
     * The format is "YYYY:MM:DD".
     *
     * @param image ref of image
     * @return date information relative to UTC, format is "YYYY:MM:DD"
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getGPSDateStampAsString(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSDateStamp);
    }

    // TODO below
//    default Enum getGPSStatus(final EdsImageRef image) {
//    }

    /**
     * Indicates the status of the GPS receiver when the image is recorded.
     * <br>
     * 'A' means measurement is in progress, and 'V' means the measurement is Interoperability.
     * <br>
     * TODO API ref says 'V' and 'W' for Interoperability, to check actual value (might be typo)
     *
     * @param image ref of image
     * @return the status of the GPS receiver when the image is recorded
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default String getGPSStatusAsString(final EdsImageRef image) {
        // TODO return custom enum?
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_GPSStatus);
    }

}
