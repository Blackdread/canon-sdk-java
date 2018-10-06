package org.blackdread.cameraframework.api.constant;

/**
 * Drive Mode Values
 * <br>
 * See API Reference - 5.2.19 kEdsPropID_DriveMode
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsDriveMode implements NativeEnum<Integer> {
    kEdsDriveMode_SingleFrame(0x00000000, "Single-Frame Shooting"),
    kEdsDriveMode_Continuous(0x00000001, "Continuous Shooting"),
    kEdsDriveMode_Video(0x00000002, "Video"),
    kEdsDriveMode_NotUsed(0x00000003, "Not used"),
    kEdsDriveMode_HighSpeedContinuous(0x00000004, "High-Speed Continuous Shooting"),
    kEdsDriveMode_LowSpeedContinuous(0x00000005, "Low-Speed Continuous Shooting"),
    kEdsDriveMode_SingleSilentShooting(0x00000006, "Single Silent shooting"),
    kEdsDriveMode_10SecSelfTimerWithContinuous(0x00000007, "10-Sec Self-Timer + Continuous Shooting"),
    kEdsDriveMode_10SecSelfTimer(0x00000010, "10-Sec Self-Timer"),
    kEdsDriveMode_2SecSelfTimer(0x00000011, "2-Sec Self-Timer"),
    kEdsDriveMode_14FpsSuperHighSpeed(0x00000012, "14fps super high speed"),
    kEdsDriveMode_SilentSingleShooting(0x00000013, "Silent single shooting"),
    kEdsDriveMode_SilentContinuousShooting(0x00000014, "Silent continuous shooting"),
    kEdsDriveMode_SilentHSContinuous(0x00000015, "Silent HS continuous"),
    kEdsDriveMode_SilentLSContinuous(0x00000016, "Silent LS continuous");

    private final int value;
    private final String description;

    EdsDriveMode(final int value, final String description) {
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

}
