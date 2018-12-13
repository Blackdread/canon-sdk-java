package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CameraPropertyEventLogic {

    /**
     * Register the handler for the camera property event of the camera passed.
     * <p>Can be called many times for same camera, has no side effects. Nevertheless it makes more sense to call it once per camera.</p>
     * <br>
     * <p>Due to Canon multi-thread model, it might be preferable to call this method from thread that initialized the SDK</p>
     *
     * @param cameraRef camera to register a handler with
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void registerCameraPropertyEvent(final EdsCameraRef cameraRef);

    /**
     * Unregister the handler for the camera property event of the camera passed.
     * <p>Can be called many times for same camera, has no side effects</p>
     * <p>Listeners are not removed</p>
     * <br>
     * <p>Due to Canon multi-thread model, it might be preferable to call this method from thread that initialized the SDK</p>
     *
     * @param cameraRef camera to unregister a handler from
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void unregisterCameraPropertyEvent(final EdsCameraRef cameraRef);

    /**
     * Add a listener that will be notified of camera property events from all cameras.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param listener listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraPropertyListener(final CameraPropertyListener listener);

    /**
     * Add a listener that will be notified of camera property events of the camera passed.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param cameraRef camera for which to listen event
     * @param listener  listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraPropertyListener(final EdsCameraRef cameraRef, final CameraPropertyListener listener);

    /**
     * Remove the listener from <b>all</b> notification lists.
     * <br>
     * Nothing happens if this listener is not found
     *
     * @param listener listener to remove
     */
    void removeCameraPropertyListener(final CameraPropertyListener listener);

    /**
     * Remove the listener from <b>only</b> notification list of this specific camera. It may still receive events if it was also added to notification list of any camera.
     * <br>
     * Nothing happens if this listener is not found
     *
     * @param cameraRef camera for which to remove listener
     * @param listener  listener to remove
     */
    void removeCameraPropertyListener(final EdsCameraRef cameraRef, final CameraPropertyListener listener);

    /**
     * Clear all listeners from all camera.
     * Callback is still registered, call {@link org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyEventLogic#unregisterCameraPropertyEvent(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef)} if needed
     */
    void clearCameraPropertyListeners();

    /**
     * Clear all listeners of the camera passed.
     * Callback is still registered, call {@link org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyEventLogic#unregisterCameraPropertyEvent(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef)} if needed.
     * Nothing happens if this camera is not found.
     *
     * @param cameraRef camera to remove listeners registered
     */
    void clearCameraPropertyListeners(final EdsCameraRef cameraRef);

}
