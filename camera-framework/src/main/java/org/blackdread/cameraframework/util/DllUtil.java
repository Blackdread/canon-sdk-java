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
