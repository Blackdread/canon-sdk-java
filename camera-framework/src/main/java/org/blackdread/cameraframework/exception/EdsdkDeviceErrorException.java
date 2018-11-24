package org.blackdread.cameraframework.exception;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/11/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class EdsdkDeviceErrorException extends EdsdkErrorException {

    public EdsdkDeviceErrorException(final EdsdkError edsdkError) {
        super(edsdkError);
    }

    public EdsdkDeviceErrorException(final String message, final EdsdkError edsdkError) {
        super(message, edsdkError);
    }

}
