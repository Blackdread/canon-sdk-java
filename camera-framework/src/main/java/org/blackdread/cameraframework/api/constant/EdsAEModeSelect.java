package org.blackdread.cameraframework.api.constant;

/**
 * Indicates settings values of the camera in shooting mode.
 * <br>
 * You cannot set (that is, Write) this property on cameras with a mode dial.
 * <br>
 * If the target object is EdsCameraRef, you can use GetPropertyDesc to access this property and get a list of property values that can currently be set.
 * <br>
 * However, you cannot get a list of settable values from models featuring a dial. The GetPropertyDesc return value will be EDS_ERR_OK, and no items will be listed as values you can set.
 * <br>
 * <br>
 * <p>
 * The shooting mode is in either an applied or simple shooting zone. When a camera is in a shooting mode of the simple shooting zone, a variety of capture-related properties (such as for auto focus, drive mode, and metering mode) are automatically set to the optimal values. Thus, when the camera is in a shooting mode of a simple shooting zone, capture-related properties cannot be set on the camera
 * </p>
 * <br>
 * <p>
 * Values defined in Enum EdsAEMode. From EOS 5DMarkIII, in addition to EdsAEMode we added EdsAEModeSelect. For the models before EOS 60D, you cannot get the AE mode wich is registered to camera user settings.
 * </p>
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 * @see EdsAEMode
 * @since 1.0.0
 */
public enum EdsAEModeSelect implements NativeEnum<Integer> {
    kEdsAEModeSelect_Custom1(7, "Custom1"),
    kEdsAEModeSelect_Custom2(16, "Custom2"),
    kEdsAEModeSelect_Custom3(17, "Custom3"),
    kEdsAEModeSelect_SCN_Special_scene(25, "SCN Special scene");


    private final int value;
    private final String description;

    EdsAEModeSelect(final int value, final String description) {
        this.value = value;
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
    public static EdsAEModeSelect ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsAEModeSelect.class, value);
    }
}
