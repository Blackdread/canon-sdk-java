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

    // could deprecate this method, and use instead 'getOneEnumValue().getClass().getEnumConstants()' but T could be not an enum so would not work
    abstract T[] getAllEnumValues();

    /**
     * @return class from library or null if not defined
     */
    abstract Class<?> getLibraryClass();

    @Test
    void canBeLoaded() {
        final T enumValue = getOneEnumValue();
        assertNotNull(enumValue);
    }

    @Test
    void allValuesDefined() {
        if (getLibraryClass() == null)
            return;
        assertEquals(countClassField(), getAllEnumValues().length);
    }

    /**
     * @return count of fields in the library class from {@link #getLibraryClass()}
     */
    int countClassField() {
        return LibraryFieldUtil.countClassField(getLibraryClass());
    }

    @Test
    void allHaveValue() {
        for (final T enumValue : getAllEnumValues()) {
            assertNotNull(enumValue.value());
        }
    }

    @Test
    void allHaveDescription() {
        for (final T enumValue : getAllEnumValues()) {
            assertNotNull(enumValue.description());
        }
    }

}
