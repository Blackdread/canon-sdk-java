package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Progress Option
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsProgressOption implements NativeEnum<Integer> {
    kEdsProgressOption_NoReport("Do not call a progress callback function"),
    kEdsProgressOption_Done("Call a progress callback function when the progress reaches 100%"),
    kEdsProgressOption_Periodically("Call a progress callback function periodically");

    private final int value;
    private final String description;

    EdsProgressOption(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsProgressOption.class, name());
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
    public static EdsProgressOption ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsProgressOption.class, value);
    }

}
