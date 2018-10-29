package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Depth of Field Preview
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsEvfDepthOfFieldPreview implements NativeEnum<Integer> {
    kEdsEvfDepthOfFieldPreview_OFF("Off"),
    kEdsEvfDepthOfFieldPreview_ON("On");

    private final int value;
    private final String description;

    EdsEvfDepthOfFieldPreview(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfDepthOfFieldPreview.class, name());
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
    public static EdsEvfDepthOfFieldPreview ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfDepthOfFieldPreview.class, value);
    }
}
