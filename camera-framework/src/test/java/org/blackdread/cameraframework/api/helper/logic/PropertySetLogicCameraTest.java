package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
public class PropertySetLogicCameraTest {

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

    @Test
    void setPropertyIsoSpeed() throws InterruptedException {
        final List<EdsISOSpeed> isoSpeeds = CanonFactory.propertyDescLogic().getPropertyDesc(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed);

        CanonFactory.propertySetLogic().setPropertyData(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed, isoSpeeds.get(0));

        final EdsISOSpeed isoSpeed = EdsISOSpeed.ofValue(CanonFactory.propertyGetLogic().getPropertyDataLong(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed).intValue());

        Assertions.assertSame(isoSpeeds.get(0), isoSpeed);

        CanonFactory.propertySetLogic().setPropertyData(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed, isoSpeeds.get(1));

        final EdsISOSpeed isoSpeed2 = EdsISOSpeed.ofValue(CanonFactory.propertyGetLogic().getPropertyDataLong(camera.getValue(), EdsPropertyID.kEdsPropID_ISOSpeed).intValue());

        Assertions.assertSame(isoSpeeds.get(1), isoSpeed2);
    }

    @Test
    void setPropertyData() {

    }

    @Test
    void setPropertyData1() {

    }

    @Test
    void setPropertyData2() {

    }
}
