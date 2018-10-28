package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsPropertyDesc;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyDescLogic {

    List<NativeEnum<Integer>> getPropertyDesc(final EdsBaseRef camera, final EdsPropertyID property);

    default List<Integer> getPropertyDescColorTemperature(final EdsBaseRef camera) {
        return getPropertyDescValues(camera, EdsPropertyID.kEdsPropID_ColorTemperature);
    }

    default List<Integer> getPropertyDescEvfColorTemperature(final EdsBaseRef camera) {
        return getPropertyDescValues(camera, EdsPropertyID.kEdsPropID_Evf_ColorTemperature);
    }

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
