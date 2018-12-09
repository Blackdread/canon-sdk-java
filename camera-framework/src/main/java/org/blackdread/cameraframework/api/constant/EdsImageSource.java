package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Image Source
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsImageSource implements NativeEnum<Integer> {
    kEdsImageSrc_FullView("The image itself (a full-sized image)"),
    kEdsImageSrc_Thumbnail("A thumbnail image"),
    kEdsImageSrc_Preview("A preview image (displayed on the back screen of the camera)"),
    kEdsImageSrc_RAWThumbnail("A RAW thumbnail image"),
    kEdsImageSrc_RAWFullView("A RAW full-sized image");

    private final int value;
    private final String description;

    EdsImageSource(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsImageSource.class, name());
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
    public static EdsImageSource ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsImageSource.class, value);
    }

}
