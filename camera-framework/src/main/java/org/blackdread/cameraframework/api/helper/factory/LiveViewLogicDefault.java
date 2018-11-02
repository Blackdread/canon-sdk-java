package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.logic.LiveViewLogic;

import java.awt.image.BufferedImage;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class LiveViewLogicDefault implements LiveViewLogic {

    protected LiveViewLogicDefault() {
    }

    @Override
    public void beginLiveView(final EdsCameraRef camera, final EdsEvfOutputDevice edsEvfOutputDevice) {
        // Force to set evf mode to enabled
        enableLiveView(camera);

        CanonFactory.propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, edsEvfOutputDevice);
    }

    @Override
    public void endLiveView(final EdsCameraRef camera) {
        CanonFactory.propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_OutputDevice, EdsEvfOutputDevice.kEdsEvfOutputDevice_TFT);

        // disable live view so to set normal mode back to camera
        disableLiveView(camera);
    }

    @Override
    public boolean isLiveViewEnabled(final EdsCameraRef camera) {
        final Long state = CanonFactory.propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode);
        return 1L == state;
    }

    @Override
    public boolean isLiveViewEnabledByDownloadingOneImage(final EdsCameraRef camera) {

        return false;
    }

    @Override
    public EdsdkLibrary.EdsBaseRef.ByReference[] getLiveViewImageReference(final EdsCameraRef camera) {
        return new EdsdkLibrary.EdsBaseRef.ByReference[0];
    }

    @Override
    public BufferedImage getLiveViewImage(final EdsCameraRef camera) {
        /*
        EdsError err = EDS_ERR_OK;
        EdsStreamRef stream = NULL;
        EdsEvfImageRef evfImage = NULL;
        // Create memory stream.
        err = EdsCreateMemoryStream( 0, &stream);
        // Create EvfImageRef.
        if(err == EDS_ERR_OK)
        {
        err = EdsCreateEvfImageRef(stream, &evfImage);
        }
        // Download live view image data.
        if(err == EDS_ERR_OK)
        {
        err = EdsDownloadEvfImage(camera, evfImage);
        }
        // Get the incidental data of the image.
        if(err == EDS_ERR_OK)
        {
        // Get the zoom ratio
        EdsUInt32 zoom;
        EdsGetPropertyData(evfImage, kEdsPropID_Evf_ZoomPosition, 0 , sizeof(zoom), &zoom);
        // Get the focus and zoom border position
        EdsPoint point;
        EdsGetPropertyData(evfImage, kEdsPropID_Evf_ZoomPosition, 0 , sizeof(point), &point);
        }
        //
        // Display image
        //
        // Release stream
        if(stream != NULL)
        {
        EdsRelease(stream);
        stream = NULL;
        }
        // Release evfImage
        if(evfImage != NULL)
        {
        EdsRelease(evfImage);
        evfImage = NULL;
        }
        return err;
         */
        return null;
    }

    @Override
    public byte[] getLiveViewImageBuffer(final EdsCameraRef camera) {
        return new byte[0];
    }

}
