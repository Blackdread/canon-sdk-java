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
package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface PropertyGetLogic {

    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @return property value as long
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not a long
     * @deprecated does not give any benefits over {{@link #getPropertyData(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)}} unless we set the other methods as protected in implementation
     */
    @Deprecated
    default Long getPropertyDataLong(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyData(ref, property, 0);
    }

    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @return property value as long
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not a long
     * @deprecated does not give any benefits over {{@link #getPropertyData(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID, long)}} unless we set the other methods as protected in implementation
     */
    @Deprecated
    default Long getPropertyDataLong(final EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        return getPropertyData(ref, property, inParam);
    }

    /**
     * Gets property information from the object designated in inRef.
     * <br>
     * Return type is automatically casted to caller type but it can still throws {@link ClassCastException}
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @param <T>      type of return value
     * @return data from camera in expected type
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not of what caller expects
     */
    default <T> T getPropertyData(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyData(ref, property, 0);
    }

    /**
     * Gets property information from the object designated in inRef.
     * It automatically convert the return value from the library in the enum or object type of property.
     * <br>
     * Return type is automatically casted to caller type but it can still throws {@link ClassCastException}
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @param <T>      type of return value
     * @return data from camera in expected type
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not of what caller expects
     */
    <T> T getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);


    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref          designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property     the property id
     * @param inParam      additional property information (see Reference API for possible values)
     * @param size         Designate the byte size of the property. If the property data size is not known in advance, it can be retrieved by means of {@link PropertyLogic#getPropertySize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)} or {{@link PropertyLogic#getPropertyTypeAndSize(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef, EdsPropertyID)}}
     * @param propertyData pointer that receives the data, the data type and value returned vary depending on the property
     * @return error from the camera, the real data is in the pointer <code>propertyData</code>
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsdkError getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam,
                                       final long size, final Pointer propertyData) {
        return toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertyData(ref, new NativeLong(property.value()), new NativeLong(inParam), new NativeLong(size), propertyData));
    }

}
