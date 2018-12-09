package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * ETTL-II Mode
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsETTL2Mode implements NativeEnum<Integer> {
    kEdsETTL2ModeEvaluative("Evaluative"),
    kEdsETTL2ModeAverage("Average");

    private final int value;
    private final String description;

    EdsETTL2Mode(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsETTL2Mode.class, name());
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
    public static EdsETTL2Mode ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsETTL2Mode.class, value);
    }

}
