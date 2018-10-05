package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsAEModeTest extends ConstantValueFromLibraryTest<EdsAEMode> {

    @Override
    EdsAEMode getOneEnumValue() {
        return EdsAEMode.kEdsAEMode_Av;
    }

    @Override
    EdsAEMode[] getAllEnumValues() {
        return EdsAEMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsAEMode.class;
    }

}
