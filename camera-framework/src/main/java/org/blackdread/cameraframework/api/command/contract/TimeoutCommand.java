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

import java.time.Duration;
import java.util.Optional;

/**
 * A command should be interrupted if its execution exceeded timeout duration.
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface TimeoutCommand {

    /**
     * @return timeout of command
     */
    Optional<Duration> getTimeout();

    /**
     * As timeout is one of the most used option, it is provided directly without need of decorator (even if one is provided by the framework).
     * <br>
     * <p>If this method is called after command has started execution, there is no guarantee it will be taken into account (no exception is thrown)</p>
     *
     * @param timeout timeout to set
     */
    void setTimeout(final Duration timeout);

}
