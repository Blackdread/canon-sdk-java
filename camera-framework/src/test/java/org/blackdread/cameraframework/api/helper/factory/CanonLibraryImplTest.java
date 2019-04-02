package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Platform;
import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.util.DllUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/04/02.</p>
 *
 * @author Yoann CAPLAIN
 */
class CanonLibraryImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLibPath() {
        final CanonLibraryImpl canonLibrary = (CanonLibraryImpl) CanonFactory.canonLibrary();
        if (Platform.is64Bit()) {
            Assertions.assertEquals(DllUtil.DEFAULT_LIB_64_PATH, canonLibrary.getLibPath().get());
        }

        canonLibrary.setArchLibraryToUse(CanonLibrary.ArchLibrary.FORCE_32);
        Assertions.assertEquals(DllUtil.DEFAULT_LIB_32_PATH, canonLibrary.getLibPath().get());

        canonLibrary.setArchLibraryToUse(CanonLibrary.ArchLibrary.FORCE_64);
        Assertions.assertEquals(DllUtil.DEFAULT_LIB_64_PATH, canonLibrary.getLibPath().get());

        System.setProperty("blackdread.cameraframework.library.path", "test");
        Assertions.assertEquals("test", canonLibrary.getLibPath().get());
    }
}
