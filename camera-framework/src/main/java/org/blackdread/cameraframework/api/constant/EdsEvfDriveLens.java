package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Drive Lens
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsEvfDriveLens implements NativeEnum<Integer> {
    kEdsEvfDriveLens_Near1("Near 1"),
    kEdsEvfDriveLens_Near2("Near 2"),
    kEdsEvfDriveLens_Near3("Near 3"),
    kEdsEvfDriveLens_Far1("Far 1"),
    kEdsEvfDriveLens_Far2("Far 2"),
    kEdsEvfDriveLens_Far3("Far 3");

    private final int value;
    private final String description;

    EdsEvfDriveLens(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfDriveLens.class, name());
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
    public static EdsEvfDriveLens ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfDriveLens.class, value);
    }
}
