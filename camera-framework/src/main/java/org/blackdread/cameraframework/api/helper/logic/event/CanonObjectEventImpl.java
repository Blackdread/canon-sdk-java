/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;

import java.util.Objects;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CanonObjectEventImpl implements CanonObjectEvent {

    private final EdsCameraRef cameraRef;
    private final EdsObjectEvent objectEvent;
    private final EdsBaseRef baseRef;

    public CanonObjectEventImpl(final EdsCameraRef cameraRef, final EdsObjectEvent objectEvent, final EdsBaseRef baseRef) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        this.objectEvent = Objects.requireNonNull(objectEvent);
        this.baseRef = Objects.requireNonNull(baseRef);
    }

    @Override
    public EdsCameraRef getCameraRef() {
        return cameraRef;
    }

    @Override
    public EdsObjectEvent getObjectEvent() {
        return objectEvent;
    }

    @Override
    public EdsBaseRef getBaseRef() {
        return baseRef;
    }

    @Override
    public String toString() {
        return "CanonObjectEventImpl{" +
            "cameraRef=" + cameraRef +
            ", objectEvent=" + objectEvent +
            ", baseRef=" + baseRef +
            '}';
    }
}
