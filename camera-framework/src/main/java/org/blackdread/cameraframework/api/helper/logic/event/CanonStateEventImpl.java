package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;

import java.util.Objects;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CanonStateEventImpl implements CanonStateEvent {

    private final EdsCameraRef cameraRef;
    private final EdsStateEvent stateEvent;

    public CanonStateEventImpl(final EdsCameraRef cameraRef, final EdsStateEvent stateEvent) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        this.stateEvent = Objects.requireNonNull(stateEvent);
    }

    @Override
    public EdsCameraRef getCameraRef() {
        return cameraRef;
    }

    @Override
    public EdsStateEvent getStateEvent() {
        return stateEvent;
    }
}
