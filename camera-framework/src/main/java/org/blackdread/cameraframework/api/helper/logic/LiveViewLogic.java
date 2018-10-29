package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;

import java.awt.image.BufferedImage;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface LiveViewLogic {

    /**
     * Start the live view
     *
     * @param camera ref of camera
     * @return true if successful to start live view
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean beginLiveView(final EdsCameraRef camera, final EdsEvfOutputDevice edsEvfOutputDevice);

    /**
     * Stop the live view
     *
     * @param camera ref of camera
     * @return true if successful to end live view
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean endLiveView(final EdsCameraRef camera);

    /**
     * @param camera ref of camera
     * @return true if live view is allowed to be <b>enabled</b>
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean isLiveViewEnabled(final EdsCameraRef camera);

    /**
     * @param camera ref of camera
     * @return true if the camera is <b>transmitting</b> live view images
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean isLiveViewEnabledByDownloadingOneImage(final EdsCameraRef camera);

    /**
     * @param camera ref of camera
     * @return
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    EdsBaseRef.ByReference[] getLiveViewImageReference(final EdsCameraRef camera);

    /**
     * @param camera ref of camera
     * @return
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    BufferedImage getLiveViewImage(final EdsCameraRef camera);

    /**
     * @param camera ref of camera
     * @return
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    byte[] getLiveViewImageBuffer(final EdsCameraRef camera);
}
