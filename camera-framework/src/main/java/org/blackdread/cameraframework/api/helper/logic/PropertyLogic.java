/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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
     * <br>
     * Similar to {@link #getPropertyType(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)} and {@link #getPropertySize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)} but in <b>one call to the camera</b>
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return Property information of data type and property size
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertyTypeAndSize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertyType(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertySize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)
     */
    default PropertyInfo getPropertyTypeAndSize(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyTypeAndSize(ref, property, 0);
    }

    /**
     * Gets the byte size and data type of a designated property from a camera object or image object.
     * <br>
     * Similar to {@link #getPropertyType(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)} and {@link #getPropertySize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)} but in one call to the camera
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @return Property information of data type and property size
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertyType(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertySize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)
     */
    PropertyInfo getPropertyTypeAndSize(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /*
     * =================== TYPE ===================
     */

    /**
     * Returns the data type of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return The data type, or null if the parameter isn't supported, or unknown if something else goes wrong.
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertyType(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)
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
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
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
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see org.blackdread.cameraframework.api.helper.logic.PropertyLogic#getPropertySize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)
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
     * @return Size in bytes
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    long getPropertySize(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

}
