package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsPropertyDesc;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.helper.logic.PropertyDescLogic;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class PropertyDescLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private PropertyDescLogic spyPropertyDescLogic;

    private PropertyDescLogicDefaultExtended propertyDescLogicDefaultExtended;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();

        propertyDescLogicDefaultExtended = new PropertyDescLogicDefaultExtended();

        spyPropertyDescLogic = Mockito.spy(MockFactory.initialCanonFactory.getPropertyDescLogic());

    }

    @AfterEach
    void tearDown() {
    }

    private static Stream<Arguments> compatibleNatives() {
        return Stream.of(
            of(EdsPropertyID.kEdsPropID_AEMode, EdsAEMode.kEdsAEMode_CandlelightPortraits, EdsAEMode.kEdsAEMode_Bulb),
            of(EdsPropertyID.kEdsPropID_AEModeSelect, EdsAEModeSelect.kEdsAEModeSelect_Custom1, EdsAEModeSelect.kEdsAEModeSelect_Custom2),
            of(EdsPropertyID.kEdsPropID_ISOSpeed, EdsISOSpeed.kEdsISOSpeed_50, EdsISOSpeed.kEdsISOSpeed_100),
            of(EdsPropertyID.kEdsPropID_MeteringMode, EdsMeteringMode.kEdsMeteringMode_Evaluative, EdsMeteringMode.kEdsMeteringMode_Spot),
            of(EdsPropertyID.kEdsPropID_Av, EdsAv.kEdsAv_3_2, EdsAv.kEdsAv_2_5),
            of(EdsPropertyID.kEdsPropID_Tv, EdsTv.kEdsTv_0_8, EdsTv.kEdsTv_1),
            of(EdsPropertyID.kEdsPropID_ExposureCompensation, EdsExposureCompensation.kEdsExposureCompensation_1_1by3, EdsExposureCompensation.kEdsExposureCompensation_3_2by3),
            of(EdsPropertyID.kEdsPropID_ImageQuality, EdsImageQuality.EdsImageQuality_CRLJ, EdsImageQuality.EdsImageQuality_CRLJF),
            of(EdsPropertyID.kEdsPropID_WhiteBalance, EdsWhiteBalance.kEdsWhiteBalance_Auto, EdsWhiteBalance.kEdsWhiteBalance_Strobe),
            of(EdsPropertyID.kEdsPropID_PictureStyle, EdsPictureStyle.kEdsPictureStyle_Monochrome, EdsPictureStyle.kEdsPictureStyle_User1),
            of(EdsPropertyID.kEdsPropID_DriveMode, EdsDriveMode.kEdsDriveMode_14FpsSuperHighSpeed, EdsDriveMode.kEdsDriveMode_SilentSingleShooting),
            of(EdsPropertyID.kEdsPropID_Evf_WhiteBalance, EdsWhiteBalance.kEdsWhiteBalance_AwbWhite, EdsWhiteBalance.kEdsWhiteBalance_PCSet2),
            of(EdsPropertyID.kEdsPropID_Evf_AFMode, EdsEvfAFMode.Evf_AFMode_LiveFace, EdsEvfAFMode.Evf_AFMode_LiveZone)
        );
    }


    @MethodSource("compatibleNatives")
    @ParameterizedTest
    void getPropertyDescCompatibleNative(final EdsPropertyID propertyID, final NativeEnum<Integer> v1, final NativeEnum<Integer> v2) {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(v1.value());
        desc[1] = new NativeLong(v2.value());
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(2),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<NativeEnum<Integer>> nativeEnums = propertyDescLogicDefaultExtended.getPropertyDesc(fakeCamera, propertyID);

        Assertions.assertEquals(2, nativeEnums.size());
        Assertions.assertEquals(v1, nativeEnums.get(0));
        Assertions.assertEquals(v2, nativeEnums.get(1));
    }

    @Test
    void getPropertyDesc() {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_50.value());
        desc[1] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_100.value());
        desc[2] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_160.value());
        desc[3] = new NativeLong(EdsISOSpeed.kEdsISOSpeed_200.value());
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(4),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<NativeEnum<Integer>> nativeEnums = propertyDescLogicDefaultExtended.getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed);

        Assertions.assertEquals(4, nativeEnums.size());
        Assertions.assertEquals(EdsISOSpeed.kEdsISOSpeed_50, nativeEnums.get(0));
        Assertions.assertEquals(EdsISOSpeed.kEdsISOSpeed_200, nativeEnums.get(3));
    }

    @Test
    void getPropertyDescColorTemperature() {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(100);
        desc[1] = new NativeLong(1000);
        desc[2] = new NativeLong(2000);
        desc[3] = new NativeLong(3000);
        desc[4] = new NativeLong(6500);
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(5),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<Integer> propertyDescColorTemperature = propertyDescLogicDefaultExtended.getPropertyDescColorTemperature(fakeCamera);

        Assertions.assertEquals(5, propertyDescColorTemperature.size());
        Assertions.assertEquals(100, propertyDescColorTemperature.get(0));
        Assertions.assertEquals(6500, propertyDescColorTemperature.get(4));
    }

    @Test
    void getPropertyDescEvfColorTemperature() {
        final NativeLong[] desc = new NativeLong[128];
        desc[0] = new NativeLong(100);
        desc[1] = new NativeLong(1000);
        desc[2] = new NativeLong(2000);
        desc[3] = new NativeLong(3000);
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(4),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<Integer> propertyDescColorTemperature = propertyDescLogicDefaultExtended.getPropertyDescEvfColorTemperature(fakeCamera);

        Assertions.assertEquals(4, propertyDescColorTemperature.size());
        Assertions.assertEquals(100, propertyDescColorTemperature.get(0));
        Assertions.assertEquals(3000, propertyDescColorTemperature.get(3));
    }

    @Test
    void getPropertyDescValues() {
        final NativeLong[] desc = new NativeLong[128];
        final EdsPropertyDesc propertyDesc = new EdsPropertyDesc(
            new NativeLong(0),
            new NativeLong(0),
            new NativeLong(0),
            desc
        );

        propertyDescLogicDefaultExtended.setPropertyDescStructure(propertyDesc);

        final List<Integer> propertyDescValues = propertyDescLogicDefaultExtended.getPropertyDescValues(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed);

        Assertions.assertEquals(0, propertyDescValues.size());
    }

    @Test
    void getPropertyDescStructure() {
        when(CanonFactory.edsdkLibrary().EdsGetPropertyDesc(eq(fakeCamera), eq(new NativeLong(EdsPropertyID.kEdsPropID_ISOSpeed.value())), any())).thenReturn(new NativeLong(0));

        final EdsPropertyDesc propertyDesc = spyPropertyDescLogic.getPropertyDescStructure(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed);

        Assertions.assertNotNull(propertyDesc);
    }

    @Test
    void getPropertyDescStructureThrowsOnError() {
        when(CanonFactory.edsdkLibrary().EdsGetPropertyDesc(eq(fakeCamera), eq(new NativeLong(EdsPropertyID.kEdsPropID_ISOSpeed.value())), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyPropertyDescLogic.getPropertyDescStructure(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed));

    }

}
