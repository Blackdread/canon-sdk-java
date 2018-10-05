package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsStorageTypeTest extends ConstantValueFromLibraryTest<EdsStorageType> {

    @Override
    EdsStorageType getOneEnumValue() {
        return EdsStorageType.kEdsStorageType_SD;
    }

    @Override
    EdsStorageType[] getAllEnumValues() {
        return EdsStorageType.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsStorageType.class;
    }
}
