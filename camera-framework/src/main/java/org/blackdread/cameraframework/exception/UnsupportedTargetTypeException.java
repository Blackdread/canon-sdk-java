package org.blackdread.cameraframework.exception;

import org.blackdread.cameraframework.api.command.TargetRefType;

/**
 * Exception when a command is executed but target ref type is not supported by command
 * <p>Created on 2018/12/11.</p>
 *
 * @author Yoann CAPLAIN
 */
public class UnsupportedTargetTypeException extends EdsdkException {

    private final TargetRefType targetRefType;

    public UnsupportedTargetTypeException(final TargetRefType targetRefType) {
        super("Unsupported target type for command");
        this.targetRefType = targetRefType;
    }
}
