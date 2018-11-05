package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CameraObjectEventLogic {

    /**
     * Register the handler for the camera object event of the camera passed.
     * <p>Can be called many times for same camera, has no side effects. Nevertheless it makes more sense to call it once per camera.</p>
     *
     * @param cameraRef camera to register a handler with
     */
    void registerCameraObjectEvent(final EdsCameraRef cameraRef);

    /**
     * Unregister the handler for the camera object event of the camera passed.
     * <p>Can be called many times for same camera, has no side effects</p>
     * <p>Listeners are not removed</p>
     *
     * @param cameraRef camera to unregister a handler from
     */
    void unregisterCameraObjectEvent(final EdsCameraRef cameraRef);

    /**
     * Add a listener that will be notified of camera object events from all cameras.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param listener listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraObjectListener(final CameraObjectListener listener);

    /**
     * Add a listener that will be notified of camera object events of the camera passed.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param listener listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraObjectListener(final EdsCameraRef cameraRef, final CameraObjectListener listener);

    /**
     * Nothing happens if this listener is not found
     *
     * @param listener listener to remove
     */
    void removeCameraObjectListener(final CameraObjectListener listener);

    /**
     * Clear all listeners from all camera.
     * Callback is still registered, call {@link #unregisterCameraObjectEvent(EdsCameraRef)} if needed
     */
    void clearCameraObjectListeners();

    /**
     * Clear all listeners of the camera passed.
     * Callback is still registered, call {@link #unregisterCameraObjectEvent(EdsCameraRef)} if needed.
     * Nothing happens if this camera is not found.
     *
     * @param cameraRef camera to remove listeners registered
     */
    void clearCameraObjectListeners(final EdsCameraRef cameraRef);

}
