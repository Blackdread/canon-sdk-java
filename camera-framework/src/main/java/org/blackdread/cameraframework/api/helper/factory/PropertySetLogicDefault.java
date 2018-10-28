package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.PropertySetLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2018/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertySetLogicDefault implements PropertySetLogic {

    private static final Logger log = LoggerFactory.getLogger(PropertySetLogicDefault.class);

    protected PropertySetLogicDefault() {
    }


    /*
    EdsError setTv(EdsCameraRef camera, EdsUInt32 TvValue)
    {
    err = EdsSetPropertyData(camera, kEdsPropID_Tv, 0 , sizeof(TvValue), &TvValue);
    return err;
    }
     */

    @Override
    public EdsdkError setPropertyDataAdvanced(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property, final long inParam, final Object value) {


        return null;
    }
}
