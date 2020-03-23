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
package org.blackdread.cameraframework.util;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.VerRsrc;
import com.sun.jna.platform.win32.Version;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class DllUtil {

    private static final Logger log = LoggerFactory.getLogger(DllUtil.class);

    public static final String DEFAULT_LIB_32_PATH = "EDSDK\\Dll\\EDSDK.dll";

    public static final String DEFAULT_LIB_64_PATH = "EDSDK_64\\Dll\\EDSDK.dll";

    /**
     * Obtain Dll version (windows only)
     *
     * @param libPath path to dll file
     * @return version read on success
     * @throws FileNotFoundException if dll file not found
     * @throws IllegalStateException if fail to read version
     */
    public static String getDllVersion(final String libPath) throws FileNotFoundException {
        if (!new File(libPath).exists())
            throw new FileNotFoundException("Lib file not found: " + libPath);
        final IntByReference dwDummy = new IntByReference();
        dwDummy.setValue(0);

        final int versionByteSize = Version.INSTANCE.GetFileVersionInfoSize(libPath, dwDummy);
        if (versionByteSize == 0)
            throw new IllegalStateException("Failed to get dll version bytes size");


        final byte[] buffer = new byte[versionByteSize];
        Pointer lpData = new Memory(buffer.length);

        if (!Version.INSTANCE.GetFileVersionInfo(libPath, 0, versionByteSize, lpData)) {
            throw new IllegalStateException("Failed to get dll version info");
        }

        final PointerByReference lplpBuffer = new PointerByReference();
        final IntByReference puLen = new IntByReference();

        if (!Version.INSTANCE.VerQueryValue(lpData, "\\", lplpBuffer, puLen)) {
            throw new IllegalStateException("Failed to query value of dll version");
        }

        final VerRsrc.VS_FIXEDFILEINFO lplpBufStructure = new VerRsrc.VS_FIXEDFILEINFO(lplpBuffer.getValue());
        lplpBufStructure.read();

        final int versionMS = (lplpBufStructure.dwFileVersionMS).intValue();
        final int v1 = versionMS >> 16;
        final int v2 = versionMS & 0xffff;
        final int versionLS = (lplpBufStructure.dwFileVersionLS).intValue();
        final int v3 = versionLS >> 16;
        final int v4 = versionLS & 0xffff;

        return String.valueOf(v1) + "." +
            String.valueOf(v2) + "." +
            String.valueOf(v3) + "." +
            String.valueOf(v4);
    }

    private DllUtil() {
    }
}
