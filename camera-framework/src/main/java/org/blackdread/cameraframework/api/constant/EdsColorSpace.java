package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Color Space
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsColorSpace implements NativeEnum<Integer> {
    kEdsColorSpace_sRGB("sRGB"),
    kEdsColorSpace_AdobeRGB("AdobeRGB"),
    kEdsColorSpace_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsColorSpace(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsColorSpace.class, name());
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
    public static EdsColorSpace ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsColorSpace.class, value);
    }

}
