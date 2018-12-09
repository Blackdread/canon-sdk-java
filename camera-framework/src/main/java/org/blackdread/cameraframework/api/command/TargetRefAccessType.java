package org.blackdread.cameraframework.api.command;

import com.google.common.collect.ImmutableSet;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static org.blackdread.cameraframework.api.command.TargetRefType.*;

/**
 * <p>Created on 2018/12/08.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
public final class TargetRefAccessType {

    public static final TargetRefAccessType CAMERA_READ = new TargetRefAccessType(CAMERA, true, false);
    public static final TargetRefAccessType CAMERA_READ_WRITE = new TargetRefAccessType(CAMERA, true, true);
    public static final TargetRefAccessType CAMERA_WRITE = new TargetRefAccessType(CAMERA, false, true);

    public static final TargetRefAccessType IMAGE_READ = new TargetRefAccessType(IMAGE, true, false);
    public static final TargetRefAccessType IMAGE_READ_WRITE = new TargetRefAccessType(IMAGE, true, true);
    public static final TargetRefAccessType IMAGE_WRITE = new TargetRefAccessType(IMAGE, false, true);

    public static final TargetRefAccessType EVF_IMAGE_READ = new TargetRefAccessType(EVF_IMAGE, true, false);
//    public static final TargetRefAccessType EVF_IMAGE_READ_WRITE = new TargetRefAccessType(EVF_IMAGE, true, true);

    /*
     * Groups must have only one same TargetRefType, different constants exist to have: READ/READ_WRITE/WRITE for same type
     */

    public static final Set<TargetRefAccessType> GROUP_NONE = Collections.emptySet();

    public static final Set<TargetRefAccessType> GROUP_CAMERA_READ = ImmutableSet.of(CAMERA_READ);
    public static final Set<TargetRefAccessType> GROUP_CAMERA_READ_WRITE = ImmutableSet.of(CAMERA_READ_WRITE);
//    public static final Set<TargetRefAccessType> GROUP_CAMERA_WRITE = ImmutableSet.of(CAMERA_WRITE);

    public static final Set<TargetRefAccessType> GROUP_IMAGE_READ = ImmutableSet.of(IMAGE_READ);
    public static final Set<TargetRefAccessType> GROUP_IMAGE_READ_WRITE = ImmutableSet.of(IMAGE_READ_WRITE);
    public static final Set<TargetRefAccessType> GROUP_IMAGE_WRITE = ImmutableSet.of(IMAGE_WRITE);

    public static final Set<TargetRefAccessType> GROUP_EVF_IMAGE_READ = ImmutableSet.of(EVF_IMAGE_READ);

    public static final Set<TargetRefAccessType> GROUP_CAMERA_READ_IMAGE_READ = ImmutableSet.of(CAMERA_READ, IMAGE_READ);

    public static final Set<TargetRefAccessType> GROUP_CAMERA_READ_WRITE_IMAGE_READ = ImmutableSet.of(CAMERA_READ_WRITE, IMAGE_READ);
    public static final Set<TargetRefAccessType> GROUP_CAMERA_READ_WRITE_IMAGE_READ_WRITE = ImmutableSet.of(CAMERA_READ_WRITE, IMAGE_READ_WRITE);

    public static final Set<TargetRefAccessType> GROUP_CAMERA_WRITE_EVF_IMAGE_READ = ImmutableSet.of(CAMERA_WRITE, EVF_IMAGE_READ);

    private final TargetRefType targetRefType;

    private final boolean read;

    private final boolean write;

    private TargetRefAccessType(final TargetRefType targetRefType, final boolean read, final boolean write) {
        this.targetRefType = targetRefType;
        this.read = read;
        this.write = write;
    }

    public TargetRefType getTargetRefType() {
        return targetRefType;
    }

    public boolean hasRead() {
        return read;
    }

    public boolean hasWrite() {
        return write;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TargetRefAccessType that = (TargetRefAccessType) o;
        return read == that.read &&
            write == that.write &&
            targetRefType == that.targetRefType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetRefType, read, write);
    }

    @Override
    public String toString() {
        return "TargetRefAccessType{" +
            "targetRefType=" + targetRefType +
            ", read=" + read +
            ", write=" + write +
            '}';
    }
}
