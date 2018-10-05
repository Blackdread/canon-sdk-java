package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfAFModeTest extends ConstantValueFromLibraryTest<EdsEvfAFMode> {

    @Override
    EdsEvfAFMode getOneEnumValue() {
        return EdsEvfAFMode.Evf_AFMode_Live;
    }

    @Override
    EdsEvfAFMode[] getAllEnumValues() {
        return EdsEvfAFMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsEvfAFMode.class;
    }
}
