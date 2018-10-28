package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.helper.logic.PropertyDescLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyDescLogicDefault implements PropertyDescLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertyDescLogicDefault.class);

    @Override
    public List<NativeEnum<Integer>> getPropertyDesc(final EdsdkLibrary.EdsBaseRef camera, final EdsPropertyID property) {
        final List<Integer> propertyDescValues = getPropertyDescValues(camera, property);

        final List<NativeEnum<Integer>> nativeEnums = new ArrayList<>(propertyDescValues.size());

        for (final Integer propertyDescValue : propertyDescValues) {
            switch (property) {
                case kEdsPropID_AEMode: // added this one as seems more logical but to check (not in documentation 3.9.0)
                    // TODO to remove if not possible in fact
                    nativeEnums.add(EdsAEMode.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_AEModeSelect: // in documentation it is this property but maybe was a typo? need to check later
                    nativeEnums.add(EdsAEModeSelect.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_ISOSpeed:
                    nativeEnums.add(EdsISOSpeed.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_MeteringMode:
                    nativeEnums.add(EdsMeteringMode.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_Av:
                    nativeEnums.add(EdsAv.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_Tv:
                    nativeEnums.add(EdsTv.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_ExposureCompensation:
                    nativeEnums.add(EdsExposureCompensation.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_ImageQuality:
                    nativeEnums.add(EdsImageQuality.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_WhiteBalance:
                    nativeEnums.add(EdsWhiteBalance.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_PictureStyle:
                    nativeEnums.add(EdsPictureStyle.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_DriveMode:
                    nativeEnums.add(EdsDriveMode.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_Evf_WhiteBalance:
                    nativeEnums.add(EdsWhiteBalance.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_Evf_AFMode:
                    nativeEnums.add(EdsEvfAFMode.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_ColorTemperature:
                case kEdsPropID_Evf_ColorTemperature:
                    log.error("Cannot get desc values of {} as those are not defined in an enum. Need to use other methods", property);
                    throw new IllegalArgumentException("Cannot get desc values of " + property + " as those are not defined in an enum.  Need to use other methods");
                default:
                    log.error("Property {} is not supported to get property desc", property);
                    throw new IllegalArgumentException("Property " + property + " is not supported to get property desc");
            }
        }

        return nativeEnums;
    }

}
