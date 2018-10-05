package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsETTL2ModeTest extends ConstantValueFromLibraryTest<EdsETTL2Mode> {

    @Override
    EdsETTL2Mode getOneEnumValue() {
        return EdsETTL2Mode.kEdsETTL2ModeAverage;
    }

    @Override
    EdsETTL2Mode[] getAllEnumValues() {
        return EdsETTL2Mode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsETTL2Mode.class;
    }
}
