package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Battery level
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsBatteryLevel2 implements NativeEnum<Integer> {
    kEdsBatteryLevel2_Empty("Empty"),
    kEdsBatteryLevel2_Low("Low"),
    kEdsBatteryLevel2_Half("Half"),
    kEdsBatteryLevel2_Normal("Normal"),
    kEdsBatteryLevel2_Hi("Hi"),
    kEdsBatteryLevel2_Quarter("Quarter"),
    kEdsBatteryLevel2_BCLevel("BC Level"),
    kEdsBatteryLevel2_Error("Error"),
    kEdsBatteryLevel2_AC("AC power"),
    kEdsBatteryLevel2_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsBatteryLevel2(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsBatteryLevel2.class, name());
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
    public static EdsBatteryLevel2 ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsBatteryLevel2.class, value);
    }

}
