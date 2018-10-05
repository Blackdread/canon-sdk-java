package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfDriveLensTest extends ConstantValueFromLibraryTest<EdsEvfDriveLens> {

    @Override
    EdsEvfDriveLens getOneEnumValue() {
        return EdsEvfDriveLens.kEdsEvfDriveLens_Far2;
    }

    @Override
    EdsEvfDriveLens[] getAllEnumValues() {
        return EdsEvfDriveLens.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsEvfDriveLens.class;
    }
}
