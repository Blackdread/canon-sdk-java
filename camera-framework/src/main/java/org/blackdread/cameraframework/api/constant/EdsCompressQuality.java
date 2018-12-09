package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Image Compress Quality
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsCompressQuality implements NativeEnum<Integer> {
    kEdsCompressQuality_Normal("Normal"),
    kEdsCompressQuality_Fine("Fine"),
    kEdsCompressQuality_Lossless("Lossless"),
    kEdsCompressQuality_SuperFine("Superfine"),
    kEdsCompressQuality_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsCompressQuality(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsCompressQuality.class, name());
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
    public static EdsCompressQuality ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsCompressQuality.class, value);
    }

}
