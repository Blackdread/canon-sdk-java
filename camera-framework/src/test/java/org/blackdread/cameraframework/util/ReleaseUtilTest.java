package org.blackdread.cameraframework.util;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class ReleaseUtilTest extends AbstractMockTest {

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void release() {
        final EdsBaseRef.ByReference byReference = new EdsBaseRef.ByReference();

        ReleaseUtil.release(byReference);

        // ================
        // Accepts null

        ReleaseUtil.release((EdsBaseRef.ByReference) null, null);

        ReleaseUtil.release((EdsBaseRef.ByReference) null);
    }

    @Test
    void release1() {
        final EdsBaseRef edsBaseRef = new EdsBaseRef();

        when(CanonFactory.edsdkLibrary().EdsRelease(edsBaseRef))
            .thenReturn(new NativeLong(0))
            .thenReturn(new NativeLong(-1));

        ReleaseUtil.release(edsBaseRef, edsBaseRef);

        // ================
        // Accepts null

        ReleaseUtil.release((EdsBaseRef) null, (EdsBaseRef) null);
    }

    @Test
    void release2() {
        final EdsBaseRef edsBaseRef = new EdsBaseRef();

        when(CanonFactory.edsdkLibrary().EdsRelease(edsBaseRef))
            .thenReturn(new NativeLong(0));

        ReleaseUtil.release(edsBaseRef);

        // ================
        // Accepts null

        ReleaseUtil.release((EdsBaseRef) null);
    }
}
