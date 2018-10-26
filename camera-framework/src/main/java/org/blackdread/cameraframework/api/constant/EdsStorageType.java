package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * StorageType
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsStorageType implements NativeEnum<Integer> {
    kEdsStorageType_Non("No memory card"),
    kEdsStorageType_CF("Compact Flash"),
    kEdsStorageType_SD("SD Flash"),
    kEdsStorageType_HD("Hard Drive"),
    kEdsStorageType_CFast("C Fast");

    private final int value;
    private final String description;

    EdsStorageType(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsStorageType.class, name());
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

    public static EdsStorageType ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsStorageType.class, value);
    }

}
