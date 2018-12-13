package org.blackdread.cameraframework.exception.error.function;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class EdsdkFunctionParameterErrorException extends EdsdkErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkFunctionParameterErrorException(final EdsdkError edsdkError) {
        super(edsdkError);
    }

    public EdsdkFunctionParameterErrorException(final String message, final EdsdkError edsdkError) {
        super(message, edsdkError);
    }

}
