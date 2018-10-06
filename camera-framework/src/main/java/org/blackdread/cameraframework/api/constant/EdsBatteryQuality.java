package org.blackdread.cameraframework.api.constant;

/**
 * Battery Quality
 * <br>
 * See API Reference - 5.2.11 kEdsPropID_BatteryQuality
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsBatteryQuality implements NativeEnum<Integer> {
    kEdsBatteryQuality_Low(0, "Very degraded"),
    kEdsBatteryQuality_Half(1, "Degraded"),
    kEdsBatteryQuality_HI(2, "Slight degradation"),
    kEdsBatteryQuality_Full(3, "No degradation");

    private final int value;
    private final String description;

    EdsBatteryQuality(final int value, final String description) {
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
