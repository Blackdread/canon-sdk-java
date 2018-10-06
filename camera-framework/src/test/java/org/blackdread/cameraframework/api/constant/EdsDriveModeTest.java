package org.blackdread.cameraframework.api.constant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsDriveModeTest extends ConstantValueFromLibraryTest<EdsDriveMode>{

    @Override
    EdsDriveMode getOneEnumValue() {
        return EdsDriveMode.kEdsDriveMode_Continuous;
    }

    @Override
    EdsDriveMode[] getAllEnumValues() {
        return EdsDriveMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
