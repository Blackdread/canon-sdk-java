package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStreamRef;
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.LiveViewLogic;
import org.blackdread.cameraframework.api.helper.logic.LiveViewReference;
import org.blackdread.cameraframework.util.ErrorUtil;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class LiveViewLogicDefault implements LiveViewLogic {

    private static final Logger log = LoggerFactory.getLogger(LiveViewLogicDefault.class);

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
        try (final LiveViewReference imageReference = getLiveViewImageReference(camera)) {
            return true;
        } catch (IOException e) {
            log.error("Error ", e);
//            throw new UncheckedIOException(e);
            return false;
        }
    }

    @Override
    public LiveViewReference getLiveViewImageReference(final EdsCameraRef camera) {
        final EdsStreamRef.ByReference streamRef = new EdsStreamRef.ByReference();

        final EdsdkError streamError = ErrorUtil.toEdsdkError(CanonFactory.edsdkLibrary().EdsCreateMemoryStream(0, streamRef));
        if (streamError != EdsdkError.EDS_ERR_OK) {
            ReleaseUtil.release(streamRef);
            throw streamError.getException();
        }

        final EdsEvfImageRef.ByReference imageRef = new EdsEvfImageRef.ByReference();
        final EdsdkError imageError = ErrorUtil.toEdsdkError(CanonFactory.edsdkLibrary().EdsCreateEvfImageRef(streamRef.getValue(), imageRef));
        if (imageError != EdsdkError.EDS_ERR_OK) {
            ReleaseUtil.release(imageRef, streamRef);

            throw streamError.getException();
        }
        final EdsdkError downloadError = ErrorUtil.toEdsdkError(CanonFactory.edsdkLibrary().EdsDownloadEvfImage(camera, imageRef.getValue()));
        if (downloadError != EdsdkError.EDS_ERR_OK) {
            ReleaseUtil.release(imageRef, streamRef);
            throw streamError.getException();
        }

        return null;
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
