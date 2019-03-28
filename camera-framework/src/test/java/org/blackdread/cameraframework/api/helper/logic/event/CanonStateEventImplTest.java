package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonStateEventImplTest {

    private EdsCameraRef cameraRef;

    private EdsStateEvent stateEvent;

    private Long eventData;

    private CanonStateEventImpl canonStateEvent;

    @BeforeEach
    void setUp() {
        cameraRef = new EdsCameraRef();
        stateEvent = EdsStateEvent.kEdsStateEvent_AfResult;
        eventData = 0L;

        canonStateEvent = new CanonStateEventImpl(cameraRef, stateEvent, eventData);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructorThrowsOnNull() {
        Assertions.assertThrows(NullPointerException.class, () -> new CanonStateEventImpl(null, stateEvent, eventData));
        Assertions.assertThrows(NullPointerException.class, () -> new CanonStateEventImpl(cameraRef, null, eventData));
    }

    @Test
    void getCameraRef() {
        final EdsCameraRef ref = canonStateEvent.getCameraRef();
        Assertions.assertNotNull(ref);
        Assertions.assertEquals(cameraRef, ref);
        Assertions.assertSame(cameraRef, canonStateEvent.getCameraRef());
    }

    @Test
    void getObjectEvent() {
        final EdsStateEvent event = canonStateEvent.getStateEvent();
        Assertions.assertNotNull(event);
        Assertions.assertEquals(stateEvent, event);
        Assertions.assertSame(stateEvent, canonStateEvent.getStateEvent());
    }

    @Test
    void getEventData() {
        final long data = canonStateEvent.getEventData();
        Assertions.assertEquals(eventData, data);
    }

    @Test
    void toStringOk() {
        Assertions.assertNotNull(canonStateEvent.toString());
    }
}
