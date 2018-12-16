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
package org.blackdread.cameraframework.util;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class ReleaseUtil {

    private static final Logger log = LoggerFactory.getLogger(ReleaseUtil.class);

    /**
     * Release refs from the camera
     *
     * @param refs refs to release
     */
    public static void release(final EdsdkLibrary.EdsBaseRef.ByReference... refs) {
        if (refs != null)
            for (final EdsdkLibrary.EdsBaseRef.ByReference byReference : refs) {
                release(byReference.getValue());
            }
    }

    /**
     * Release refs from the camera
     *
     * @param refs refs to release
     */
    public static void release(final EdsdkLibrary.EdsBaseRef... refs) {
        if (refs != null)
            for (final EdsdkLibrary.EdsBaseRef ref : refs) {
                release(ref);
            }
    }

    /**
     * Release a ref from the camera
     *
     * @param ref refs to release
     */
    public static void release(final EdsdkLibrary.EdsBaseRef ref) {
        if (ref != null) {
            final NativeLong nativeLong = CanonFactory.edsdkLibrary().EdsRelease(ref);
            if (nativeLong.intValue() == 0xFFFFFFFF) {
                log.warn("Failed to release {}", ref);
            }
        }
    }

    private ReleaseUtil() {
    }
}
