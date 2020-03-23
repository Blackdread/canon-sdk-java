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
package org.blackdread.cameraframework.api.constant;

/**
 * Indicates ISO sensitivity settings values.
 * Caution is advised because it is possible to retrieve different values by means of EdsCameraRef and EdsImageRef.
 * <br>
 * If the target object is EdsCameraRef, you can use GetPropertyDesc to access this property and get a list of
 * property values that can currently be set.
 * <br>
 * See API Reference - 5.2.20 kEdsPropID_ISOSpeed
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsISOSpeed implements NativeEnum<Integer> {
    kEdsISOSpeed_Auto(0x0, "Auto"),
    /**
     * @deprecated not part of reference anymore
     */
    kEdsISOSpeed_6(0x28, "6"),
    /**
     * @deprecated not part of reference anymore
     */
    kEdsISOSpeed_12(0x30, "12"),
    /**
     * @deprecated not part of reference anymore
     */
    kEdsISOSpeed_25(0x38, "25"),
    kEdsISOSpeed_50(0x40, "50"),
    kEdsISOSpeed_100(0x48, "100"),
    kEdsISOSpeed_125(0x4B, "125"),
    kEdsISOSpeed_160(0x4D, "160"),
    kEdsISOSpeed_200(0x50, "200"),
    kEdsISOSpeed_250(0x53, "250"),
    kEdsISOSpeed_320(0x55, "320"),
    kEdsISOSpeed_400(0x58, "400"),
    kEdsISOSpeed_500(0x5B, "500"),
    kEdsISOSpeed_640(0x5D, "640"),
    kEdsISOSpeed_800(0x60, "800"),
    kEdsISOSpeed_1000(0x63, "1000"),
    kEdsISOSpeed_1250(0x65, "1250"),
    kEdsISOSpeed_1600(0x68, "1600"),
    kEdsISOSpeed_2000(0x6b, "2000"),
    kEdsISOSpeed_2500(0x6d, "2500"),
    kEdsISOSpeed_3200(0x70, "3200"),
    kEdsISOSpeed_4000(0x73, "4000"),
    kEdsISOSpeed_5000(0x75, "5000"),
    kEdsISOSpeed_6400(0x78, "6400"),
    kEdsISOSpeed_8000(0x7B, "8000"),
    kEdsISOSpeed_10000(0x7D, "10000"),
    kEdsISOSpeed_12800(0x80, "12800"),
    /**
     * @deprecated not part of reference anymore, found in sample code
     */
    kEdsISOSpeed_16000(0x83, "16000"),
    /**
     * @deprecated not part of reference anymore, found in sample code
     */
    kEdsISOSpeed_20000(0x85, "20000"),
    kEdsISOSpeed_25600(0x88, "25600"),
    /**
     * @deprecated not part of reference anymore, found in sample code
     */
    kEdsISOSpeed_32000(0x8B, "32000"),
    /**
     * @deprecated not part of reference anymore, found in sample code
     */
    kEdsISOSpeed_40000(0x8D, "40000"),
    kEdsISOSpeed_51200(0x90, "51200"),
    kEdsISOSpeed_102400(0x98, "102400"),
    kEdsISOSpeed_204800(0xA0, "204800"),
    kEdsISOSpeed_409600(0xA8, "409600"),
    kEdsISOSpeed_Unknown(0xFFFFFFFF, "Not valid/no settings changes");

    private final int value;

    private final String description;

    EdsISOSpeed(final int value, final String description) {
        this.value = value;
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
    public static EdsISOSpeed ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsISOSpeed.class, value);
    }
}
