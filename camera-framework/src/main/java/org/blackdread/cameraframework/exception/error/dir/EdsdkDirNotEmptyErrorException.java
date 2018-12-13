package org.blackdread.cameraframework.exception.error.dir;

import org.blackdread.cameraframework.api.constant.EdsdkError;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class EdsdkDirNotEmptyErrorException extends EdsdkDirectoryErrorException {

    private static final long serialVersionUID = 1L;

    public EdsdkDirNotEmptyErrorException() {
        super(EdsdkError.EDS_ERR_DIR_NOT_EMPTY.description(), EdsdkError.EDS_ERR_DIR_NOT_EMPTY);
    }

    public EdsdkDirNotEmptyErrorException(final String message) {
        super(message, EdsdkError.EDS_ERR_DIR_NOT_EMPTY);
    }

}
