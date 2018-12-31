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

/**
 * Exposure Compensation Values (Same as Flash Compensation Values)
 * <br>
 * Exposure compensation refers to compensation relative to the position of the standard exposure mark (in the
 * center of the exposure gauge)
 * <br>
 * Caution is advised because EdsCameraRef and EdsImageRef yield different values
 * <br>
 * Exposure compensation is not available if the camera is in manual exposure mode. Thus, the exposure
 * compensation property is invalid
 * <br>
 * <br>
 * See API Reference - 5.2.25 kEdsPropID_ExposureCompensation
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsExposureCompensation implements NativeEnum<Integer> {
    kEdsExposureCompensation_5(0x28, "+5"),
    kEdsExposureCompensation_4_2by3(0x25, "+4 2/3"),
    kEdsExposureCompensation_4_1by2(0x24, "+4 1/2"),
    kEdsExposureCompensation_4_1by3(0x23, "+4 1/3"),
    kEdsExposureCompensation_4(0x20, "+4"),
    kEdsExposureCompensation_3_2by3(0x1D, "+3 2/3"),
    kEdsExposureCompensation_3_1by2(0x1C, "+3 1/2"),
    kEdsExposureCompensation_3_1by3(0x1B, "+3 1/3"),
    kEdsExposureCompensation_3(0x18, "+3"),
    kEdsExposureCompensation_2_2by3(0x15, "+2 2/3"),
    kEdsExposureCompensation_2_1by2(0x14, "+2 1/2"),
    kEdsExposureCompensation_2_1by3(0x13, "+2 1/3"),
    kEdsExposureCompensation_2(0x10, "+2"),
    kEdsExposureCompensation_1_2by3(0x0D, "+1 2/3"),
    kEdsExposureCompensation_1_1by2(0x0C, "+1 1/2"),
    kEdsExposureCompensation_1_1by3(0x0B, "+1 1/3"),
    kEdsExposureCompensation_1(0x08, "+1"),
    kEdsExposureCompensation_2by3(0x05, "+2/3"),
    kEdsExposureCompensation_1by2(0x04, "+1/2"),
    kEdsExposureCompensation_1by3(0x03, "+1/3"),
    kEdsExposureCompensation_0(0x00, "0"),
    kEdsExposureCompensation_n1by3(0xFD, "-1/3"),
    kEdsExposureCompensation_n1by2(0xFC, "-1/2"),
    kEdsExposureCompensation_n2by3(0xFB, "-2/3"),
    kEdsExposureCompensation_n1(0xF8, "-1"),
    kEdsExposureCompensation_n1_1by3(0xF5, "-1 1/3"),
    kEdsExposureCompensation_n1_1by2(0xF4, "-1 1/2"),
    kEdsExposureCompensation_n1_2by3(0xF3, "-1 2/3"),
    kEdsExposureCompensation_n2(0xF0, "-2"),
    kEdsExposureCompensation_n2_1by3(0xED, "-2 1/3"),
    kEdsExposureCompensation_n2_1by2(0xEC, "-2 1/2"),
    kEdsExposureCompensation_n2_2by3(0xEB, "-2 2/3"),
    kEdsExposureCompensation_n3(0xE8, "-3"),
    kEdsExposureCompensation_n3_1by3(0xE5, "-3 1/3"),
    kEdsExposureCompensation_n3_1by2(0xE4, "-3 1/2"),
    kEdsExposureCompensation_n3_2by3(0xE3, "-3 2/3"),
    kEdsExposureCompensation_n4(0xE0, "-4"),
    kEdsExposureCompensation_n4_1by3(0xDD, "-4 1/3"),
    kEdsExposureCompensation_n4_1by2(0xDC, "-4 1/2"),
    kEdsExposureCompensation_n4_2by3(0xDB, "-4 2/3"),
    kEdsExposureCompensation_n5(0xD8, "-5"),
    kEdsExposureCompensation_Unknown(0xFFFFFFFF, "Not valid/no settings changes");

    private final int value;
    private final String description;

    EdsExposureCompensation(final int value, final String description) {
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
    public static EdsExposureCompensation ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsExposureCompensation.class, value);
    }

}
