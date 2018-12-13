package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class UnRegisterStateEventCommand extends EventCommand {

    private final EdsCameraRef cameraRef;

    public UnRegisterStateEventCommand(final EdsCameraRef cameraRef) {
        this.cameraRef = cameraRef;
    }

    public UnRegisterStateEventCommand(final UnRegisterStateEventCommand toCopy) {
        super(toCopy);
        this.cameraRef = toCopy.cameraRef;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraStateEventLogic().unregisterCameraStateEvent(cameraRef);
        return null;
    }
}
