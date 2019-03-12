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
package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.camera.CanonCamera;

/**
 * <p>Created on 2018/12/14.</p>
 *
 * @author Yoann CAPLAIN
 * @see OpenSessionOption OpenSessionOption for documentation on options
 * @since 1.0.0
 */
public class OpenSessionOptionBuilder {

    private boolean openSessionOnly = false;
    private EdsCameraRef cameraRef;
    private CanonCamera camera;
    private Integer cameraByIndex = 0;
    private String cameraBySerialNumber;
    private boolean registerObjectEvent = true;
    private boolean registerPropertyEvent = true;
    private boolean registerStateEvent = true;

    public OpenSessionOptionBuilder setOpenSessionOnly(final boolean openSessionOnly) {
        this.openSessionOnly = openSessionOnly;
        return this;
    }

    public OpenSessionOptionBuilder setCameraRef(final EdsCameraRef cameraRef) {
        this.cameraRef = cameraRef;
        return this;
    }

    public OpenSessionOptionBuilder setCamera(final CanonCamera camera) {
        this.camera = camera;
        return this;
    }

    public OpenSessionOptionBuilder setCameraByIndex(final Integer cameraByIndex) {
        this.cameraByIndex = cameraByIndex;
        this.cameraBySerialNumber = null;
        return this;
    }

    public OpenSessionOptionBuilder setCameraBySerialNumber(final String cameraBySerialNumber) {
        this.cameraBySerialNumber = cameraBySerialNumber;
        this.cameraByIndex = null;
        return this;
    }

    public OpenSessionOptionBuilder setRegisterObjectEvent(final boolean registerObjectEvent) {
        this.registerObjectEvent = registerObjectEvent;
        return this;
    }

    public OpenSessionOptionBuilder setRegisterPropertyEvent(final boolean registerPropertyEvent) {
        this.registerPropertyEvent = registerPropertyEvent;
        return this;
    }

    public OpenSessionOptionBuilder setRegisterStateEvent(final boolean registerStateEvent) {
        this.registerStateEvent = registerStateEvent;
        return this;
    }

    public OpenSessionOption build() {
// This should be set explicitly by user
//        if (camera != null && cameraRef == null) {
//            camera.getCameraRef().ifPresent(this::setCameraRef);
//        }
        validate();
        return new OpenSessionOption(openSessionOnly, cameraRef, camera, cameraByIndex, cameraBySerialNumber, registerObjectEvent, registerPropertyEvent, registerStateEvent);
    }

    private void validate() {
        if (openSessionOnly && cameraRef == null) {
            throw new IllegalStateException("To only open session, cameraRef must be provided");
        }
        if (!openSessionOnly && cameraRef != null) {
            throw new IllegalStateException("CameraRef is only used when openSessionOnly is true");
        }
        if (openSessionOnly && (registerObjectEvent || registerPropertyEvent || registerStateEvent)) {
            throw new IllegalStateException("Default logic register events only for new EdsCameraRef taken from camera");
        }
        if (cameraByIndex == null && cameraBySerialNumber == null) {
            throw new IllegalStateException("At least one option must be chosen");
        }
        if (cameraByIndex != null && cameraBySerialNumber != null) {
            throw new IllegalStateException("cameraByIndex and cameraBySerialNumber cannot be both non-null");
        }
    }
}
