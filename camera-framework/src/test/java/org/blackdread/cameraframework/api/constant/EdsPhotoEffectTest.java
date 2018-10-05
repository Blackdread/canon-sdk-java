package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsPhotoEffectTest extends ConstantValueFromLibraryTest<EdsPhotoEffect> {

    @Override
    EdsPhotoEffect getOneEnumValue() {
        return EdsPhotoEffect.kEdsPhotoEffect_Monochrome;
    }

    @Override
    EdsPhotoEffect[] getAllEnumValues() {
        return EdsPhotoEffect.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsPhotoEffect.class;
    }
}
