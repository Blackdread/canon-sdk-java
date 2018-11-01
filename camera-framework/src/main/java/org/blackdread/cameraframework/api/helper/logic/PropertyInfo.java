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
