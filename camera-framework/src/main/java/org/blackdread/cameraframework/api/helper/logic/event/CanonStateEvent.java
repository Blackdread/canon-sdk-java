package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.cameraframework.api.constant.EdsStateEvent;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CanonStateEvent extends CanonEvent {

    // TODO add cameraRef

    /**
     * @return event type that occurred
     */
    EdsStateEvent getStateEvent();

    /*
     *
     * @return pointer to the event data. The content designated here varies depending on the property type
     */
//    long getEventData();
}
