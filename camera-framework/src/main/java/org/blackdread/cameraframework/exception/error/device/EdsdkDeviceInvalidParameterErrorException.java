package org.blackdread.cameraframework.exception.error.device;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkDeviceInvalidParameterErrorException extends EdsdkDeviceErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkDeviceInvalidParameterErrorException() {
        super(EdsdkError.EDS_ERR_DEVICE_INVALID_PARAMETER.description(), EdsdkError.EDS_ERR_DEVICE_INVALID_PARAMETER);
    }

    public EdsdkDeviceInvalidParameterErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_DEVICE_INVALID_PARAMETER);
    }

}
