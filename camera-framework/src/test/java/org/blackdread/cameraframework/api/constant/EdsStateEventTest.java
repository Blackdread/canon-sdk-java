package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 07/10/2018</p>
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
