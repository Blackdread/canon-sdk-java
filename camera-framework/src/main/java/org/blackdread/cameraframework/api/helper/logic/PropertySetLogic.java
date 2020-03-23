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
package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.constant.NativeEnum;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @throws ClassCastException                                           if the object {@code value} does not match the type of the property to set
     */
    void setPropertyDataAdvanced(final EdsBaseRef ref, final EdsPropertyID property,
                                 final long inParam, final Object value);

    default EdsdkError setPropertyData(final EdsBaseRef ref, final EdsPropertyID property,
                                       final long inParam, final int size, final Pointer data) {
        return toEdsdkError(edsdkLibrary().EdsSetPropertyData(ref, new NativeLong(property.value()), new NativeLong(inParam), new NativeLong(size), data));
    }

}
