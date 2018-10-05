package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsTransferOptionTest extends ConstantValueFromLibraryTest<EdsTransferOption> {

    @Override
    EdsTransferOption getOneEnumValue() {
        return EdsTransferOption.kEdsTransferOption_ToDesktop;
    }

    @Override
    EdsTransferOption[] getAllEnumValues() {
        return EdsTransferOption.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsTransferOption.class;
    }
}
