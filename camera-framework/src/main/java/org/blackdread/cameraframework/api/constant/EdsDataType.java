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
package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Data Types
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsDataType implements NativeEnum<Integer> {
    kEdsDataType_Unknown("Unknown"),
    kEdsDataType_Bool("EdsBool"),
    kEdsDataType_String("EdsChar[]"),
    kEdsDataType_Int8("EdsInt8"),
    kEdsDataType_UInt8("EdsUInt8"),
    kEdsDataType_Int16("EdsInt16"),
    kEdsDataType_UInt16("EdsUInt16"),
    kEdsDataType_Int32("EdsInt32"),
    kEdsDataType_UInt32("EdsUInt32"),
    kEdsDataType_Int64("EdsInt64"),
    kEdsDataType_UInt64("EdsUInt64"),
    kEdsDataType_Float("EdsFloat"),
    kEdsDataType_Double("EdsDouble"),
    /**
     * In API reference document, data type is sometimes EdsInt8[] and EdsUInt32[]
     */
    kEdsDataType_ByteBlock("Byte Block"),
    kEdsDataType_Rational("EdsRational"),
    kEdsDataType_Point("EdsPoint"),
    kEdsDataType_Rect("EdsRect"),
    kEdsDataType_Time("EdsTime"),

    kEdsDataType_Bool_Array("EdsBool[]"),
    kEdsDataType_Int8_Array("EdsInt8[]"),
    kEdsDataType_Int16_Array("EdsInt16[]"),
    kEdsDataType_Int32_Array("EdsInt32[]"),
    kEdsDataType_UInt8_Array("EdsUInt8[]"),
    kEdsDataType_UInt16_Array("EdsUInt16[]"),
    kEdsDataType_UInt32_Array("EdsUInt32[]"),
    kEdsDataType_Rational_Array("EdsRational[]"),

    kEdsDataType_FocusInfo("EdsFocusInfo"),
    kEdsDataType_PictureStyleDesc("EdsPictureStyleDesc");

    private final int value;
    private final String description;

    EdsDataType(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsDataType.class, name());
        this.description = description;
    }

    @Override
    public final Integer value() {
        return value;
    }

    @Override
    public final String description() {
        return description;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsDataType ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsDataType.class, value);
    }

}
