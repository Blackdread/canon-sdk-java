package org.blackdread.cameraframework.api.constant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsTvTest extends ConstantValueFromLibraryTest<EdsTv> {

    @Override
    EdsTv getOneEnumValue() {
        return EdsTv.kEdsTv_0_3;
    }

    @Override
    EdsTv[] getAllEnumValues() {
        return EdsTv.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
