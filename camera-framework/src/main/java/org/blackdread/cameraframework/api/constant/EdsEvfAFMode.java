package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Set/Get the AF mode for the live view.<br>
 * This property can set/get from the EOS 50D or EOS 5D Mark II or later
 * <br>
 * See API Reference - 5.2.80 kEdsPropID_Evf_AFMode
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsEvfAFMode implements NativeEnum<Integer> {
    Evf_AFMode_Quick("Quick Mode"),
    Evf_AFMode_Live("Live Mode - 1-point AF"),
    Evf_AFMode_LiveFace("Live Face Mode - Face + Tracking"),
    Evf_AFMode_LiveMulti("FlexiZone - Multi"),
    Evf_AFMode_LiveZone("Zone AF"),
    Evf_AFMode_LiveSingleExpandCross("Expand AF area"),
    Evf_AFMode_LiveSingleExpandSurround("Expand AF area:Around"),
    Evf_AFMode_LiveZoneLargeH("Large Zone AF: Horizontal"),
    Evf_AFMode_LiveZoneLargeV("Large Zone AF: Vertical");

    private final int value;
    private final String description;

    EdsEvfAFMode(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsEvfAFMode.class, name());
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
    public static EdsEvfAFMode ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfAFMode.class, value);
    }
}
