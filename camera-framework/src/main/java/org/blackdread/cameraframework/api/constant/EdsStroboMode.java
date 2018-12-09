package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Strobo Mode
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsStroboMode implements NativeEnum<Integer> {
    kEdsStroboModeInternal("Internal"),
    kEdsStroboModeExternalETTL("External ETTL"),
    kEdsStroboModeExternalATTL("External ATTL"),
    kEdsStroboModeExternalTTL("External TTL"),
    kEdsStroboModeExternalAuto("External Auto"),
    kEdsStroboModeExternalManual("External Manual"),
    kEdsStroboModeManual("Manual");

    private final int value;
    private final String description;

    EdsStroboMode(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsStroboMode.class, name());
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
    public static EdsStroboMode ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsStroboMode.class, value);
    }

}
