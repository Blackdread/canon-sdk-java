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
package org.blackdread.cameraframework.api;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CanonLibrary {

    /**
     * <p>On first call it will initialize the library, any settings need to be set before</p>
     * <p>This can be called many times, it should always return same instance unless library was reloaded, therefore
     * instance returned shall never be kept but instead always call this method when library is needed.</p>
     *
     * @return library instance, never null, throws if failed to init library
     * @throws IllegalStateException if could not load library
     * @throws IllegalStateException if system is not supported
     */
    EdsdkLibrary edsdkLibrary();

    /**
     * Default to {@link ArchLibrary#AUTO}
     *
     * @return architecture that will be used when loading library
     */
    ArchLibrary getArchLibraryToUse();

    /**
     * @param archLibraryToUse Architecture to use when loading library
     */
    void setArchLibraryToUse(final ArchLibrary archLibraryToUse);

    enum ArchLibrary {
        /**
         * Logic will select arch based on current pc spec
         */
        AUTO,
        /**
         * Force to load DLL from 32 bit arch, even on 64 bit arch
         */
        FORCE_32,
        /**
         * Force to load DLL from 64 bit arch, even on 32 bit arch.
         * <p>Will certainly crash or fail to load library</p>
         */
        FORCE_64
    }

}
