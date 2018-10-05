package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsImageTypeTest extends ConstantValueFromLibraryTest<EdsImageType> {

    @Override
    EdsImageType getOneEnumValue() {
        return EdsImageType.kEdsImageType_Jpeg;
    }

    @Override
    EdsImageType[] getAllEnumValues() {
        return EdsImageType.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsImageType.class;
    }
}
