package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsBatteryLevel2Test extends ConstantValueFromLibraryTest<EdsBatteryLevel2> {

    @Override
    EdsBatteryLevel2 getOneEnumValue() {
        return EdsBatteryLevel2.kEdsBatteryLevel2_AC;
    }

    @Override
    EdsBatteryLevel2[] getAllEnumValues() {
        return EdsBatteryLevel2.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsBatteryLevel2.class;
    }
}
