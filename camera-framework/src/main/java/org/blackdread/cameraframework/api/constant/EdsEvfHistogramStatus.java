package org.blackdread.cameraframework.api.constant;

/**
 * Live View Histogram Status
 * <br>
 * Gets the display status of the histogram.
 * <br>
 * The display status of the histogram varies depending on settings such as whether live view exposure simulation is
 * ON/OFF, whether strobe shooting is used, whether bulb shooting is used, etc.
 * <br>
 * See API Reference - 5.2.79 kEdsPropID_Evf_HistogramStatus
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsEvfHistogramStatus implements NativeEnum<Integer> {
    kEdsEvfHistogramStatus_Hide(0, "Hide the histogram"),
    kEdsEvfHistogramStatus_Normal(1, "Display the histogram"),
    kEdsEvfHistogramStatus_Grayout(2, "Gray out the histogram");

    private final int value;
    private final String description;

    EdsEvfHistogramStatus(final int value, final String description) {
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

    public static EdsEvfHistogramStatus ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsEvfHistogramStatus.class, value);
    }

}
