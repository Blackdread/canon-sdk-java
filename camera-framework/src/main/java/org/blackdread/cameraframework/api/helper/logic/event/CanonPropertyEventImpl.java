package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;

import java.util.Objects;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CanonPropertyEventImpl implements CanonPropertyEvent {

    private final EdsCameraRef cameraRef;
    private final EdsPropertyEvent propertyEvent;
    private final EdsPropertyID propertyID;
    private final long inParam;

    public CanonPropertyEventImpl(final EdsCameraRef cameraRef, final EdsPropertyEvent propertyEvent, final EdsPropertyID propertyID, final long inParam) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        this.propertyEvent = Objects.requireNonNull(propertyEvent);
        this.propertyID = Objects.requireNonNull(propertyID);
        this.inParam = inParam;
    }

    @Override
    public EdsCameraRef getCameraRef() {
        return cameraRef;
    }

    @Override
    public EdsPropertyEvent getPropertyEvent() {
        return propertyEvent;
    }

    @Override
    public EdsPropertyID getPropertyId() {
        return propertyID;
    }

    @Override
    public long getInParam() {
        return inParam;
    }

    @Override
    public String toString() {
        return "CanonPropertyEventImpl{" +
            "cameraRef=" + cameraRef +
            ", propertyEvent=" + propertyEvent +
            ", propertyID=" + propertyID +
            ", inParam=" + inParam +
            '}';
    }
}
