package org.blackdread.cameraframework.api.constant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsBatteryQualityTest extends ConstantValueFromLibraryTest<EdsBatteryQuality>{

    @Override
    EdsBatteryQuality getOneEnumValue() {
        return EdsBatteryQuality.kEdsBatteryQuality_Half;
    }

    @Override
    EdsBatteryQuality[] getAllEnumValues() {
        return EdsBatteryQuality.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
