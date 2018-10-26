package org.blackdread.cameraframework.api.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class ConstantGetByValueTest {

    private static final Logger log = LoggerFactory.getLogger(ConstantGetByValueTest.class);

    @Test
    void getByValue() {
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
