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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class ConstantGetByValueTest {

    private static final Logger log = LoggerFactory.getLogger(ConstantGetByValueTest.class);

    @Test
    void getByValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final List<NativeEnum> nativeEnums = ConstantUtil.getNativeEnums();

        for (final NativeEnum<Integer> nativeEnum : nativeEnums) {
            final NativeEnum<Integer> enumFound = ConstantUtil.ofValue((Class<? extends NativeEnum<Integer>>) nativeEnum.getClass(), nativeEnum.value());
            Assertions.assertNotNull(enumFound);
            Assertions.assertEquals(enumFound.value(), nativeEnum.value());

            if (!hasEnumDuplicateValues(nativeEnum)) {
                Assertions.assertSame(enumFound, nativeEnum);
                Assertions.assertEquals(enumFound.name(), nativeEnum.name());
                Assertions.assertEquals(enumFound.description(), nativeEnum.description());
            }
        }

        for (final NativeEnum<Integer> nativeEnum : nativeEnums) {
            final Method ofValue = nativeEnum.getClass().getMethod("ofValue", Integer.class);
            final NativeEnum<Integer> result = (NativeEnum<Integer>) ofValue.invoke(null, nativeEnum.value());
            Assertions.assertNotNull(result);
            Assertions.assertEquals(result.value(), nativeEnum.value());

            if (!hasEnumDuplicateValues(nativeEnum)) {
                Assertions.assertSame(result, nativeEnum);
                Assertions.assertEquals(result.name(), nativeEnum.name());
                Assertions.assertEquals(result.description(), nativeEnum.description());
            }
        }
    }

    @Test
    void getByValueThrowsForNullValue() {
        Assertions.assertThrows(NullPointerException.class, () -> ConstantUtil.ofValue(EdsISOSpeed.class, null));
    }

    @Test
    void getByValueThrowsForNotFoundValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ConstantUtil.ofValue(EdsISOSpeed.class, 9898989));

    }


    private boolean hasEnumDuplicateValues(final NativeEnum<Integer> nativeEnum) {
        if (nativeEnum == EdsBatteryLevel2.kEdsBatteryLevel2_Empty
            || nativeEnum == EdsBatteryLevel2.kEdsBatteryLevel2_Error
            || nativeEnum == EdsBatteryLevel2.kEdsBatteryLevel2_BCLevel
        )
            return true;
        return false;
    }

}
