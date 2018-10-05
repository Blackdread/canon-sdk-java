package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsShutterButtonTest extends ConstantValueFromLibraryTest<EdsShutterButton> {

    @Override
    EdsShutterButton getOneEnumValue() {
        return EdsShutterButton.kEdsCameraCommand_ShutterButton_Completely;
    }

    @Override
    EdsShutterButton[] getAllEnumValues() {
        return EdsShutterButton.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsShutterButton.class;
    }
}
