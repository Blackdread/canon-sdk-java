package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/04/05.</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsDcLensBarrelStateTest extends ConstantValueFromLibraryTest<EdsDcLensBarrelState> {


    @Override
    EdsDcLensBarrelState getOneEnumValue() {
        return EdsDcLensBarrelState.kDcLensBarrelStateInner;
    }

    @Override
    EdsDcLensBarrelState[] getAllEnumValues() {
        return EdsDcLensBarrelState.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsDcLensBarrelState.class;
    }

}
