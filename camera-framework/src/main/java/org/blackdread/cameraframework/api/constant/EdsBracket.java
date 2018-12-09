package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Indicates the current bracket type.<br>
 * If multiple brackets have been set on the camera, you can get the bracket type as a logical sum.<br>
 * This property cannot be used to get bracket compensation.
 * Compensation is collected separately because there are separate properties for each bracket type.
 * <br>
 * See API Reference - 5.2.30 kEdsPropID_Bracket
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsBracket implements NativeEnum<Integer> {
    kEdsBracket_AEB("AE bracket"),
    kEdsBracket_ISOB("ISO bracket"),
    kEdsBracket_WBB("WB bracket"),
    kEdsBracket_FEB("FE bracket"),
    kEdsBracket_Unknown("Bracket off");

    private final int value;
    private final String description;

    EdsBracket(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsBracket.class, name());
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
    public static EdsBracket ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsBracket.class, value);
    }

}
