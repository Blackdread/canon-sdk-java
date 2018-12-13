package org.blackdread.cameraframework.exception.error.stream;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkStreamTellErrorException extends EdsdkStreamErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkStreamTellErrorException() {
        super(EdsdkError.EDS_ERR_STREAM_TELL_ERROR.description(), EdsdkError.EDS_ERR_STREAM_TELL_ERROR);
    }

    public EdsdkStreamTellErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_STREAM_TELL_ERROR);
    }

}
