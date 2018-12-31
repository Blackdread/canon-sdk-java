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
package org.blackdread.cameraframework.api;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.DllOnPath;
import org.blackdread.cameraframework.util.DllUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.blackdread.cameraframework.util.DllUtil.DEFAULT_LIB_32_PATH;
import static org.blackdread.cameraframework.util.DllUtil.DEFAULT_LIB_64_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
@DllOnPath
@EnabledOnOs(OS.WINDOWS)
class DllTest {

    private static final Logger log = LoggerFactory.getLogger(DllTest.class);

    private static final String LAST_LIBRARY_VERSION_TESTED_32 = "3.9.0.0";

    private static final String LAST_LIBRARY_VERSION_TESTED_64 = "3.9.0.6400";

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void loadLib32() {
        // throws on java 64 bit (win32-x86-64/EDSDK\Dll\EDSDK.dll), not tested with 32 bit runtime
        loadLibraryBasics(DEFAULT_LIB_32_PATH);
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void loadLib64() {
        // might throw on 32 bit, not tested
        loadLibraryBasics(DEFAULT_LIB_64_PATH);
    }

    @Test
    void libVersion32() throws FileNotFoundException {
        final String dllVersion = DllUtil.getDllVersion(DEFAULT_LIB_32_PATH);
        log.info("Dll version {}", dllVersion);
        assertEquals(LAST_LIBRARY_VERSION_TESTED_32, dllVersion);
    }

    @Test
    void libVersion64() throws FileNotFoundException {
        final String dllVersion = DllUtil.getDllVersion(DEFAULT_LIB_64_PATH);
        log.info("Dll version {}", dllVersion);
        assertEquals(LAST_LIBRARY_VERSION_TESTED_64, dllVersion);
    }

    private static void loadLibraryBasics(final String libPath) {
        final EdsdkLibrary library = Native.loadLibrary(libPath, EdsdkLibrary.class, new HashMap<>());
        NativeLong result = library.EdsInitializeSDK();
        if (result.intValue() != 0)
            fail("Failed to init SDK");

        result = library.EdsTerminateSDK();
        if (result.intValue() != 0)
            fail("Failed to terminate SDK");
    }

}
