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
package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CameraCommand extends AbstractCanonCommand<Void> {

    private final EdsCameraCommand cameraCommand;

    private final long inParam;

    public CameraCommand(final EdsCameraCommand cameraCommand) {
        this.cameraCommand = cameraCommand;
        this.inParam = 0;
    }

    public CameraCommand(final EdsCameraCommand cameraCommand, final long inParam) {
        this.cameraCommand = cameraCommand;
        this.inParam = inParam;
    }

    public CameraCommand(final EdsCameraCommand cameraCommand, final NativeEnum<? extends Number> param) {
        this.cameraCommand = cameraCommand;
        this.inParam = param.value().longValue();
    }

    public CameraCommand(final CameraCommand toCopy) {
        super(toCopy);
        this.cameraCommand = toCopy.cameraCommand;
        this.inParam = toCopy.inParam;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraLogic().sendCommand((EdsCameraRef) getTargetRefInternal(), cameraCommand, inParam);
        return null;
    }
}
