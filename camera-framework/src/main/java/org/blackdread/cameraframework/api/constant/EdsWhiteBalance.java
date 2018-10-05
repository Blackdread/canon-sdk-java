package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * White Balance
 * <br>
 * <ul>
 * <li>If the white balance type is "Color Temperature," to know the actual color temperature you must reference another property (kEdsPropID_ColorTemperature)</li>
 * <li>With this property, it is possible to get values at the time of shooting</li>
 * </ul>
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsWhiteBalance implements NativeEnum<Integer> {
    kEdsWhiteBalance_Auto("Auto"),
    kEdsWhiteBalance_Daylight("Daylight"),
    kEdsWhiteBalance_Cloudy("Cloudy"),
    kEdsWhiteBalance_Tangsten("Tungsten"),
    kEdsWhiteBalance_Fluorescent("Fluorescent"),
    kEdsWhiteBalance_Strobe("Flash"),
    kEdsWhiteBalance_WhitePaper("Manual (set by shooting a white card or paper)"),
    kEdsWhiteBalance_Shade("Shade"),
    kEdsWhiteBalance_ColorTemp("Color temperature"),
    kEdsWhiteBalance_PCSet1("Custom white balance: PC-1"),
    kEdsWhiteBalance_PCSet2("Custom white balance: PC-2"),
    kEdsWhiteBalance_PCSet3("Custom white balance: PC-3"),
    kEdsWhiteBalance_WhitePaper2("Manual 2"),
    kEdsWhiteBalance_WhitePaper3("Manual 3"),
    kEdsWhiteBalance_WhitePaper4("Manual 4"),
    kEdsWhiteBalance_WhitePaper5("Manual 5"),
    kEdsWhiteBalance_PCSet4("Custom white balance: PC-4"),
    kEdsWhiteBalance_PCSet5("Custom white balance: PC-5"),
    /**
     * Not in API Reference
     */
    kEdsWhiteBalance_AwbWhite(""),
    kEdsWhiteBalance_Click("Setting the white balance by clicking image coordinates"),
    kEdsWhiteBalance_Pasted("White balance copied from another image");

    private final int value;
    private final String description;

    EdsWhiteBalance(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsWhiteBalance.class, name());
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
