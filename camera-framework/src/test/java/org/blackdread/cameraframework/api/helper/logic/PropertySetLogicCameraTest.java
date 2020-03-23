/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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

    private static EdsdkLibrary.EdsCameraRef cameraRef;

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

    @Test
    void setPropertyIsoSpeed() throws InterruptedException {
        final List<EdsISOSpeed> isoSpeeds = CanonFactory.propertyDescLogic().getPropertyDesc(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed);

        CanonFactory.propertySetLogic().setPropertyData(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed, isoSpeeds.get(0));

        final EdsISOSpeed isoSpeed = EdsISOSpeed.ofValue(CanonFactory.propertyGetLogic().getPropertyDataLong(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed).intValue());

        Assertions.assertSame(isoSpeeds.get(0), isoSpeed);

        CanonFactory.propertySetLogic().setPropertyData(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed, isoSpeeds.get(1));

        final EdsISOSpeed isoSpeed2 = EdsISOSpeed.ofValue(CanonFactory.propertyGetLogic().getPropertyDataLong(cameraRef, EdsPropertyID.kEdsPropID_ISOSpeed).intValue());

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
