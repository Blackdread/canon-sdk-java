package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * <p>Created on 2018/12/16.</p>
 *
 * @author Yoann CAPLAIN
 */
class OpenSessionOptionBuilderTest {

    private EdsdkLibrary.EdsCameraRef cameraRef;
    private CanonCamera canonCamera;
    private OpenSessionOptionBuilder builder;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsdkLibrary.EdsCameraRef();
        canonCamera = new CanonCamera();
        builder = new OpenSessionOptionBuilder();
    }

    @Test
    void buildOkNoChanges() {
        builder.build();
    }

    @Test
    void setOpenSessionOnly() {
        final OpenSessionOption option = builder
            .setOpenSessionOnly(true)
            .setRegisterObjectEvent(false)
            .setRegisterPropertyEvent(false)
            .setRegisterStateEvent(false)
            .setCameraRef(cameraRef)
            .build();
        Assertions.assertTrue(option.isOpenSessionOnly());
        Assertions.assertTrue(option.getCameraRef().isPresent());
    }

    @Test
    void setOpenSessionOnlyThrowsWithoutCameraRef() {
        Assertions.assertThrows(IllegalStateException.class, () -> builder.setOpenSessionOnly(true).build());
    }

    @Test
    void setOpenSessionOnlyThrowsWithEvent() {
        Assertions.assertThrows(IllegalStateException.class, () -> builder.setOpenSessionOnly(true).setCameraRef(cameraRef).build());
    }

    @Test
    void throwsIfCameraRefIsSetForOtherThanOnlySession() {
        Assertions.assertThrows(IllegalStateException.class, () -> builder
            .setCameraBySerialNumber("adawda")
            .setCameraRef(cameraRef).build());
    }

    @Test
    void throwsIfCameraRefIsSetForOtherThanOnlySession1() {
        Assertions.assertThrows(IllegalStateException.class, () -> builder
            .setCameraByIndex(0)
            .setCameraRef(cameraRef).build());
    }

    @Test
    void throwsIfCameraRefIsSetForOtherThanOnlySession2() {
        Assertions.assertThrows(IllegalStateException.class, () -> builder
            .setRegisterObjectEvent(true)
            .setCameraRef(cameraRef).build());
        Assertions.assertThrows(IllegalStateException.class, () -> builder
            .setRegisterObjectEvent(false)
            .setRegisterPropertyEvent(true)
            .setCameraRef(cameraRef).build());
        Assertions.assertThrows(IllegalStateException.class, () -> builder
            .setRegisterPropertyEvent(false)
            .setRegisterStateEvent(true)
            .setCameraRef(cameraRef).build());
    }

    @Test
    void throwBothNull() {
        builder.setCameraByIndex(null);
        Assertions.assertThrows(IllegalStateException.class, () -> builder.build());
    }

    @Test
    void throwBothNonNull() throws NoSuchFieldException, IllegalAccessException {
        builder.setCameraBySerialNumber("asdaddasd");

        final Field cameraByIndex = builder.getClass()
            .getDeclaredField("cameraByIndex");
        cameraByIndex.setAccessible(true);
        cameraByIndex.set(builder, 95);

        Assertions.assertThrows(IllegalStateException.class, () -> builder.build());
    }

    @Test
    void setCameraByIndex() {
        final OpenSessionOption option = builder
            .setCameraBySerialNumber("adawda")
            .setCameraByIndex(5)
            .build();
        Assertions.assertFalse(option.getCameraBySerialNumber().isPresent());
        Assertions.assertTrue(option.getCameraByIndex().isPresent());
    }

    @Test
    void setCameraBySerialNumber() {
        final OpenSessionOption option = builder
            .setCameraByIndex(5)
            .setCameraBySerialNumber("adawda")
            .build();
        Assertions.assertTrue(option.getCameraBySerialNumber().isPresent());
        Assertions.assertFalse(option.getCameraByIndex().isPresent());
    }

    @Test
    void setRegisterObjectEvent() {
        final OpenSessionOption option = builder
            .setCamera(canonCamera)
            .setCameraBySerialNumber("adawda")
            .setRegisterObjectEvent(true)
            .build();
    }

    @Test
    void setRegisterPropertyEvent() {
        final OpenSessionOption option = builder
            .setCamera(canonCamera)
            .setCameraBySerialNumber("adawda")
            .setRegisterPropertyEvent(true)
            .build();
    }

    @Test
    void setRegisterStateEvent() {
        final OpenSessionOption option = builder
            .setCamera(canonCamera)
            .setCameraBySerialNumber("adawda")
            .setRegisterStateEvent(true)
            .build();
    }
}
