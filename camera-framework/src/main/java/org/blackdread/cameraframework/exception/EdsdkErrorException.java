package org.blackdread.cameraframework.exception;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * Based exception for errors from camera in {@link EdsdkError}
 *
 * <p>Created on 2018/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public class EdsdkErrorException extends EdsdkException {

    private final EdsdkError edsdkError;

    public EdsdkErrorException(final EdsdkError edsdkError) {
        super(edsdkError.description());
        this.edsdkError = edsdkError;
    }

    public EdsdkErrorException(final String message, final EdsdkError edsdkError) {
        super(message);
        this.edsdkError = edsdkError;
    }

    public final EdsdkError getEdsdkError() {
        return edsdkError;
    }
}
