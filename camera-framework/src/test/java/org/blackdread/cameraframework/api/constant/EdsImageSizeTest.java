package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsImageSizeTest extends ConstantValueFromLibraryTest<EdsImageSize> {

    @Override
    EdsImageSize getOneEnumValue() {
        return EdsImageSize.kEdsImageSize_Small;
    }

    @Override
    EdsImageSize[] getAllEnumValues() {
        return EdsImageSize.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsImageSize.class;
    }
}
