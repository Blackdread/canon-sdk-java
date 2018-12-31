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

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * Make the dispatcher fetch events
 *
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class FetchEventCommand extends AbstractCanonCommand<Void> {

    private final boolean throwOnGetEventError;
    private final int fetchCount;

    public FetchEventCommand() {
        this.throwOnGetEventError = true;
        this.fetchCount = 1;
    }

    public FetchEventCommand(final boolean throwOnGetEventError, final int fetchCount) {
        if (fetchCount <= 0) {
            throw new IllegalArgumentException("Fetch count must > 0");
        }
        this.throwOnGetEventError = throwOnGetEventError;
        this.fetchCount = fetchCount;
    }

    public FetchEventCommand(final FetchEventCommand toCopy) {
        super(toCopy);
        this.throwOnGetEventError = toCopy.throwOnGetEventError;
        this.fetchCount = toCopy.fetchCount;
    }

    @Override
    protected Void runInternal() {
        for (int i = 0; i < fetchCount; i++) {
            final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetEvent());
            if (throwOnGetEventError && edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }
        }
        return null;
    }
}
