package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.*;

import java.util.List;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyDescLogic;

/**
 * <p>Created on 2018/11/18.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyDescShortcutLogic {

    default List<EdsAEMode> getAEModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_AEMode);
    }

    default List<EdsAEModeSelect> getAEModeSelectDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_AEModeSelect);
    }

    default List<EdsAv> getAvDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Av);
    }

    default List<Integer> getColorTemperatureDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDescColorTemperature(camera);
    }

    default List<EdsDriveMode> getDriveModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_DriveMode);
    }

    default List<EdsExposureCompensation> getExposureCompensationDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_ExposureCompensation);
    }

    default List<EdsImageQuality> getImageQualityDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_ImageQuality);
    }

    default List<EdsISOSpeed> getISOSpeedDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_ISOSpeed);
    }

    /**
     * Indicates settings for each picture style.
     * This property is valid only for models supporting picture styles.
     *
     * @param camera ref of camera
     * @return settings for each picture style
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

    default List<EdsMeteringMode> getMeteringModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_MeteringMode);
    }

    default List<EdsTv> getTvDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Tv);
    }

    default List<EdsWhiteBalance> getWhiteBalanceDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_WhiteBalance);
    }

    // **************************
    //        Evf Desc
    // **************************

    default List<EdsEvfAFMode> getEvfAFModeDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Evf_AFMode);
    }

    /**
     * @param camera ref of camera
     * @return list of available settings for {@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Evf_ColorTemperature}
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see PropertyDescLogic#getPropertyDescEvfColorTemperature(EdsdkLibrary.EdsBaseRef)
     */
    default List<Integer> getEvfColorTemperatureDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDescEvfColorTemperature(camera);
    }

    default List<EdsWhiteBalance> getEvfWhiteBalanceDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_Evf_WhiteBalance);
    }

}
