package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * File attribute
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsFileAttributes implements NativeEnum<Integer> {
    kEdsFileAttribute_Normal("A standard file"),
    kEdsFileAttribute_ReadOnly("Read-Only"),
    kEdsFileAttribute_Hidden("Hidden attribute"),
    kEdsFileAttribute_System("System attribute"),
    kEdsFileAttribute_Archive("Archive attribute");

    private final int value;
    private final String description;

    EdsFileAttributes(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFileAttributes.class,
            name());
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
    public static EdsFileAttributes ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsFileAttributes.class, value);
    }

}
