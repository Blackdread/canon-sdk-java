package org.blackdread.cameraframework.api.helper.logic;

import org.apache.commons.lang3.tuple.Pair;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyLogic;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <p>Created on 2018/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class PropertyLogicCameraTest {

    private static EdsCameraRef.ByReference camera;

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
    }

    @AfterAll
    static void tearDownClass() {
        TestShortcutUtil.closeSession(camera);
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest()
    @MethodSource("getPropertyTypeAndSize")
    void getPropertyTypeAndSize(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final Pair<EdsDataType, Long> propertyTypeAndSize = propertyLogic().getPropertyTypeAndSize(camera.getValue(), propertyID);

        Assertions.assertNotNull(propertyTypeAndSize);
        Assertions.assertEquals(expectedType, propertyTypeAndSize.getKey());
        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertyTypeAndSize.getValue() > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertyTypeAndSize.getValue().longValue());
    }

    static Stream<Arguments> getPropertyTypeAndSize() {
        return Stream.of(
            arguments(EdsPropertyID.kEdsPropID_ISOSpeed, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_BodyIDEx, EdsDataType.kEdsDataType_String, 10),
            arguments(EdsPropertyID.kEdsPropID_AEBracket, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_Artist, EdsDataType.kEdsDataType_String, 5),
            arguments(EdsPropertyID.kEdsPropID_ImageQuality, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_ColorTemperature, EdsDataType.kEdsDataType_Int32, 4),
            arguments(EdsPropertyID.kEdsPropID_Evf_AFMode, EdsDataType.kEdsDataType_UInt32, 4),
            arguments(EdsPropertyID.kEdsPropID_DateTime, EdsDataType.kEdsDataType_Time, 28)
        );
    }

    @Test
    void getPropertyTypeAndSizeWithParam() {
    }

    @Test
    void getPropertyType() {
    }

    @Test
    void getPropertyType1() {
    }

    @Test
    void getPropertySize() {
    }

    @Test
    void getPropertySize1() {
    }

    @Test
    void getPropertyDataLong() {
    }

    @Test
    void getPropertyDataLong1() {
    }

    @Test
    void getPropertyData() {
    }

    @Test
    void getPropertyData1() {
    }

    @Test
    void getPropertyData2() {
    }
}
