package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyGetLogic {

    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @return property value as long
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not a long
     * @deprecated does not give any benefits over {{@link #getPropertyData(EdsBaseRef, EdsPropertyID)}} unless we set the other methods as protected in implementation
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
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not a long
     * @deprecated does not give any benefits over {{@link #getPropertyData(EdsBaseRef, EdsPropertyID, long)}} unless we set the other methods as protected in implementation
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
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
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
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if type of data retrieved is not of what caller expects
     */
    <T> T getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref          designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property     the property id
     * @param inParam      additional property information (see Reference API for possible values)
     * @param size         Designate the byte size of the property. If the property data size is not known in advance, it can be retrieved by means of {@link PropertyLogic#getPropertySize(EdsBaseRef, EdsPropertyID)} or {{@link PropertyLogic#getPropertyTypeAndSize(EdsBaseRef, EdsPropertyID)}}
     * @param propertyData pointer that receives the data, the data type and value returned vary depending on the property
     * @return error from the camera, the real data is in the pointer <code>propertyData</code>
     * @throws org.blackdread.cameraframework.exception.EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @deprecated can keep but is only kind of shortcut to query camera then transform the result into an error enum. This is more an internal method
     */
    @Deprecated
    default EdsdkError getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam,
                                       final long size, final Pointer propertyData) {
        return toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertyData(ref, new NativeLong(property.value()), new NativeLong(inParam), new NativeLong(size), propertyData));
    }

}
