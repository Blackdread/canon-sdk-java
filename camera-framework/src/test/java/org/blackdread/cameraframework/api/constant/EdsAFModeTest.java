package org.blackdread.cameraframework.api.constant;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsAFModeTest extends ConstantValueFromLibraryTest<EdsAFMode> {

    @Override
    EdsAFMode getOneEnumValue() {
        return EdsAFMode.kEdsAFMode_Manual;
    }

    @Override
    EdsAFMode[] getAllEnumValues() {
        return EdsAFMode.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
