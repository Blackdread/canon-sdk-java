package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Toning Effects
 * <br>
 * Indicates the monochrome tone.
 * <br>
 * The supported models are the Kiss Digital N/350D/REBEL XT and 20D only.
 * <br>
 * <br>
 * Note:
 * <ul>
 * <li>This property is invalid for models supporting picture styles. For models supporting picture styles, use the property kEdsPropID_PictureStyleDesc</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <br>
 * See API Reference - 5.2.50 kEdsPropID_ToningEffect
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsTonigEffect implements NativeEnum<Integer> {
    kEdsTonigEffect_None("None"),
    kEdsTonigEffect_Sepia("Sepia"),
    kEdsTonigEffect_Blue("Blue"),
    kEdsTonigEffect_Purple("Purple"),
    kEdsTonigEffect_Green("Green");
//    kEdsTonigEffect_Unknown("Unknown"); // is in reference but not in Library

    private final int value;
    private final String description;

    EdsTonigEffect(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsTonigEffect.class, name());
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

}
