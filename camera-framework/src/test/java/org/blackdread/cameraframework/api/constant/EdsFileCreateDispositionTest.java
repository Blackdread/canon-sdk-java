package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsFileCreateDispositionTest extends ConstantValueFromLibraryTest<EdsFileCreateDisposition> {

    @Override
    EdsFileCreateDisposition getOneEnumValue() {
        return EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateNew;
    }

    @Override
    EdsFileCreateDisposition[] getAllEnumValues() {
        return EdsFileCreateDisposition.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsFileCreateDisposition.class;
    }
}
