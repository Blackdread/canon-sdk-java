package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * Close session of a camera
 *
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CloseSessionCommand extends AbstractCanonCommand<Void> {

    private final EdsCameraRef cameraRef;

    /**
     * @param cameraRef camera to close session
     */
    public CloseSessionCommand(final EdsCameraRef cameraRef) {
        this.cameraRef = cameraRef;
    }

    public CloseSessionCommand(final CloseSessionCommand toCopy) {
        super(toCopy);
        this.cameraRef = toCopy.cameraRef;
    }

    @Override
    protected Void runInternal() {
        final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsCloseSession(cameraRef));
        if (edsdkError != EdsdkError.EDS_ERR_OK) {
            throw edsdkError.getException();
        }
        return null;
    }
}
