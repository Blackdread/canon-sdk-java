package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class RegisterStateEventCommand extends EventCommand {

    private final EdsCameraRef cameraRef;

    public RegisterStateEventCommand(final EdsCameraRef cameraRef) {
        this.cameraRef = cameraRef;
    }

    public RegisterStateEventCommand(final RegisterStateEventCommand toCopy) {
        super(toCopy);
        this.cameraRef = toCopy.cameraRef;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraStateEventLogic().registerCameraStateEvent(cameraRef);
        return null;
    }
}
