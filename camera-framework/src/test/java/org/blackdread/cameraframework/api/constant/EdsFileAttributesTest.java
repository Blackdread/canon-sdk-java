package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsFileAttributesTest extends ConstantValueFromLibraryTest<EdsFileAttributes> {

    @Override
    EdsFileAttributes getOneEnumValue() {
        return EdsFileAttributes.kEdsFileAttribute_Normal;
    }

    @Override
    EdsFileAttributes[] getAllEnumValues() {
        return EdsFileAttributes.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsFileAttributes.class;
    }
}
