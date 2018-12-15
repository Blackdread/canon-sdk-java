package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * Open session of a camera
 *
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class OpenSessionCommand extends AbstractCanonCommand<EdsCameraRef> {

    private final OpenSessionOption option;

    public OpenSessionCommand() {
        this.option = null;
    }

    public OpenSessionCommand(final OpenSessionOption option) {
        this.option = option;
    }

    public OpenSessionCommand(final OpenSessionCommand toCopy) {
        super(toCopy);
        this.option = toCopy.option;
    }

    @Override
    protected EdsCameraRef runInternal() {
        if (option == null) {
            return CanonFactory.cameraLogic().openSession();
        } else {
            return CanonFactory.cameraLogic().openSession(option);
        }
    }
}
