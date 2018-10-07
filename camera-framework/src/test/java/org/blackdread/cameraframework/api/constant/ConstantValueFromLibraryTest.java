package org.blackdread.cameraframework.api.constant;

import org.blackdread.cameraframework.util.LibraryFieldUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
abstract class ConstantValueFromLibraryTest<T extends NativeEnum> {

    private static final Logger log = LoggerFactory.getLogger(ConstantValueFromLibraryTest.class);

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
    void noDuplicateValues() {
        final List<T> skipCheckDuplicateValues = skipCheckDuplicateValues();

        final List<T> duplicateValues = Arrays.stream(getAllEnumValues())
            .collect(Collectors.groupingBy(NativeEnum::value)).values().stream()
            .filter(duplicateEnum -> duplicateEnum.size() > 1)
            .map(duplicateEnum -> {
                if (duplicateEnum.removeAll(skipCheckDuplicateValues)) {
                    log.warn("Duplicate removed: {}" + skipCheckDuplicateValues);
                }
                return duplicateEnum;
            })
            .filter(duplicateEnum -> duplicateEnum.size() > 1)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        assertEquals(0, duplicateValues.size(), () -> "Duplicate values for: " + String.join(", ", duplicateValues.stream().map(Object::toString).collect(Collectors.toList())));
    }

    /**
     * One of each duplicate values should be kept to make sure future changes can be checked
     *
     * @return List of native enum to skip from checking if value is duplicated
     */
    List<T> skipCheckDuplicateValues() {
        return Collections.emptyList();
    }

    @Test
    void allHaveDescription() {
        for (final T enumValue : getAllEnumValues()) {
            assertNotNull(enumValue.description());
        }
    }

}
