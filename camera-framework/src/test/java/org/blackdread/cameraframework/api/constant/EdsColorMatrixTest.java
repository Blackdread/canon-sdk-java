package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsColorMatrixTest extends ConstantValueFromLibraryTest<EdsColorMatrix> {

    @Override
    EdsColorMatrix getOneEnumValue() {
        return EdsColorMatrix.kEdsColorMatrix_1;
    }

    @Override
    EdsColorMatrix[] getAllEnumValues() {
        return EdsColorMatrix.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsColorMatrix.class;
    }
}
