package org.blackdread.cameraframework.api.constant;

import org.blackdread.cameraframework.exception.EdsdkErrorException;

/**
 * <p>Created on 2018/10/04.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface NativeErrorEnum<V> extends NativeEnum<V> {

    /**
     * @param <T>
     * @return
     * @deprecated Not yet implemented neither fully defined design
     */
    <T extends EdsdkErrorException> T getException();

}
