package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/04/05.</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsDcStrobeTest extends ConstantValueFromLibraryTest<EdsDcStrobe> {


    @Override
    EdsDcStrobe getOneEnumValue() {
        return EdsDcStrobe.kEdsDcStrobeAuto;
    }

    @Override
    EdsDcStrobe[] getAllEnumValues() {
        return EdsDcStrobe.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsDcStrobe.class;
    }

}
