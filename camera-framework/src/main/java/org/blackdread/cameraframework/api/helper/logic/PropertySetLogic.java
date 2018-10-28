package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertySetLogic {

    EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                               final NativeEnum<? extends Number> value);

    EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                               final long inParam, final NativeEnum<? extends Number> value);

    EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long value);

    EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                               final long inParam, final long value);

    EdsdkError setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                               final long inParam, final int size, final Pointer data);

    EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final Object value);

    EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final Object value);

}
