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
package org.blackdread.cameraframework.api;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.DllOnPath;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/22.<p>
 *
 * @author Yoann CAPLAIN
 */
class CanonLibraryTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DllOnPath
    void edsdkLibrary() {
        final EdsdkLibrary edsdkLibrary = CanonFactory.getCanonFactory().getCanonLibrary().edsdkLibrary();
        assertNotNull(edsdkLibrary);

        final EdsdkLibrary shortcut = CanonFactory.edsdkLibrary();
        assertSame(edsdkLibrary, shortcut);
    }

    @Test
    void archLibraryToUseIsAutoByDefault() {
        assertEquals(CanonLibrary.ArchLibrary.AUTO, CanonFactory.getCanonFactory().getCanonLibrary().getArchLibraryToUse());
    }

    @Test
    void setArchLibraryToUse() {
        CanonFactory.getCanonFactory().getCanonLibrary().setArchLibraryToUse(CanonLibrary.ArchLibrary.FORCE_32);
        assertEquals(CanonLibrary.ArchLibrary.FORCE_32, CanonFactory.getCanonFactory().getCanonLibrary().getArchLibraryToUse());
        CanonFactory.getCanonFactory().getCanonLibrary().setArchLibraryToUse(CanonLibrary.ArchLibrary.FORCE_64);
        assertEquals(CanonLibrary.ArchLibrary.FORCE_64, CanonFactory.getCanonFactory().getCanonLibrary().getArchLibraryToUse());
        CanonFactory.getCanonFactory().getCanonLibrary().setArchLibraryToUse(CanonLibrary.ArchLibrary.AUTO);
        assertEquals(CanonLibrary.ArchLibrary.AUTO, CanonFactory.getCanonFactory().getCanonLibrary().getArchLibraryToUse());
    }

}
