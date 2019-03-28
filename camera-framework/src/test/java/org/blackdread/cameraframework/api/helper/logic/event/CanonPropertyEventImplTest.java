package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonPropertyEventImplTest {

    private EdsCameraRef cameraRef;

    private EdsPropertyEvent propertyEvent;

    private EdsPropertyID propertyID;

    private Long inParam;

    private CanonPropertyEventImpl canonPropertyEvent;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsCameraRef();
        propertyEvent = EdsPropertyEvent.kEdsPropertyEvent_PropertyChanged;
        propertyID = EdsPropertyID.kEdsPropID_ISOSpeed;
        inParam = 0L;

        canonPropertyEvent = new CanonPropertyEventImpl(cameraRef, propertyEvent, propertyID, inParam);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructorThrowsOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> new CanonPropertyEventImpl(null, propertyEvent, propertyID, inParam));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonPropertyEventImpl(cameraRef, null, propertyID, inParam));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonPropertyEventImpl(cameraRef, propertyEvent, null, inParam));
    }

    @Test
    void getCameraRef() {
        final EdsCameraRef ref = canonPropertyEvent.getCameraRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(cameraRef, ref);
        Assertions.assertSame(cameraRef, canonPropertyEvent.getCameraRef());
    }

    @Test
    void getPropertyEvent() {
        final EdsPropertyEvent event = canonPropertyEvent.getPropertyEvent();
        Assertions.assertNotNull(event);
        Assertions.assertEquals(propertyEvent, event);
        Assertions.assertSame(propertyEvent, canonPropertyEvent.getPropertyEvent());
    }

    @Test
    void getPropertyId() {
        final EdsPropertyID id = canonPropertyEvent.getPropertyId();
        Assertions.assertNotNull(id);
        Assertions.assertEquals(propertyID, id);
        Assertions.assertSame(propertyID, canonPropertyEvent.getPropertyId());
    }

    @Test
    void getInParam() {
        final long param = canonPropertyEvent.getInParam();
        Assertions.assertEquals(inParam, param);
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(canonPropertyEvent.toString());
    }
}
