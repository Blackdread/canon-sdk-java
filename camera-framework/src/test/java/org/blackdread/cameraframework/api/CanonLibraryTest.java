package org.blackdread.cameraframework.api;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.DllOnPath;
import org.blackdread.cameraframework.util.DllUtil;
import org.junit.jupiter.api.Test;
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
class CanonLibraryTest {

    private static final Logger log = LoggerFactory.getLogger(CanonLibraryTest.class);

    private static final String LAST_LIBRARY_VERSION_TESTED_32 = "3.9.0.0";

    private static final String LAST_LIBRARY_VERSION_TESTED_64 = "3.9.0.6400";

    @Test
    void loadLib32() {
        // throws on java 64 bit (win32-x86-64/EDSDK\Dll\EDSDK.dll), not tested with 32 bit runtime
        loadLibraryBasics(DEFAULT_LIB_32_PATH);
    }

    @Test
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
