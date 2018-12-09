package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.command.TargetRefType;

import java.util.Optional;

/**
 * Provide methods to set and get target ref of a command.
 * It removes the need to define constructors with multiple parameters and this parameter can be changed later on by Camera or other manager class.
 * <p>Created on 2018/12/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface TargetRefCommand {

    void setTargetRef(final EdsBaseRef targetRef);

    /**
     * @return target ref of command
     */
    Optional<EdsBaseRef> getTargetRef();

    /**
     * @return type of target ref
     * @throws IllegalStateException if targetRef has not been set yet
     */
    TargetRefType getTargetRefType();

}
