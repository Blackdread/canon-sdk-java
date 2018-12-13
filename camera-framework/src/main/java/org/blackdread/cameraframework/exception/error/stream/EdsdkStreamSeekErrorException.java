package org.blackdread.cameraframework.exception.error.stream;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkStreamSeekErrorException extends EdsdkStreamErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkStreamSeekErrorException() {
        super(EdsdkError.EDS_ERR_STREAM_SEEK_ERROR.description(), EdsdkError.EDS_ERR_STREAM_SEEK_ERROR);
    }

    public EdsdkStreamSeekErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_STREAM_SEEK_ERROR);
    }

}
