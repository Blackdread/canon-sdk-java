package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Image Size
 * <br>
 * Note: Small Raw1 and Small Raw2 are only EOS 50D and EOS 5D Mark II.
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsImageSize implements NativeEnum<Integer> {
    kEdsImageSize_Large("Large"),
    kEdsImageSize_Middle("Medium"),
    kEdsImageSize_Small("Small"),
    kEdsImageSize_Middle1("Medium 1"),
    kEdsImageSize_Middle2("Medium 2"),
    kEdsImageSize_Small1("Small 1"),
    kEdsImageSize_Small2("Small 2"),
    kEdsImageSize_Small3("Small 3"),
    kEdsImageSize_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsImageSize(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsImageSize.class, name());
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
    public static EdsImageSize ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsImageSize.class, value);
    }

}
