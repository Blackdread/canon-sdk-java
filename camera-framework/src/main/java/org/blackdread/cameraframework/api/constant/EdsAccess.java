package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * File and Properties Access
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsAccess implements NativeEnum<Integer> {
    kEdsAccess_Read("Read Only"),
    kEdsAccess_Write("Write Only"),
    kEdsAccess_ReadWrite("Read and Write"),
    /**
     * This means that the designated memory card is in a state
     * preventing use, such as when the card is not formatted.
     */
    kEdsAccess_Error("Access Error");

    private final int value;
    private final String description;

    EdsAccess(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsAccess.class, name());
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

    public static EdsAccess ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsAccess.class, value);
    }

}
