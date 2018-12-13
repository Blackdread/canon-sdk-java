package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class UnRegisterPropertyEventCommand extends EventCommand {

    private final EdsCameraRef cameraRef;

    public UnRegisterPropertyEventCommand(final EdsCameraRef cameraRef) {
        this.cameraRef = cameraRef;
    }

    public UnRegisterPropertyEventCommand(final UnRegisterPropertyEventCommand toCopy) {
        super(toCopy);
        this.cameraRef = toCopy.cameraRef;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraPropertyEventLogic().unregisterCameraPropertyEvent(cameraRef);
        return null;
    }
}
