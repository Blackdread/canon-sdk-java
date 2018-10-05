package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsSaveToTest extends ConstantValueFromLibraryTest<EdsSaveTo> {

    @Override
    EdsSaveTo getOneEnumValue() {
        return EdsSaveTo.kEdsSaveTo_Both;
    }

    @Override
    EdsSaveTo[] getAllEnumValues() {
        return EdsSaveTo.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsSaveTo.class;
    }
}
