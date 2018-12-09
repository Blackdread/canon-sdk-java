package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Color Matrix
 * <br>
 * Only valid for the EOS 1D Mark II and EOS 1Ds Mark II.
 * <br>
 * See API Reference - 5.2.44 kEdsPropID_ColorMatrix
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsColorMatrix implements NativeEnum<Integer> {
    kEdsColorMatrix_Custom("Custom"),
    kEdsColorMatrix_1("ColorMatrix1"),
    kEdsColorMatrix_2("ColorMatrix2"),
    kEdsColorMatrix_3("ColorMatrix3"),
    kEdsColorMatrix_4("ColorMatrix4"),
    kEdsColorMatrix_5("ColorMatrix5"),
    kEdsColorMatrix_6("ColorMatrix6"),
    kEdsColorMatrix_7("ColorMatrix7");
//    kEdsColorMatrix_Unknown("Unknown, also applies for a color matrix customized on a computer and set on the camera");

    private final int value;
    private final String description;

    EdsColorMatrix(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsColorMatrix.class, name());
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
    public static EdsColorMatrix ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsColorMatrix.class, value);
    }

}
