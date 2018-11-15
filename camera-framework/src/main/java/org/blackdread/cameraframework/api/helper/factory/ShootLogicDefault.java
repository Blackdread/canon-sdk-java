package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class ShootLogicDefault implements ShootLogic {

    protected ShootLogicDefault() {
    }

    public void shoot(final EdsCameraRef camera) {
        CanonFactory.edsdkLibrary().EdsSendCommand(camera, new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value()), new NativeLong(EdsdkLibrary.EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely));
        CanonFactory.edsdkLibrary().EdsSendCommand(camera, new NativeLong(EdsCameraCommand.kEdsCameraCommand_PressShutterButton.value()), new NativeLong(EdsdkLibrary.EdsShutterButton.kEdsCameraCommand_ShutterButton_OFF));

    }

    /*
    EdsError err;
    err = EdsSendCommand(camera , kEdsCameraCommand_PressShutterButton, kEdsCameraCommand_ShutterButton_Completely);
    err = EdsSendCommand(camera, kEdsCameraCommand_PressShutterButton, kEdsCameraCommand_ShutterButton_OFF);
    return err;
     */
}
