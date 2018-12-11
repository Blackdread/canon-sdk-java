package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * Open session of a camera
 *
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class OpenSessionCommand extends AbstractCanonCommand<EdsCameraRef> {

    // no other constructors, we use static methods to construct after

    public OpenSessionCommand(final OpenSessionCommand toCopy) {
        super(toCopy);
    }

    @Override
    protected EdsCameraRef runInternal() {

        return null;
    }
}
