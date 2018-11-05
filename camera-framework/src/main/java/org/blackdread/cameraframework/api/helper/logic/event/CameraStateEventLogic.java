package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CameraStateEventLogic {

    /**
     * Register the handler for the camera state event of the camera passed.
     * <p>Can be called many times for same camera, has no side effects. Nevertheless it makes more sense to call it once per camera.</p>
     *
     * @param cameraRef camera to register a handler with
     */
    void registerCameraStateEvent(final EdsCameraRef cameraRef);

    /**
     * Unregister the handler for the camera state event of the camera passed.
     * <p>Can be called many times for same camera, has no side effects</p>
     * <p>Listeners are not removed</p>
     *
     * @param cameraRef camera to unregister a handler from
     */
    void unregisterCameraStateEvent(final EdsCameraRef cameraRef);

    /**
     * Add a listener that will be notified of camera state events from all cameras.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param listener listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraStateListener(final CameraStateListener listener);

    /**
     * Add a listener that will be notified of camera state events of the camera passed.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param listener listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraStateListener(final EdsCameraRef cameraRef, final CameraObjectListener listener);

    /**
     * Nothing happens if this listener is not found
     *
     * @param listener listener to remove
     */
    void removeCameraStateListener(final CameraStateListener listener);

    /**
     * Clear all listeners from all camera.
     * Callback is still registered, call {@link #unregisterCameraStateEvent(EdsCameraRef)} if needed
     */
    void clearCameraStateListeners();

    /**
     * Clear all listeners of the camera passed.
     * Callback is still registered, call {@link #unregisterCameraStateEvent(EdsCameraRef)} if needed.
     * Nothing happens if this camera is not found.
     *
     * @param cameraRef camera to remove listeners registered
     */
    void clearCameraStateListeners(final EdsCameraRef cameraRef);

}
