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

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStreamRef;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * Stream and image ref of the live view.
 * * <br>
 * * Do not forget to <b>release both refs once not used</b> by calling close, best is to use <b>try-with-resource</b>.
 * <p>Created on 2018/11/03.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class LiveViewReference implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(LiveViewReference.class);

    private final EdsStreamRef.ByReference streamRef;

    private final EdsEvfImageRef.ByReference imageRef;

    public LiveViewReference(final EdsStreamRef.ByReference streamRef, final EdsEvfImageRef.ByReference imageRef) {
        this.streamRef = streamRef;
        this.imageRef = imageRef;
    }

    public EdsStreamRef.ByReference getStreamRef() {
        return streamRef;
    }

    public EdsEvfImageRef.ByReference getImageRef() {
        return imageRef;
    }

    @Override
    public void close() {
        try {
            ReleaseUtil.release(imageRef);
        } catch (Exception e) {
            log.warn("Fail release (should never occur)", e);
        }
        try {
            ReleaseUtil.release(streamRef);
        } catch (Exception e) {
            log.warn("Fail release (should never occur)", e);
        }
    }
}
