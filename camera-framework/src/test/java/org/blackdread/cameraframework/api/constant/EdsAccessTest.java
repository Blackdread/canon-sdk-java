package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsAccessTest extends ConstantValueFromLibraryTest<EdsAccess> {

    @Override
    EdsAccess getOneEnumValue() {
        return EdsAccess.kEdsAccess_Read;
    }

    @Override
    EdsAccess[] getAllEnumValues() {
        return EdsAccess.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsAccess.class;
    }
}
