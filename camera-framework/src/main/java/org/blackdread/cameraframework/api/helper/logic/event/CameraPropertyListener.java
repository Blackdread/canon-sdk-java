package org.blackdread.cameraframework.api.helper.logic.event;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
@FunctionalInterface
public interface CameraPropertyListener {

    void handleCameraPropertyEvent(final CanonPropertyEvent event);

}
