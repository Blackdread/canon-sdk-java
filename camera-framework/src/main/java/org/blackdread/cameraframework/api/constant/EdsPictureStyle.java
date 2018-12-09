package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * PictureStyle
 * <br>
 * Indicates the picture style.
 * <br>
 * This property is valid only for models supporting picture styles.
 * <br>
 * To get or set the picture style registered in "User Setting," designate user setting 1– (kEdsPictureStyle_User1– ) in
 * inParam. By using inParam = 0, you can designate the current picture style.
 * <br>
 * <br>
 * Note: Write is available as the access type with EdsImageRef only for RAW images.
 * <br>
 * <ul>
 * <li>Computer settings (1 and so on) refers to data that was set by designating a picture style file to upload to the camera from a host computer. Computer setting data is registered in the corresponding user setting. (For example, computer setting 1 corresponds to user setting 1). As a user setting, it represents a picture style that users can select</li>
 * <li>Picture styles registered in computer settings always have a base picture style. As for picture styles other than presets, only base picture styles can be retrieved by means of this property value</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <br>
 * See API Reference - 5.2.52 kEdsPropID_PictureStyle
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsPictureStyle implements NativeEnum<Integer> {
    kEdsPictureStyle_Standard("Standard"),
    kEdsPictureStyle_Portrait("Portrait"),
    kEdsPictureStyle_Landscape("Landscape"),
    kEdsPictureStyle_Neutral("Neutral"),
    kEdsPictureStyle_Faithful("Faithful"),
    kEdsPictureStyle_Monochrome("Monochrome"),
    kEdsPictureStyle_Auto("Auto (only for supported models)"),
    kEdsPictureStyle_FineDetail("Fine Detail(only for supported models)"),
    kEdsPictureStyle_User1("User 1"),
    kEdsPictureStyle_User2("User 2"),
    kEdsPictureStyle_User3("User 3"),
    kEdsPictureStyle_PC1("Computer 1 (base picture style only)"),
    kEdsPictureStyle_PC2("Computer 2 (base picture style only)"),
    kEdsPictureStyle_PC3("Computer 3 (base picture style only)");

    private final int value;
    private final String description;

    EdsPictureStyle(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsPictureStyle.class, name());
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
    public static EdsPictureStyle ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsPictureStyle.class, value);
    }

}
