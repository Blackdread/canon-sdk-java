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
 * Toning Effects
 * <br>
 * Indicates the monochrome tone.
 * <br>
 * The supported models are the Kiss Digital N/350D/REBEL XT and 20D only.
 * <br>
 * <br>
 * Note:
 * <ul>
 * <li>This property is invalid for models supporting picture styles. For models supporting picture styles, use the property kEdsPropID_PictureStyleDesc</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <br>
 * See API Reference - 5.2.50 kEdsPropID_ToningEffect
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsTonigEffect implements NativeEnum<Integer> {
    kEdsTonigEffect_None("None"),
    kEdsTonigEffect_Sepia("Sepia"),
    kEdsTonigEffect_Blue("Blue"),
    kEdsTonigEffect_Purple("Purple"),
    kEdsTonigEffect_Green("Green");
//    kEdsTonigEffect_Unknown("Unknown"); // is in reference but not in Library

    private final int value;
    private final String description;

    EdsTonigEffect(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsTonigEffect.class, name());
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
    public static EdsTonigEffect ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsTonigEffect.class, value);
    }

}
