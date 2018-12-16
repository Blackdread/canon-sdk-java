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
package org.blackdread.cameraframework.api.constant.custom;

import org.blackdread.cameraframework.api.constant.NativeEnum;

/**
 * Indicates image rotation information.
 * <p>Created on 2018/11/18.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum ImageOrientation implements NativeEnum<Integer> {
    NORMAL(1, "The 0th row is at the visual top of the image, and the 0th column is on the visual left-hand side"),
    NORMAL_UPSIDE_DOWN(3, "The 0th row is at the visual bottom of the image, and the 0th column is on the visual right-hand side"),
    // TODO name
    NAME_IT_BETTER(6, "The 0th row is on the visual right-hand side of the image, and the 0th column is at the visual top"),
    NAME_IT_BETTER_2(8, "The 0th row is on the visual left-hand side of the image, and the 0th column is at the visual bottom");

    private final int value;
    private final String description;

    ImageOrientation(final int value, final String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static ImageOrientation ofValue(final int value) {
        switch (value) {
            case 1:
                return NORMAL;
            case 3:
                return NORMAL_UPSIDE_DOWN;
            case 6:
                return NAME_IT_BETTER_2;
            case 8:
                return NAME_IT_BETTER_2;
        }
        throw new IllegalArgumentException("No native enum found for value " + value + " for enum " + ImageOrientation.class.getSimpleName());
    }
}
