package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsSeekOriginTest extends ConstantValueFromLibraryTest<EdsSeekOrigin> {

    @Override
    EdsSeekOrigin getOneEnumValue() {
        return EdsSeekOrigin.kEdsSeek_Cur;
    }

    @Override
    EdsSeekOrigin[] getAllEnumValues() {
        return EdsSeekOrigin.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsSeekOrigin.class;
    }
}
