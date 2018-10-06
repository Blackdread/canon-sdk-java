package org.blackdread.cameraframework.api.constant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsAvTest extends ConstantValueFromLibraryTest<EdsAv> {

    @Override
    EdsAv getOneEnumValue() {
        return EdsAv.kEdsAv_1;
    }

    @Override
    EdsAv[] getAllEnumValues() {
        return EdsAv.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
