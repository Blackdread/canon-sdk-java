package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 07/10/2018</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsPropertyEventTest extends ConstantValueFromLibraryTest<EdsPropertyEvent> {

    @Override
    EdsPropertyEvent getOneEnumValue() {
        return EdsPropertyEvent.kEdsPropertyEvent_All;
    }

    @Override
    EdsPropertyEvent[] getAllEnumValues() {
        return EdsPropertyEvent.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.class;
    }


    @Override
    int countClassField() {
        return (int) Arrays.stream(EdsdkLibrary.class.getDeclaredFields())
            .filter(name -> name.getName().startsWith("kEdsPropertyEvent_"))
            .count();
    }
}
