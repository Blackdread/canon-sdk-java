package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkErrors;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyLogicDefault implements PropertyLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertyLogicDefault.class);

    protected PropertyLogicDefault() {
    }

    @Override
    public Pair<EdsDataType, Long> getPropertyTypeAndSize(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final int bufferSize = 1;
        final IntBuffer outDataType = IntBuffer.allocate(bufferSize);
        final NativeLongByReference outSize = new NativeLongByReference(new NativeLong(bufferSize));
        final EdsdkErrors error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(inParam), outDataType, outSize));
        if (error == EdsdkErrors.EDS_ERR_OK) {
            return new ImmutablePair<>(EdsDataType.ofValue(outDataType.get(0)), outSize.getValue().longValue());
        }
        log.error("Failed ({}) to get property type {} and size, inParam {}. Probably not supported by camera", error, property, inParam);
        throw error.getException();
    }

    @Override
    public EdsDataType getPropertyType(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final int bufferSize = 1;
        final IntBuffer outDataType = IntBuffer.allocate(bufferSize);
        final NativeLongByReference outSize = new NativeLongByReference(new NativeLong(bufferSize));
        final EdsdkErrors error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(inParam), outDataType, outSize));
        if (error == EdsdkErrors.EDS_ERR_OK) {
            return EdsDataType.ofValue(outDataType.get(0));
        }
        log.error("Failed ({}) to get property type {}, inParam {}. Probably not supported by camera", error, property, inParam);
        throw error.getException();
    }

    @Override
    public long getPropertySize(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final int bufferSize = 1;
        final IntBuffer outDataType = IntBuffer.allocate(bufferSize);
        final NativeLongByReference outSize = new NativeLongByReference(new NativeLong(bufferSize));
        final EdsdkErrors error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(inParam), outDataType, outSize));
        if (error == EdsdkErrors.EDS_ERR_OK) {
            return outSize.getValue().longValue();
        }
        log.error("Failed ({}) to get property type {}, inParam {}. Probably not supported by camera", error, property, inParam);
        throw error.getException();
    }

    @Override
    public Long getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property) {
        return null;
    }

    @Override
    public Long getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        return null;
    }

    @Override
    public <T> T getPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property) {
        return null;
    }

    @Override
    public <T> T getPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        return null;
    }

    @Override
    public EdsdkErrors getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final long size, final Pointer data) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final NativeEnum<? extends Number> value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final NativeEnum<? extends Number> value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final long value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final int size, final Pointer data) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final Object value) {
        return null;
    }

    @Override
    public EdsdkErrors setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final Object value) {
        return null;
    }
}
