package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.Memory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyGetLogicDefaultExtended extends PropertyGetLogicDefault {

    private static final Logger log = LoggerFactory.getLogger(PropertyGetLogicDefaultExtended.class);

    private Memory memory;

    protected Memory getMemoryForHoldingData(final long size) {
        if (memory == null) {
            return super.getMemoryForHoldingData(size);
        }
        return memory;
    }

    public void setMemory(final Memory memory) {
        this.memory = memory;
    }

    //    @Override
//    public EdsdkError getPropertyData(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final long size, final Pointer propertyData) {
//
//        return null;
//    }
}
