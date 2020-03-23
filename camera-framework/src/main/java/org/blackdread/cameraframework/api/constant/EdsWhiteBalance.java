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

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * White Balance
 * <br>
 * <ul>
 * <li>If the white balance type is "Color Temperature," to know the actual color temperature you must reference another property (kEdsPropID_ColorTemperature)</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsWhiteBalance implements NativeEnum<Integer> {
    kEdsWhiteBalance_Auto("Auto: Ambiance priority"),
    kEdsWhiteBalance_Daylight("Daylight"),
    kEdsWhiteBalance_Cloudy("Cloudy"),
    kEdsWhiteBalance_Tangsten("Tungsten"),
    kEdsWhiteBalance_Fluorescent("Fluorescent"),
    kEdsWhiteBalance_Strobe("Flash"),
    kEdsWhiteBalance_WhitePaper("Manual (set by shooting a white card or paper)"),
    kEdsWhiteBalance_Shade("Shade"),
    kEdsWhiteBalance_ColorTemp("Color temperature"),
    kEdsWhiteBalance_PCSet1("Custom white balance: PC-1"),
    kEdsWhiteBalance_PCSet2("Custom white balance: PC-2"),
    kEdsWhiteBalance_PCSet3("Custom white balance: PC-3"),
    kEdsWhiteBalance_WhitePaper2("Manual 2"),
    kEdsWhiteBalance_WhitePaper3("Manual 3"),
    kEdsWhiteBalance_WhitePaper4("Manual 4"),
    kEdsWhiteBalance_WhitePaper5("Manual 5"),
    kEdsWhiteBalance_PCSet4("Custom white balance: PC-4"),
    kEdsWhiteBalance_PCSet5("Custom white balance: PC-5"),
    kEdsWhiteBalance_AwbWhite("Auto: White priority"),
    kEdsWhiteBalance_Click("Setting the white balance by clicking image coordinates"),
    kEdsWhiteBalance_Pasted("White balance copied from another image"),
    /**
     * @deprecated got it from my camera... do not use. Put it here for now to let tests pass
     */
    kEdsWhiteBalance_UnknownSelf(32768, "Unknown value");

    private final int value;
    private final String description;

    EdsWhiteBalance(final int value, final String description) {
        this.value = value;
        this.description = description;
    }

    EdsWhiteBalance(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsWhiteBalance.class, name());
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
    public static EdsWhiteBalance ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsWhiteBalance.class, value);
    }

}
