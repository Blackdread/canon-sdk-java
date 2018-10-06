package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Send Camera Commands
 * <br>
 * See API Reference - 3.1.14 EdsSendCommand
 *
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsCameraCommand implements NativeEnum<Integer> {
    kEdsCameraCommand_TakePicture("Take Picture. Requests the camera to shoot"),
    kEdsCameraCommand_ExtendShutDownTimer("Extend Auto-off Timer. Requests to extend the time for the auto shut-off timer (Keep Device On)"),
    /**
     * An exposure time event is generated at the start of bulb shooting (kEdsStateEvent_BulbExposureTime).
     */
    kEdsCameraCommand_BulbStart("Start Bulb Shooting. Lock the UI before bulb shooting"),
    kEdsCameraCommand_BulbEnd("Stop Bulb Shooting"),

    /**
     * Controls auto focus in live view mode.
     */
    kEdsCameraCommand_DoEvfAf("Change Live View AF. Controls auto focus in live view mode"),
    /**
     * Drives the lens and adjusts focus
     * <br>
     * This command is supported only in live view mode
     */
    kEdsCameraCommand_DriveLensEvf("Change Live View Focus. Drives the lens and adjusts focus"),
    /**
     * Adjusts the white balance of the live view image at the specified position
     * <br>
     * This command is supported only in live view mode
     */
    kEdsCameraCommand_DoClickWBEvf("Change Live View WB at Location. Adjusts the white balance of the live view image at the specified position"),

    /**
     * Controls shutter button operations
     */
    kEdsCameraCommand_PressShutterButton("Change Shutter Button");

    private final int value;
    private final String description;

    EdsCameraCommand(final String description) {
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
