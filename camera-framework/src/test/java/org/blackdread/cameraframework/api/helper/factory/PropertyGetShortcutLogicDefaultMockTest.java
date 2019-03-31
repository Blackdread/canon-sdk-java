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
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsRational;
import org.blackdread.camerabinding.jna.EdsRect;
import org.blackdread.camerabinding.jna.EdsSize;
import org.blackdread.camerabinding.jna.EdsTime;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.constant.custom.ImageOrientation;
import org.blackdread.cameraframework.api.helper.logic.PropertyGetShortcutLogic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    private EdsdkLibrary.EdsEvfImageRef fakeEvfImage;

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

        final String result = spyPropertyGetShortcutLogic.getOwnerName(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getOwnerName1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_OwnerName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getOwnerName(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getArtist() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Artist, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getArtist(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getArtist1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Artist, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getArtist(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCopyright() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Copyright, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getCopyright(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCopyright1() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Copyright, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getCopyright(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getMakerName() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_MakerName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getMakerName(fakeImage);

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
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_BatteryLevel, (long) expectedResult.value());

        final EdsBatteryLevel2 result = spyPropertyGetShortcutLogic.getBatteryLevel(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBatteryQuality() {
        final EdsBatteryQuality expectedResult = EdsBatteryQuality.kEdsBatteryQuality_HI;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_BatteryQuality, (long) expectedResult.value());

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
        final EdsFocusInfo expectedResult = new EdsFocusInfo();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_FocusInfo, expectedResult);

        final EdsFocusInfo result = spyPropertyGetShortcutLogic.getFocusInfo(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getImageQuality() {
        final EdsImageQuality expectedResult = EdsImageQuality.EdsImageQuality_LJF;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_ImageQuality, (long) expectedResult.value());

        final EdsImageQuality result = spyPropertyGetShortcutLogic.getImageQuality(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getJpegQuality() {
        final int expectedResult = 5;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_JpegQuality, expectedResult);

        final int result = spyPropertyGetShortcutLogic.getJpegQuality(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getJpegQuality1() {
        final int expectedResult = 5;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_JpegQuality, expectedResult);

        final int result = spyPropertyGetShortcutLogic.getJpegQuality(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getOrientation() {
        final ImageOrientation expectedResult = ImageOrientation.NORMAL;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Orientation, expectedResult.value());

        final ImageOrientation result = spyPropertyGetShortcutLogic.getOrientation(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getOrientationAsInt() {
    }

    @Test
    void getAEMode() {
        final EdsAEMode expectedResult = EdsAEMode.kEdsAEMode_Av;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_AEMode, (long) expectedResult.value());

        final EdsAEMode result = spyPropertyGetShortcutLogic.getAEMode(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getAEMode1() {
    }

    @Test
    void getAEModeSelect() {
        final EdsAEModeSelect expectedResult = EdsAEModeSelect.kEdsAEModeSelect_Custom2;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_AEModeSelect, (long) expectedResult.value());

        final EdsAEModeSelect result = spyPropertyGetShortcutLogic.getAEModeSelect(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDriveMode() {
        final EdsDriveMode expectedResult = EdsDriveMode.kEdsDriveMode_10SecSelfTimer;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_DriveMode, (long) expectedResult.value());

        final EdsDriveMode result = spyPropertyGetShortcutLogic.getDriveMode(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getDriveMode1() {
    }

    @Test
    void getISOSpeed() {
        final EdsISOSpeed expectedResult = EdsISOSpeed.kEdsISOSpeed_50;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_ISOSpeed, (long) expectedResult.value());

        final EdsISOSpeed result = spyPropertyGetShortcutLogic.getISOSpeed(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getISOSpeed1() {
    }

    @Test
    void getMeteringMode() {
        final EdsMeteringMode expectedResult = EdsMeteringMode.kEdsMeteringMode_CenterWeightedAvg;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_MeteringMode, (long) expectedResult.value());

        final EdsMeteringMode result = spyPropertyGetShortcutLogic.getMeteringMode(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getMeteringMode1() {
    }

    @Test
    void getAFMode() {
        final EdsAFMode expectedResult = EdsAFMode.kEdsAFMode_AIFocus;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_AFMode, (long) expectedResult.value());

        final EdsAFMode result = spyPropertyGetShortcutLogic.getAFMode(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getAFMode1() {
    }

    @Test
    void getAv() {
        final EdsAv expectedResult = EdsAv.kEdsAv_1_2;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Av, (long) expectedResult.value());

        final EdsAv result = spyPropertyGetShortcutLogic.getAv(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getAv1() {
    }

    @Test
    void getTv() {
        final EdsTv expectedResult = EdsTv.kEdsTv_0_5;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Tv, (long) expectedResult.value());

        final EdsTv result = spyPropertyGetShortcutLogic.getTv(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getTv1() {
    }

    @Test
    void getExposureCompensation() {
        final EdsExposureCompensation expectedResult = EdsExposureCompensation.kEdsExposureCompensation_1_1by2;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_ExposureCompensation, (long) expectedResult.value());

        final EdsExposureCompensation result = spyPropertyGetShortcutLogic.getExposureCompensation(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getExposureCompensation1() {
    }

    @Test
    void getDigitalExposure() {
        final EdsRational expectedResult = new EdsRational();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_DigitalExposure, expectedResult);

        final EdsRational result = spyPropertyGetShortcutLogic.getDigitalExposure(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFlashCompensation() {
        final EdsExposureCompensation expectedResult = EdsExposureCompensation.kEdsExposureCompensation_1by2;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_FlashCompensation, (long) expectedResult.value());

        final EdsExposureCompensation result = spyPropertyGetShortcutLogic.getFlashCompensation(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFocalLength() {
        final EdsRational[] expectedResult = new EdsRational[0];
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_FocalLength, expectedResult);

        final EdsRational[] result = spyPropertyGetShortcutLogic.getFocalLength(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getAvailableShots() {
        final long expectedResult = 1L;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_AvailableShots, expectedResult);

        final long result = spyPropertyGetShortcutLogic.getAvailableShots(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBracket() {
        final EdsBracket expectedResult = EdsBracket.kEdsBracket_ISOB;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Bracket, (long) expectedResult.value());

        final EdsBracket result = spyPropertyGetShortcutLogic.getBracket(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getBracket1() {
        final EdsBracket expectedResult = EdsBracket.kEdsBracket_ISOB;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Bracket, (long) expectedResult.value());

        final EdsBracket result = spyPropertyGetShortcutLogic.getBracket(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getAEBracket() {
        final EdsRational expectedResult = new EdsRational();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_AEBracket, expectedResult);

        final EdsRational result = spyPropertyGetShortcutLogic.getAEBracket(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFEBracket() {
        final EdsRational expectedResult = new EdsRational();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_FEBracket, expectedResult);

        final EdsRational result = spyPropertyGetShortcutLogic.getFEBracket(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getWhiteBalanceBracket() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_WhiteBalanceBracket, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getWhiteBalanceBracket(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getWhiteBalanceBracket1() {
    }

    @Test
    void getISOBracket() {
        final EdsRational expectedResult = new EdsRational();
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_ISOBracket, expectedResult);

        final EdsRational result = spyPropertyGetShortcutLogic.getISOBracket(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getWhiteBalance() {
        final EdsWhiteBalance expectedResult = EdsWhiteBalance.kEdsWhiteBalance_Daylight;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_WhiteBalance, (long) expectedResult.value());

        final EdsWhiteBalance result = spyPropertyGetShortcutLogic.getWhiteBalance(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getWhiteBalance1() {
    }

    @Test
    void getColorTemperature() {
        final long expectedResult = 5L;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_ColorTemperature, expectedResult);

        final long result = spyPropertyGetShortcutLogic.getColorTemperature(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getColorTemperature1() {
    }

    @Test
    void getWhiteBalanceShift() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_WhiteBalanceShift, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getWhiteBalanceShift(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getWhiteBalanceShift1() {
    }

    @Test
    void getWBCoeffs() {
        final byte[] expectedResult = new byte[0];
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_WBCoeffs, expectedResult);

        final byte[] result = spyPropertyGetShortcutLogic.getWBCoeffs(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getLinear() {
        final boolean expectedResult = true;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_Linear, expectedResult);

        final boolean result = spyPropertyGetShortcutLogic.getLinear(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getColorSpace() {
    }

    @Test
    void getColorSpace1() {
    }

    @Test
    void getToneCurve() {
        final long expectedResult = 5L;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_ToneCurve, expectedResult);

        final long result = spyPropertyGetShortcutLogic.getToneCurve(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getToneCurve1() {
    }

    @Test
    void getPictureStyle() {
        final EdsPictureStyle expectedResult = EdsPictureStyle.kEdsPictureStyle_Faithful;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_PictureStyle, (long) expectedResult.value());

        final EdsPictureStyle result = spyPropertyGetShortcutLogic.getPictureStyle(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getPictureStyle1() {
    }

    @Test
    void getFlashOn() {
        final boolean expectedResult = true;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_FlashOn, expectedResult);

        final boolean result = spyPropertyGetShortcutLogic.getFlashOn(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getFlashMode() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_FlashMode, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getFlashMode(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getRedEye() {
        final int expectedResult = 5;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_RedEye, expectedResult);

        final int result = spyPropertyGetShortcutLogic.getRedEye(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getNoiseReduction() {
        final long expectedResult = 5L;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_NoiseReduction, expectedResult);

        final long result = spyPropertyGetShortcutLogic.getNoiseReduction(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getPictureStyleCaption() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_PictureStyleCaption, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getPictureStyleCaption(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getSaveTo() {
        final EdsSaveTo expectedResult = EdsSaveTo.kEdsSaveTo_Camera;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_SaveTo, (long) expectedResult.value());

        final EdsSaveTo result = spyPropertyGetShortcutLogic.getSaveTo(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getLensStatus() {
        final long expectedResult = 5L;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_LensStatus, expectedResult);

        final long result = spyPropertyGetShortcutLogic.getLensStatus(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getLensName() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_LensName, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getLensName(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCurrentStorage() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_CurrentStorage, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getCurrentStorage(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getCurrentFolder() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_CurrentFolder, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getCurrentFolder(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getHDDirectoryStructure() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_HDDirectoryStructure, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getHDDirectoryStructure(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfOutputDevice() {
        final EdsEvfOutputDevice expectedResult = EdsEvfOutputDevice.kEdsEvfOutputDevice_TFT;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, (long) expectedResult.value());

        final EdsEvfOutputDevice result = spyPropertyGetShortcutLogic.getEvfOutputDevice(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfMode() {
        final boolean expectedResult = true;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Evf_Mode, 1L);

        final boolean result = spyPropertyGetShortcutLogic.getEvfMode(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfWhiteBalance() {
        final EdsWhiteBalance expectedResult = EdsWhiteBalance.kEdsWhiteBalance_ColorTemp;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Evf_WhiteBalance, (long) expectedResult.value());

        final EdsWhiteBalance result = spyPropertyGetShortcutLogic.getEvfWhiteBalance(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfColorTemperature() {
        final long expectedResult = 5L;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Evf_ColorTemperature, expectedResult);

        final long result = spyPropertyGetShortcutLogic.getEvfColorTemperature(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfDepthOfFieldPreview() {
        final boolean expectedResult = true;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Evf_DepthOfFieldPreview, 1L);

        final boolean result = spyPropertyGetShortcutLogic.getEvfDepthOfFieldPreview(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfZoom() {
        final EdsEvfZoom expectedResult = EdsEvfZoom.kEdsEvfZoom_x5;
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_Zoom, (long) expectedResult.value());

        final EdsEvfZoom result = spyPropertyGetShortcutLogic.getEvfZoom(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfZoomPosition() {
        final EdsPoint expectedResult = new EdsPoint();
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_ZoomPosition, expectedResult);

        final EdsPoint result = spyPropertyGetShortcutLogic.getEvfZoomPosition(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvf_ZoomRect() {
        final EdsRect expectedResult = new EdsRect();
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_ZoomRect, expectedResult);

        final EdsRect result = spyPropertyGetShortcutLogic.getEvf_ZoomRect(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfImagePosition() {
        final EdsPoint expectedResult = new EdsPoint();
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_ImagePosition, expectedResult);

        final EdsPoint result = spyPropertyGetShortcutLogic.getEvfImagePosition(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfCoordinateSystem() {
        final EdsSize expectedResult = new EdsSize();
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_CoordinateSystem, expectedResult);

        final EdsSize result = spyPropertyGetShortcutLogic.getEvfCoordinateSystem(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfHistogramY() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_HistogramY, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getEvfHistogramY(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfHistogramR() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_HistogramR, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getEvfHistogramR(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfHistogramG() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_HistogramG, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getEvfHistogramG(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfHistogramB() {
        final int[] expectedResult = new int[0];
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_HistogramB, expectedResult);

        final int[] result = spyPropertyGetShortcutLogic.getEvfHistogramB(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfHistogramStatus() {
        final EdsEvfHistogramStatus expectedResult = EdsEvfHistogramStatus.kEdsEvfHistogramStatus_Grayout;
        mockGetProperty(fakeEvfImage, EdsPropertyID.kEdsPropID_Evf_HistogramStatus, (long) expectedResult.value());

        final EdsEvfHistogramStatus result = spyPropertyGetShortcutLogic.getEvfHistogramStatus(fakeEvfImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getEvfAFMode() {
        final EdsEvfAFMode expectedResult = EdsEvfAFMode.Evf_AFMode_LiveMulti;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Evf_AFMode, (long) expectedResult.value());

        final EdsEvfAFMode result = spyPropertyGetShortcutLogic.getEvfAFMode(fakeCamera);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getRecordStatus() {
        final boolean expectedResult = true;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_Record, 1L);

        final boolean result = spyPropertyGetShortcutLogic.getRecordStatus(fakeCamera);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSVersionIDAsInt() {
        final int expectedResult = 5;
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_GPSVersionID, (byte) 0x5);

        final int result = spyPropertyGetShortcutLogic.getGPSVersionIDAsInt(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSLatitudeRefAsString() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_GPSLatitudeRef, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getGPSLatitudeRefAsString(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSLatitudeAsRational() {
        final EdsRational[] expectedResult = new EdsRational[0];
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_GPSLatitude, expectedResult);

        final EdsRational[] result = spyPropertyGetShortcutLogic.getGPSLatitudeAsRational(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSLongitudeRefAsString() {
        final String expectedResult = "value";
        mockGetProperty(fakeCamera, EdsPropertyID.kEdsPropID_GPSLongitudeRef, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getGPSLongitudeRefAsString(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSLongitudeAsRational() {
        final EdsRational[] expectedResult = new EdsRational[0];
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSLongitude, expectedResult);

        final EdsRational[] result = spyPropertyGetShortcutLogic.getGPSLongitudeAsRational(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSAltitudeRefAsInt() {
        final int expectedResult = 5;
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSAltitudeRef, (byte) expectedResult);

        final int result = spyPropertyGetShortcutLogic.getGPSAltitudeRefAsInt(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSAltitude() {
        final EdsRational expectedResult = new EdsRational();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSAltitude, expectedResult);

        final EdsRational result = spyPropertyGetShortcutLogic.getGPSAltitude(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSTimeStampAsRational() {
        final EdsRational[] expectedResult = new EdsRational[0];
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSTimeStamp, expectedResult);

        final EdsRational[] result = spyPropertyGetShortcutLogic.getGPSTimeStampAsRational(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSSatellites() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSSatellites, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getGPSSatellites(fakeImage);

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSMapDatum() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSMapDatum, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getGPSMapDatum(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSDateStamp() {
        final LocalDate expectedResult = LocalDate.now();
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSDateStamp, "value");

        Assertions.assertThrows(NotImplementedException.class, () -> spyPropertyGetShortcutLogic.getGPSDateStamp(fakeImage));
//        final LocalDate result = spyPropertyGetShortcutLogic.getGPSDateStamp(fakeImage);

//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSDateStampAsString() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSDateStamp, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getGPSDateStampAsString(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getGPSStatusAsString() {
        final String expectedResult = "value";
        mockGetProperty(fakeImage, EdsPropertyID.kEdsPropID_GPSStatus, expectedResult);

        final String result = spyPropertyGetShortcutLogic.getGPSStatusAsString(fakeImage);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResult, result);
    }
}
