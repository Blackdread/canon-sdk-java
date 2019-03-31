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
package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.Memory;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import static org.blackdread.cameraframework.api.TestUtil.assertNoError;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyGetLogic;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyLogic;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <p>Created on 2018/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class PropertyGetLogicCameraTest {

    private static final Logger log = LoggerFactory.getLogger(PropertyGetLogicCameraTest.class);

    private static EdsCameraRef.ByReference camera;

    private static EdsCameraRef cameraRef;

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
        cameraRef = camera.getValue();
    }

    @AfterAll
    static void tearDownClass() {
        try {
            TestShortcutUtil.closeSession(camera);
        } finally {
            ReleaseUtil.release(camera);
        }
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    static Stream<Arguments> propertyTypeAndSizeExpected() {
        // some lines below are commented as camera could not get the data for some of them
        // some of those properties have been removed from newer cameras
        // TODO some are not compatible with cameraRef, should test with EdsEvfImageRef or EdsImageRef or EdsVolumeRef
        return Stream.of(
            arguments(EdsPropertyID.kEdsPropID_ISOSpeed, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_BodyIDEx, EdsDataType.kEdsDataType_String, 10),
            arguments(EdsPropertyID.kEdsPropID_AEBracket, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Artist, EdsDataType.kEdsDataType_String, 5),
            arguments(EdsPropertyID.kEdsPropID_ImageQuality, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_ColorTemperature, EdsDataType.kEdsDataType_Int32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_AFMode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_DateTime, EdsDataType.kEdsDataType_Time, 28),

            arguments(EdsPropertyID.kEdsPropID_ProductName, EdsDataType.kEdsDataType_String, 4),
            arguments(EdsPropertyID.kEdsPropID_OwnerName, EdsDataType.kEdsDataType_String, 0),
//            arguments(EdsPropertyID.kEdsPropID_MakerName, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_FirmwareVersion, EdsDataType.kEdsDataType_String, 4),
            arguments(EdsPropertyID.kEdsPropID_BatteryLevel, EdsDataType.kEdsDataType_Int32, 4),
            arguments(EdsPropertyID.kEdsPropID_SaveTo, EdsDataType.kEdsDataType_UInt32, 4),
//            arguments(EdsPropertyID.kEdsPropID_CurrentStorage, EdsDataType.kEdsDataType_String, 4), // not supported
//            arguments(EdsPropertyID.kEdsPropID_CurrentFolder, EdsDataType.kEdsDataType_UInt32, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_MyMenu, EdsDataType.kEdsDataType_UInt32_Array, 0),
//            arguments(EdsPropertyID.kEdsPropID_BatteryQuality, EdsDataType.kEdsDataType_UInt32, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_HDDirectoryStructure, EdsDataType.kEdsDataType_String, 4),  // not supported
//            arguments(EdsPropertyID.kEdsPropID_JpegQuality, EdsDataType.kEdsDataType_UInt32, 4), // not supported
//            arguments(EdsPropertyID.kEdsPropID_Orientation, EdsDataType.kEdsDataType_UInt32, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_ICCProfile, EdsDataType.kEdsDataType_ByteBlock, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_FocusInfo, EdsDataType.kEdsDataType_FocusInfo, 19224),
//            arguments(EdsPropertyID.kEdsPropID_DigitalExposure, EdsDataType.kEdsDataType_Rational, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_WhiteBalance, EdsDataType.kEdsDataType_Int32, 4),
            arguments(EdsPropertyID.kEdsPropID_WhiteBalanceShift, EdsDataType.kEdsDataType_Int32_Array, 8),
            arguments(EdsPropertyID.kEdsPropID_Contrast, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_ColorSaturation, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_ColorTone, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_Sharpness, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_ColorSpace, EdsDataType.kEdsDataType_Int32, 4),
//            arguments(EdsPropertyID.kEdsPropID_ToneCurve, EdsDataType.kEdsDataType_UInt32, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_PhotoEffect, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_FilterEffect, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_ToningEffect, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_ParameterSet, EdsDataType.kEdsDataType_Unknown, 4), // not supported
            arguments(EdsPropertyID.kEdsPropID_ColorMatrix, EdsDataType.kEdsDataType_Unknown, 4), // not supported
//            arguments(EdsPropertyID.kEdsPropID_PictureStyle, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_PictureStyleDesc, EdsDataType.kEdsDataType_PictureStyleDesc, 32),
//            arguments(EdsPropertyID.kEdsPropID_PictureStyleCaption, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Linear, EdsDataType.kEdsDataType_Bool, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_ClickWBPoint, EdsDataType.kEdsDataType_Point, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_WBCoeffs, EdsDataType.kEdsDataType_ByteBlock, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSVersionID, EdsDataType.kEdsDataType_UInt8, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSLatitudeRef, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSLatitude, EdsDataType.kEdsDataType_Rational_Array, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSLongitudeRef, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSLongitude, EdsDataType.kEdsDataType_Rational_Array, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSAltitudeRef, EdsDataType.kEdsDataType_UInt8, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSAltitude, EdsDataType.kEdsDataType_Rational, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSTimeStamp, EdsDataType.kEdsDataType_Rational_Array, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSSatellites, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSStatus, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSMapDatum, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_GPSDateStamp, EdsDataType.kEdsDataType_String, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_AtCapture_Flag, EdsDataType.kEdsDataType_UInt32, 4), // EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_AEMode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_DriveMode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_MeteringMode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_AFMode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Av, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Tv, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_ExposureCompensation, EdsDataType.kEdsDataType_UInt32, 4),
//            arguments(EdsPropertyID.kEdsPropID_FlashCompensation, EdsDataType.kEdsDataType_UInt32, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_FocalLength, EdsDataType.kEdsDataType_Rational_Array, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_AvailableShots, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Bracket, EdsDataType.kEdsDataType_UInt32, 4),
//            arguments(EdsPropertyID.kEdsPropID_WhiteBalanceBracket, EdsDataType.kEdsDataType_Int32_Array, 4),// not supported
            arguments(EdsPropertyID.kEdsPropID_LensName, EdsDataType.kEdsDataType_String, 4),
//            arguments(EdsPropertyID.kEdsPropID_FEBracket, EdsDataType.kEdsDataType_Rational, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_ISOBracket, EdsDataType.kEdsDataType_Rational, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_NoiseReduction, EdsDataType.kEdsDataType_UInt32, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_FlashOn, EdsDataType.kEdsDataType_UInt32, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_RedEye, EdsDataType.kEdsDataType_UInt32, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_FlashMode, EdsDataType.kEdsDataType_UInt32_Array, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_LensStatus, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Copyright, EdsDataType.kEdsDataType_String, 0),
            arguments(EdsPropertyID.kEdsPropID_DepthOfField, EdsDataType.kEdsDataType_Unknown, 4),// not supported
            arguments(EdsPropertyID.kEdsPropID_EFCompensation, EdsDataType.kEdsDataType_Unknown, 4),// not supported
            arguments(EdsPropertyID.kEdsPropID_AEModeSelect, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Record, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_OutputDevice, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_Mode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_WhiteBalance, EdsDataType.kEdsDataType_Int32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_ColorTemperature, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_DepthOfFieldPreview, EdsDataType.kEdsDataType_UInt32, 4),
//            arguments(EdsPropertyID.kEdsPropID_Evf_Zoom, EdsDataType.kEdsDataType_UInt32, 4),// not supported
//            arguments(EdsPropertyID.kEdsPropID_Evf_ZoomPosition, EdsDataType.kEdsDataType_Point, 4),// not supported
            arguments(EdsPropertyID.kEdsPropID_Evf_FocusAid, EdsDataType.kEdsDataType_Unknown, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_Histogram, EdsDataType.kEdsDataType_ByteBlock, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_ImagePosition, EdsDataType.kEdsDataType_Point, 4),// not supported
//            arguments(EdsPropertyID.kEdsPropID_Evf_HistogramStatus, EdsDataType.kEdsDataType_UInt32, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_HistogramY, EdsDataType.kEdsDataType_ByteBlock, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_HistogramR, EdsDataType.kEdsDataType_ByteBlock, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_HistogramG, EdsDataType.kEdsDataType_ByteBlock, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_HistogramB, EdsDataType.kEdsDataType_ByteBlock, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_CoordinateSystem, EdsDataType.kEdsDataType_Point, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
//            arguments(EdsPropertyID.kEdsPropID_Evf_ZoomRect, EdsDataType.kEdsDataType_Point, 4),// EDS_ERR_PROPERTIES_UNAVAILABLE
            arguments(EdsPropertyID.kEdsPropID_Evf_ImageClipRect, EdsDataType.kEdsDataType_Unknown, 4)// EDS_ERR_PROPERTIES_UNAVAILABLE
        );
    }

    /**
     * @return all EdsPropertyID minus Unknown
     */
    static Stream<Arguments> allEdsPropertyID() {
        return Stream.of(
            Arrays.stream(EdsPropertyID.values())
                .filter(propertyID -> !EdsPropertyID.kEdsPropID_Unknown.equals(propertyID))
                .map(Arguments::arguments)
                .toArray(Arguments[]::new)
        );
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyTypeAndSize(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        if (EdsDataType.kEdsDataType_Unknown.equals(expectedType)) {
            log.warn("Skip test of {} as it is unknown", propertyID);
            return;
        }
        final PropertyInfo propertyInfo = propertyLogic().getPropertyTypeAndSize(cameraRef, propertyID);

        Assertions.assertNotNull(propertyInfo);
        Assertions.assertEquals(expectedType, propertyInfo.getDataType());
        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertyInfo.getSize() > sizeExpected, "Size expected should be more than " + sizeExpected + ", was " + propertyInfo.getSize());
        else
            Assertions.assertEquals(sizeExpected, propertyInfo.getSize().longValue());
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyTypeAndSizeWithParam(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        if (EdsDataType.kEdsDataType_Unknown.equals(expectedType)) {
            log.warn("Skip test of {} as it is unknown", propertyID);
            return;
        }
        final PropertyInfo propertyInfo = propertyLogic().getPropertyTypeAndSize(cameraRef, propertyID, 0);

        Assertions.assertNotNull(propertyInfo);
        Assertions.assertEquals(expectedType, propertyInfo.getDataType());
        Assertions.assertNotNull(propertyInfo.toString());
        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertyInfo.getSize() > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertyInfo.getSize().longValue());
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyType(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final EdsDataType propertyType;
        if (EdsDataType.kEdsDataType_Unknown.equals(expectedType)) {
            try {
                propertyType = propertyLogic().getPropertyType(cameraRef, propertyID);
                Assertions.fail("Property " + propertyID + "works but was not supported with my camera");
            } catch (EdsdkErrorException e) {
                log.warn("Skip test of {} as it is unknown", propertyID);
                return;
            }
        } else
            propertyType = propertyLogic().getPropertyType(cameraRef, propertyID);

        Assertions.assertNotNull(propertyType);
        Assertions.assertEquals(expectedType, propertyType);
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyTypeWithInParam(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        if (EdsDataType.kEdsDataType_Unknown.equals(expectedType)) {
            log.warn("Skip test of {} as it is unknown", propertyID);
            return;
        }
        final EdsDataType propertyType = propertyLogic().getPropertyType(cameraRef, propertyID, 0);

        Assertions.assertNotNull(propertyType);
        Assertions.assertEquals(expectedType, propertyType);
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertySize(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        if (EdsDataType.kEdsDataType_Unknown.equals(expectedType)) {
            log.warn("Skip test of {} as it is unknown", propertyID);
            return;
        }
        final long propertySize = propertyLogic().getPropertySize(cameraRef, propertyID);

        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertySize > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertySize);
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertySizeWithInParam(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        if (EdsDataType.kEdsDataType_Unknown.equals(expectedType)) {
            log.warn("Skip test of {} as it is unknown", propertyID);
            return;
        }
        final long propertySize = propertyLogic().getPropertySize(cameraRef, propertyID);

        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertySize > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertySize);
    }

    @Test
    void getPropertyDataLong() {
        final Long value = propertyGetLogic().getPropertyDataLong(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed);
        Assertions.assertNotNull(value);
        final EdsISOSpeed isoSpeed = EdsISOSpeed.ofValue(value.intValue());
        Assertions.assertNotNull(isoSpeed);
    }

    @Test
    void getPropertyDataLongWithInParam() {
        final Long value = propertyGetLogic().getPropertyDataLong(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed, 0);
        Assertions.assertNotNull(value);
        final EdsISOSpeed isoSpeed = EdsISOSpeed.ofValue(value.intValue());
        Assertions.assertNotNull(isoSpeed);
    }

    @Test
    void getPropertyData() {
        final Long value = propertyGetLogic().getPropertyData(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed);
        Assertions.assertNotNull(value);
        final EdsISOSpeed isoSpeed = EdsISOSpeed.ofValue(value.intValue());
        Assertions.assertNotNull(isoSpeed);
    }

    @Test
    void getPropertyDataWithInParam() {
        final Long value = propertyGetLogic().getPropertyData(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed, 0);
        Assertions.assertNotNull(value);
        final EdsISOSpeed isoSpeed = EdsISOSpeed.ofValue(value.intValue());
        Assertions.assertNotNull(isoSpeed);
    }

    @ParameterizedTest()
    @MethodSource("allEdsPropertyID")
    void getPropertyDataShortcut(EdsPropertyID propertyID) {
        final long propertySize;
        try {
            propertySize = propertyLogic().getPropertySize(cameraRef, propertyID, 0);
        } catch (EdsdkErrorException e) {
            switch (e.getEdsdkError()) {
                case EDS_ERR_NOT_SUPPORTED:
                    log.error("Property ({}) is not supported by current camera", propertyID);
                    return;
                case EDS_ERR_PROPERTIES_UNAVAILABLE:
                    log.error("Property ({}) is unavailable for current camera", propertyID);
                    return;
                default:
                    throw e;
            }
        }

        final Memory memory = new Memory(propertySize == 0 ? 1 : propertySize);

        assertNoError(propertyGetLogic().getPropertyData(cameraRef, propertyID, 0, propertySize, memory));


    }
}
