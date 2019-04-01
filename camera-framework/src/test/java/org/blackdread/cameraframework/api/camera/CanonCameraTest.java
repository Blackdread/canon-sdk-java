package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.decorator.builder.CommandBuilderReusable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * <p>Created on 2018/12/22.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonCameraTest {

    private CanonCamera cameraDefaultConstructor;
    private CanonCamera cameraWithSerialNumber;

    @BeforeEach
    void setUp() {
        cameraDefaultConstructor = new CanonCamera();
        cameraWithSerialNumber = new CanonCamera("asdasd1651518");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCameraRef() {
        Assertions.assertFalse(cameraDefaultConstructor.getCameraRef().isPresent());
        Assertions.assertFalse(cameraWithSerialNumber.getCameraRef().isPresent());
    }

    @Test
    void setCameraRef() {
        final EdsdkLibrary.EdsCameraRef edsCameraRef = new EdsdkLibrary.EdsCameraRef();
        cameraDefaultConstructor.setCameraRef(edsCameraRef);
        cameraWithSerialNumber.setCameraRef(edsCameraRef);
        Assertions.assertTrue(cameraDefaultConstructor.getCameraRef().isPresent());
        Assertions.assertTrue(cameraWithSerialNumber.getCameraRef().isPresent());

        final EdsdkLibrary.EdsCameraRef edsCameraRef2 = new EdsdkLibrary.EdsCameraRef();
        cameraDefaultConstructor.setCameraRef(edsCameraRef2);
        cameraWithSerialNumber.setCameraRef(edsCameraRef2);
    }

    @Test
    void getSerialNumber() {
        Assertions.assertFalse(cameraDefaultConstructor.getSerialNumber().isPresent());
        Assertions.assertTrue(cameraWithSerialNumber.getSerialNumber().isPresent());
    }

    @Test
    void setSerialNumber() {
        cameraDefaultConstructor.setSerialNumber("adwd16");

        Assertions.assertThrows(IllegalStateException.class, () -> cameraWithSerialNumber.setSerialNumber("adwd16"));

        // Cannot change once set
        Assertions.assertThrows(IllegalStateException.class, () -> cameraDefaultConstructor.setSerialNumber("adwd16"));
    }

    @Test
    void getCommandBuilderReusable() {
        Assertions.assertFalse(cameraDefaultConstructor.getCommandBuilderReusable().isPresent());
        Assertions.assertFalse(cameraWithSerialNumber.getCommandBuilderReusable().isPresent());
    }

    @Test
    void setCommandBuilderReusable() {
        final CommandBuilderReusable.ReusableBuilder<Object> builder = new CommandBuilderReusable.ReusableBuilder<>();
        cameraDefaultConstructor.setCommandBuilderReusable(builder);
        cameraWithSerialNumber.setCommandBuilderReusable(builder);
        Assertions.assertTrue(cameraDefaultConstructor.getCommandBuilderReusable().isPresent());
        Assertions.assertTrue(cameraWithSerialNumber.getCommandBuilderReusable().isPresent());
    }

    @Test
    void getDefaultTimeout() {
        Assertions.assertFalse(cameraDefaultConstructor.getDefaultTimeout().isPresent());
        Assertions.assertFalse(cameraWithSerialNumber.getDefaultTimeout().isPresent());
    }

    @Test
    void setDefaultTimeout() {
        cameraDefaultConstructor.setDefaultTimeout(Duration.ofSeconds(30));
        cameraWithSerialNumber.setDefaultTimeout(Duration.ofSeconds(30));
        Assertions.assertTrue(cameraDefaultConstructor.getDefaultTimeout().isPresent());
        Assertions.assertTrue(cameraWithSerialNumber.getDefaultTimeout().isPresent());
    }

    @Test
    void getEvent() {
        Assertions.assertNotNull(cameraDefaultConstructor.getEvent());
        Assertions.assertNotNull(cameraWithSerialNumber.getEvent());
    }

    @Test
    void getShoot() {
        Assertions.assertNotNull(cameraDefaultConstructor.getShoot());
        Assertions.assertNotNull(cameraWithSerialNumber.getShoot());
    }

    @Test
    void getLiveView() {
        Assertions.assertNotNull(cameraDefaultConstructor.getLiveView());
        Assertions.assertNotNull(cameraWithSerialNumber.getLiveView());
    }

    @Test
    void getProperty() {
        Assertions.assertNotNull(cameraDefaultConstructor.getProperty());
        Assertions.assertNotNull(cameraWithSerialNumber.getProperty());
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(cameraDefaultConstructor.toString());
        Assertions.assertNotNull(cameraWithSerialNumber.toString());
    }
}
