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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * <p>Created on 2019/03/13.</p>
 *
 * @author Yoann CAPLAIN
 */
class ConstantUtilTest {

    private static final Logger log = LoggerFactory.getLogger(ConstantUtilTest.class);

    /*
    @Test
    void ofValue() {
    }

    @Test
    void getNativeEnums() {
    }

    @Test
    void getNativeEnumClasses() {
    }
    //*/

    @Test
    void getNativeEnumClassesManuallyIsImmutable() {
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually = ConstantUtil.getNativeEnumClassesManually();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> nativeEnumClassesManually.add(null));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> nativeEnumClassesManually.removeIf(o -> true));
    }

    @Test
    void getNativeEnumClassesManuallyIsSameInstance() {
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually = ConstantUtil.getNativeEnumClassesManually();
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually2 = ConstantUtil.getNativeEnumClassesManually();

        Assertions.assertSame(nativeEnumClassesManually, nativeEnumClassesManually2);
    }

    @Test
    void getNativeEnumClassesManuallyReturnsSameAsRuntimeClassPath() {
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClasses = ConstantUtil.getNativeEnumClasses();
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually = ConstantUtil.getNativeEnumClassesManually();

        Assertions.assertEquals(nativeEnumClasses.size(), nativeEnumClassesManually.size());
        Assertions.assertEquals(nativeEnumClasses, nativeEnumClassesManually);
    }
}
