package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Transfer Option
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsTransferOption implements NativeEnum<Integer> {
    kEdsTransferOption_ByDirectTransfer("By Direct Transfer"),
    kEdsTransferOption_ByRelease("By Release"),
    kEdsTransferOption_ToDesktop("To Desktop");

    private final int value;
    private final String description;

    EdsTransferOption(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsTransferOption.class, name());
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

    public static EdsTransferOption ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsTransferOption.class, value);
    }

}
