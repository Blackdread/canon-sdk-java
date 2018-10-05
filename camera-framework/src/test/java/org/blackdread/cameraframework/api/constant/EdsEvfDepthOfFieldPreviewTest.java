package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsEvfDepthOfFieldPreviewTest extends ConstantValueFromLibraryTest<EdsEvfDepthOfFieldPreview> {

    @Override
    EdsEvfDepthOfFieldPreview getOneEnumValue() {
        return EdsEvfDepthOfFieldPreview.kEdsEvfDepthOfFieldPreview_OFF;
    }

    @Override
    EdsEvfDepthOfFieldPreview[] getAllEnumValues() {
        return EdsEvfDepthOfFieldPreview.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsEvfDepthOfFieldPreview.class;
    }
}
