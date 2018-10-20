package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsPropertyDesc;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.NativeEnum;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyDescLogic {

    // want different API
    @Deprecated
    NativeEnum<?> getPropertyDesc(final EdsCameraRef camera, final EdsPropertyID property);

    EdsPropertyDesc getPropertyDesc(final EdsBaseRef ref, final EdsPropertyID property);

}
