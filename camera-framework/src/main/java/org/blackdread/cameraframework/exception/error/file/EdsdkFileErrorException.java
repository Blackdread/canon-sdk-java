package org.blackdread.cameraframework.exception.error.file;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class EdsdkFileErrorException extends EdsdkErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkFileErrorException(final EdsdkError edsdkError) {
        super(edsdkError);
    }

    public EdsdkFileErrorException(final String message, final EdsdkError edsdkError) {
        super(message, edsdkError);
    }

}
