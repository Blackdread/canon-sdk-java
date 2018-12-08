package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.command.TargetRefType;

import java.util.Optional;

/**
 * <p>Created on 2018/12/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface TargetRefCommand {

    void setTargetRef(final EdsBaseRef targetRef);

    Optional<EdsBaseRef> getTargetRef();

    TargetRefType getTargetRefType();

}
