package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.awt.image.BufferedImage;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * TODO After a live view and sdk terminated, usually the camera will be in a busy status -> cannot interact with camera by hand but we can still shoot, re-start live view etc by sending commands from PC. If cable disconnected then camera is not busy anymore
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface LiveViewLogic {

    /**
     * Enable the live view, and allow to further set settings of live view (Evf)
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void enableLiveView(final EdsCameraRef camera) {
        CanonFactory.propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode, 1L);
    }

    /**
     * Disable the live view, and further set settings of live view (Evf) might not be possible until re-enabled
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void disableLiveView(final EdsCameraRef camera) {
        CanonFactory.propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode, 0L);
    }

    default void beginLiveView(final EdsCameraRef camera) {
        beginLiveView(camera, EdsEvfOutputDevice.kEdsEvfOutputDevice_PC);
    }

    /**
     * Start the live view (auto enable live view)
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void beginLiveView(final EdsCameraRef camera, final EdsEvfOutputDevice edsEvfOutputDevice);

    /**
     * Stop the live view (auto disable live view)
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void endLiveView(final EdsCameraRef camera);

    /**
     * If is not enabled then {@link #enableLiveView} can be used
     *
     * @param camera ref of camera
     * @return true if live view is allowed to be <b>enabled</b>
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean isLiveViewEnabled(final EdsCameraRef camera);

    /**
     * If is not enabled then {@link #enableLiveView} can be used
     *
     * @param camera ref of camera
     * @return true if the camera is <b>transmitting</b> live view images
     */
    boolean isLiveViewEnabledByDownloadingOneImage(final EdsCameraRef camera);

    /**
     * Current image from live view as a BufferedImage
     *
     * @param camera ref of camera
     * @return live view image as a BufferedImage
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    BufferedImage getLiveViewImage(final EdsCameraRef camera);

    /**
     * Current image from live view as a byte array
     *
     * @param camera ref of camera
     * @return live view image as a byte array
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    byte[] getLiveViewImageBuffer(final EdsCameraRef camera);

    /**
     * Create a stream and image ref of the live view.
     * <br>
     * Do not forget to <b>release both refs once not used</b>, best is to use <b>try-with-resource</b> with the result of this method
     *
     * @param camera ref of camera
     * @return live view image reference
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    LiveViewReference getLiveViewImageReference(final EdsCameraRef camera);

}
