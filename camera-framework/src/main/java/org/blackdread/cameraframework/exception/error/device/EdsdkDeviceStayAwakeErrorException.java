package org.blackdread.cameraframework.exception.error.device;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkDeviceStayAwakeErrorException extends EdsdkDeviceErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkDeviceStayAwakeErrorException() {
        super(EdsdkError.EDS_ERR_DEVICE_STAY_AWAKE.description(), EdsdkError.EDS_ERR_DEVICE_STAY_AWAKE);
    }

    public EdsdkDeviceStayAwakeErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_DEVICE_STAY_AWAKE);
    }

}
