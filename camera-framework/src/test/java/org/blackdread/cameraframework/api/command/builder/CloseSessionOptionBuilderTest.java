package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2018/12/16.</p>
 *
 * @author Yoann CAPLAIN
 */
class CloseSessionOptionBuilderTest {

    private EdsdkLibrary.EdsCameraRef cameraRef;
    private CloseSessionOptionBuilder builder;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsdkLibrary.EdsCameraRef();
        builder = new CloseSessionOptionBuilder();
    }

    @Test
    void setCameraRef() {
        final CloseSessionOption option = builder
            .setCameraRef(cameraRef)
            .build();
        Assertions.assertNotNull(option.getCameraRef());
        Assertions.assertFalse(option.getCamera().isPresent());
        Assertions.assertTrue(option.isReleaseCameraRef());
    }

    @Test
    void setCameraRefThrowsOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> builder.setCameraRef(null));
    }

    @Test
    void setReleaseCameraRef() {
        final CloseSessionOption option = builder
            .setCameraRef(cameraRef)
            .setReleaseCameraRef(true)
            .build();
        Assertions.assertNotNull(option.getCameraRef());
        Assertions.assertTrue(option.isReleaseCameraRef());

        final CloseSessionOption option1 = builder.build();
        Assertions.assertEquals(option, option1);
        Assertions.assertEquals(option.hashCode(), option1.hashCode());
    }

    @Test
    void setCamera() {
        final CloseSessionOption option = builder
            .setCameraRef(cameraRef)
            .setCamera(null)
            .build();
        Assertions.assertNotNull(option.getCameraRef());
        Assertions.assertFalse(option.getCamera().isPresent());
        final CloseSessionOption option1 = builder
            .setCameraRef(cameraRef)
            .setCamera(new CanonCamera())
            .build();
        Assertions.assertNotNull(option1.getCameraRef());
        Assertions.assertTrue(option1.getCamera().isPresent());
        Assertions.assertNotEquals(option, option1);
        Assertions.assertNotEquals(option.hashCode(), option1.hashCode());
    }

    @Test
    void build() {
        final CloseSessionOption option = builder
            .setCameraRef(cameraRef)
            .build();
        final String l = option.toString();

    }

    @Test
    void buildThrowsIfCameraRefNull() {
        Assertions.assertThrows(IllegalStateException.class, () -> builder.build());
    }
}
