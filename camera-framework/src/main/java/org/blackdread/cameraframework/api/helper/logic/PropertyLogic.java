package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.Pointer;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkErrors;
import org.blackdread.cameraframework.api.constant.NativeEnum;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyLogic {

    default EdsDataType getPropertyType(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertyType(ref, property, 0);
    }

    EdsDataType getPropertyType(final EdsBaseRef ref, final EdsPropertyID property, final long param);

    default long getPropertySize(final EdsBaseRef ref, final EdsPropertyID property) {
        return getPropertySize(ref, property, 0);
    }

    long getPropertySize(final EdsBaseRef ref, final EdsPropertyID property, final long param);

    Long getPropertyData(final EdsBaseRef ref, final EdsPropertyID property);

    Long getPropertyData(final EdsBaseRef ref, final EdsPropertyID property, final long param);

    <T> T getPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property);

    <T> T getPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property, final long param);


    EdsdkErrors getPropertyData(final EdsBaseRef ref,
                                final EdsPropertyID property,
                                final long param,
                                final long size,
                                final Pointer data);

    EdsdkErrors setPropertyData(final EdsBaseRef ref,
                                final EdsPropertyID property,
                                final NativeEnum<? extends Number> value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref,
                                final EdsPropertyID property,
                                final long param,
                                final NativeEnum<? extends Number> value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref,
                                final EdsPropertyID property,
                                final long value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref,
                                final EdsPropertyID property,
                                final long param,
                                final long value);

    EdsdkErrors setPropertyData(final EdsBaseRef ref,
                                final EdsPropertyID property,
                                final long param,
                                final int size,
                                final Pointer data);

    EdsdkErrors setPropertyDataAdvanced(final EdsBaseRef ref,
                                        final EdsPropertyID property,
                                        final Object value);

    EdsdkErrors setPropertyDataAdvanced(final EdsBaseRef ref,
                                        final EdsPropertyID property,
                                        final long param,
                                        final Object value);
}
