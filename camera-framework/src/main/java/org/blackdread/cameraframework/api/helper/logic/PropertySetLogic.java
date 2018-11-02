package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertySetLogic {

    default void setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                 final NativeEnum<? extends Number> value) {
        this.setPropertyDataAdvanced(ref, property, 0, value.value().longValue());
    }

    default void setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                 final long inParam, final NativeEnum<? extends Number> value) {
        this.setPropertyDataAdvanced(ref, property, inParam, value.value().longValue());
    }

    default void setPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long value) {
        this.setPropertyDataAdvanced(ref, property, 0, value);
    }

    default void setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                         final long inParam, final long value) {
        this.setPropertyDataAdvanced(ref, property, 0, value);
    }

    default void setPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property, final Object value) {
        setPropertyDataAdvanced(ref, property, 0, value);
    }

    /**
     * Sets property data for the object designated in inRef.
     * When you set properties of an image object (EdsImageRef), this API maintains the change internally.
     *
     * @param ref      designate the object for which to set properties. Designate either EdsCameraRef or EdsImageRef
     * @param property the property id
     * @param inParam  designate additional property information. Use additional property information if multiple items of information such as picture styles can be set or retrieved for a property. For descriptions of values that can be designated for each property, see the description of inParam for EdsGetPropertyData
     * @param value    designate the property data to set
     */
    void setPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property,
                         final long inParam, final Object value);

    default EdsdkError setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final int size, final Pointer data) {
        return toEdsdkError(edsdkLibrary().EdsSetPropertyData(ref, new NativeLong(property.value()), new NativeLong(inParam), new NativeLong(size), data));
    }

}
