package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsPropertyDesc;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
public class PropertyDescLogicDefaultExtended extends PropertyDescLogicDefault {

    private static final Logger log = LoggerFactory.getLogger(PropertyDescLogicDefaultExtended.class);

    private EdsPropertyDesc edsPropertyDesc;

    @Override
    public EdsPropertyDesc getPropertyDescStructure(final EdsdkLibrary.EdsBaseRef ref, final EdsPropertyID property) {
        return edsPropertyDesc;
    }

    public void setPropertyDescStructure(EdsPropertyDesc edsPropertyDesc) {
        this.edsPropertyDesc = edsPropertyDesc;
    }
}
