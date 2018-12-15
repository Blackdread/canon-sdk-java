package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.camera.CanonCamera;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;
import java.util.Optional;

/**
 * Options for opening session commands, instead of having many different methods (with different parameters) to open session, we provide one wrapper of options
 * <p>Created on 2018/12/14.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@Immutable
public class OpenSessionOption {

    public static final OpenSessionOption DEFAULT_OPEN_SESSION_OPTION = new OpenSessionOptionBuilder()
        .build();

    /**
     * If true it only opens the session with the library
     */
    private final boolean openSessionOnly;

    /**
     * EdsCameraRef to be used only with {@code openSessionOnly} to allow to open session if session was previously closed but no release has been done.
     */
    private EdsCameraRef cameraRef;

    /**
     * If not null then will set the EdsCameraRef that is returned by command.
     * <br>
     * As a convenience so no need to use command decorator to intercept result or to manually get from the command.
     */
    private final CanonCamera camera;

    /**
     * Get a camera by the index.
     * <br>
     * To get the first camera then just pass an index of 0.
     * <br>
     * Null means another option is used.
     */
    private final Integer cameraByIndex;

    /**
     * Get a camera by its serial number (BodyIDEx). Very useful when serial is known in advance.
     * <br>
     * Null means another option is used.
     */
    private final String cameraBySerialNumber;

    private final boolean registerObjectEvent;

    private final boolean registerPropertyEvent;

    private final boolean registerStateEvent;

    protected OpenSessionOption(final boolean openSessionOnly, final EdsCameraRef cameraRef, @Nullable final CanonCamera camera, @Nullable final Integer cameraByIndex, @Nullable final String cameraBySerialNumber, final boolean registerObjectEvent, final boolean registerPropertyEvent, final boolean registerStateEvent) {
        this.openSessionOnly = openSessionOnly;
        this.cameraRef = cameraRef;
        this.camera = camera;
        this.cameraByIndex = cameraByIndex;
        this.cameraBySerialNumber = cameraBySerialNumber;
        this.registerObjectEvent = registerObjectEvent;
        this.registerPropertyEvent = registerPropertyEvent;
        this.registerStateEvent = registerStateEvent;
    }

    public boolean isOpenSessionOnly() {
        return openSessionOnly;
    }

    public Optional<EdsCameraRef> getCameraRef() {
        return Optional.ofNullable(cameraRef);
    }

    public Optional<CanonCamera> getCamera() {
        return Optional.ofNullable(camera);
    }

    public Optional<Integer> getCameraByIndex() {
        return Optional.ofNullable(cameraByIndex);
    }

    public Optional<String> getCameraBySerialNumber() {
        return Optional.ofNullable(cameraBySerialNumber);
    }

    public boolean isRegisterObjectEvent() {
        return registerObjectEvent;
    }

    public boolean isRegisterPropertyEvent() {
        return registerPropertyEvent;
    }

    public boolean isRegisterStateEvent() {
        return registerStateEvent;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OpenSessionOption that = (OpenSessionOption) o;
        return openSessionOnly == that.openSessionOnly &&
            registerObjectEvent == that.registerObjectEvent &&
            registerPropertyEvent == that.registerPropertyEvent &&
            registerStateEvent == that.registerStateEvent &&
            Objects.equals(cameraRef, that.cameraRef) &&
            Objects.equals(camera, that.camera) &&
            Objects.equals(cameraByIndex, that.cameraByIndex) &&
            Objects.equals(cameraBySerialNumber, that.cameraBySerialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openSessionOnly, cameraRef, camera, cameraByIndex, cameraBySerialNumber, registerObjectEvent, registerPropertyEvent, registerStateEvent);
    }

    @Override
    public String toString() {
        return "OpenSessionOption{" +
            "openSessionOnly=" + openSessionOnly +
            ", cameraRef=" + cameraRef +
            ", camera=" + camera +
            ", cameraByIndex=" + cameraByIndex +
            ", cameraBySerialNumber='" + cameraBySerialNumber + '\'' +
            ", registerObjectEvent=" + registerObjectEvent +
            ", registerPropertyEvent=" + registerPropertyEvent +
            ", registerStateEvent=" + registerStateEvent +
            '}';
    }
}
