package org.blackdread.cameraframework.exception.error.picture;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkPictureMirrorUpErrorException extends EdsdkTakePictureErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkPictureMirrorUpErrorException() {
        super(EdsdkError.EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG.description(), EdsdkError.EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG);
    }

    public EdsdkPictureMirrorUpErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG);
    }

}
