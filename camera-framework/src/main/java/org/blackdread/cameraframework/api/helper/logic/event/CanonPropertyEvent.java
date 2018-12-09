package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CanonPropertyEvent extends CanonEvent {

    /**
     * @return camera from which event is
     */
    EdsCameraRef getCameraRef();

    /**
     * @return event type that occurred
     */
    EdsPropertyEvent getPropertyEvent();

    /**
     * @return the property ID created by the event
     */
    EdsPropertyID getPropertyId();

    /**
     * Used to identify information created by the event for custom function (CF) properties or other properties that have multiple items of information.
     *
     * @return inParam from camera
     */
    long getInParam();

}
