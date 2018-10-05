package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsBracketTest extends ConstantValueFromLibraryTest<EdsBracket> {

    @Override
    EdsBracket getOneEnumValue() {
        return EdsBracket.kEdsBracket_AEB;
    }

    @Override
    EdsBracket[] getAllEnumValues() {
        return EdsBracket.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsBracket.class;
    }
}
