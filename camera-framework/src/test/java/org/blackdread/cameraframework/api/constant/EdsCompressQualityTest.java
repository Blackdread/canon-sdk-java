package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsCompressQualityTest extends ConstantValueFromLibraryTest<EdsCompressQuality> {

    @Override
    EdsCompressQuality getOneEnumValue() {
        return EdsCompressQuality.kEdsCompressQuality_Fine;
    }

    @Override
    EdsCompressQuality[] getAllEnumValues() {
        return EdsCompressQuality.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsCompressQuality.class;
    }
}
