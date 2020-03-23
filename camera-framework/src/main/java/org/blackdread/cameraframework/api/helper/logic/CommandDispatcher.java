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
package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.CanonCommand;

/**
 * Dispatcher of commands of the framework.
 * <p>Created on 2018/11/03</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CommandDispatcher {

    /**
     * Schedule a command to be executed by dispatcher
     *
     * @param command command to execute asynchronously
     */
    void scheduleCommand(final CanonCommand<?> command);

    /**
     * Schedule a command to be executed by dispatcher
     *
     * @param owner   camera owner of this command, the owner is simply from which camera this command was created from or that target of command is that camera, dispatcher implementation may apply some logic with this information
     * @param command command to execute asynchronously
     */
    void scheduleCommand(final EdsCameraRef owner, final CanonCommand<?> command);

    /**
     * Returns true if the calling thread is the Dispatcher Thread.
     * Use this call to ensure that a given task is being executed
     * (or not being executed) on the Dispatcher Thread.
     *
     * @return true if running on the Dispatcher Thread
     * @since 1.1.0
     */
    boolean isDispatcherThread();

}
