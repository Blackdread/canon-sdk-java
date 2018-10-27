package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdread.camerabinding.jna.EdsFocusInfo;
import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsRational;
import org.blackdread.camerabinding.jna.EdsRect;
import org.blackdread.camerabinding.jna.EdsTime;
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
        log.error("Failed  to get property type and size of {} ({}), inParam {}. Probably not supported by camera", property, error, inParam);
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
        log.error("Failed to get property type {} ({}), inParam {}. Probably not supported by camera", property, error, inParam);
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
        log.error("Failed to get property type {} ({}), inParam {}. Probably not supported by camera", property, error, inParam);
        throw error.getException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final Pair<EdsDataType, Long> propertyTypeAndSize = getPropertyTypeAndSize(ref, property, inParam);
        final EdsDataType propertyType = propertyTypeAndSize.getKey();
        final Long propertySize = propertyTypeAndSize.getValue();

        final Memory propertyData = new Memory(propertySize > 0 ? propertySize : 1);

        final EdsdkErrors error = getPropertyData(ref, property, inParam, propertySize, propertyData);

        if (!EdsdkErrors.EDS_ERR_OK.equals(error)) {
            log.error("Failed to get property data {}, inParam: {}, propertyType:{}, propertySize: {}", property, inParam, propertyType, propertySize);
            throw error.getException();
        }

        switch (propertyType) {
            case kEdsDataType_Unknown:
                throw new IllegalStateException("Unknown data type returned by camera to get property data");
            case kEdsDataType_Bool:
                final Boolean b = propertyData.getByte(0) != 0;
                return (T) b;
            case kEdsDataType_String:
                return (T) propertyData.getString(0);
            case kEdsDataType_Int8:
            case kEdsDataType_UInt8:
                return (T) (Byte) propertyData.getByte(0);
            case kEdsDataType_Int16:
            case kEdsDataType_UInt16:
                return (T) (Short) propertyData.getShort(0);
            case kEdsDataType_Int32:
            case kEdsDataType_UInt32:
                return (T) (Long) propertyData.getNativeLong(0).longValue();
            case kEdsDataType_Int64:
            case kEdsDataType_UInt64:
                return (T) (Long) propertyData.getLong(0);
            case kEdsDataType_Float:
                return (T) (Float) propertyData.getFloat(0);
            case kEdsDataType_Double:
                return (T) (Double) propertyData.getDouble(0);
            case kEdsDataType_ByteBlock:
                // not tested and documentation gives this data type as many types :
                // EdsInt8[]
                // EdsUInt32[]
                return (T) propertyData.getIntArray(0, propertySize.intValue() / 4);
            case kEdsDataType_Rational:
                return (T) new EdsRational(propertyData);
            case kEdsDataType_Point:
                return (T) new EdsPoint(propertyData);
            case kEdsDataType_Rect:
                return (T) new EdsRect(propertyData);
            case kEdsDataType_Time:
                return (T) new EdsTime(propertyData);
            case kEdsDataType_Bool_Array:
                throw new NotImplementedException("to test");
            case kEdsDataType_Int8_Array:
            case kEdsDataType_UInt8_Array:
                return (T) propertyData.getByteArray(0, propertySize.intValue());
            case kEdsDataType_Int16_Array:
            case kEdsDataType_UInt16_Array:
                return (T) propertyData.getShortArray(0, propertySize.intValue() / 2);
            case kEdsDataType_Int32_Array:
            case kEdsDataType_UInt32_Array:
                return (T) propertyData.getIntArray(0, propertySize.intValue() / 4);
            case kEdsDataType_Rational_Array:
                throw new NotImplementedException("to test");
            case kEdsDataType_FocusInfo:
                return (T) new EdsFocusInfo(propertyData);
            case kEdsDataType_PictureStyleDesc:
                return (T) new EdsPictureStyleDesc(propertyData);
            default:
                log.error("Unknown property type: {}, {}", propertyType, propertyType.value());
                throw new IllegalStateException("Unknown property type: " + propertyType + ", " + propertyType.value());
        }
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
