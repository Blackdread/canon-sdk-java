package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Photo Effects
 * <br>
 * This property is valid only for the 20D and Kiss Digital N/350D/REBEL XT.
 * <br>
 * See API Reference - 5.2.48 kEdsPropID_PhotoEffect
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsPhotoEffect implements NativeEnum<Integer> {
    kEdsPhotoEffect_Off("Off (Color Effect deactivated. Normal shooting)"),
    kEdsPhotoEffect_Monochrome("Monochrome (Black and white)");

    private final int value;
    private final String description;

    EdsPhotoEffect(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsPhotoEffect.class, name());
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
    public static EdsPhotoEffect ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsPhotoEffect.class, value);
    }

}
