package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsTonigEffectTest extends ConstantValueFromLibraryTest<EdsTonigEffect> {

    @Override
    EdsTonigEffect getOneEnumValue() {
        return EdsTonigEffect.kEdsTonigEffect_Blue;
    }

    @Override
    EdsTonigEffect[] getAllEnumValues() {
        return EdsTonigEffect.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsTonigEffect.class;
    }
}
