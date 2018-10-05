package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsImageSourceTest extends ConstantValueFromLibraryTest<EdsImageSource> {

    @Override
    EdsImageSource getOneEnumValue() {
        return EdsImageSource.kEdsImageSrc_FullView;
    }

    @Override
    EdsImageSource[] getAllEnumValues() {
        return EdsImageSource.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsImageSource.class;
    }
}
