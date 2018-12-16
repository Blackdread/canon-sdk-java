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
package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.TargetRefType;

import java.util.Optional;

/**
 * Provide methods to set and get target ref of a command.
 * It removes the need to define constructors with multiple parameters and this parameter can be changed later on by Camera or other manager class.
 * <p>Created on 2018/12/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface TargetRefCommand {

    /**
     * This is set by the camera at {@link org.blackdread.cameraframework.api.camera.CanonCamera#dispatchCommand(CanonCommand)} if not already set.
     * <br>
     * Implementation of {@link org.blackdread.cameraframework.api.helper.logic.CommandDispatcher} may also set this value but only if provided the target ref which is not in default implementation of framework.
     *
     * @param targetRef targetRef
     */
    void setTargetRef(final EdsBaseRef targetRef);

    /**
     * @return target ref of command
     */
    Optional<EdsBaseRef> getTargetRef();

    /**
     * Value is set only if {@link #setTargetRef(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef)} was called
     *
     * @return type of target ref
     * @throws IllegalStateException if targetRef has not been set yet
     */
    TargetRefType getTargetRefType();

}
