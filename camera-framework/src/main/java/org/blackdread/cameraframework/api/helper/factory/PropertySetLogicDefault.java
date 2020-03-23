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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.PropertyInfo;
import org.blackdread.cameraframework.api.helper.logic.PropertySetLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class PropertySetLogicDefault implements PropertySetLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertySetLogicDefault.class);

    private static final String DEBUG_RETURNED_SIZE_AND_PRESET_ONE = "Size returned is {} but we reset set it to {}";

    protected PropertySetLogicDefault() {
    }


    /*
    Example from documentation
    EdsError setTv(EdsCameraRef camera, EdsUInt32 TvValue)
    {
    err = EdsSetPropertyData(camera, kEdsPropID_Tv, 0 , sizeof(TvValue), &TvValue);
    return err;
    }
     */

    @Override
    public void setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final Object value) {

        final PropertyInfo propertyInfo = CanonFactory.propertyLogic().getPropertyTypeAndSize(ref, property);
        final EdsDataType propertyType = propertyInfo.getDataType();
        final Long propertySize = propertyInfo.getSize();

        final int recalculatedSize;
        final Pointer data;

        // TODO Add support to give a value of Long then is automatically changed to int/short/etc if necessary and throws if value is more than max bound of lower value (use Number class for that)
        // TODO Add same behavior but from lower size to higher (long) (use Number class for that)

        switch (propertyType) {
            case kEdsDataType_Unknown:
                throw new IllegalStateException("Unknown data type returned by camera to set property data");
            case kEdsDataType_Bool:
                log.error("property: {}, propertyType: {}, propertySize: {}, value: {}", property, propertyType, propertySize, value);
                throw new IllegalStateException("to impl");
            case kEdsDataType_String:
                final String string = (String) value;
                recalculatedSize = string.length() + 1;
                data = new Memory(recalculatedSize);
                data.setString(0, string);
                break;
            case kEdsDataType_Int8:
            case kEdsDataType_UInt8:
                recalculatedSize = 1;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.setByte(0, (Byte) value);
                break;
            case kEdsDataType_Int16:
            case kEdsDataType_UInt16:
                recalculatedSize = 2;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.setShort(0, (Short) value);
                break;
            case kEdsDataType_Int32:
            case kEdsDataType_UInt32:
                recalculatedSize = 4;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.setNativeLong(0, new NativeLong((Long) value));
                break;
            case kEdsDataType_Int64:
            case kEdsDataType_UInt64:
                recalculatedSize = 8;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.setLong(0, (Long) value);
                break;
            case kEdsDataType_Float:
                recalculatedSize = 4;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.setFloat(0, (Float) value);
                break;
            case kEdsDataType_Double:
                recalculatedSize = 8;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.setDouble(0, (Double) value);
                break;
            case kEdsDataType_ByteBlock: {
                // In API reference, was seen as EdsInt8[] or EdsUInt32[], we use the later one as a safety
                final int[] array = (int[]) value;
                recalculatedSize = 4 * array.length;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.write(0, array, 0, array.length);
                break;
            }
            case kEdsDataType_Rational:
            case kEdsDataType_Point:
            case kEdsDataType_Rect:
            case kEdsDataType_Time:
            case kEdsDataType_FocusInfo:
            case kEdsDataType_PictureStyleDesc:
                final Structure structure = (Structure) value;
                structure.write();
                data = structure.getPointer();
                recalculatedSize = structure.size();
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                break;
            case kEdsDataType_Bool_Array:
                log.error("property: {}, propertyType: {}, propertySize: {}, value: {}", property, propertyType, propertySize, value);
                throw new IllegalStateException("to impl");
            case kEdsDataType_Int8_Array:
            case kEdsDataType_UInt8_Array: {
                final byte[] array = (byte[]) value;
                recalculatedSize = array.length;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.write(0, array, 0, array.length);
                break;
            }
            case kEdsDataType_Int16_Array:
            case kEdsDataType_UInt16_Array: {
                final short[] array = (short[]) value;
                recalculatedSize = 2 * array.length;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.write(0, array, 0, array.length);
                break;
            }
            case kEdsDataType_Int32_Array:
            case kEdsDataType_UInt32_Array: {
                final int[] array = (int[]) value;
                recalculatedSize = 4 * array.length;
                log.debug(DEBUG_RETURNED_SIZE_AND_PRESET_ONE, propertySize, recalculatedSize);
                data = new Memory(recalculatedSize);
                data.write(0, array, 0, array.length);
                break;
            }
            case kEdsDataType_Rational_Array:
                log.error("property: {}, propertyType: {}, propertySize: {}, value: {}", property, propertyType, propertySize, value);
                throw new IllegalStateException("to impl");
            default:
                log.error("Unknown property type: {}, {}", propertyType, propertyType.value());
                throw new IllegalStateException("Unknown property type: " + propertyType + ", " + propertyType.value());
        }

        final EdsdkError error = setPropertyData(ref, property, inParam, recalculatedSize, data);
        if (error != EdsdkError.EDS_ERR_OK) {
            throw error.getException();
        }
    }
}
