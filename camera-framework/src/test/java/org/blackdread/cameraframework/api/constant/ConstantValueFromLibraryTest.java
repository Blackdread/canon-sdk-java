package org.blackdread.cameraframework.api.constant;

import org.blackdread.cameraframework.util.LibraryFieldUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
abstract class ConstantValueFromLibraryTest<T extends NativeEnum> {

    abstract T getOneEnumValue();

    abstract T[] getAllEnumValues();

    abstract Class<?> getLibraryClass();

    @Test
    void canBeLoaded() {
        final T enumValue = getOneEnumValue();
        assertNotNull(enumValue);
    }

    @Test
    void allValuesDefined() {
        assertEquals(getAllEnumValues().length, LibraryFieldUtil.countClassField(getLibraryClass()));
    }

    @Test
    void allHaveValue() {
        for (final T enumValue : getAllEnumValues()) {
            assertNotNull(enumValue.value());
        }
    }

}
