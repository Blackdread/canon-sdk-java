package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;

/**
 * <p>Created on 2018/10/07</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsObjectEventTest extends ConstantValueFromLibraryTest<EdsObjectEvent>{

    @Override
    EdsObjectEvent getOneEnumValue() {
        return EdsObjectEvent.kEdsObjectEvent_All;
    }

    @Override
    EdsObjectEvent[] getAllEnumValues() {
        return EdsObjectEvent.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.class;
    }

    @Override
    int countClassField() {
        return (int) Arrays.stream(EdsdkLibrary.class.getDeclaredFields())
            .filter(name -> name.getName().startsWith("kEdsObjectEvent_"))
            .count();
    }
}
