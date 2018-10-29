package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Filter Effects
 * <br>
 * Indicates the monochrome filter effect
 * <br>
 * The supported models are the Kiss Digital N/350D/REBEL XT and 20D only
 * <ul>
 * <li>This property is invalid for models supporting picture styles. For models supporting picture styles, use the property kEdsPropID_PictureStyleDesc</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <br>
 * See API Reference - 5.2.49 kEdsPropID_FilterEffect
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsFilterEffect implements NativeEnum<Integer> {
    kEdsFilterEffect_None("None"),
    kEdsFilterEffect_Yellow("Yellow"),
    kEdsFilterEffect_Orange("Orange"),
    kEdsFilterEffect_Red("Red"),
    kEdsFilterEffect_Green("Green");

    private final int value;
    private final String description;

    EdsFilterEffect(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFilterEffect.class, name());
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
    public static EdsFilterEffect ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsFilterEffect.class, value);
    }

}
