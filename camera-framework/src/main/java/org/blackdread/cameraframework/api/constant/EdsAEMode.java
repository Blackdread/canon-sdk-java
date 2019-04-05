/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
 * Indicates settings values of the camera in shooting mode.
 * <br>
 * When the AE Mode Dial is set to camera user settings, you will get the AE mode which is been registered to the selected camera user setting.
 * <br>
 * For the camera which AE Mode is settable, you can change the AE Mode by using kEdsPropID_AEModeSelect.
 * <br>
 * See API Reference - 5.2.17 kEdsPropID_AEMode
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsAEMode implements NativeEnum<Integer> {

    kEdsAEMode_Program("Program AE"),
    kEdsAEMode_Tv("Shutter-Speed Priority AE"),
    kEdsAEMode_Av("Aperture Priority AE"),
    kEdsAEMode_Manual("Manual Exposure"),
    /**
     * Note: For some models, the value of the property cannot be retrieved as kEdsPropID_AEMode.
     * In this case, Bulb is retrieved as the value of the shutter speed (kEdsPropID_Tv) property.
     * Note: Bulb is designed so that it cannot be set on cameras from a computer by means of SetPropertyData.
     */
    kEdsAEMode_Bulb("Bulb"),
    kEdsAEMode_A_DEP("Auto Depth-of-Field AE"),
    kEdsAEMode_DEP("Depth-of-Field AE"),
    kEdsAEMode_Custom("Camera settings registered"),
    kEdsAEMode_Lock("Lock"),
    kEdsAEMode_Green("Auto"),
    kEdsAEMode_NightPortrait("Night Scene Portrait"),
    kEdsAEMode_Sports("Sports"),
    kEdsAEMode_Portrait("Portrait"),
    kEdsAEMode_Landscape("Landscape"),
    kEdsAEMode_Closeup("Close-Up"),
    kEdsAEMode_FlashOff("Flash Off"),
    kEdsAEMode_CreativeAuto("Creative Auto"),
    kEdsAEMode_Movie("Movie"),
    /**
     * This value is valid for only Image
     */
    kEdsAEMode_PhotoInMovie("Photo In Movie"),
    kEdsAEMode_SceneIntelligentAuto("Scene Intelligent Auto"),
    kEdsAEMode_SCN("Unknown description"),
    kEdsAEMode_NightScenes("Night Scenes"),
    kEdsAEMode_BacklitScenes("Backlit Scenes"),
    kEdsAEMode_Children("Children"),
    kEdsAEMode_Food("Food"),
    kEdsAEMode_CandlelightPortraits("Candlelight Portraits"),
    kEdsAEMode_CreativeFilter("Creative Filter"),
    kEdsAEMode_RoughMonoChrome("Grainy B/W"),
    kEdsAEMode_SoftFocus("Soft focus"),
    kEdsAEMode_ToyCamera("Toy camera effect"),
    kEdsAEMode_Fisheye("Fish-eye effect"),
    kEdsAEMode_WaterColor("Water painting effect"),
    kEdsAEMode_Miniature("Miniature effect"),
    kEdsAEMode_Hdr_Standard("HDR art standard"),
    kEdsAEMode_Hdr_Vivid("HDR art vivid"),
    kEdsAEMode_Hdr_Bold("HDR art bold"),
    kEdsAEMode_Hdr_Embossed("HDR art embossed"),
    kEdsAEMode_Movie_Fantasy("Dream"),
    kEdsAEMode_Movie_Old("Old Movies"),
    kEdsAEMode_Movie_Memory("Memory"),
    kEdsAEMode_Movie_DirectMono("Dramatic B&W"),
    kEdsAEMode_Movie_Mini("Miniature effect movie"),
    kEdsAEMode_PanningAssist("Panning assist"),
    kEdsAEMode_GroupPhoto("Group Photo"),
    kEdsAEMode_Myself("Myself"),
    /**
     * @since edsdk 13.9.10
     * @since 1.2.0
     */
    kEdsAEMode_PlusMovieAuto("Plus Movie Auto"),
    kEdsAEMode_SmoothSkin("SmoothSkin"),
    /**
     * @since edsdk 13.9.10
     * @since 1.2.0
     */
    kEdsAEMode_Panorama("Panorama"),
    /**
     * @since edsdk 13.9.10
     * @since 1.2.0
     */
    kEdsAEMode_Silent("Silent"),
    kEdsAEMode_Flexible("Flexible-priority AE"),
    /**
     * @since edsdk 13.9.10
     * @since 1.2.0
     */
    kEdsAEMode_OilPainting("Oil Painting"),
    /**
     * @since edsdk 13.9.10
     * @since 1.2.0
     */
    kEdsAEMode_Fireworks("Fireworks"),

    kEdsAEMode_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsAEMode(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsAEMode.class, name());
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
    public static EdsAEMode ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsAEMode.class, value);
    }
}
