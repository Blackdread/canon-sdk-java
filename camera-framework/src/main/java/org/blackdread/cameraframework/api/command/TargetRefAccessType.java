/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
 * @since 1.0.0
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
