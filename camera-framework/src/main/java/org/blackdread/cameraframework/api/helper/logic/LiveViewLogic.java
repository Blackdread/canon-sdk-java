/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import java.awt.image.BufferedImage;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface LiveViewLogic {

    // TODO After a live view and sdk terminated, usually the camera will be in a busy status -> cannot interact with camera by hand but we can still shoot, re-start live view etc by sending commands from PC. If cable disconnected then camera is not busy anymore

    /**
     * Enable the live view, and allow to further set settings of live view (Evf)
     * <br>
     * A property change event notification is issued from the camera if property settings were made successfully.
     * <br>
     * Can start downloading live view image once the property change notification arrives (for output device set to PC).
     * <br>
     * Camera will return <b>BUSY error</b> for about 1-2 seconds, as the camera is "starting" live view.
     *
     * @param camera ref of camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void enableLiveView(final EdsCameraRef camera) {
        CanonFactory.propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode, 1L);
    }

    /**
     * Disable the live view, and further set settings of live view (Evf) might not be possible until re-enabled
     * <br>
     * A property change event notification is issued from the camera if property settings were made successfully.
     * <br>
     * Camera will return <b>BUSY error</b> for about 1-2 seconds, as the camera is "stopping" live view.
     *
     * @param camera ref of camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void disableLiveView(final EdsCameraRef camera) {
        CanonFactory.propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode, 0L);
    }

    /**
     * Start the live view (auto enable live view), default output to PC.
     * <br>
     * A property change event notification is issued from the camera if property settings were made successfully.
     * <br>
     * Can start downloading live view image once the property change notification arrives.
     * <br>
     * Camera will return <b>BUSY error</b> for about 1-2 seconds, as the camera is "starting" live view.
     *
     * @param camera ref of camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void beginLiveView(final EdsCameraRef camera) {
        beginLiveView(camera, EdsEvfOutputDevice.kEdsEvfOutputDevice_PC);
    }

    /**
     * Start the live view (auto enable live view)
     * <br>
     * A property change event notification is issued from the camera if property settings were made successfully.
     * <br>
     * Can start downloading live view image once the property change notification arrives (for output device set to PC).
     * <br>
     * Camera will return <b>BUSY error</b> for about 1-2 seconds, as the camera is "starting" live view.
     *
     * @param camera             ref of camera
     * @param edsEvfOutputDevice device that will receive the live view
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void beginLiveView(final EdsCameraRef camera, final EdsEvfOutputDevice edsEvfOutputDevice);

    /**
     * Stop the live view (auto disable live view)
     * <br>
     * A property change event notification is issued from the camera if property settings were made successfully.
     * <br>
     * Camera will return <b>BUSY error</b> for about 1-2 seconds, as the camera is "stopping" live view.
     *
     * @param camera ref of camera
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void endLiveView(final EdsCameraRef camera);

    /**
     * If is not enabled then {@link #enableLiveView} can be used
     *
     * @param camera ref of camera
     * @return true if live view is allowed to be <b>enabled</b>
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean isLiveViewEnabled(final EdsCameraRef camera);

    /**
     * If is not enabled then {@link #enableLiveView} can be used
     * <br>
     * Important: swallow any exception returned by camera
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
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    BufferedImage getLiveViewImage(final EdsCameraRef camera);

    /**
     * Current image from live view as a byte array
     *
     * @param camera ref of camera
     * @return live view image as a byte array
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    byte[] getLiveViewImageBuffer(final EdsCameraRef camera);

    /**
     * Create a stream and image ref of the live view.
     * <br>
     * Do not forget to <b>release both refs once not used</b>, best is to use <b>try-with-resource</b> with the result of this method
     *
     * @param camera ref of camera
     * @return live view image reference
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    LiveViewReference getLiveViewImageReference(final EdsCameraRef camera);

}
