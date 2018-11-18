package org.blackdread.cameraframework.api.helper.logic;

import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.camerabinding.jna.EdsFocusInfo;
import org.blackdread.camerabinding.jna.EdsRational;
import org.blackdread.camerabinding.jna.EdsTime;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.constant.custom.ImageOrientation;

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
 */
public interface PropertyGetShortcutLogic {

    /**
     * Indicates the name of the remote camera.
     *
     * @param camera ref of camera
     * @return A string representing the product name
     */
    default String getProductName(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_ProductName);
    }

    /**
     * Indicates the name of the camera used to shoot the image.
     *
     * @param image ref of image
     * @return A string representing the product name
     */
    default String getProductName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_ProductName);
    }

    /**
     * Indicates the product serial number.
     *
     * @param camera ref of camera
     * @return the serial number of the remote camera
     */
    default String getBodyIDEx(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_BodyIDEx);
    }

    /**
     * Indicates the serial number of the camera used to shoot the image.
     *
     * @param image ref of image
     * @return the serial number of the camera used to shoot the image
     */
    default String getBodyIDEx(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_BodyIDEx);
    }

    /**
     * Indicates a string identifying the owner as registered on the camera.
     *
     * @param camera ref of camera
     * @return the owner name for the remote camera
     */
    default String getOwnerName(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_OwnerName);
    }

    /**
     * Indicates a string identifying the owner as registered on the camera used to shoot the image.
     *
     * @param image ref of image
     * @return the owner name for the camera used to shoot the image
     */
    default String getOwnerName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_OwnerName);
    }

    /**
     * Indicates a string identifying the photographer as registered on the camera.
     *
     * @param camera ref of camera
     * @return the photographer owner name as registered on the camera
     */
    default String getArtist(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Artist);
    }

    /**
     * Indicates a string identifying the photographer as registered on the camera.
     *
     * @param image ref of image
     * @return the photographer owner name for the camera used to shoot the image.
     */
    default String getArtist(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Artist);
    }

    /**
     * Indicates a string identifying the copyright information as registered on the camera.
     *
     * @param camera ref of camera
     * @return the copyright information as registered on the camera
     */
    default String getCopyright(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Copyright);
    }

    /**
     * Indicates a string identifying the copyright information as registered on the camera used to shoot the image.
     *
     * @param image ref of image
     * @return the copyright information as registered on the camera used to shoot the image
     */
    default String getCopyright(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Copyright);
    }

    /**
     * Indicates a string identifying the manufacturer.
     *
     * @param image ref of image
     * @return a string identifying the manufacturer
     */
    default String getMakerName(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_MakerName);
    }

    /**
     * Indicates the time and date set on the camera
     *
     * @param camera ref of camera
     * @return the time and date set on the camera
     */
    default EdsTime getDateTime(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_DateTime);
    }

    /**
     * Indicates the time and date of the shooting date and time of images.
     *
     * @param image ref of image
     * @return the time and date of shooting of the image
     */
    default EdsTime getDateTime(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_DateTime);
    }

    /**
     * Indicates the camera's firmware version.
     *
     * @param camera ref of camera
     * @return the camera's firmware version
     */
    default String getFirmwareVersion(final EdsCameraRef camera) {
        return propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_FirmwareVersion);
    }

    /**
     * Indicates the camera's firmware version used to shoot the image.
     *
     * @param image ref of image
     * @return the camera's firmware version used to shoot the image
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
     * @return the camera's firmware version
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
     * <thead><tr><th>Live View</th><th>AF Frame</th></tr></thead>
     * <tbody>
     * <tr><td>When operating</td><td>The AF frame depending on the AF mode during live view set for the camera</td></tr>
     * <tr><td>When stopped</td><td>The AF frame during Quick Mode</td></tr>
     * </tbody>
     * </table>
     *
     * @param camera ref of camera
     * @return the level of degradation of the battery
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
     */
    default int getJpegQuality(final EdsCameraRef camera) {
        final Integer value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_JpegQuality);
        throw new NotImplementedException("");
    }

    /**
     * Indicates the JPEG compression of the image.
     * <br>
     * This property is <b>valid for the EOS 1 series only</b>.
     *
     * @param image ref of image
     * @return an integer value of 0–10 (0 if uncompressed)
     */
    default int getJpegQuality(final EdsImageRef image) {
        final Integer value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_JpegQuality);
        throw new NotImplementedException("");
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
     * @see #getOrientation(EdsImageRef)
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
     * @return setting value of the camera in shooting mode.
     */
    default EdsISOSpeed getAEMode(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_AEMode);
        return EdsISOSpeed.ofValue(value.intValue());
    }

    /**
     * Indicates settings values of the camera in shooting mode used to shoot the image.
     * <br>
     * When the AE Mode Dial is set to camera user settings, you will get the AE mode which is been registered to the selected camera user setting.
     *
     * @param image ref of image
     * @return setting value of the camera in shooting mode used to shoot the image
     */
    default EdsISOSpeed getAEMode(final EdsImageRef image) {
        final Long value = propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_AEMode);
        return EdsISOSpeed.ofValue(value.intValue());
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
     */
    default EdsRational getAv(final EdsImageRef image) {
        return propertyGetLogic().getPropertyData(image, EdsPropertyID.kEdsPropID_Av);
    }

    /**
     * Indicates the shutter speed.
     *
     * @param camera ref of camera
     * @return the shutter speed of the camera
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
     */
    default EdsExposureCompensation getFlashCompensation(final EdsCameraRef camera) {
        final Long value = propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_FlashCompensation);
        return EdsExposureCompensation.ofValue(value.intValue());
    }



}
