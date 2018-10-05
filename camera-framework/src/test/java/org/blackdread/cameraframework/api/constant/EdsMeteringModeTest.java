package org.blackdread.cameraframework.api.constant;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsMeteringModeTest extends ConstantValueFromLibraryTest<EdsMeteringMode> {

    @Override
    EdsMeteringMode getOneEnumValue() {
        return EdsMeteringMode.kEdsMeteringMode_Partial;
    }

    @Override
    EdsMeteringMode[] getAllEnumValues() {
        return EdsMeteringMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
