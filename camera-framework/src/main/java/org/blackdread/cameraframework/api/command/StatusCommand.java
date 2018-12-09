package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public class StatusCommand extends AbstractCanonCommand<Void> {

    private final EdsCameraStatusCommand statusCommand;

    public StatusCommand(final EdsCameraStatusCommand statusCommand) {
        this.statusCommand = statusCommand;
    }

    public StatusCommand(final StatusCommand toCopy) {
        super(toCopy);
        this.statusCommand = toCopy.statusCommand;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraLogic().sendStatusCommand((EdsCameraRef) getTargetRefInternal(), statusCommand);
        return null;
    }
}
