package org.blackdread.cameraframework.api.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.blackdread.cameraframework.api.command.TargetRefAccessType.*;

/**
 * <p>Created on 2019/04/02.</p>
 *
 * @author Yoann CAPLAIN
 */
class TargetRefAccessTypeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTargetRefType() {
        Assertions.assertEquals(TargetRefType.CAMERA, CAMERA_READ.getTargetRefType());
        Assertions.assertEquals(TargetRefType.CAMERA, CAMERA_WRITE.getTargetRefType());
        Assertions.assertEquals(TargetRefType.CAMERA, CAMERA_READ_WRITE.getTargetRefType());

        Assertions.assertEquals(TargetRefType.IMAGE, IMAGE_READ.getTargetRefType());
        Assertions.assertEquals(TargetRefType.IMAGE, IMAGE_WRITE.getTargetRefType());
        Assertions.assertEquals(TargetRefType.IMAGE, IMAGE_READ_WRITE.getTargetRefType());

        Assertions.assertEquals(TargetRefType.EVF_IMAGE, EVF_IMAGE_READ.getTargetRefType());
    }

    @Test
    void hasRead() {
        Assertions.assertFalse(CAMERA_WRITE.hasRead());
        Assertions.assertFalse(IMAGE_WRITE.hasRead());

        Assertions.assertTrue(CAMERA_READ.hasRead());
        Assertions.assertTrue(IMAGE_READ.hasRead());
        Assertions.assertTrue(EVF_IMAGE_READ.hasRead());
        Assertions.assertTrue(IMAGE_READ_WRITE.hasRead());
        Assertions.assertTrue(CAMERA_READ_WRITE.hasRead());
    }

    @Test
    void hasWrite() {
        Assertions.assertTrue(CAMERA_READ_WRITE.hasWrite());
        Assertions.assertTrue(CAMERA_WRITE.hasWrite());
        Assertions.assertTrue(IMAGE_READ_WRITE.hasWrite());
        Assertions.assertTrue(IMAGE_WRITE.hasWrite());

        Assertions.assertFalse(CAMERA_READ.hasWrite());
        Assertions.assertFalse(IMAGE_READ.hasWrite());
        Assertions.assertFalse(EVF_IMAGE_READ.hasWrite());
    }

    @Test
    void equals() {
        Assertions.assertEquals(CAMERA_READ, CAMERA_READ);

        Assertions.assertNotEquals(CAMERA_WRITE, CAMERA_READ_WRITE);
        Assertions.assertNotEquals(CAMERA_READ, CAMERA_WRITE);

        Assertions.assertNotEquals(IMAGE_READ, IMAGE_READ_WRITE);
        Assertions.assertNotEquals(IMAGE_WRITE, IMAGE_READ_WRITE);
        Assertions.assertNotEquals(CAMERA_READ, IMAGE_WRITE);

        Assertions.assertNotEquals(CAMERA_READ, EVF_IMAGE_READ);
    }

    @Test
    void hashCodeOk() {
        final TargetRefAccessType cameraRead = CAMERA_READ;
        Assertions.assertEquals(Objects.hash(cameraRead.getTargetRefType(), cameraRead.hasRead(), cameraRead.hasWrite()), cameraRead.hashCode());
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(CAMERA_READ.toString());
    }
}
