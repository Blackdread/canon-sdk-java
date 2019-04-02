package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.helper.logic.PropertyDescShortcutLogic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * <p>Created on 2019/04/03.</p>
 *
 * @author Yoann CAPLAIN
 */
class PropertyDescShortcutLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private EdsImageRef fakeImage;

    private PropertyDescShortcutLogic spyPropertyDescShortcutLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeCamera = new EdsCameraRef();
        fakeImage = new EdsImageRef();

        spyPropertyDescShortcutLogic = Mockito.spy(MockFactory.initialCanonFactory.getPropertyDescShortcutLogic());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAEModeDesc() {
        final List<EdsAEMode> result = spyPropertyDescShortcutLogic.getAEModeDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_AEMode);
    }

    @Test
    void getAEModeSelectDesc() {
        final List<EdsAEModeSelect> result = spyPropertyDescShortcutLogic.getAEModeSelectDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_AEModeSelect);
    }

    @Test
    void getAvDesc() {
        final List<EdsAv> result = spyPropertyDescShortcutLogic.getAvDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_Av);
    }

    @Test
    void getColorTemperatureDesc() {
        final List<Integer> result = spyPropertyDescShortcutLogic.getColorTemperatureDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDescColorTemperature(fakeCamera);
        verify(propertyDescLogic, times(0)).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_ColorTemperature);
    }

    @Test
    void getDriveModeDesc() {
        final List<EdsDriveMode> result = spyPropertyDescShortcutLogic.getDriveModeDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_DriveMode);
    }

    @Test
    void getExposureCompensationDesc() {
        final List<EdsExposureCompensation> result = spyPropertyDescShortcutLogic.getExposureCompensationDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_ExposureCompensation);
    }

    @Test
    void getImageQualityDesc() {
        final List<EdsImageQuality> result = spyPropertyDescShortcutLogic.getImageQualityDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_ImageQuality);
    }

    @Test
    void getISOSpeedDesc() {
        final List<EdsISOSpeed> result = spyPropertyDescShortcutLogic.getISOSpeedDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed);
    }

    @Test
    void getPictureStyleDesc() {
        final List<EdsPictureStyle> result = spyPropertyDescShortcutLogic.getPictureStyleDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_PictureStyleDesc);
    }

    @Test
    void getPictureStyleDescImage() {
        final List<EdsPictureStyle> result = spyPropertyDescShortcutLogic.getPictureStyleDesc(fakeImage);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_PictureStyleDesc);
    }

    @Test
    void getMeteringModeDesc() {
        final List<EdsMeteringMode> result = spyPropertyDescShortcutLogic.getMeteringModeDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_MeteringMode);
    }

    @Test
    void getTvDesc() {
        final List<EdsTv> result = spyPropertyDescShortcutLogic.getTvDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_Tv);
    }

    @Test
    void getWhiteBalanceDesc() {
        final List<EdsWhiteBalance> result = spyPropertyDescShortcutLogic.getWhiteBalanceDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_WhiteBalance);
    }

    @Test
    void getEvfAFModeDesc() {
        final List<EdsEvfAFMode> result = spyPropertyDescShortcutLogic.getEvfAFModeDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_Evf_AFMode);
    }

    @Test
    void getEvfColorTemperatureDesc() {
        final List<Integer> result = spyPropertyDescShortcutLogic.getEvfColorTemperatureDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDescEvfColorTemperature(fakeCamera);
        verify(propertyDescLogic, times(0)).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_Evf_ColorTemperature);
    }

    @Test
    void getEvfWhiteBalanceDesc() {
        final List<EdsWhiteBalance> result = spyPropertyDescShortcutLogic.getEvfWhiteBalanceDesc(fakeCamera);

        Assertions.assertNotNull(result);

        verify(propertyDescLogic).getPropertyDesc(fakeCamera, EdsPropertyID.kEdsPropID_Evf_WhiteBalance);
    }
}
