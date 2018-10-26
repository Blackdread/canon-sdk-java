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
    EdsImageQuality_LJ("JPEG Large"),
    EdsImageQuality_M1J("JPEG Medium 1"),
    EdsImageQuality_M2J("JPEG Medium 2"),
    EdsImageQuality_SJ("JPEG Small"),
    EdsImageQuality_LJF("JPEG Large Fine"),
    EdsImageQuality_LJN("JPEG Large Normal"),
    EdsImageQuality_MJF("JPEG Medium Fine"),
    EdsImageQuality_MJN("JPEG Medium Normal"),
    EdsImageQuality_SJF("JPEG Small Fine"),
    EdsImageQuality_SJN("JPEG Small Normal"),
    EdsImageQuality_S1JF("JPEG Small 1 Fine"),
    EdsImageQuality_S1JN("JPEG Small 1 Normal"),
    EdsImageQuality_S2JF("JPEG Small 2"),
    EdsImageQuality_S3JF("JPEG Small 3"),

    /* RAW + Jpeg */
    EdsImageQuality_LR("RAW"),
    EdsImageQuality_LRLJF("RAW + JPEG Large Fine"),
    EdsImageQuality_LRLJN("RAW + JPEG Large Normal"),
    EdsImageQuality_LRMJF("RAW + JPEG Middle Fine"),
    EdsImageQuality_LRMJN("RAW + JPEG Middle Normal"),
    EdsImageQuality_LRSJF("RAW + JPEG Small Fine"),
    EdsImageQuality_LRSJN("RAW + JPEG Small Normal"),
    EdsImageQuality_LRS1JF("RAW + JPEG Small 1 Fine"),
    EdsImageQuality_LRS1JN("RAW + JPEG Small 1 Normal"),
    EdsImageQuality_LRS2JF("RAW + JPEG Small 2"),
    EdsImageQuality_LRS3JF("RAW + JPEG Small 3"),

    EdsImageQuality_LRLJ("RAW + JPEG Large"),
    EdsImageQuality_LRM1J("RAW + JPEG Middle 1"),
    EdsImageQuality_LRM2J("RAW + JPEG Middle 2"),
    EdsImageQuality_LRSJ("RAW + JPEG Small"),

    /* MRAW(SRAW1) + Jpeg */
    EdsImageQuality_MR("MRAW (SRAW1)"),
    EdsImageQuality_MRLJF("MRAW (SRAW1) + JPEG Large Fine"),
    EdsImageQuality_MRLJN("MRAW (SRAW1) + JPEG Large Normal"),
    EdsImageQuality_MRMJF("MRAW (SRAW1) + JPEG Medium Fine"),
    EdsImageQuality_MRMJN("MRAW (SRAW1) + JPEG Medium Normal"),
    EdsImageQuality_MRSJF("MRAW (SRAW1) + JPEG Small Fine"),
    EdsImageQuality_MRSJN("MRAW (SRAW1) + JPEG Small Normal"),
    EdsImageQuality_MRS1JF("MRAW (SRAW1) + JPEG Small 1 Fine"),
    EdsImageQuality_MRS1JN("MRAW (SRAW1) + JPEG Small 1 Normal"),
    EdsImageQuality_MRS2JF("MRAW (SRAW1) + JPEG Small 2"),
    EdsImageQuality_MRS3JF("MRAW (SRAW1) + JPEG Small 3"),

    EdsImageQuality_MRLJ("MRAW (SRAW1) + JPEG Large"),
    EdsImageQuality_MRM1J("MRAW (SRAW1) + JPEG Medium 1"),
    EdsImageQuality_MRM2J("MRAW (SRAW1) + JPEG Medium 2"),
    EdsImageQuality_MRSJ("MRAW (SRAW1) + JPEG Small"),

    /* SRAW(SRAW2) + Jpeg */
    EdsImageQuality_SR("SRAW (SRAW2)"),
    EdsImageQuality_SRLJF("SRAW (SRAW2) + JPEG Large Fine"),
    EdsImageQuality_SRLJN("SRAW (SRAW2) + JPEG Large Normal"),
    EdsImageQuality_SRMJF("SRAW (SRAW2) + JPEG Middle Fine"),
    EdsImageQuality_SRMJN("SRAW (SRAW2) + JPEG Middle Normal"),
    EdsImageQuality_SRSJF("SRAW (SRAW2) + JPEG Small Fine"),
    EdsImageQuality_SRSJN("SRAW (SRAW2) + JPEG Small Normal"),
    EdsImageQuality_SRS1JF("SRAW (SRAW2) + JPEG Small1 Fine"),
    EdsImageQuality_SRS1JN("SRAW (SRAW2) + JPEG Small1 Normal"),
    EdsImageQuality_SRS2JF("SRAW (SRAW2) + JPEG Small2"),
    EdsImageQuality_SRS3JF("SRAW (SRAW2) + JPEG Small3"),

    EdsImageQuality_SRLJ("SRAW (SRAW2) + JPEG Large"),
    EdsImageQuality_SRM1J("SRAW (SRAW2) + JPEG Medium 1"),
    EdsImageQuality_SRM2J("SRAW (SRAW2) + JPEG Medium 2"),
    EdsImageQuality_SRSJ("SRAW (SRAW2) + JPEG Small"),

    /* CRAW + Jpeg */
    EdsImageQuality_CR("CRAW"),
    EdsImageQuality_CRLJF("CRAW + Jpeg Large Fine"),
    EdsImageQuality_CRMJF("CRAW + Jpeg Middle Fine"),
    EdsImageQuality_CRM1JF("CRAW + Jpeg Middle1 Fine"),
    EdsImageQuality_CRM2JF("CRAW + Jpeg Middle2 Fine"),
    EdsImageQuality_CRSJF("CRAW + Jpeg Small Fine"),
    EdsImageQuality_CRS1JF("CRAW + Jpeg Small1 Fine"),
    EdsImageQuality_CRS2JF("CRAW + Jpeg Small2 Fine"),
    EdsImageQuality_CRS3JF("CRAW + Jpeg Small3 Fine"),
    EdsImageQuality_CRLJN("CRAW + Jpeg Large Normal"),
    EdsImageQuality_CRMJN("CRAW + Jpeg Middle Normal"),
    EdsImageQuality_CRM1JN("CRAW + Jpeg Middle1 Normal"),
    EdsImageQuality_CRM2JN("CRAW + Jpeg Middle2 Normal"),
    EdsImageQuality_CRSJN("CRAW + Jpeg Small Normal"),
    EdsImageQuality_CRS1JN("CRAW + Jpeg Small1 Normal"),

    EdsImageQuality_CRLJ("CRAW + Jpeg Large"),
    EdsImageQuality_CRM1J("CRAW + Jpeg Middle1"),
    EdsImageQuality_CRM2J("CRAW + Jpeg Middle2"),
    EdsImageQuality_CRSJ("CRAW + Jpeg Small"),

    EdsImageQuality_Unknown("Unknown");

    private final int value;
    private final String description;

    EdsImageQuality(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsImageQuality.class, name());
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

    public static EdsImageQuality ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsImageQuality.class, value);
    }

}
