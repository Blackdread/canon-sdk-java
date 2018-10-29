package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsImageType implements NativeEnum<Integer> {
    kEdsImageType_Unknown("Folder, or unknown image type"),
    kEdsImageType_Jpeg("JPEG"),
    kEdsImageType_CRW("CRW"),
    kEdsImageType_RAW("RAW"),
    kEdsImageType_CR2("CR2");

    private final int value;
    private final String description;

    EdsImageType(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsImageType.class, name());
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
    public static EdsImageType ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsImageType.class, value);
    }

}
