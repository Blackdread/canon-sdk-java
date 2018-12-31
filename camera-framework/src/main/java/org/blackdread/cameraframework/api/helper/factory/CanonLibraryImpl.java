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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.command.TerminateSdkCommand;
import org.blackdread.cameraframework.util.DllUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Optional;

/**
 * <p>Created on 2018/10/16.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@ThreadSafe
class CanonLibraryImpl implements CanonLibrary {

    private static final Logger log = LoggerFactory.getLogger(CanonLibraryImpl.class);

    private static final String JNA_PATH_PROPERTY = "jna.library.path";

    // fields are static but will change to instance fields

    static {
        final CodeSource codeSource = CanonLibraryImpl.class.getProtectionDomain().getCodeSource();
        try {
            final File tmp = new File(codeSource.getLocation().toURI().getPath());
            jarFile = tmp;
            log.info("Jar file is in {}, folder {}", tmp.getPath(), tmp.getParentFile().getPath());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to build jar file path");
        }
    }

    /**
     * no used yet, might be necessary in getLibPath()
     */
    private static final File jarFile;
    /**
     * no used yet, might be necessary in getLibPath()
     */
    private static final File jarDir = jarFile.getParentFile();

    private final Object initLibraryLock = new Object();

    /**
     * Instance of library
     */
    private volatile EdsdkLibrary EDSDK = null;

    private Thread shutdownHookThread = null;

    private ArchLibrary archLibraryToUse = ArchLibrary.AUTO;

    CanonLibraryImpl() {
    }

    @Override
    public EdsdkLibrary edsdkLibrary() {
        initLibrary();
        return EDSDK;
    }

    @Override
    public ArchLibrary getArchLibraryToUse() {
        return archLibraryToUse;
    }

    @Override
    public void setArchLibraryToUse(final ArchLibrary archLibraryToUse) {
        this.archLibraryToUse = archLibraryToUse;
    }

    /**
     * @return path to lib to load
     */
    protected Optional<String> getLibPath() {
        final String jnaPath = System.getProperty(JNA_PATH_PROPERTY);
        if (jnaPath != null) {
            // user has specified himself the path, we follow what he gave
            return Optional.of(jnaPath);
        }

        switch (archLibraryToUse) {
            case AUTO:
                // is64Bit() already checks java runtime with "sun.arch.data.model" for 32 or 64
                if (Platform.is64Bit()) {
                    log.info("Dll auto selected to 64 bit");
                    return Optional.of(DllUtil.DEFAULT_LIB_64_PATH);
                }
                log.info("Dll auto selected to 32 bit");
                return Optional.of(DllUtil.DEFAULT_LIB_32_PATH);
            case FORCE_32:
                log.info("Dll forced to 32 bit");
                return Optional.of(DllUtil.DEFAULT_LIB_32_PATH);
            case FORCE_64:
                log.info("Dll forced to 64 bit");
                return Optional.of(DllUtil.DEFAULT_LIB_64_PATH);
            default:
                throw new IllegalStateException("Enum unknown: " + archLibraryToUse);
        }
    }

    /**
     * <p>Method is called at every call of {@link #edsdkLibrary()}</p>
     */
    protected void initLibrary() {
        if (EDSDK == null) {
            synchronized (initLibraryLock) {
                if (EDSDK == null) {
                    final String libPath = getLibPath()
                        .orElseThrow(() -> new IllegalStateException("Could not init library, lib path not found"));
                    if (Platform.isWindows()) {
                        // no options for now
                        EDSDK = Native.loadLibrary(libPath, EdsdkLibrary.class, new HashMap<>());
                        registerCanonShutdownHook();
                        log.info("Library successfully loaded");
                        return;
                    }
                    throw new IllegalStateException("Not supported OS: " + Platform.getOSType());
                }
            }
        }
    }

    private void registerCanonShutdownHook() {
        final EdsdkLibrary edsdk = EDSDK;
        final Thread shutdownThread = new Thread(() ->
        {
            log.info("Shutdown hook run");
            CanonFactory.commandDispatcher().scheduleCommand(new TerminateSdkCommand());
//            if (edsdk != null)
//                edsdk.EdsTerminateSDK();
        });
        if (shutdownHookThread != null) {
            Runtime.getRuntime().removeShutdownHook(shutdownHookThread);
            shutdownHookThread.interrupt();
        }
        shutdownHookThread = shutdownThread;
        Runtime.getRuntime().addShutdownHook(shutdownThread);
        log.info("Registered shutdown hook of library");
    }
}
