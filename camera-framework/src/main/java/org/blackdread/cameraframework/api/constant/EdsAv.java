package org.blackdread.cameraframework.api.constant;

/**
 * Aperture Values
 * <br>
 * Caution is advised because EdsCameraRef and EdsImageRef yield different data types and values.
 * <br>
 * Note: EdsAv names ending with 'b' represent aperture values when the
 * exposure step set in the Custom Function is 1/3 instead of 1/2.
 * <br>
 * See API Reference - 5.2.23 kEdsPropID_Av
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsAv implements NativeEnum<Integer> {
    kEdsAv_1(0x08, "1"),
    kEdsAv_1_1(0x0B, "1.1"),
    kEdsAv_1_2(0x0C, "1.2"),
    kEdsAv_1_2b(0x0D, "1.2 (1/3)"),
    kEdsAv_1_4(0x10, "1.4"),
    kEdsAv_1_6(0x13, "1.6"),
    kEdsAv_1_8(0x14, "1.8"),
    kEdsAv_1_8b(0x15, "1.8 (1/3)"),
    kEdsAv_2(0x18, "2"),
    kEdsAv_2_2(0x1B, "2.2"),
    kEdsAv_2_5(0x1C, "2.5"),
    kEdsAv_2_5b(0x1D, "2.5 (1/3)"),
    kEdsAv_2_8(0x20, "2.8"),
    kEdsAv_3_2(0x23, "3.2"),
    kEdsAv_3_5(0x24, "3.5"),
    kEdsAv_3_5b(0x25, "3.5 (1/3)"),
    kEdsAv_4(0x28, "4"),
    // both 0x2B and 0x2C labeled '4.5' in EDSDK API docs, 1/3 value determined by camera reading
    kEdsAv_4_5b(0x2B, "4.5 (1/3)"),
    kEdsAv_4_5(0x2C, "4.5"),
    kEdsAv_5_0(0x2D, "5.0"),
    kEdsAv_5_6(0x30, "5.6"),
    kEdsAv_6_3(0x33, "6.3"),
    kEdsAv_6_7(0x34, "6.7"),
    kEdsAv_7_1(0x35, "7.1"),
    kEdsAv_8(0x38, "8"),
    kEdsAv_9(0x3B, "9"),
    kEdsAv_9_5(0x3C, "9.5"),
    kEdsAv_10(0x3D, "10"),
    kEdsAv_11(0x40, "11"),
    kEdsAv_13b(0x43, "13 (1/3)"),
    kEdsAv_13(0x44, "13"),
    kEdsAv_14(0x45, "14"),
    kEdsAv_16(0x48, "16"),
    kEdsAv_18(0x4B, "18"),
    kEdsAv_19(0x4C, "19"),
    kEdsAv_20(0x4D, "20"),
    kEdsAv_22(0x50, "22"),
    kEdsAv_25(0x53, "25"),
    kEdsAv_27(0x54, "27"),
    kEdsAv_29(0x55, "29"),
    kEdsAv_32(0x58, "32"),
    kEdsAv_36(0x5B, "36"),
    kEdsAv_38(0x5C, "38"),
    kEdsAv_40(0x5D, "40"),
    kEdsAv_45(0x60, "45"),
    kEdsAv_51(0x63, "51"),
    kEdsAv_54(0x64, "54"),
    kEdsAv_57(0x65, "57"),
    kEdsAv_64(0x68, "64"),
    kEdsAv_72(0x6B, "72"),
    kEdsAv_76(0x6C, "76"),
    kEdsAv_80(0x6D, "80"),
    kEdsAv_91(0x70, "91"),
    kEdsAv_Unknown(0xFFFFFFFF, "Not valid/no settings changes");

    private final int value;
    private final String description;

    EdsAv(final int value, final String description) {
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

    public static EdsAv ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsAv.class, value);
    }

}
