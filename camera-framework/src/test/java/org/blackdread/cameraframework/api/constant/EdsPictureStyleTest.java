package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsPictureStyleTest extends ConstantValueFromLibraryTest<EdsPictureStyle> {

    @Override
    EdsPictureStyle getOneEnumValue() {
        return EdsPictureStyle.kEdsPictureStyle_Auto;
    }

    @Override
    EdsPictureStyle[] getAllEnumValues() {
        return EdsPictureStyle.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsPictureStyle.class;
    }
}
