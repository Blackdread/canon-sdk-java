/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
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
