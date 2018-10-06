package org.blackdread.cameraframework.api.constant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfHistogramStatusTest extends ConstantValueFromLibraryTest<EdsEvfHistogramStatus> {

    @Override
    EdsEvfHistogramStatus getOneEnumValue() {
        return EdsEvfHistogramStatus.kEdsEvfHistogramStatus_Hide;
    }

    @Override
    EdsEvfHistogramStatus[] getAllEnumValues() {
        return EdsEvfHistogramStatus.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return null;
    }
}
