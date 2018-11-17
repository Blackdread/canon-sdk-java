package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsCapacity;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsCustomFunction;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class CameraLogicDefault implements CameraLogic {

    private static final Logger log = LoggerFactory.getLogger(CameraLogicDefault.class);

    protected CameraLogicDefault() {
    }

    @Override
    public void setCapacity(final EdsCameraRef camera, final int capacity, final int bytesPerSector) {
        final EdsCapacity.ByValue edsCapacity = new EdsCapacity.ByValue();
        edsCapacity.bytesPerSector = new NativeLong(bytesPerSector);
        edsCapacity.numberOfFreeClusters = new NativeLong(capacity / edsCapacity.bytesPerSector.intValue());
        //  To make sure that flag is reset at each set capacity, so can keep shooting images
        edsCapacity.reset = 1;
        final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsSetCapacity(camera, edsCapacity));
        if (error != EdsdkError.EDS_ERR_OK)
            throw error.getException();
    }

    @Override
    public boolean isMirrorLockupEnabled(final EdsCameraRef camera) {
        try {
            final Long result = CanonFactory.propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value());
            return 1L == result;
        } catch (final IllegalArgumentException e) {
            log.warn("Could not check if mirror lockup enabled", e);
        }
        return false;
    }

}
