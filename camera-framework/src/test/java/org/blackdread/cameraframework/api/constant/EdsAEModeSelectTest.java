package org.blackdread.cameraframework.api.constant;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class EdsAEModeSelectTest extends ConstantValueFromLibraryTest<EdsAEModeSelect> {

    @Override
    EdsAEModeSelect getOneEnumValue() {
        return EdsAEModeSelect.kEdsAEModeSelect_Custom1;
    }

    @Override
    EdsAEModeSelect[] getAllEnumValues() {
        return EdsAEModeSelect.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
