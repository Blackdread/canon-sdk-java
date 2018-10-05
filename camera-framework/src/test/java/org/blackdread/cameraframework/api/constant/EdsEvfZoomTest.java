package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfZoomTest extends ConstantValueFromLibraryTest<EdsEvfZoom> {

    @Override
    EdsEvfZoom getOneEnumValue() {
        return EdsEvfZoom.kEdsEvfZoom_Fit;
    }

    @Override
    EdsEvfZoom[] getAllEnumValues() {
        return EdsEvfZoom.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsEvfZoom.class;
    }
}
