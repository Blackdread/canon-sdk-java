package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Image Quality
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsImageQuality implements NativeEnum<Integer> {
    /* Jpeg Only */
    EdsImageQuality_LJ("JPEG Large", 1),
    EdsImageQuality_M1J("JPEG Medium 1", 1),
    EdsImageQuality_M2J("JPEG Medium 2", 1),
    EdsImageQuality_SJ("JPEG Small", 1),
    EdsImageQuality_LJF("JPEG Large Fine", 1),
    EdsImageQuality_LJN("JPEG Large Normal", 1),
    EdsImageQuality_MJF("JPEG Medium Fine", 1),
    EdsImageQuality_MJN("JPEG Medium Normal", 1),
    EdsImageQuality_SJF("JPEG Small Fine", 1),
    EdsImageQuality_SJN("JPEG Small Normal", 1),
    EdsImageQuality_S1JF("JPEG Small 1 Fine", 1),
    EdsImageQuality_S1JN("JPEG Small 1 Normal", 1),
    EdsImageQuality_S2JF("JPEG Small 2", 1),
    EdsImageQuality_S3JF("JPEG Small 3", 1),

    /* RAW + Jpeg */
    EdsImageQuality_LR("RAW", 1),
    EdsImageQuality_LRLJF("RAW + JPEG Large Fine", 2),
    EdsImageQuality_LRLJN("RAW + JPEG Large Normal", 2),
    EdsImageQuality_LRMJF("RAW + JPEG Middle Fine", 2),
    EdsImageQuality_LRMJN("RAW + JPEG Middle Normal", 2),
    EdsImageQuality_LRSJF("RAW + JPEG Small Fine", 2),
    EdsImageQuality_LRSJN("RAW + JPEG Small Normal", 2),
    EdsImageQuality_LRS1JF("RAW + JPEG Small 1 Fine", 2),
    EdsImageQuality_LRS1JN("RAW + JPEG Small 1 Normal", 2),
    EdsImageQuality_LRS2JF("RAW + JPEG Small 2", 2),
    EdsImageQuality_LRS3JF("RAW + JPEG Small 3", 2),

    EdsImageQuality_LRLJ("RAW + JPEG Large", 2),
    EdsImageQuality_LRM1J("RAW + JPEG Middle 1", 2),
    EdsImageQuality_LRM2J("RAW + JPEG Middle 2", 2),
    EdsImageQuality_LRSJ("RAW + JPEG Small", 2),

    /* MRAW(SRAW1) + Jpeg */
    EdsImageQuality_MR("MRAW (SRAW1)", 1),
    EdsImageQuality_MRLJF("MRAW (SRAW1) + JPEG Large Fine", 2),
    EdsImageQuality_MRLJN("MRAW (SRAW1) + JPEG Large Normal", 2),
    EdsImageQuality_MRMJF("MRAW (SRAW1) + JPEG Medium Fine", 2),
    EdsImageQuality_MRMJN("MRAW (SRAW1) + JPEG Medium Normal", 2),
    EdsImageQuality_MRSJF("MRAW (SRAW1) + JPEG Small Fine", 2),
    EdsImageQuality_MRSJN("MRAW (SRAW1) + JPEG Small Normal", 2),
    EdsImageQuality_MRS1JF("MRAW (SRAW1) + JPEG Small 1 Fine", 2),
    EdsImageQuality_MRS1JN("MRAW (SRAW1) + JPEG Small 1 Normal", 2),
    EdsImageQuality_MRS2JF("MRAW (SRAW1) + JPEG Small 2", 2),
    EdsImageQuality_MRS3JF("MRAW (SRAW1) + JPEG Small 3", 2),

    EdsImageQuality_MRLJ("MRAW (SRAW1) + JPEG Large", 2),
    EdsImageQuality_MRM1J("MRAW (SRAW1) + JPEG Medium 1", 2),
    EdsImageQuality_MRM2J("MRAW (SRAW1) + JPEG Medium 2", 2),
    EdsImageQuality_MRSJ("MRAW (SRAW1) + JPEG Small", 2),

    /* SRAW(SRAW2) + Jpeg */
    EdsImageQuality_SR("SRAW (SRAW2)", 1),
    EdsImageQuality_SRLJF("SRAW (SRAW2) + JPEG Large Fine", 2),
    EdsImageQuality_SRLJN("SRAW (SRAW2) + JPEG Large Normal", 2),
    EdsImageQuality_SRMJF("SRAW (SRAW2) + JPEG Middle Fine", 2),
    EdsImageQuality_SRMJN("SRAW (SRAW2) + JPEG Middle Normal", 2),
    EdsImageQuality_SRSJF("SRAW (SRAW2) + JPEG Small Fine", 2),
    EdsImageQuality_SRSJN("SRAW (SRAW2) + JPEG Small Normal", 2),
    EdsImageQuality_SRS1JF("SRAW (SRAW2) + JPEG Small1 Fine", 2),
    EdsImageQuality_SRS1JN("SRAW (SRAW2) + JPEG Small1 Normal", 2),
    EdsImageQuality_SRS2JF("SRAW (SRAW2) + JPEG Small2", 2),
    EdsImageQuality_SRS3JF("SRAW (SRAW2) + JPEG Small3", 2),

    EdsImageQuality_SRLJ("SRAW (SRAW2) + JPEG Large", 2),
    EdsImageQuality_SRM1J("SRAW (SRAW2) + JPEG Medium 1", 2),
    EdsImageQuality_SRM2J("SRAW (SRAW2) + JPEG Medium 2", 2),
    EdsImageQuality_SRSJ("SRAW (SRAW2) + JPEG Small", 2),

    /* CRAW + Jpeg */
    EdsImageQuality_CR("CRAW", 1),
    EdsImageQuality_CRLJF("CRAW + Jpeg Large Fine", 2),
    EdsImageQuality_CRMJF("CRAW + Jpeg Middle Fine", 2),
    EdsImageQuality_CRM1JF("CRAW + Jpeg Middle1 Fine", 2),
    EdsImageQuality_CRM2JF("CRAW + Jpeg Middle2 Fine", 2),
    EdsImageQuality_CRSJF("CRAW + Jpeg Small Fine", 2),
    EdsImageQuality_CRS1JF("CRAW + Jpeg Small1 Fine", 2),
    EdsImageQuality_CRS2JF("CRAW + Jpeg Small2 Fine", 2),
    EdsImageQuality_CRS3JF("CRAW + Jpeg Small3 Fine", 2),
    EdsImageQuality_CRLJN("CRAW + Jpeg Large Normal", 2),
    EdsImageQuality_CRMJN("CRAW + Jpeg Middle Normal", 2),
    EdsImageQuality_CRM1JN("CRAW + Jpeg Middle1 Normal", 2),
    EdsImageQuality_CRM2JN("CRAW + Jpeg Middle2 Normal", 2),
    EdsImageQuality_CRSJN("CRAW + Jpeg Small Normal", 2),
    EdsImageQuality_CRS1JN("CRAW + Jpeg Small1 Normal", 2),

    EdsImageQuality_CRLJ("CRAW + Jpeg Large", 2),
    EdsImageQuality_CRM1J("CRAW + Jpeg Middle1", 2),
    EdsImageQuality_CRM2J("CRAW + Jpeg Middle2", 2),
    EdsImageQuality_CRSJ("CRAW + Jpeg Small", 2),

    EdsImageQuality_Unknown("Unknown", 0);

    private final int value;
    private final String description;
    private final int expectedFileCount;

    EdsImageQuality(final String description, final int expectedFileCount) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsImageQuality.class, name());
        this.description = description;
        this.expectedFileCount = expectedFileCount;
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
     * @return count of files expected to be created during shoot
     */
    public final int getExpectedFileCount() {
        return expectedFileCount;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsImageQuality ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsImageQuality.class, value);
    }

}
