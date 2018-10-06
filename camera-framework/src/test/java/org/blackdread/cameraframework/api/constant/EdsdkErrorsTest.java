package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsdkErrorsTest extends ConstantValueFromLibraryTest<EdsdkErrors> {

    @Override
    EdsdkErrors getOneEnumValue() {
        return EdsdkErrors.EDS_ERR_OK;
    }

    @Override
    EdsdkErrors[] getAllEnumValues() {
        return EdsdkErrors.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.class;
    }

    @Override
    int countClassField() {
        return (int) Arrays.stream(EdsdkLibrary.class.getDeclaredFields())
            .filter(name -> name.getName().startsWith("EDS_ERR_"))
            .count();
    }
}
