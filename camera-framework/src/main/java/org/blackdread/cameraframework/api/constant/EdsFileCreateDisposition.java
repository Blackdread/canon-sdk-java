package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * File Create Disposition
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsFileCreateDisposition implements NativeEnum<Integer> {
    kEdsFileCreateDisposition_CreateNew("Creates a new file. An error occurs if the designated file already exists"),
    kEdsFileCreateDisposition_CreateAlways("Creates a new file. If the designated file already exists, that file is overwritten and existing attributes is erased"),
    kEdsFileCreateDisposition_OpenExisting("Opens a file. An error occurs if the designated file does not exist"),
    kEdsFileCreateDisposition_OpenAlways("If the file exists, it is opened. If the designated file does not exist, a new file is created"),
    kEdsFileCreateDisposition_TruncateExsisting("Opens a file and sets the file size to 0 bytes");

    private final int value;
    private final String description;

    EdsFileCreateDisposition(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFileCreateDisposition.class, name());
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

    public static EdsFileCreateDisposition ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsFileCreateDisposition.class, value);
    }

}
