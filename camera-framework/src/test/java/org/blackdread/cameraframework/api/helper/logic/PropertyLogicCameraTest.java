package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.Memory;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
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
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyLogic;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <p>Created on 2018/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class PropertyLogicCameraTest {

    private static final Logger log = LoggerFactory.getLogger(PropertyLogicCameraTest.class);

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

    static Stream<Arguments> propertyTypeAndSizeExpected() {
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
        final Pair<EdsDataType, Long> propertyTypeAndSize = propertyLogic().getPropertyTypeAndSize(camera.getValue(), propertyID);

        Assertions.assertNotNull(propertyTypeAndSize);
        Assertions.assertEquals(expectedType, propertyTypeAndSize.getKey());
        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertyTypeAndSize.getValue() > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertyTypeAndSize.getValue().longValue());
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyTypeAndSizeWithParam(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final Pair<EdsDataType, Long> propertyTypeAndSize = propertyLogic().getPropertyTypeAndSize(camera.getValue(), propertyID, 0);

        Assertions.assertNotNull(propertyTypeAndSize);
        Assertions.assertEquals(expectedType, propertyTypeAndSize.getKey());
        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertyTypeAndSize.getValue() > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertyTypeAndSize.getValue().longValue());
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyType(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final EdsDataType propertyType = propertyLogic().getPropertyType(camera.getValue(), propertyID);

        Assertions.assertNotNull(propertyType);
        Assertions.assertEquals(expectedType, propertyType);
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertyTypeWithInParam(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final EdsDataType propertyType = propertyLogic().getPropertyType(camera.getValue(), propertyID, 0);

        Assertions.assertNotNull(propertyType);
        Assertions.assertEquals(expectedType, propertyType);
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertySize(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final long propertySize = propertyLogic().getPropertySize(camera.getValue(), propertyID);

        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertySize > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertySize);
    }

    @ParameterizedTest()
    @MethodSource("propertyTypeAndSizeExpected")
    void getPropertySizeWithInParam(EdsPropertyID propertyID, EdsDataType expectedType, long sizeExpected) {
        final long propertySize = propertyLogic().getPropertySize(camera.getValue(), propertyID);

        if (expectedType == EdsDataType.kEdsDataType_String)
            Assertions.assertTrue(propertySize > sizeExpected);
        else
            Assertions.assertEquals(sizeExpected, propertySize);
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

    @ParameterizedTest()
    @MethodSource("allEdsPropertyID")
    void getPropertyDataShortcut(EdsPropertyID propertyID) {
        final long propertySize;
        try {
            propertySize = propertyLogic().getPropertySize(camera.getValue(), propertyID, 0);
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

        assertNoError(propertyLogic().getPropertyData(camera.getValue(), propertyID, 0, propertySize, memory));


    }
}
