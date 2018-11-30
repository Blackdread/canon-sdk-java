package org.blackdread.cameraframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <p>Created on 2018/10/04</p>
 *
 * @author Yoann CAPLAIN
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
