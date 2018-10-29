package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsEvfAf implements NativeEnum<Integer> {
    kEdsCameraCommand_EvfAf_OFF("AF Off"),
    kEdsCameraCommand_EvfAf_ON("AF On");

    private final int value;
    private final String description;

    EdsEvfAf(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfAf.class, name());
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
    public static EdsEvfAf ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfAf.class, value);
    }

}
