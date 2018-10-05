package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * <p>Created on 2018/10/04.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsConstant implements NativeEnum<Integer> {
    EDS_MAX_NAME("Maximum File Name Length"),
    EDS_TRANSFER_BLOCK_SIZE("Transfer Block Size");

    private final int value;
    private final String description;

    EdsConstant(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.class, name());
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
