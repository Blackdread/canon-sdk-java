package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.logic.PropertyGetShortcutLogic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyGetLogic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class PropertyGetShortcutLogicDefaultMockTest extends AbstractMockTest {

    private EdsCameraRef fakeCamera;

    private EdsImageRef fakeImage;

    private PropertyGetShortcutLogic spyPropertyGetShortcutLogic;

    @BeforeEach
    void setUp() {
        fakeCamera = new EdsCameraRef();

        fakeImage = new EdsImageRef();

        spyPropertyGetShortcutLogic = spy(MockFactory.initialCanonFactory.getPropertyGetShortcutLogic());
    }

    @AfterEach
    void tearDown() {
    }

    private static <T> void mockGetProperty(final EdsBaseRef baseRef, final EdsPropertyID propertyID, final T result) {
        when(propertyGetLogic().getPropertyData(baseRef, propertyID)).thenReturn(result);
    }

    @Test
    void getProductName() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_ProductName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getProductName(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getProductName1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_ProductName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getProductName(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBodyIDEx() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_BodyIDEx, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBodyIDEx1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_BodyIDEx, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getOwnerName() {
    }

    @Test
    void getOwnerName1() {
    }

    @Test
    void getArtist() {
    }

    @Test
    void getArtist1() {
    }

    @Test
    void getCopyright() {
    }

    @Test
    void getCopyright1() {
    }

    @Test
    void getMakerName() {
    }

    @Test
    void getDateTime() {
    }

    @Test
    void getDateTime1() {
    }

    @Test
    void getFirmwareVersion() {
    }

    @Test
    void getFirmwareVersion1() {
    }

    @Test
    void getBatteryLevel() {
    }

    @Test
    void getBatteryQuality() {
    }

    @Test
    void getICCProfile() {
    }

    @Test
    void getFocusInfo() {
    }

    @Test
    void getFocusInfo1() {
    }

    @Test
    void getImageQuality() {
    }

    @Test
    void getJpegQuality() {
    }

    @Test
    void getJpegQuality1() {
    }

    @Test
    void getOrientation() {
    }

    @Test
    void getOrientationAsInt() {
    }

    @Test
    void getAEMode() {
    }

    @Test
    void getAEMode1() {
    }

    @Test
    void getAEModeSelect() {
    }

    @Test
    void getDriveMode() {
    }

    @Test
    void getDriveMode1() {
    }

    @Test
    void getISOSpeed() {
    }

    @Test
    void getISOSpeed1() {
    }

    @Test
    void getMeteringMode() {
    }

    @Test
    void getMeteringMode1() {
    }

    @Test
    void getAFMode() {
    }

    @Test
    void getAFMode1() {
    }

    @Test
    void getAv() {
    }

    @Test
    void getAv1() {
    }

    @Test
    void getTv() {
    }

    @Test
    void getTv1() {
    }

    @Test
    void getExposureCompensation() {
    }

    @Test
    void getExposureCompensation1() {
    }

    @Test
    void getDigitalExposure() {
    }

    @Test
    void getFlashCompensation() {
    }

    @Test
    void getFocalLength() {
    }

    @Test
    void getAvailableShots() {
    }

    @Test
    void getBracket() {
    }

    @Test
    void getBracket1() {
    }

    @Test
    void getAEBracket() {
    }

    @Test
    void getFEBracket() {
    }

    @Test
    void getWhiteBalanceBracket() {
    }

    @Test
    void getWhiteBalanceBracket1() {
    }

    @Test
    void getISOBracket() {
    }

    @Test
    void getWhiteBalance() {
    }

    @Test
    void getWhiteBalance1() {
    }

    @Test
    void getColorTemperature() {
    }

    @Test
    void getColorTemperature1() {
    }

    @Test
    void getWhiteBalanceShift() {
    }

    @Test
    void getWhiteBalanceShift1() {
    }

    @Test
    void getWBCoeffs() {
    }

    @Test
    void getLinear() {
    }

    @Test
    void getColorSpace() {
    }

    @Test
    void getColorSpace1() {
    }

    @Test
    void getToneCurve() {
    }

    @Test
    void getToneCurve1() {
    }

    @Test
    void getPictureStyle() {
    }

    @Test
    void getPictureStyle1() {
    }

    @Test
    void getFlashOn() {
    }

    @Test
    void getFlashMode() {
    }

    @Test
    void getRedEye() {
    }

    @Test
    void getNoiseReduction() {
    }

    @Test
    void getPictureStyleCaption() {
    }

    @Test
    void getSaveTo() {
    }

    @Test
    void getLensStatus() {
    }

    @Test
    void getLensName() {
    }

    @Test
    void getCurrentStorage() {
    }

    @Test
    void getCurrentFolder() {
    }

    @Test
    void getHDDirectoryStructure() {
    }

    @Test
    void getEvfOutputDevice() {
    }

    @Test
    void getEvfMode() {
    }

    @Test
    void getEvfWhiteBalance() {
    }

    @Test
    void getEvfColorTemperature() {
    }

    @Test
    void getEvfDepthOfFieldPreview() {
    }

    @Test
    void getEvfZoom() {
    }

    @Test
    void getEvfZoomPosition() {
    }

    @Test
    void getEvf_ZoomRect() {
    }

    @Test
    void getEvfImagePosition() {
    }

    @Test
    void getEvfCoordinateSystem() {
    }

    @Test
    void getEvfHistogramY() {
    }

    @Test
    void getEvfHistogramR() {
    }

    @Test
    void getEvfHistogramG() {
    }

    @Test
    void getEvfHistogramB() {
    }

    @Test
    void getEvfHistogramStatus() {
    }

    @Test
    void getEvfAFMode() {
    }

    @Test
    void getRecordStatus() {
    }

    @Test
    void getGPSVersionIDAsInt() {
    }

    @Test
    void getGPSLatitudeRefAsString() {
    }

    @Test
    void getGPSLatitudeAsRational() {
    }

    @Test
    void getGPSLongitudeRefAsString() {
    }

    @Test
    void getGPSLongitudeAsRational() {
    }

    @Test
    void getGPSAltitudeRefAsInt() {
    }

    @Test
    void getGPSAltitude() {
    }

    @Test
    void getGPSTimeStampAsRational() {
    }

    @Test
    void getGPSSatellites() {
    }

    @Test
    void getGPSMapDatum() {
    }

    @Test
    void getGPSDateStamp() {
    }

    @Test
    void getGPSDateStampAsString() {
    }

    @Test
    void getGPSStatusAsString() {
    }
}
