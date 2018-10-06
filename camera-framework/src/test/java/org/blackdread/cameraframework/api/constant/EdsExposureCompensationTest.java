package org.blackdread.cameraframework.api.constant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsExposureCompensationTest extends ConstantValueFromLibraryTest<EdsExposureCompensation> {

    @Override
    EdsExposureCompensation getOneEnumValue() {
        return EdsExposureCompensation.kEdsExposureCompensation_0;
    }

    @Override
    EdsExposureCompensation[] getAllEnumValues() {
        return EdsExposureCompensation.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
