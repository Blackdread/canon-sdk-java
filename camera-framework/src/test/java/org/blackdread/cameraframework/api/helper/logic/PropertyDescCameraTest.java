package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.NativeEnum;
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

import java.util.List;
import java.util.stream.Stream;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyDescLogic;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
public class PropertyDescCameraTest {

    private static final Logger log = LoggerFactory.getLogger(PropertyGetLogicCameraTest.class);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

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

    /**
     * @return all compatible property id with GetPropertyDesc
     */
    static Stream<Arguments> allEdsPropertyIDCompatibleWithGetPropertyDesc() {
        return Stream.of(
            Arguments.arguments(EdsPropertyID.kEdsPropID_AEMode), // TODO not sure about this one, in doc it is kEdsPropID_AEModeSelect but could be a typo
            Arguments.arguments(EdsPropertyID.kEdsPropID_AEModeSelect),
            Arguments.arguments(EdsPropertyID.kEdsPropID_ISOSpeed),
            Arguments.arguments(EdsPropertyID.kEdsPropID_MeteringMode),
            Arguments.arguments(EdsPropertyID.kEdsPropID_Av),
            Arguments.arguments(EdsPropertyID.kEdsPropID_Tv),
            Arguments.arguments(EdsPropertyID.kEdsPropID_ExposureCompensation),
            Arguments.arguments(EdsPropertyID.kEdsPropID_ImageQuality),
            Arguments.arguments(EdsPropertyID.kEdsPropID_WhiteBalance),
//            Arguments.arguments(EdsPropertyID.kEdsPropID_ColorTemperature), // tested in another method
            Arguments.arguments(EdsPropertyID.kEdsPropID_PictureStyle),
            Arguments.arguments(EdsPropertyID.kEdsPropID_DriveMode),
            Arguments.arguments(EdsPropertyID.kEdsPropID_Evf_WhiteBalance),
//            Arguments.arguments(EdsPropertyID.kEdsPropID_Evf_ColorTemperature), // tested in another method
            Arguments.arguments(EdsPropertyID.kEdsPropID_Evf_AFMode)
        );
    }

    @ParameterizedTest()
    @MethodSource("allEdsPropertyIDCompatibleWithGetPropertyDesc")
    void getPropertyDesc(EdsPropertyID propertyID) {
        final List<NativeEnum<Integer>> nativeEnums = propertyDescLogic().getPropertyDesc(camera.getValue(), propertyID);

        Assertions.assertNotNull(nativeEnums);
        if (nativeEnums.isEmpty()) {
            log.warn("No values returned for {}, it might not be supported by current camera", propertyID);
        }
        log.info("Available values for {}: {}", propertyID, nativeEnums);
    }

    @Test
    void getPropertyDescThrowsForColorTemperature() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> propertyDescLogic().getPropertyDesc(camera.getValue(), EdsPropertyID.kEdsPropID_ColorTemperature));
    }

    @Test
    void getPropertyDescThrowsForEvfColorTemperature() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> propertyDescLogic().getPropertyDesc(camera.getValue(), EdsPropertyID.kEdsPropID_Evf_ColorTemperature));
    }

    @Test
    void getPropertyDescColorTemperature() {
        final List<Integer> colorTemperatures = propertyDescLogic().getPropertyDescColorTemperature(camera.getValue());
        Assertions.assertNotNull(colorTemperatures);
        log.info("Available values for {}: {}", EdsPropertyID.kEdsPropID_ColorTemperature, colorTemperatures);
    }

    @Test
    void getPropertyDescEvfColorTemperature() {
        final List<Integer> evfColorTemperatures = propertyDescLogic().getPropertyDescEvfColorTemperature(camera.getValue());
        Assertions.assertNotNull(evfColorTemperatures);
        log.info("Available values for {}: {}", EdsPropertyID.kEdsPropID_Evf_ColorTemperature, evfColorTemperatures);
    }
}