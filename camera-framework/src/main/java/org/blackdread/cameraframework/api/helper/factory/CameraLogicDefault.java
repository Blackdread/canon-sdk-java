package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsCapacity;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.constant.EdsCustomFunction;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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

    @Override
    public EdsCameraRef openSession(final OpenSessionOption option) {

        final EdsdkLibrary.EdsCameraListRef.ByReference listRef = new EdsdkLibrary.EdsCameraListRef.ByReference();


        EdsdkError edsdkError;
        try {
            edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetCameraList(listRef));
            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }

            final NativeLongByReference outRef = new NativeLongByReference();
            edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetChildCount(listRef.getValue(), outRef));
            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }

            final long numCams = outRef.getValue().longValue();
            if (numCams <= 0) {
                throw EdsdkError.EDS_ERR_DEVICE_NOT_FOUND.getException();
            }

            if (option.getCameraByIndex().isPresent() && option.getCameraByIndex().get() >= numCams) {
                throw EdsdkError.EDS_ERR_DEVICE_NOT_FOUND.getException();
            }

            for (int i = 0; i < numCams; i++) {
                if (option.getCameraByIndex().isPresent() && option.getCameraByIndex().get() != i) {
                    continue;
                }

                final EdsdkLibrary.EdsCameraRef.ByReference cameraRef = new EdsdkLibrary.EdsCameraRef.ByReference();

                edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetChildAtIndex(listRef.getValue(), new NativeLong(i), cameraRef));
                if (edsdkError != EdsdkError.EDS_ERR_OK) {
                    throw edsdkError.getException();
                }

                // TODO maybe before open session, could test if session is not already open

                // Session is not automatically closed if the camera does not match, we could auto-close if we test before if it was already open or not
                edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsOpenSession(cameraRef.getValue()));
                if (edsdkError != EdsdkError.EDS_ERR_OK) {
                    ReleaseUtil.release(cameraRef);
                    throw edsdkError.getException();
                }

                final String bodyIDEx = CanonFactory.propertyGetShortcutLogic().getBodyIDEx(cameraRef.getValue());

                if (bodyIDEx == null) {
                    log.warn("BodyIDEx returned was null");
                    ReleaseUtil.release(cameraRef);
                    // TODO we can throw or continue next iteration but no reason for BodyIDEx to be null...
                    throw new IllegalStateException("BodyIDEx is null");
                } else {
                    if (option.getCameraBySerialNumber().isPresent() && option.getCameraBySerialNumber().get().equalsIgnoreCase(bodyIDEx)) {
                        return cameraRef.getValue();
                    }
                    if (option.getCameraByIndex().isPresent()) {
                        // it means we are looking by index
                        return cameraRef.getValue();
                    }

                    // No match then next iteration of loop
                    ReleaseUtil.release(cameraRef);
                }
            }

            throw new IllegalStateException("No camera found matching index or serial number");
        } finally {
            ReleaseUtil.release(listRef);
        }
    }

}
