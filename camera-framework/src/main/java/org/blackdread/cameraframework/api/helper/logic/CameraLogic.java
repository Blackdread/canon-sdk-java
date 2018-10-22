package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.constant.EdsdkErrors;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CameraLogic {

    EdsdkErrors setCapacity(final EdsCameraRef ref);

    EdsdkErrors setCapacity(final EdsCameraRef ref, final int capacity);

    boolean isMirrorLockupEnabled(final EdsCameraRef camera);
}
