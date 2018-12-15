package org.blackdread.cameraframework.api.command.builder;

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
     * Get a camera by the index.
     * <br>
     * To get the first camera then just pass an index of 0.
     * <br>
     * Null means another option is used.
     */
    private final Integer cameraByIndex;

    /**
     * Get a camera by its serial number (BodyIDEx). Very useful when serial is known in advance
     * <br>
     * Null means another option is used.
     */
    private final String cameraBySerialNumber;

    // TODO can add option to automatically register events for that camera on success

    protected OpenSessionOption(final Integer cameraByIndex, final String cameraBySerialNumber) {
        this.cameraByIndex = cameraByIndex;
        this.cameraBySerialNumber = cameraBySerialNumber;
    }

    public Optional<Integer> getCameraByIndex() {
        return Optional.ofNullable(cameraByIndex);
    }

    public Optional<String> getCameraBySerialNumber() {
        return Optional.ofNullable(cameraBySerialNumber);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OpenSessionOption that = (OpenSessionOption) o;
        return Objects.equals(cameraByIndex, that.cameraByIndex) &&
            Objects.equals(cameraBySerialNumber, that.cameraBySerialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cameraByIndex, cameraBySerialNumber);
    }

    @Override
    public String toString() {
        return "OpenSessionOption{" +
            "cameraByIndex=" + cameraByIndex +
            ", cameraBySerialNumber='" + cameraBySerialNumber + '\'' +
            '}';
    }
}
