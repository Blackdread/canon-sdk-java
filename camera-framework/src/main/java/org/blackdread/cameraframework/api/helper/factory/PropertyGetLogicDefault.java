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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Memory;
import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.camerabinding.jna.EdsFocusInfo;
import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsRational;
import org.blackdread.camerabinding.jna.EdsRect;
import org.blackdread.camerabinding.jna.EdsTime;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.PropertyGetLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class PropertyGetLogicDefault implements PropertyGetLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertyGetLogicDefault.class);

    protected PropertyGetLogicDefault() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final PropertyInfo propertyInfo = CanonFactory.propertyLogic().getPropertyTypeAndSize(ref, property, inParam);
        final EdsDataType propertyType = propertyInfo.getDataType();
        final long propertySize = propertyInfo.getSize();

        final Memory propertyData = new Memory(propertySize > 0 ? propertySize : 1);

        final EdsdkError error = getPropertyData(ref, property, inParam, propertySize, propertyData);

        if (!EdsdkError.EDS_ERR_OK.equals(error)) {
            log.error("Failed to get property data {}, inParam: {}, propertyType:{}, propertySize: {}", property, inParam, propertyType, propertySize);
            throw error.getException();
        }

        switch (propertyType) {
            case kEdsDataType_Unknown:
                throw new IllegalStateException("Unknown data type returned by camera to get property data");
            case kEdsDataType_Bool:
                // TODO not tested
                final Boolean b = propertyData.getByte(0) != 0;
                log.warn("to test: {}, {}, {}", b, propertyData, propertyData.dump());
                return (T) b;
            case kEdsDataType_String:
                return (T) propertyData.getString(0);
            case kEdsDataType_Int8:
            case kEdsDataType_UInt8:
                return (T) (Byte) propertyData.getByte(0);
            case kEdsDataType_Int16:
            case kEdsDataType_UInt16:
                return (T) (Short) propertyData.getShort(0);
            case kEdsDataType_Int32:
            case kEdsDataType_UInt32:
                return (T) (Long) propertyData.getNativeLong(0).longValue();
            case kEdsDataType_Int64:
            case kEdsDataType_UInt64:
                return (T) (Long) propertyData.getLong(0);
            case kEdsDataType_Float:
                return (T) (Float) propertyData.getFloat(0);
            case kEdsDataType_Double:
                return (T) (Double) propertyData.getDouble(0);
            case kEdsDataType_ByteBlock:
                // TODO not tested
                // not tested and documentation gives this data type as many types :
                // EdsInt8[]
                // EdsUInt32[]
                return (T) propertyData.getIntArray(0, (int) (propertySize / 4));
            case kEdsDataType_Rational:
                return (T) new EdsRational(propertyData);
            case kEdsDataType_Point:
                return (T) new EdsPoint(propertyData);
            case kEdsDataType_Rect:
                return (T) new EdsRect(propertyData);
            case kEdsDataType_Time:
                return (T) new EdsTime(propertyData);
            case kEdsDataType_Bool_Array:
                // TODO not tested
                log.warn("to test: {}, {}", propertyData, propertyData.dump());
                throw new NotImplementedException("to test");
            case kEdsDataType_Int8_Array:
            case kEdsDataType_UInt8_Array:
                // TODO maybe use intArray
                return (T) propertyData.getByteArray(0, (int) propertySize);
            case kEdsDataType_Int16_Array:
            case kEdsDataType_UInt16_Array:
                // TODO maybe use intArray
                return (T) propertyData.getShortArray(0, (int) (propertySize / 2));
            case kEdsDataType_Int32_Array:
            case kEdsDataType_UInt32_Array:
                // TODO maybe use longArray
                return (T) propertyData.getIntArray(0, (int) (propertySize / 4));
            case kEdsDataType_Rational_Array:
                // TODO not tested
                log.warn("to test: {}, {}", propertyData, propertyData.dump());
                throw new NotImplementedException("to test");
            case kEdsDataType_FocusInfo:
                return (T) new EdsFocusInfo(propertyData);
            case kEdsDataType_PictureStyleDesc:
                return (T) new EdsPictureStyleDesc(propertyData);
            default:
                log.error("Unknown property type: {}, {}", propertyType, propertyType.value());
                throw new IllegalStateException("Unknown property type: " + propertyType + ", " + propertyType.value());
        }
    }

}
