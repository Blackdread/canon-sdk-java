package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsPropertyDesc;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface PropertyDescLogic {

    /**
     * Gets a list of property data that can be set for the object designated in inRef, as well as maximum and minimum values.
     * <br>
     * <p>Be sure before executing <b>EdsSetPropertyData</b>, use this API to get the values that can be set for the Properties supported by {@link org.blackdread.camerabinding.jna.EdsdkLibrary#EdsGetPropertyDesc(EdsBaseRef, NativeLong, EdsPropertyDesc)}.</p>
     * <br>
     * Known property id supported are
     * <ul>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_AEMode}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_AEModeSelect}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_ISOSpeed}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_MeteringMode}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Av}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Tv}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_ExposureCompensation}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_ImageQuality}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_WhiteBalance}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_ColorTemperature} <b>Not be used in this method!</b></li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_PictureStyle}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_DriveMode}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Evf_WhiteBalance}</li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Evf_ColorTemperature} <b>Not be used in this method!</b></li>
     * <li>{@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Evf_AFMode}</li>
     * </ul>
     *
     * @param camera   the target object. Designate EdsCameraRef
     * @param property the property ID (see Reference API for possible values)
     * @param <T>      T type of return value (allows to not cast at the caller)
     * @return list of available settings for the given property
     * @throws IllegalArgumentException                                     if {@code property} is not supported by this method
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not of what caller expects
     */
    <T extends NativeEnum<Integer>> List<T> getPropertyDesc(final EdsBaseRef camera, final EdsPropertyID property);

    /**
     * @param camera the target object. Designate EdsCameraRef
     * @return list of available settings for {@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_ColorTemperature}
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<Integer> getPropertyDescColorTemperature(final EdsBaseRef camera) {
        return getPropertyDescValues(camera, EdsPropertyID.kEdsPropID_ColorTemperature);
    }

    /**
     * @param camera the target object. Designate EdsCameraRef
     * @return list of available settings for {@link org.blackdread.cameraframework.api.constant.EdsPropertyID#kEdsPropID_Evf_ColorTemperature}
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<Integer> getPropertyDescEvfColorTemperature(final EdsBaseRef camera) {
        return getPropertyDescValues(camera, EdsPropertyID.kEdsPropID_Evf_ColorTemperature);
    }

    /**
     * Gets a list of property data that can be set for the object designated in inRef, as well as maximum and minimum values
     *
     * @param camera   the target object. Designate EdsCameraRef
     * @param property the property ID (see Reference API for possible values)
     * @return list of values contained in the structure {@link EdsPropertyDesc}
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default List<Integer> getPropertyDescValues(final EdsBaseRef camera, final EdsPropertyID property) {
        final EdsPropertyDesc propertyDesc = getPropertyDescStructure(camera, property);

        final int descCount = propertyDesc.numElements.intValue();

        if (descCount <= 0) {
            return Collections.emptyList();
        }

        final List<Integer> values = new ArrayList<>(descCount);
        for (int i = 0; i < descCount; i++) {
            values.add(propertyDesc.propDesc[i].intValue());
        }

        return values;
    }

    /**
     * Gets a list of property data that can be set for the object designated in inRef, as well as maximum and minimum values.
     * This API is intended for only some shooting-related properties.
     * <p>Be sure before executing <b>EdsSetPropertyData</b>, use this API to get the values that can be set for the Properties supported by {@link org.blackdread.camerabinding.jna.EdsdkLibrary#EdsGetPropertyDesc(EdsBaseRef, NativeLong, EdsPropertyDesc)}.</p>
     *
     * @param ref      the target object. Designate EdsCameraRef
     * @param property the property ID (see Reference API for possible values)
     * @return EdsPropertyDesc structure for getting a list of property data that can currently be set in the target object
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsPropertyDesc getPropertyDescStructure(final EdsBaseRef ref, final EdsPropertyID property) {
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc();
        final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertyDesc(ref, new NativeLong(property.value()), propertyDesc));
        if (error == EdsdkError.EDS_ERR_OK) {
            return propertyDesc;
        }
        // EDS_ERR_INVALID_PARAMETER is returned if a property ID is designated in inPropertyID that cannot be used with GetPropertyDesc
        throw error.getException();
    }

}
