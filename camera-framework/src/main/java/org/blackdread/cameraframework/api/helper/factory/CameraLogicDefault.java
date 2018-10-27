package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class CameraLogicDefault implements CameraLogic {

    @Override
    public EdsdkError setCapacity(final EdsdkLibrary.EdsCameraRef ref) {
        return null;
    }

    @Override
    public EdsdkError setCapacity(final EdsdkLibrary.EdsCameraRef ref, final int capacity) {
        return null;
    }

    @Override
    public boolean isMirrorLockupEnabled(final EdsdkLibrary.EdsCameraRef camera) {
        return false;
    }

}
