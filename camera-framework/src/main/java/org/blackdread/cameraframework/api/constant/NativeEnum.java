package org.blackdread.cameraframework.api.constant;

/**
 * Representation of enum or constant value from native library defined in header files or in library reference
 * <p>Created on 2018/10/04.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface NativeEnum<V> {

    /**
     * @return Name of native enum
     */
    String name();

    /**
     * For constants, it is the value
     *
     * @return value returned by library API and library reference
     */
    V value();

    /**
     * An explanation of what name/value means
     *
     * @return description of the name/value
     */
    String description();

}
