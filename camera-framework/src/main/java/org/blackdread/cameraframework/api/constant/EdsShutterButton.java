package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsShutterButton implements NativeEnum<Integer> {
    kEdsCameraCommand_ShutterButton_OFF("Not Depressed"),
    kEdsCameraCommand_ShutterButton_Halfway("Halfway Depressed"),
    kEdsCameraCommand_ShutterButton_Completely("Fully Depressed"),
    kEdsCameraCommand_ShutterButton_Halfway_NonAF("Halfway Depressed (Non-AF)"),
    kEdsCameraCommand_ShutterButton_Completely_NonAF("Fully Depressed (Non-AF)");

    private final int value;
    private final String description;

    EdsShutterButton(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsShutterButton.class, name());
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

    public static EdsShutterButton ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsShutterButton.class, value);
    }

}
