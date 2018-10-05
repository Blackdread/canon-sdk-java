package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsStroboModeTest extends ConstantValueFromLibraryTest<EdsStroboMode> {

    @Override
    EdsStroboMode getOneEnumValue() {
        return EdsStroboMode.kEdsStroboModeExternalManual;
    }

    @Override
    EdsStroboMode[] getAllEnumValues() {
        return EdsStroboMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsStroboMode.class;
    }
}
