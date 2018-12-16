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
 * Filter Effects
 * <br>
 * Indicates the monochrome filter effect
 * <br>
 * The supported models are the Kiss Digital N/350D/REBEL XT and 20D only
 * <ul>
 * <li>This property is invalid for models supporting picture styles. For models supporting picture styles, use the property kEdsPropID_PictureStyleDesc</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <br>
 * See API Reference - 5.2.49 kEdsPropID_FilterEffect
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsFilterEffect implements NativeEnum<Integer> {
    kEdsFilterEffect_None("None"),
    kEdsFilterEffect_Yellow("Yellow"),
    kEdsFilterEffect_Orange("Orange"),
    kEdsFilterEffect_Red("Red"),
    kEdsFilterEffect_Green("Green");

    private final int value;
    private final String description;

    EdsFilterEffect(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFilterEffect.class, name());
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
    public static EdsFilterEffect ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsFilterEffect.class, value);
    }

}
