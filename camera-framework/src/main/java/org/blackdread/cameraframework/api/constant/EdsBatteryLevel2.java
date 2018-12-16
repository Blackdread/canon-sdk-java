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
package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Battery level
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsBatteryLevel2 implements NativeEnum<Integer> {
    kEdsBatteryLevel2_Empty("Empty"),
    kEdsBatteryLevel2_Low("Low"),
    kEdsBatteryLevel2_Half("Half"),
    kEdsBatteryLevel2_Normal("Normal"),
    kEdsBatteryLevel2_Hi("Hi"),
    kEdsBatteryLevel2_Quarter("Quarter"),
    kEdsBatteryLevel2_BCLevel("BC Level"),
    kEdsBatteryLevel2_Error("Error"),
    kEdsBatteryLevel2_AC("AC power"),
    kEdsBatteryLevel2_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsBatteryLevel2(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsBatteryLevel2.class, name());
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
    public static EdsBatteryLevel2 ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsBatteryLevel2.class, value);
    }

}
