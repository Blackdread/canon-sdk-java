package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Gets/sets the zoom ratio for the live view.<br>
 * The zoom ratio is set using EdsCameraRef, but obtained using live view image data, in other words, by using
 * EdsEvfImageRef
 * <br>
 * See API Reference - 5.2.70 kEdsPropID_Evf_Zoom
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsEvfZoom implements NativeEnum<Integer> {
    kEdsEvfZoom_Fit("Entire Screen"),
    kEdsEvfZoom_x5("5 times"),
    kEdsEvfZoom_x10("10 times");

    private final int value;
    private final String description;

    EdsEvfZoom(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfZoom.class, name());
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
    public static EdsEvfZoom ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfZoom.class, value);
    }

}
