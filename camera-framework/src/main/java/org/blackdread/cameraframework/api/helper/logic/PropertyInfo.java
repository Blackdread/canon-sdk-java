/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;

import java.nio.IntBuffer;

/**
 * Simple representation of data obtained with  {@link org.blackdread.camerabinding.jna.EdsdkLibrary#EdsGetPropertySize(EdsdkLibrary.EdsBaseRef, NativeLong, NativeLong, IntBuffer, NativeLongByReference)}
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class PropertyInfo {

    private final EdsDataType dataType;
    private final long size;

    public PropertyInfo(final EdsDataType dataType, final long size) {
        this.dataType = dataType;
        this.size = size;
    }

    public EdsDataType getDataType() {
        return dataType;
    }

    public Long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "PropertyInfo{" +
            "dataType=" + dataType +
            ", size=" + size +
            '}';
    }
}
