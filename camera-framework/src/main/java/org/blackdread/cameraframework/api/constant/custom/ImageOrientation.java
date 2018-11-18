package org.blackdread.cameraframework.api.constant.custom;

import org.blackdread.cameraframework.api.constant.NativeEnum;

/**
 * Indicates image rotation information.
 * <p>Created on 2018/11/18.</p>
 *
 * @author Yoann CAPLAIN
 */
public enum ImageOrientation implements NativeEnum<Integer> {
    NORMAL(1, "The 0th row is at the visual top of the image, and the 0th column is on the visual left-hand side"),
    NORMAL_UPSIDE_DOWN(3, "The 0th row is at the visual bottom of the image, and the 0th column is on the visual right-hand side"),
    // TODO name
    NAME_IT_BETTER(6, "The 0th row is on the visual right-hand side of the image, and the 0th column is at the visual top"),
    NAME_IT_BETTER_2(8, "The 0th row is on the visual left-hand side of the image, and the 0th column is at the visual bottom");

    private final int value;
    private final String description;

    ImageOrientation(final int value, final String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static ImageOrientation ofValue(final int value) {
        switch (value) {
            case 1:
                return NORMAL;
            case 3:
                return NORMAL_UPSIDE_DOWN;
            case 6:
                return NAME_IT_BETTER_2;
            case 8:
                return NAME_IT_BETTER_2;
        }
        throw new IllegalArgumentException("No native enum found for value " + value + " for enum " + ImageOrientation.class.getSimpleName());
    }
}
