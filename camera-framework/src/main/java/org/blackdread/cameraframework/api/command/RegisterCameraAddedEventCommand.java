package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class RegisterCameraAddedEventCommand extends EventCommand {

    public RegisterCameraAddedEventCommand() {
    }

    public RegisterCameraAddedEventCommand(final RegisterCameraAddedEventCommand toCopy) {
        super(toCopy);
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraAddedEventLogic().registerCameraAddedEvent();
        return null;
    }

}
