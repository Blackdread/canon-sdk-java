package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.constant.EdsdkError;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CameraLogic {

    EdsdkError setCapacity(final EdsCameraRef ref);

    EdsdkError setCapacity(final EdsCameraRef ref, final int capacity);

    boolean isMirrorLockupEnabled(final EdsCameraRef camera);
}
