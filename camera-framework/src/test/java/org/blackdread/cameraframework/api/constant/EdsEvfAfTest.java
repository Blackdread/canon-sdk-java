package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfAfTest extends ConstantValueFromLibraryTest<EdsEvfAf> {

    @Override
    EdsEvfAf getOneEnumValue() {
        return EdsEvfAf.kEdsCameraCommand_EvfAf_OFF;
    }

    @Override
    EdsEvfAf[] getAllEnumValues() {
        return EdsEvfAf.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsEvfAf.class;
    }
}
