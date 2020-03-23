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
 * Starts/ends live view.<br>
 * The camera TFT and PC to be used as the output device for live view can be specified.<br>
 * If a PC only is set for the output device, UILock status will be set for the camera except for the SET button
 * <br>
 * See API Reference - 5.2.65 kEdsPropID_Evf_OutputDevice
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsEvfOutputDevice implements NativeEnum<Integer> {
    kEdsEvfOutputDevice_TFT("Live view is displayed on the camera’s TFT"),
    kEdsEvfOutputDevice_PC("The live view image can be transferred to the PC"),
    /**
     * Unknown enum
     */
    kEdsEvfOutputDevice_MOBILE("Unknown enum"),
    /**
     * Unknown enum
     */
    kEdsEvfOutputDevice_MOBILE2("Unknown enum");

    private final int value;
    private final String description;

    EdsEvfOutputDevice(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfOutputDevice.class, name());
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
    public static EdsEvfOutputDevice ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfOutputDevice.class, value);
    }

}
