package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsWhiteBalanceTest extends ConstantValueFromLibraryTest<EdsWhiteBalance> {

    @Override
    EdsWhiteBalance getOneEnumValue() {
        return EdsWhiteBalance.kEdsWhiteBalance_ColorTemp;
    }

    @Override
    EdsWhiteBalance[] getAllEnumValues() {
        return EdsWhiteBalance.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsWhiteBalance.class;
    }
}
