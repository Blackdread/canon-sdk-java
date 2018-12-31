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
package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.cameraframework.api.command.CanonCommand;

/**
 * General contract to copy a command to be able to send it (re-send the original one but its copy).
 * <br>
 * <br>
 * <p>If instance on which copy() is called is a "normal" command meaning it has no decorators then it returns the copy (without decorators of course as there was none).
 * <br>
 * If instance is a decorator then user may use getRoot() from RootDecoratorCommand class to copy only the "normal" command without the decorators.
 * <br>
 * Otherwise decorators will be copied and applied to the returned command of copy() method</p>
 * <p>Created on 2018/12/03.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CopyCommand<R> {

    /**
     * Allow to re-send a command as a command cannot be re-sent without making a copy.
     * <p>Not all fields need to be copied</p>
     *
     * @return copied command
     */
    CanonCommand<R> copy();

}
