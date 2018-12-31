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
package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
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
 * @since 1.0.0
 */
public class PropertyDescLogicDefault implements PropertyDescLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertyDescLogicDefault.class);

    protected PropertyDescLogicDefault() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NativeEnum<Integer>> List<T> getPropertyDesc(final EdsBaseRef camera, final EdsPropertyID property) {
        final List<Integer> propertyDescValues = getPropertyDescValues(camera, property);

        final List<NativeEnum<Integer>> nativeEnums = new ArrayList<>(propertyDescValues.size());

        for (final Integer propertyDescValue : propertyDescValues) {
            switch (property) {
                case kEdsPropID_AEMode: // added this one as seems more logical but to check (not in documentation 3.9.0)
                    // TODO to remove if not possible in fact
                    nativeEnums.add(EdsAEMode.ofValue(propertyDescValue));
                    break;
                case kEdsPropID_AEModeSelect: // in documentation it is this property but maybe was a typo? need to check later
                    // TODO to remove if not possible in fact
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

        return (List<T>) nativeEnums;
    }

}
