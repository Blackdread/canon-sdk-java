package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.api.constant.EdsPictureStyle;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;

import java.util.List;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyDescLogic;

/**
 * <p>Created on 2018/11/18.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyDescShortcutLogic {

    /**
     * Indicates settings for each picture style.
     * This property is valid only for models supporting picture styles.
     *
     * @param camera ref of camera
     * @return settings for each picture style
     */
    default List<EdsPictureStyle> getPictureStyleDesc(final EdsCameraRef camera) {
        return propertyDescLogic().getPropertyDesc(camera, EdsPropertyID.kEdsPropID_PictureStyleDesc);
    }

    /**
     * Indicates settings for each picture style.
     * This property is valid only for models supporting picture styles.
     *
     * @param image ref of image
     * @return settings for each picture style
     */
    default List<EdsPictureStyle> getPictureStyleDesc(final EdsImageRef image) {
        return propertyDescLogic().getPropertyDesc(image, EdsPropertyID.kEdsPropID_PictureStyleDesc);
    }

}
