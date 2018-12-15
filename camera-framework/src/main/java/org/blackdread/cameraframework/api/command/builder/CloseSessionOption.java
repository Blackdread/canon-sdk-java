package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.camera.CanonCamera;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.Optional;

/**
 * Options for closing session commands, instead of having many different methods (with different parameters) to close session, we provide one wrapper of options
 * <p>Created on 2018/12/15.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@Immutable
public class CloseSessionOption {

    /**
     * Camera ref to close session
     */
    private final EdsCameraRef cameraRef;

    /**
     * If true then on success to close session, will release camera ref
     */
    private final boolean releaseCameraRef;

    /**
     * Camera to set cameraRef to null on success of close session.
     * <br>
     * May be null
     */
    private final CanonCamera camera;

    protected CloseSessionOption(final EdsCameraRef cameraRef, final boolean releaseCameraRef, final CanonCamera camera) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        this.releaseCameraRef = releaseCameraRef;
        this.camera = camera;
    }

    public EdsCameraRef getCameraRef() {
        return cameraRef;
    }

    public boolean isReleaseCameraRef() {
        return releaseCameraRef;
    }

    public Optional<CanonCamera> getCamera() {
        return Optional.ofNullable(camera);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CloseSessionOption that = (CloseSessionOption) o;
        return releaseCameraRef == that.releaseCameraRef &&
            Objects.equals(cameraRef, that.cameraRef) &&
            Objects.equals(camera, that.camera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cameraRef, releaseCameraRef, camera);
    }

    @Override
    public String toString() {
        return "CloseSessionOption{" +
            "cameraRef=" + cameraRef +
            ", releaseCameraRef=" + releaseCameraRef +
            ", camera=" + camera +
            '}';
    }
}
