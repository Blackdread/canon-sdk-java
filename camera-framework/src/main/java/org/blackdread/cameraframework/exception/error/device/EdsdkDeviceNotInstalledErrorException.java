package org.blackdread.cameraframework.exception.error.device;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkDeviceNotInstalledErrorException extends EdsdkDeviceErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkDeviceNotInstalledErrorException() {
        super(EdsdkError.EDS_ERR_DEVICE_NOT_INSTALLED.description(), EdsdkError.EDS_ERR_DEVICE_NOT_INSTALLED);
    }

    public EdsdkDeviceNotInstalledErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_DEVICE_NOT_INSTALLED);
    }

}
