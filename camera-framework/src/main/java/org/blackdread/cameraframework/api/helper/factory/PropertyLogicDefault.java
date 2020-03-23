/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
