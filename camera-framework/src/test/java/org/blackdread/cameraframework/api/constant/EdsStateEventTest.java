package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;

/**
 * <p>Created on 2018/10/07</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsStateEventTest extends ConstantValueFromLibraryTest<EdsStateEvent> {

    @Override
    EdsStateEvent getOneEnumValue() {
        return EdsStateEvent.kEdsStateEvent_AfResult;
    }

    @Override
    EdsStateEvent[] getAllEnumValues() {
        return EdsStateEvent.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.class;
    }

    @Override
    int countClassField() {
        return (int) Arrays.stream(EdsdkLibrary.class.getDeclaredFields())
            .filter(name -> name.getName().startsWith("kEdsStateEvent_"))
            .count();
    }
}
