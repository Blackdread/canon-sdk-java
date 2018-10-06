package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsPropertyIDTest extends ConstantValueFromLibraryTest<EdsPropertyID> {

    @Override
    EdsPropertyID getOneEnumValue() {
        return EdsPropertyID.kEdsPropID_AEMode;
    }

    @Override
    EdsPropertyID[] getAllEnumValues() {
        return EdsPropertyID.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.class;
    }

    @Override
    int countClassField() {
        return (int) Arrays.stream(EdsdkLibrary.class.getDeclaredFields())
            .filter(name -> name.getName().startsWith("kEdsPropID_"))
            .count();
    }
}
