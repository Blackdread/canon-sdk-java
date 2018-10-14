package org.blackdread.cameraframework.api.helper.property;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsDataType;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
public interface PropertyLogic {

    EdsDataType getPropertyType(EdsdkLibrary.EdsBaseRef ref, EdsPropertyID property, long param);

}
