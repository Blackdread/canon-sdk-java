package org.blackdread.cameraframework.api.constant;

/**
 * Custom Function Values
 * <br>
 * Value passed as a 'param' to getPropertyData or setPropertyData
 * <br>
 * Data values given in comments are for Canon EOS 550D
 * <ul>
 * <li>0x1xx = C.Fn I :Exposure</li>
 * <li>0x2xx = C.Fn II :Image</li>
 * <li>0x5xx = C.Fn III :Autofocus/Drive</li>
 * <li>0x6xx = C.Fn III :Autofocus/Drive</li>
 * <li>0x7xx = C.Fn IV :Operation/Others</li>
 * <li>0x8xx = C.Fn IV :Operation/Others</li>
 * </ul>
 *
 * <p>Created on 2018/10/07.<p>
 *
 * @author Yoann CAPLAIN
 * @deprecated Not tested and not part of Reference API neither library. Will not be removed but at your own risk
 */
public enum EdsCustomFunction implements NativeEnum<Integer> {
    /**
     * 0:1/3-stop,
     * 1:1/2-stop
     */
    kEdsCustomFunction_ExposureIncrements(0x101, "Exposure level increments"),
    /**
     * 0:Off,
     * 1:On
     */
    kEdsCustomFunction_ISOExpansion(0x103, "ISO Expansion"),
    /**
     * 0:Auto,
     * 1:1/200-1/60sec.
     * auto,
     * 2:1/200sec.
     * (fixed)
     */
    kEdsCustomFunction_FlashSyncSpeed(0x10F, "Flash sync. speed in Av mode"),
    /**
     * 0:Off,
     * 1:Auto,
     * 2:On
     */
    kEdsCustomFunction_LongExpNoiseReduction(0x201, "Long exp. noise reduction"),
    /**
     * 0:Standard,
     * 1:Low,
     * 2:Strong,
     * 3:Disable
     */
    kEdsCustomFunction_HighISONoiseReduction(0x202, "High ISO speed noise reduction"),
    /**
     * 0:Disable,
     * 1:Enable
     */
    kEdsCustomFunction_HighlighTonePriority(0x203, "Highlight tone priority"),
    /**
     * 0:Enable,
     * 1:Disable,
     * 2:Enable external flash only,
     * 3:IR AF assist beam only
     */
    kEdsCustomFunction_AFAssistBeam(0x50E, "AF-assist beam firing"),
    /**
     * 0:Disable, 1:Enable
     */
    kEdsCustomFunction_MirrorLockup(0x60F, "Mirror lockup"),
    /**
     * 0:AF/AE lock,
     * 1:AE lock/AF,
     * 2:AF/AF lock, no AE lock,
     * 3:AE/AF, no AE lock
     */
    kEdsCustomFunction_ShutterAELockButton(0x701, "Shutter/AE lock button"),
    /**
     * 0:Normal (disabled),
     * 1:Image quality,
     * 2:Flash exposure comp.,
     * 3:LCD  monitor On/Off,
     * 4:Menu display,
     * 5:ISO speed
     */
    kEdsCustomFunction_AssignSETButton(0x704, "Assign SET button"),
    /**
     * 0:Display on,
     * 1:Previous display status
     */
    kEdsCustomFunction_LCDDisplayWhenOn(0x80F, "LCD display when power ON"),
    /**
     * 0:Disable,
     * 1:Enable
     */
    kEdsCustomFunction_AddVerificationData(0x811, "Add image verification data");

    private final int value;
    private final String description;

    EdsCustomFunction(final int value, final String description) {
        this.value = value;
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
    public static EdsCustomFunction ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsCustomFunction.class, value);
    }

}
