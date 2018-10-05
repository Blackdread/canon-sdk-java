package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsColorSpaceTest extends ConstantValueFromLibraryTest<EdsColorSpace> {

    @Override
    EdsColorSpace getOneEnumValue() {
        return EdsColorSpace.kEdsColorSpace_sRGB;
    }

    @Override
    EdsColorSpace[] getAllEnumValues() {
        return EdsColorSpace.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsColorSpace.class;
    }
}
