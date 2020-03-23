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
 * Shutter Speed Values
 * <br>
 * Caution is advised because EdsCameraRef and EdsImageRef yield different values.
 * <br>
 * Bulb is designed so that it cannot be set on cameras from a computer by means of SetPropertyData
 * <br>
 * See API Reference - 5.2.24 kEdsPropID_Tv
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsTv implements NativeEnum<Integer> {
    kEdsTv_Bulb(0x0C, "Bulb"),
    kEdsTv_30(0x10, "30\""),
    kEdsTv_25(0x13, "25\""),
    kEdsTv_20(0x14, "20\""),
    kEdsTv_20b(0x15, "20\" (1/3)"),
    kEdsTv_15(0x18, "15\""),
    kEdsTv_13(0x1B, "13\""),
    kEdsTv_10(0x1C, "10\""),
    kEdsTv_10b(0x1D, "10\" (1/3)"),
    kEdsTv_8(0x20, "8\""),
    kEdsTv_6b(0x23, "6\" (1/3)"),
    kEdsTv_6(0x24, "6\""),
    kEdsTv_5(0x25, "5\""),
    kEdsTv_4(0x28, "4\""),
    kEdsTv_3_2(0x2B, "3\"2"),
    kEdsTv_3(0x2C, "3\""),
    kEdsTv_2_5(0x2D, "2\"5"),
    kEdsTv_2(0x30, "2\""),
    kEdsTv_1_6(0x33, "1\"6"),
    kEdsTv_1_5(0x34, "1\"5"),
    kEdsTv_1_3(0x35, "1\"3"),
    kEdsTv_1(0x38, "1\""),
    kEdsTv_0_8(0x3B, "0\"8"),
    kEdsTv_0_7(0x3C, "0\"7"),
    kEdsTv_0_6(0x3D, "0\"6"),
    kEdsTv_0_5(0x40, "0\"5"),
    kEdsTv_0_4(0x43, "0\"4"),
    kEdsTv_0_3(0x44, "0\"3"),
    kEdsTv_0_3b(0x45, "0\"3 (1/3)"),
    kEdsTv_1by4(0x48, "1/4"),
    kEdsTv_1by5(0x4B, "1/5"),
    kEdsTv_1by6(0x4C, "1/6"),
    kEdsTv_1by6b(0x4D, "1/6 (1/3)"),
    kEdsTv_1by8(0x50, "1/8"),
    kEdsTv_1by10b(0x53, "1/10 (1/3)"),
    kEdsTv_1by10(0x54, "1/10"),
    kEdsTv_1by13(0x55, "1/13"),
    kEdsTv_1by15(0x58, "1/15"),
    kEdsTv_1by20b(0x5B, "1/20 (1/3)"),
    kEdsTv_1by20(0x5C, "1/20"),
    kEdsTv_1by25(0x5D, "1/25"),
    kEdsTv_1by30(0x60, "1/30"),
    kEdsTv_1by40(0x63, "1/40"),
    kEdsTv_1by45(0x64, "1/45"),
    kEdsTv_1by50(0x65, "1/50"),
    kEdsTv_1by60(0x68, "1/60"),
    kEdsTv_1by80(0x6B, "1/80"),
    kEdsTv_1by90(0x6C, "1/90"),
    kEdsTv_1by100(0x6D, "1/100"),
    kEdsTv_1by125(0x70, "1/125"),
    kEdsTv_1by160(0x73, "1/160"),
    kEdsTv_1by180(0x74, "1/180"),
    kEdsTv_1by200(0x75, "1/200"),
    kEdsTv_1by250(0x78, "1/250"),
    kEdsTv_1by320(0x7B, "1/320"),
    kEdsTv_1by350(0x7C, "1/350"),
    kEdsTv_1by400(0x7D, "1/400"),
    kEdsTv_1by500(0x80, "1/500"),
    kEdsTv_1by640(0x83, "1/640"),
    kEdsTv_1by750(0x84, "1/750"),
    kEdsTv_1by800(0x85, "1/800"),
    kEdsTv_1by1000(0x88, "1/1000"),
    kEdsTv_1by1250(0x8B, "1/1250"),
    kEdsTv_1by1500(0x8C, "1/1500"),
    kEdsTv_1by1600(0x8D, "1/1600"),
    kEdsTv_1by2000(0x90, "1/2000"),
    kEdsTv_1by2500(0x93, "1/2500"),
    kEdsTv_1by3000(0x94, "1/3000"),
    kEdsTv_1by3200(0x95, "1/3200"),
    kEdsTv_1by4000(0x98, "1/4000"),
    kEdsTv_1by5000(0x9B, "1/5000"),
    kEdsTv_1by6000(0x9C, "1/6000"),
    kEdsTv_1by6400(0x9D, "1/6400"),
    kEdsTv_1by8000(0xA0, "1/8000"),
    kEdsTv_Unknown(0xFFFFFFFF, "Not valid/no settings changes");

    private final int value;
    private final String description;

    EdsTv(final int value, final String description) {
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
    public static EdsTv ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsTv.class, value);
    }

}
