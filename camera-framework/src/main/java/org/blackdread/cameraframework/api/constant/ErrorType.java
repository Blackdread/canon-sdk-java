package org.blackdread.cameraframework.api.constant;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public enum ErrorType {
    /**
     * Only for {@link EdsdkError#EDS_ERR_OK}
     */
    NOT_ERROR,
    GENERAL_ERROR,
    FILE_ACCESS_ERROR,
    DIRECTORY_ERROR,
    PROPERTY_ERROR,
    FUNCTION_PARAMETER_ERROR,
    DEVICE_ERROR,
    STREAM_ERROR,
    COMMUNICATION_ERROR,
    CAMERA_UI_LOCK_UNLOCK_ERROR,
    STI_WIA_ERROR,
    OTHER_GENERAL_ERROR,
    PTP_ERROR,
    TAKE_PICTURE_ERROR
}
