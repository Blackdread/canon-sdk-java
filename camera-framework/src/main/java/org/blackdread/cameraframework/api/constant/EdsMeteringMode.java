package org.blackdread.cameraframework.api.constant;

/**
 * Indicates the metering mode.
 * If the target object is EdsCameraRef, you can use GetPropertyDesc to access this property and get a list of
 * property values that can currently be set.
 * <br>
 * See API Reference - 5.2.21 kEdsPropID_MeteringMode
 * <p>Created on 2018/10/04.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsMeteringMode implements NativeEnum<Integer> {
    kEdsMeteringMode_Spot(1, "Spot metering"),
    kEdsMeteringMode_Evaluative(3, "Evaluative metering"),
    kEdsMeteringMode_Partial(4, "Partial metering"),
    kEdsMeteringMode_CenterWeightedAvg(5, "Center-weighted averaging metering"),
    kEdsMeteringMode_Unknown(0xFFFFFFFF, "Not valid/no settings changes");

    private final int value;

    private final String description;

    EdsMeteringMode(final int value, final String description) {
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
