package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.PropertyInfo;
import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class PropertyLogicDefault implements PropertyLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertyLogicDefault.class);

    protected PropertyLogicDefault() {
    }

    @Override
    public PropertyInfo getPropertyTypeAndSize(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final int bufferSize = 1;
        final IntBuffer outDataType = IntBuffer.allocate(bufferSize);
        final NativeLongByReference outSize = new NativeLongByReference(new NativeLong(bufferSize));
        final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(inParam), outDataType, outSize));
        if (error == EdsdkError.EDS_ERR_OK) {
            return new PropertyInfo(EdsDataType.ofValue(outDataType.get(0)), outSize.getValue().longValue());
        }
        log.error("Failed  to get property type and size of {} ({}), inParam {}. Probably not supported by camera", property, error, inParam);
        throw error.getException();
    }

    @Override
    public EdsDataType getPropertyType(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final int bufferSize = 1;
        final IntBuffer outDataType = IntBuffer.allocate(bufferSize);
        final NativeLongByReference outSize = new NativeLongByReference(new NativeLong(bufferSize));
        final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(inParam), outDataType, outSize));
        if (error == EdsdkError.EDS_ERR_OK) {
            return EdsDataType.ofValue(outDataType.get(0));
        }
        log.error("Failed to get property type of {} ({}), inParam {}. Probably not supported by camera", property, error, inParam);
        throw error.getException();
    }

    @Override
    public long getPropertySize(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam) {
        final int bufferSize = 1;
        final IntBuffer outDataType = IntBuffer.allocate(bufferSize);
        final NativeLongByReference outSize = new NativeLongByReference(new NativeLong(bufferSize));
        final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetPropertySize(ref, new NativeLong(property.value()), new NativeLong(inParam), outDataType, outSize));
        if (error == EdsdkError.EDS_ERR_OK) {
            return outSize.getValue().longValue();
        }
        log.error("Failed to get property size of {} ({}), inParam {}. Probably not supported by camera", property, error, inParam);
        throw error.getException();
    }

}
