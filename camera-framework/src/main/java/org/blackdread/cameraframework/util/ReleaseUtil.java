package org.blackdread.cameraframework.util;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
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
            // TODO not public yet and might be an instance from a factory
//            CanonLibrary.getEdsdkLibrary().EdsRelease(ref);
        }
    }

    private ReleaseUtil() {
    }
}
