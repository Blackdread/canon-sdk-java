package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.util.Objects;

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

    /**
     * May be null
     */
    private final CanonCamera camera;
    private final EdsCameraRef cameraRef;

    /**
     * @param cameraRef camera ref to close session
     */
    public CloseSessionCommand(final EdsCameraRef cameraRef) {
        this.camera = null;
        this.cameraRef = Objects.requireNonNull(cameraRef);
    }

    /**
     * @param camera    camera to set cameraRef to null on success of close session
     * @param cameraRef camera ref to close session
     */
    public CloseSessionCommand(final CanonCamera camera, final EdsCameraRef cameraRef) {
        this.camera = camera;
        this.cameraRef = Objects.requireNonNull(cameraRef);
    }

    public CloseSessionCommand(final CloseSessionCommand toCopy) {
        super(toCopy);
        this.camera = toCopy.camera;
        this.cameraRef = toCopy.cameraRef;
    }

    @Override
    protected Void runInternal() {
        final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsCloseSession(cameraRef));
        if (edsdkError != EdsdkError.EDS_ERR_OK) {
            throw edsdkError.getException();
        }
        if (camera != null) {
            camera.setCameraRef(null);
        }
        return null;
    }
}
