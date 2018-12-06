package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;

import java.util.Optional;

/**
 * <p>Created on 2018/12/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface TargetRefCommand {

    enum TargetRefType {
        CAMERA,
        IMAGE,
        EVF_IMAGE,
        VOLUME,
        DIRECTORY_ITEM
    }

    void setTargetRef(final EdsBaseRef targetRef);

    Optional<EdsBaseRef> getTargetRef();

    TargetRefType getTargetRefType();

}
