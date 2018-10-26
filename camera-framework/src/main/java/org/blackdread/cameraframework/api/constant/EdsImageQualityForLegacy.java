package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Image Quality Legacy
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsImageQualityForLegacy implements NativeEnum<Integer> {
    kEdsImageQualityForLegacy_LJ("JPEG large"),
    kEdsImageQualityForLegacy_M1J("JPEG medium 1"),
    kEdsImageQualityForLegacy_M2J("JPEG medium 2"),
    kEdsImageQualityForLegacy_SJ("JPEG small"),

    kEdsImageQualityForLegacy_LJF("JPEG large fine"),
    kEdsImageQualityForLegacy_LJN("JPEG large normal"),
    kEdsImageQualityForLegacy_MJF("JPEG medium fine"),
    kEdsImageQualityForLegacy_MJN("JPEG medium normal"),
    kEdsImageQualityForLegacy_SJF("JPEG small fine"),
    kEdsImageQualityForLegacy_SJN("JPEG small normal"),

    kEdsImageQualityForLegacy_LR("RAW"),
    kEdsImageQualityForLegacy_LRLJF("RAW + JPEG large fine"),
    kEdsImageQualityForLegacy_LRLJN("RAW + JPEG large normal"),
    kEdsImageQualityForLegacy_LRMJF("RAW + JPEG medium fine"),
    kEdsImageQualityForLegacy_LRMJN("RAW + JPEG medium normal"),
    kEdsImageQualityForLegacy_LRSJF("RAW + JPEG small fine"),
    kEdsImageQualityForLegacy_LRSJN("RAW + JPEG small normal"),

    kEdsImageQualityForLegacy_LR2("RAW "),
    kEdsImageQualityForLegacy_LR2LJ("RAW + JPEG large"),
    kEdsImageQualityForLegacy_LR2M1J("RAW + JPEG medium 1"),
    kEdsImageQualityForLegacy_LR2M2J("RAW + JPEG medium 2"),
    kEdsImageQualityForLegacy_LR2SJ("RAW + JPEG small"),

    kEdsImageQualityForLegacy_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsImageQualityForLegacy(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsImageQualityForLegacy.class, name());
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

    public static EdsImageQualityForLegacy ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsImageQualityForLegacy.class, value);
    }

}
