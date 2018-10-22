package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
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
    public boolean beginLiveView(final EdsdkLibrary.EdsCameraRef camera, final EdsEvfOutputDevice edsEvfOutputDevice) {
        /*
        EdsError err = EDS_ERR_OK;
        // Get the output device for the live view image
        EdsUInt32 device;
        err = EdsGetPropertyData(camera, kEdsPropID_Evf_OutputDevice, 0 , sizeof(device), &device );
        // PC live view starts by setting the PC as the output device for the live view image.
        if(err == EDS_ERR_OK)
        {
        device |= kEdsEvfOutputDevice_PC;
        err = EdsSetPropertyData(camera, kEdsPropID_Evf_OutputDevice, 0 , sizeof(device), &device);
        }
        // A property change event notification is issued from the camera if property settings are made successfully.
        // Start downloading of the live view image once the property change notification arrives.
        return err;
         */
        final NativeLongByReference number = new NativeLongByReference(new NativeLong(edsEvfOutputDevice.value()));
        final Pointer data = number.getPointer();
        CanonFactory.propertyLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode, 0, NativeLong.SIZE, data);
        return false;
    }

    @Override
    public boolean endLiveView(final EdsdkLibrary.EdsCameraRef camera) {
        /*
        EdsError err = EDS_ERR_OK;
        // Get the output device for the live view image
        EdsUInt32 device;
        err = EdsGetPropertyData(camera, kEdsPropID_Evf_OutputDevice, 0 , sizeof(device), &device );
        // PC live view ends if the PC is disconnected from the live view image output device.
        if(err == EDS_ERR_OK)
        {
        device &= ~kEdsEvfOutputDevice_PC;
        err = EdsSetPropertyData(camera, kEdsPropID_Evf_OutputDevice, 0 , sizeof(device), &device);
        }
        return err;
         */
        return false;
    }

    @Override
    public boolean isLiveViewEnabled(final EdsdkLibrary.EdsCameraRef camera) {
        return false;
    }

    @Override
    public boolean isLiveViewEnabledByDownloadingOneImage(final EdsdkLibrary.EdsCameraRef camera) {
        return false;
    }

    @Override
    public EdsdkLibrary.EdsBaseRef.ByReference[] getLiveViewImageReference(final EdsdkLibrary.EdsCameraRef camera) {
        return new EdsdkLibrary.EdsBaseRef.ByReference[0];
    }

    @Override
    public BufferedImage getLiveViewImage(final EdsdkLibrary.EdsCameraRef camera) {
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
    public byte[] getLiveViewImageBuffer(final EdsdkLibrary.EdsCameraRef camera) {
        return new byte[0];
    }

}
