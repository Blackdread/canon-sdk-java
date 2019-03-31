/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.helper.factory;

import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.camerabinding.jna.EdsFocusInfo;
import org.blackdread.camerabinding.jna.EdsTime;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsBatteryLevel2;
import org.blackdread.cameraframework.api.constant.EdsBatteryQuality;
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
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_OwnerName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getOwnerName1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_OwnerName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getArtist() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Artist, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getArtist1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Artist, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCopyright() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Copyright, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCopyright1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Copyright, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getMakerName() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_MakerName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getBodyIDEx(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDateTime() {
        final EdsTime expectedResult = new EdsTime();
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_DateTime, expectedResult);

        final EdsTime result = spyPropertyGetShortcutLogic.getDateTime(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDateTime1() {
        final EdsTime expectedResult = new EdsTime();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_DateTime, expectedResult);

        final EdsTime result = spyPropertyGetShortcutLogic.getDateTime(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFirmwareVersion() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_FirmwareVersion, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getFirmwareVersion(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFirmwareVersion1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_FirmwareVersion, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getFirmwareVersion(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBatteryLevel() {
        final EdsBatteryLevel2 expectedResult = EdsBatteryLevel2.kEdsBatteryLevel2_Hi;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_BatteryLevel, expectedResult);

        final EdsBatteryLevel2 result = spyPropertyGetShortcutLogic.getBatteryLevel(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBatteryQuality() {
        final EdsBatteryQuality expectedResult = EdsBatteryQuality.kEdsBatteryQuality_HI;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_BatteryQuality, expectedResult);

        final EdsBatteryQuality result = spyPropertyGetShortcutLogic.getBatteryQuality(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getICCProfile() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_ICCProfile, expectedResult);

        Assertions.assertThrows(NotImplementedException.class, () -> spyPropertyGetShortcutLogic.getICCProfile(fakeImage));

//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFocusInfo() {
        final EdsFocusInfo expectedResult = new EdsFocusInfo();
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_FocusInfo, expectedResult);

        final EdsFocusInfo result = spyPropertyGetShortcutLogic.getFocusInfo(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
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
