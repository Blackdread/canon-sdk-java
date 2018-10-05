package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Starts/ends live view.<br>
 * The camera TFT and PC to be used as the output device for live view can be specified.<br>
 * If a PC only is set for the output device, UILock status will be set for the camera except for the SET button
 * <br>
 * See API Reference - 5.2.65 kEdsPropID_Evf_OutputDevice
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsEvfOutputDevice implements NativeEnum<Integer> {
    kEdsEvfOutputDevice_TFT("Live view is displayed on the camera’s TFT"),
    kEdsEvfOutputDevice_PC("The live view image can be transferred to the PC"),
    /**
     * Unknown enum
     */
    kEdsEvfOutputDevice_MOBILE(""),
    /**
     * Unknown enum
     */
    kEdsEvfOutputDevice_MOBILE2("");

    private final int value;
    private final String description;

    EdsEvfOutputDevice(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfOutputDevice.class, name());
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
