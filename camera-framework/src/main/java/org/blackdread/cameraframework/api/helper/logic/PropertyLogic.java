package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkErrors;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyLogic {

    /**
     * Gets the byte size and data type of a designated property from a camera object or image object.
     * <br/>
     * Similar to {@link #getPropertyType(EdsBaseRef, EdsPropertyID)} and {@link #getPropertySize(EdsBaseRef, EdsPropertyID)} but in <b>one call to the camera</b>
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return A tuple with data type and property size (TODO might change return type later as to not enforce dependency to apache lang3)
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
     * @see #getPropertyType(EdsBaseRef, EdsPropertyID, long)
     * @see #getPropertySize(EdsBaseRef, EdsPropertyID, long)
     */
    Pair<EdsDataType, Long> getPropertyTypeAndSize(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /**
     * Returns the data type of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return The data type, or null if the parameter isn't supported, or unknown if something else goes wrong.
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
     */
    EdsDataType getPropertyType(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /**
     * Gets the byte size of a designated property from a camera object or image object.
     *
     * @param ref      either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @return Size in bytes
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
     * @return Size in bytes
     */
    long getPropertySize(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);

    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @return property value as long
     * @throws ClassCastException if type of data retrieved is not a long
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
     * @throws ClassCastException if type of data retrieved is not a long
     * @deprecated does not give any benefits over {{@link #getPropertyData(EdsBaseRef, EdsPropertyID, long)}} unless we set the other methods as protected in implementation
     */
    @Deprecated
    default Long getPropertyDataLong(final EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        return getPropertyData(ref, property, inParam);
    }

    /**
     * Gets property information from the object designated in inRef.
     * <br/>
     * Return type is automatically casted to caller type but it can still throws {@link ClassCastException}
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @param <T>      type of return value
     * @return data from camera in expected type
     * @throws ClassCastException if type of data retrieved is not of what caller expects
     */
    default <T> T getPropertyData(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyData(ref, property, 0);
    }

    /**
     * Gets property information from the object designated in inRef.
     * <br/>
     * Return type is automatically casted to caller type but it can still throws {@link ClassCastException}
     *
     * @param ref      designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property the property id
     * @param inParam  additional property information (see Reference API for possible values)
     * @param <T>      type of return value
     * @return data from camera in expected type
     * @throws ClassCastException if type of data retrieved is not of what caller expects
     */
    <T> T getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam);


    /**
     * Gets property information from the object designated in inRef.
     *
     * @param ref          designate the object for which to get properties. The EDSDK objects you can designate are EdsCameraRef, EdsDirectoryItemRef, or EdsImageRef
     * @param property     the property id
     * @param inParam      additional property information (see Reference API for possible values)
     * @param size         Designate the byte size of the property. If the property data size is not known in advance, it can be retrieved by means of {@link #getPropertySize(EdsBaseRef, EdsPropertyID)} or {{@link #getPropertyTypeAndSize(EdsBaseRef, EdsPropertyID)}}
     * @param propertyData pointer that receives the data, the data type and value returned vary depending on the property
     * @return error from the camera, the real data is in the pointer <code>propertyData</code>
     * @deprecated can keep but is only kind of shortcut to query camera then transform the result into an error enum. This is more an internal method
     */
    @Deprecated
    default EdsdkErrors getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long inParam,
                                        final long size, final Pointer propertyData) {
        return toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertyData(ref, new NativeLong(property.value()), new NativeLong(inParam), new NativeLong(size), propertyData));
    }

    /*
     * =================== SET ===================
     */

    EdsdkErrors setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                final NativeEnum<? extends Number> value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                final long inParam, final NativeEnum<? extends Number> value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                final long inParam, final long value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                final long inParam, final int size, final Pointer data);

    EdsdkErrors setPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property, final Object value);

    EdsdkErrors setPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property,
                                        final long inParam, final Object value);
}
