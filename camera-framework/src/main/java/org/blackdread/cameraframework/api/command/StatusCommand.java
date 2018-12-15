package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class StatusCommand extends AbstractCanonCommand<Void> {

    private final EdsCameraStatusCommand edsCameraStatusCommand;

    public StatusCommand(final EdsCameraStatusCommand edsCameraStatusCommand) {
        this.edsCameraStatusCommand = edsCameraStatusCommand;
    }

    public StatusCommand(final StatusCommand toCopy) {
        super(toCopy);
        this.edsCameraStatusCommand = toCopy.edsCameraStatusCommand;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraLogic().sendStatusCommand((EdsCameraRef) getTargetRefInternal(), edsCameraStatusCommand);
        return null;
    }
}
