package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsImageQualityTest extends ConstantValueFromLibraryTest<EdsImageQuality> {

    @Override
    EdsImageQuality getOneEnumValue() {
        return EdsImageQuality.EdsImageQuality_LJF;
    }

    @Override
    EdsImageQuality[] getAllEnumValues() {
        return EdsImageQuality.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsImageQuality.class;
    }
}
