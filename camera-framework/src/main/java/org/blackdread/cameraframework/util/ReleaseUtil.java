package org.blackdread.cameraframework.util;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class ReleaseUtil {

    private static final Logger log = LoggerFactory.getLogger(ReleaseUtil.class);

    /**
     * Release refs from the camera
     *
     * @param refs refs to release
     */
    public static void release(final EdsdkLibrary.EdsBaseRef.ByReference... refs) {
        if (refs != null)
            for (final EdsdkLibrary.EdsBaseRef.ByReference byReference : refs) {
                release(byReference.getValue());
            }
    }

    /**
     * Release refs from the camera
     *
     * @param refs refs to release
     */
    public static void release(final EdsdkLibrary.EdsBaseRef... refs) {
        if (refs != null)
            for (final EdsdkLibrary.EdsBaseRef ref : refs) {
                release(ref);
            }
    }

    /**
     * Release a ref from the camera
     *
     * @param ref refs to release
     */
    public static void release(final EdsdkLibrary.EdsBaseRef ref) {
        if (ref != null) {
            final NativeLong nativeLong = CanonFactory.edsdkLibrary().EdsRelease(ref);
            if (nativeLong.intValue() == 0xFFFFFFFF) {
                log.warn("Failed to release {}", ref);
            }
        }
    }

    private ReleaseUtil() {
    }
}
