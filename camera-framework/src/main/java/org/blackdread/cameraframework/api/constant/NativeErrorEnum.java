package org.blackdread.cameraframework.api.constant;

import org.blackdread.cameraframework.exception.EdsdkErrorException;

/**
 * <p>Created on 2018/10/04.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface NativeErrorEnum<V> extends NativeEnum<V> {

    /**
     * @return type of error
     */
    ErrorType getErrorType();

    /**
     * @param <T> type of exception
     * @return exception to throw if this error is encountered
     */
    <T extends EdsdkErrorException> T getException();

}
