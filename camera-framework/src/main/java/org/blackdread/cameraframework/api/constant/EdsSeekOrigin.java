package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Stream Seek Origins
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsSeekOrigin implements NativeEnum<Integer> {
    kEdsSeek_Cur("Current"),
    kEdsSeek_Begin("Beginning"),
    kEdsSeek_End("Ending");

    private final int value;
    private final String description;

    EdsSeekOrigin(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsSeekOrigin.class, name());
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
