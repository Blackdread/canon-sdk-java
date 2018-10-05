package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsDataTypeTest extends ConstantValueFromLibraryTest<EdsDataType> {

    @Override
    EdsDataType getOneEnumValue() {
        return EdsDataType.kEdsDataType_Int8;
    }

    @Override
    EdsDataType[] getAllEnumValues() {
        return EdsDataType.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsDataType.class;
    }
}
