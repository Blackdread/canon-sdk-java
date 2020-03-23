/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
 * @since 1.0.0
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
     * <br>
     * Value inParam from {@link EdsEvfAf}
     */
    kEdsCameraCommand_DoEvfAf("Change Live View AF. Controls auto focus in live view mode"),
    /**
     * Drives the lens and adjusts focus
     * <br>
     * This command is supported only in live view mode
     * <br>
     * Value inParam from {@link EdsEvfDriveLens}
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
     * <br>
     * Value inParam from {@link EdsShutterButton}
     */
    kEdsCameraCommand_PressShutterButton("Change Shutter Button"),
    /**
     * This command is supported only for model of PowerShot Series
     * <br>
     * Value inParam from {@link EdsDcRemoteShootingMode}
     *
     * @since edsdk 13.9.10
     * @since 1.2.0
     */
    kEdsCameraCommand_SetRemoteShootingMode("Controls remote shooting mode");

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

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsCameraCommand ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsCameraCommand.class, value);
    }

}
