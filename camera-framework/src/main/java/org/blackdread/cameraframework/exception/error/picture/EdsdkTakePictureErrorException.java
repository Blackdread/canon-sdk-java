package org.blackdread.cameraframework.exception.error.picture;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class EdsdkTakePictureErrorException extends EdsdkErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkTakePictureErrorException(final EdsdkError edsdkError) {
        super(edsdkError);
    }

    public EdsdkTakePictureErrorException(final String message, final EdsdkError edsdkError) {
        super(message, edsdkError);
    }

}
