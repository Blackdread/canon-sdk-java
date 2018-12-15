package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.camera.CanonCamera;

import java.util.Objects;

/**
 * <p>Created on 2018/12/15.</p>
 *
 * @author Yoann CAPLAIN
 * @see CloseSessionOption CloseSessionOption for documentation on options
 * @since 1.0.0
 */
public class CloseSessionOptionBuilder {
    private EdsCameraRef cameraRef;
    private boolean releaseCameraRef = true;
    private CanonCamera camera;

    public CloseSessionOptionBuilder setCameraRef(final EdsCameraRef cameraRef) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        return this;
    }

    public CloseSessionOptionBuilder setReleaseCameraRef(final boolean releaseCameraRef) {
        this.releaseCameraRef = releaseCameraRef;
        return this;
    }

    public CloseSessionOptionBuilder setCamera(final CanonCamera camera) {
        this.camera = camera;
        return this;
    }

    public CloseSessionOption build() {
        validate();
        return new CloseSessionOption(cameraRef, releaseCameraRef, camera);
    }


    private void validate() {
        if (cameraRef == null) {
            throw new IllegalStateException("CameraRef must not be null");
        }
    }
}
