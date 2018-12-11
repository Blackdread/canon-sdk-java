package org.blackdread.cameraframework.exception;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/11/20.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkDeviceBusyErrorException extends EdsdkDeviceErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkDeviceBusyErrorException() {
        super(EdsdkError.EDS_ERR_DEVICE_BUSY);
    }

    public EdsdkDeviceBusyErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_DEVICE_BUSY);
    }

}
