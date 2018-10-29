package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Save To
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsSaveTo implements NativeEnum<Integer> {
    kEdsSaveTo_Camera("Camera"),
    kEdsSaveTo_Host("Host Computer"),
    kEdsSaveTo_Both("Both");

    private final int value;
    private final String description;

    EdsSaveTo(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsSaveTo.class, name());
        this.description = description;
    }

    @Override
    public final Integer value() {
        return value;
    }

    @Override
    public final String description() {
        return description;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsSaveTo ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsSaveTo.class, value);
    }

}
