package org.blackdread.cameraframework.api.helper.logic;

import org.apache.commons.lang3.tuple.Pair;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyLogic {

    // *******************
    // TODO might add these methods
//    List<EdsPropertyID> getSupportedPropertiesByCamera(final EdsBaseRef ref);
//    List<EdsPropertyID> getNotSupportedPropertiesByCamera(final EdsBaseRef ref);
    // *******************

    /*
     * =================== TYPE AND SIZE ===================
     */

    /**
     * Gets the byte size and data type of a designated property from a camera object or image object.
     * <br/>
     * Similar to {@link #getPropertyType(EdsBaseRef, EdsPropertyID)} and {@link #getPropertySize(EdsBaseRef, EdsPropertyID)} but in <b>one call to the camera</b>
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return A tuple with data type and property size (TODO might change return type later as to not enforce dependency to apache lang3)
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see #getPropertyTypeAndSize(EdsBaseRef, EdsPropertyID, long)
     * @see #getPropertyType(EdsBaseRef, EdsPropertyID)
     * @see #getPropertySize(EdsBaseRef, EdsPropertyID)
     */
    default Pair<EdsDataType, Long> getPropertyTypeAndSize(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyTypeAndSize(ref, property, 0);
    }

    /**
     * Gets the byte size and data type of a designated property from a camera object or image object.
     * <br/>
     * Similar to {@link #getPropertyType(EdsBaseRef, EdsPropertyID, long)} and {@link #getPropertySize(EdsBaseRef, EdsPropertyID, long)} but in one call to the camera
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @return A tuple with data type and property size (TODO might change return type later as to not enforce dependency to apache lang3)
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see #getPropertyType(EdsBaseRef, EdsPropertyID, long)
     * @see #getPropertySize(EdsBaseRef, EdsPropertyID, long)
     */
    Pair<EdsDataType, Long> getPropertyTypeAndSize(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /*
     * =================== TYPE ===================
     */

    /**
     * Returns the data type of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return The data type, or null if the parameter isn't supported, or unknown if something else goes wrong.
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see #getPropertyType(EdsBaseRef, EdsPropertyID, long)
     */
    default EdsDataType getPropertyType(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyType(ref, property, 0);
    }

    /**
     * Returns the data type of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property Property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @return The data type, or null if the parameter isn't supported, or unknown if something else goes wrong.
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    EdsDataType getPropertyType(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /*
     * =================== SIZE ===================
     */

    /**
     * Gets the byte size of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return Size in bytes
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see #getPropertySize(EdsBaseRef, EdsPropertyID, long)
     */
    default long getPropertySize(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertySize(ref, property, 0);
    }

    /**
     * Gets the byte size of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @return Size in bytes
     */
    long getPropertySize(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

}
