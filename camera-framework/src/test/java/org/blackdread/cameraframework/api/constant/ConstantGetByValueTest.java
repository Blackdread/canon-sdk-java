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
                Assertions.assertSame(enumFound.name(), nativeEnum.name());
                Assertions.assertSame(enumFound.description(), nativeEnum.description());
            }
        }

        for (final NativeEnum<Integer> nativeEnum : nativeEnums) {
            final Method ofValue = nativeEnum.getClass().getMethod("ofValue", Integer.class);
            final NativeEnum<Integer> result = (NativeEnum<Integer>) ofValue.invoke(null, nativeEnum.value());
            Assertions.assertNotNull(result);
            Assertions.assertEquals(result.value(), nativeEnum.value());

            if (!hasEnumDuplicateValues(nativeEnum)) {
                Assertions.assertSame(result, nativeEnum);
                Assertions.assertSame(result.name(), nativeEnum.name());
                Assertions.assertSame(result.description(), nativeEnum.description());
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
