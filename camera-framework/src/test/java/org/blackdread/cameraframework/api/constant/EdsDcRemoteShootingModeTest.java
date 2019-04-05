package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/04/05.</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsDcRemoteShootingModeTest extends ConstantValueFromLibraryTest<EdsDcRemoteShootingMode> {


    @Override
    EdsDcRemoteShootingMode getOneEnumValue() {
        return EdsDcRemoteShootingMode.kDcRemoteShootingModeStart;
    }

    @Override
    EdsDcRemoteShootingMode[] getAllEnumValues() {
        return EdsDcRemoteShootingMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsDcRemoteShootingMode.class;
    }

}
