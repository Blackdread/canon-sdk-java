package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsProgressOptionTest extends ConstantValueFromLibraryTest<EdsProgressOption> {

    @Override
    EdsProgressOption getOneEnumValue() {
        return EdsProgressOption.kEdsProgressOption_NoReport;
    }

    @Override
    EdsProgressOption[] getAllEnumValues() {
        return EdsProgressOption.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsProgressOption.class;
    }
}
