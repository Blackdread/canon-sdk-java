package org.blackdread.cameraframework.api;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface CanonLibrary {

    /**
     * <p>On first call it will initialize the library, any settings need to be set before</p>
     * <p>This can be called many times, it should always return same instance unless library was reloaded, therefore
     * instance returned shall never be kept but instead always call this method when library is needed.</p>
     *
     * @return library instance, never null, throws if failed to init library
     * @throws IllegalStateException if could not load library
     * @throws IllegalStateException if system is not supported
     */
    EdsdkLibrary edsdkLibrary();

    /**
     * Default to {@link ArchLibrary#AUTO}
     *
     * @return architecture that will be used when loading library
     */
    ArchLibrary getArchLibraryToUse();

    /**
     * @param archLibraryToUse Architecture to use when loading library
     */
    void setArchLibraryToUse(final ArchLibrary archLibraryToUse);

    enum ArchLibrary {
        /**
         * Logic will select arch based on current pc spec
         */
        AUTO,
        /**
         * Force to load DLL from 32 bit arch, even on 64 bit arch
         */
        FORCE_32,
        /**
         * Force to load DLL from 64 bit arch, even on 32 bit arch.
         * <p>Will certainly crash or fail to load library</p>
         */
        FORCE_64
    }

}
