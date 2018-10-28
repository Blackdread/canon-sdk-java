package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
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

    default EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                                       final NativeEnum<? extends Number> value) {
        return setPropertyDataAdvanced(ref, property, 0, value.value().longValue());
    }

    default EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final NativeEnum<? extends Number> value) {
        return setPropertyDataAdvanced(ref, property, inParam, value.value().longValue());
    }

    default EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long value) {
        return setPropertyDataAdvanced(ref, property, 0, value);
    }

    default EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final long value) {
        return setPropertyDataAdvanced(ref, property, 0, value);
    }

    default EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final Object value) {
        return setPropertyDataAdvanced(ref, property, 0, value);
    }

    EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final Object value);

    default EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final int size, final Pointer data) {
        return toEdsdkError(edsdkLibrary().EdsSetPropertyData(ref, new NativeLong(property.value()), new NativeLong(inParam), new NativeLong(size), data));
    }

}
