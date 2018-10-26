package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Target Image Types
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsTargetImageType implements NativeEnum<Integer> {
    kEdsTargetImageType_Unknown("Folder or unknown image type"),
    kEdsTargetImageType_Jpeg("JPEG"),
    kEdsTargetImageType_TIFF("8-bit TIFF"),
    kEdsTargetImageType_TIFF16("16-bit TIFF"),
    kEdsTargetImageType_RGB("8-bit RGB, chunky format"),
    kEdsTargetImageType_RGB16("16-bit RGB, chunky format"),
    kEdsTargetImageType_DIB("DIB (BMP)");

    private final int value;
    private final String description;

    EdsTargetImageType(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsTargetImageType.class, name());
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

    public static EdsTargetImageType ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsTargetImageType.class, value);
    }

}
