package org.blackdread.cameraframework.exception.error.device;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkDeviceNotFoundErrorException extends EdsdkDeviceErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkDeviceNotFoundErrorException() {
        super(EdsdkError.EDS_ERR_DEVICE_NOT_FOUND.description(), EdsdkError.EDS_ERR_DEVICE_NOT_FOUND);
    }

    public EdsdkDeviceNotFoundErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_DEVICE_NOT_FOUND);
    }

}
