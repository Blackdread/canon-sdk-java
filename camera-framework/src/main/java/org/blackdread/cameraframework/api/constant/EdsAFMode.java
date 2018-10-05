package org.blackdread.cameraframework.api.constant;

/**
 * Auto-Focus Mode Values
 * <br>
 * Indicates AF mode settings values
 * <br>
 * It is read-only (only get from API)
 * <br>
 * See API Reference - 5.2.22 kEdsPropID_AFMode
 * <p>Created on 2018/10/04.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsAFMode implements NativeEnum<Integer> {
    kEdsAFMode_OneShot(0, "One-Shot AF"),
    kEdsAFMode_AIServo(1, "AI Servo AF"),
    kEdsAFMode_AIFocus(2, "AI Focus AF"),
    kEdsAFMode_Manual(3, "Manual Focus"),
    kEdsAFMode_Unknown(0xFFFFFFFF, "Not valid/no settings changes");

    private final int value;
    private final String description;

    EdsAFMode(final int value, final String description) {
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
