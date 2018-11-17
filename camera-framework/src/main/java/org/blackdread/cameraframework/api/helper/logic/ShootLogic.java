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
     * </p>
     * <br>
     * Supported by EOS-1D Mark III, EOS 40D, EOS-1Ds Mark III, EOS DIGITAL REBEL Xsi/450D/ Kiss X2, EOS DIGITAL REBEL XS/ 1000D/ KISS F.
     * <br>
     * For EOS 50D or EOS 5D Mark II or later, prefer to use kEdsCameraCommand_PressShutterButton, {@link #shootAFv2(EdsCameraRef)} or {@link #shootNoAF(EdsCameraRef)}
     * <br>
     *
     * @param camera ref of camera
     */
    default void shootV0(final EdsCameraRef camera) {
        cameraLogic().sendCommand(camera, EdsCameraCommand.kEdsCameraCommand_TakePicture);
    }

    /**
     * Depending on current camera setting, it will do AF or not.
     *
     * @param camera ref of camera
     */
    default void shootAFv1(final EdsCameraRef camera) {
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely);
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

    default void shootAFv2(final EdsCameraRef camera) {


    }

    /**
     * Execute shoot
     *
     * @param camera ref of camera
     */
    default void shootNoAF(final EdsCameraRef camera) {
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely_NonAF);
        cameraLogic().sendCommand(camera, EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF);
    }

}
