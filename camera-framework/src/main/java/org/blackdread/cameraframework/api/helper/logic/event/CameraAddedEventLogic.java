package org.blackdread.cameraframework.api.helper.logic.event;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CameraAddedEventLogic {

    /**
     * Register the handler for the camera added event of the library.
     * <p>Can be called many times, has no side effects. Nevertheless it makes more sense to call it once at the beginning of the program.</p>
     * <br>
     * <p>Due to Canon multi-thread model, it might be preferable to call this method from thread that initialized the SDK</p>
     *
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void registerCameraAddedEvent();

    /**
     * Add a listener that will be notified when a camera is physically connected to pc.
     * Caller must hold a strong reference to the listener as implementation use WeakReference.
     *
     * @param listener listener to add, must be held a strong reference as implementation keeps a weak reference to the listener
     */
    void addCameraAddedListener(final CameraAddedListener listener);

    /**
     * Nothing happens if this listener is not found
     *
     * @param listener listener to remove
     */
    void removeCameraAddedListener(final CameraAddedListener listener);

    /**
     * Clear all listeners
     */
    void clearCameraAddedListeners();

}
