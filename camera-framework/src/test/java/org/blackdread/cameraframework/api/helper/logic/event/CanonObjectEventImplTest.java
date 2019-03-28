package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonObjectEventImplTest {

    private EdsCameraRef cameraRef;

    private EdsObjectEvent objectEvent;

    private EdsBaseRef baseRef;

    private CanonObjectEventImpl canonObjectEvent;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsCameraRef();
        objectEvent = EdsObjectEvent.kEdsObjectEvent_DirItemCreated;
        baseRef = new EdsBaseRef();

        canonObjectEvent = new CanonObjectEventImpl(cameraRef, objectEvent, baseRef);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructorThrowsOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> new CanonObjectEventImpl(null, objectEvent, baseRef));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonObjectEventImpl(cameraRef, null, baseRef));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonObjectEventImpl(cameraRef, objectEvent, null));
    }

    @Test
    void getCameraRef() {
        final EdsCameraRef ref = canonObjectEvent.getCameraRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(cameraRef, ref);
        Assertions.assertSame(cameraRef, canonObjectEvent.getCameraRef());
    }

    @Test
    void getObjectEvent() {
        final EdsObjectEvent event = canonObjectEvent.getObjectEvent();
        Assertions.assertNotNull(event);
        Assertions.assertEquals(objectEvent, event);
        Assertions.assertSame(objectEvent, canonObjectEvent.getObjectEvent());
    }

    @Test
    void getBaseRef() {
        final EdsBaseRef ref = canonObjectEvent.getBaseRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(baseRef, ref);
        Assertions.assertSame(baseRef, canonObjectEvent.getBaseRef());
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(canonObjectEvent.toString());
    }
}
