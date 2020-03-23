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
package org.blackdread.cameraframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <p>Created on 2018/10/04</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class LibraryFieldUtil {

    private static final Logger log = LoggerFactory.getLogger(LibraryFieldUtil.class);

    /**
     * Find in a class a constant defined with same name and return its value
     *
     * @param klass     class to search in
     * @param fieldName field name to find
     * @return value of the field
     */
    public static int getNativeIntValue(final Class klass, final String fieldName) {
        try {
            final Field field = klass.getField(fieldName);
            return field.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Failed to get field value for '{}' in {}: {}", fieldName, klass, e.getMessage());
            throw new IllegalStateException("Failed to get field value for '" + fieldName + "' from " + klass.getCanonicalName(), e);
        }
    }

    /**
     * @param klass class to search in
     * @return count of fields of the given class
     */
    public static int countClassField(final Class klass) {
        return klass.getDeclaredFields().length;
    }


    private LibraryFieldUtil() {
    }
}
