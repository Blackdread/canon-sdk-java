package org.blackdread.cameraframework.api.constant;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsConstantTest extends ConstantValueFromLibraryTest<EdsConstant> {

    @Override
    EdsConstant getOneEnumValue() {
        return EdsConstant.EDS_MAX_NAME;
    }

    @Override
    EdsConstant[] getAllEnumValues() {
        return EdsConstant.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
