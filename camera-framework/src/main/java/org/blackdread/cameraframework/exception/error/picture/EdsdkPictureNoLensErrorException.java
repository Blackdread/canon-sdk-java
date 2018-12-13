package org.blackdread.cameraframework.exception.error.picture;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkPictureNoLensErrorException extends EdsdkTakePictureErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkPictureNoLensErrorException() {
        super(EdsdkError.EDS_ERR_TAKE_PICTURE_NO_LENS_NG.description(), EdsdkError.EDS_ERR_TAKE_PICTURE_NO_LENS_NG);
    }

    public EdsdkPictureNoLensErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_TAKE_PICTURE_NO_LENS_NG);
    }

}
