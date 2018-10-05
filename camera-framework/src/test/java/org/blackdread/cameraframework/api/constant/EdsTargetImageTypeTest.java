package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsTargetImageTypeTest extends ConstantValueFromLibraryTest<EdsTargetImageType> {

    @Override
    EdsTargetImageType getOneEnumValue() {
        return EdsTargetImageType.kEdsTargetImageType_Jpeg;
    }

    @Override
    EdsTargetImageType[] getAllEnumValues() {
        return EdsTargetImageType.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsTargetImageType.class;
    }
}
