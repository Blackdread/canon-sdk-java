package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsShutterButton;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraLogic;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface ShootLogic {

    /**
     * <p>
     * Old command to make camera shoot.
     * Depending on current camera setting, it will do AF or not.
     * <br>
     * If AF is "enabled" then it might fail to do it and camera will return an error {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_TAKE_PICTURE_AF_NG}
     * </p>
     * <br>
     * Supported by EOS-1D Mark III, EOS 40D, EOS-1Ds Mark III, EOS DIGITAL REBEL Xsi/450D/ Kiss X2, EOS DIGITAL REBEL XS/ 1000D/ KISS F.
     * <br>
     * For EOS 50D or EOS 5D Mark II or later, prefer to use kEdsCameraCommand_PressShutterButton, {@link #shootAF(EdsCameraRef)} or {@link #shootNoAF(EdsCameraRef)}
     * <br>
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void shootV0(final EdsCameraRef camera) {
        cameraLogic().sendCommand(camera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
    }

    /**
     * Depending on current camera setting, it will do AF or not.
     * <br>
     * If AF is "enabled" then it might fail to do it and camera will return an error {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_TAKE_PICTURE_AF_NG}
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void shootAF(final EdsCameraRef camera) {
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

    /**
     * Execute shoot without AF
     *
     * @param camera ref of camera
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void shootNoAF(final EdsCameraRef camera) {
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

}
