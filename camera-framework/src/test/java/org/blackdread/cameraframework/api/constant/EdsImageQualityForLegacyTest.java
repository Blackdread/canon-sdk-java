package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsImageQualityForLegacyTest extends ConstantValueFromLibraryTest<EdsImageQualityForLegacy> {

    @Override
    EdsImageQualityForLegacy getOneEnumValue() {
        return EdsImageQualityForLegacy.kEdsImageQualityForLegacy_LJ;
    }

    @Override
    EdsImageQualityForLegacy[] getAllEnumValues() {
        return EdsImageQualityForLegacy.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsImageQualityForLegacy.class;
    }
}
