package org.blackdread.cameraframework.api.helper.logic.event;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@FunctionalInterface
public interface CameraAddedListener {

    void handleCameraAddedEvent(final CanonEvent event);

}
