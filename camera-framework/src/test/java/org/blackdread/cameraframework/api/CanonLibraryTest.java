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
