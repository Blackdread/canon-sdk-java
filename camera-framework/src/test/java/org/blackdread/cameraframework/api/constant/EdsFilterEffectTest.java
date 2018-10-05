package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsFilterEffectTest extends ConstantValueFromLibraryTest<EdsFilterEffect> {

    @Override
    EdsFilterEffect getOneEnumValue() {
        return EdsFilterEffect.kEdsFilterEffect_None;
    }

    @Override
    EdsFilterEffect[] getAllEnumValues() {
        return EdsFilterEffect.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsFilterEffect.class;
    }
}
