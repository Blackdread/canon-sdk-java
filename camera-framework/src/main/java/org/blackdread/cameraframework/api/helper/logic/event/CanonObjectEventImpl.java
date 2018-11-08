package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;

import java.util.Objects;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CanonObjectEventImpl implements CanonObjectEvent {

    private final EdsCameraRef cameraRef;
    private final EdsObjectEvent objectEvent;
    private final EdsBaseRef baseRef;

    public CanonObjectEventImpl(final EdsCameraRef cameraRef, final EdsObjectEvent objectEvent, final EdsBaseRef baseRef) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        this.objectEvent = Objects.requireNonNull(objectEvent);
        this.baseRef = Objects.requireNonNull(baseRef);
    }

    @Override
    public EdsCameraRef getCameraRef() {
        return cameraRef;
    }

    @Override
    public EdsObjectEvent getObjectEvent() {
        return objectEvent;
    }

    @Override
    public EdsBaseRef getBaseRef() {
        return baseRef;
    }
}
