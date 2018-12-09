package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.*;

import java.util.List;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyDescLogic;

/**
 * Shortcut to get properties available to be set for some specific PropertyId, simplify also the returned type as it matches the property.
 * <br>
 * It allows to not copy/paste this logic every time the list of available properties is queried
 * <br>
 * It also strongly type the reference that receives the command (EdsCameraRef, EdsImageRef, etc)
 * <p>Created on 2018/11/18.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface PropertyDescShortcutLogic {

    /**
     * Indicates settings available for AE mode.
     *
     * @param camera ref of camera
     * @return settings available for AE mode
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @deprecated not sure this one is supported
     */
    default List<EdsAEMode> getAEModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_AEMode);
    }

    /**
     * Indicates settings available for AE mode select.
     *
     * @param camera ref of camera
     * @return settings available for AE mode select
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @deprecated not sure this one is supported
     */
    default List<EdsAEModeSelect> getAEModeSelectDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_AEModeSelect);
    }

    /**
     * Indicates settings available for AV (Aperture).
     *
     * @param camera ref of camera
     * @return settings available for AV (Aperture)
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsAv> getAvDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Av);
    }

    /**
     * Indicates settings available for color temperature.
     *
     * @param camera ref of camera
     * @return settings available for color temperature
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<Integer> getColorTemperatureDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDescColorTemperature(camera);
    }

    /**
     * Indicates settings available for drive mode.
     *
     * @param camera ref of camera
     * @return settings available for drive mode
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsDriveMode> getDriveModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_DriveMode);
    }

    /**
     * Indicates settings available for exposure compensation.
     *
     * @param camera ref of camera
     * @return settings available for exposure compensation
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsExposureCompensation> getExposureCompensationDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_ExposureCompensation);
    }

    /**
     * Indicates settings available for image quality.
     *
     * @param camera ref of camera
     * @return settings available for image quality
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsImageQuality> getImageQualityDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_ImageQuality);
    }

    /**
     * Indicates settings available for ISO speed.
     *
     * @param camera ref of camera
     * @return settings available for ISO speed
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsISOSpeed> getISOSpeedDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_ISOSpeed);
    }

    /**
     * Indicates settings available for picture style.
     * This property is valid only for models supporting picture styles.
     *
     * @param camera ref of camera
     * @return settings available for picture style
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsPictureStyle> getPictureStyleDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_PictureStyleDesc);
    }

    /**
     * Indicates settings for each picture style.
     * This property is valid only for models supporting picture styles.
     *
     * @param image ref of image
     * @return settings for each picture style
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsPictureStyle> getPictureStyleDesc(final EdsdkLibrary.EdsImageRef image) {
        return propertyDescLogic().getPropertyDesc(image, EdsPropertyID.kEdsPropID_PictureStyleDesc);
    }

    /**
     * Indicates settings available for metering mode.
     *
     * @param camera ref of camera
     * @return settings available for metering mode
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsMeteringMode> getMeteringModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_MeteringMode);
    }

    /**
     * Indicates settings available for TV (shutter speed).
     *
     * @param camera ref of camera
     * @return settings available for TV (shutter speed)
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsTv> getTvDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Tv);
    }

    /**
     * Indicates settings available for white balance.
     *
     * @param camera ref of camera
     * @return settings available for white balance
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsWhiteBalance> getWhiteBalanceDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_WhiteBalance);
    }

    // **************************
    //        Evf Desc
    // **************************

    /**
     * Indicates settings available for evf AF mode.
     *
     * @param camera ref of camera
     * @return settings available for evf AF mode
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsEvfAFMode> getEvfAFModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Evf_AFMode);
    }

    /**
     * Indicates settings available for evf color temperature.
     *
     * @param camera ref of camera
     * @return list of available settings for {@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Evf_ColorTemperature}
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see PropertyDescLogic#getPropertyDescEvfColorTemperature(EdsdkLibrary.EdsBaseRef)
     */
    default List<Integer> getEvfColorTemperatureDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDescEvfColorTemperature(camera);
    }

    /**
     * Indicates settings available for evf white balance.
     *
     * @param camera ref of camera
     * @return settings available for evf white balance
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<EdsWhiteBalance> getEvfWhiteBalanceDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Evf_WhiteBalance);
    }

}
