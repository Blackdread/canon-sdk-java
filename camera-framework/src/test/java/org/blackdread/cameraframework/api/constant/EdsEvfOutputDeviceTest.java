package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfOutputDeviceTest extends ConstantValueFromLibraryTest<EdsEvfOutputDevice> {

    @Override
    EdsEvfOutputDevice getOneEnumValue() {
        return EdsEvfOutputDevice.kEdsEvfOutputDevice_TFT;
    }

    @Override
    EdsEvfOutputDevice[] getAllEnumValues() {
        return EdsEvfOutputDevice.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsEvfOutputDevice.class;
    }

}
