/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStreamRef;
import org.blackdread.cameraframework.api.constant.EdsEvfOutputDevice;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.LiveViewLogic;
import org.blackdread.cameraframework.api.helper.logic.LiveViewReference;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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
        // Can decide to catch errors and return false instead
        final Long state = CanonFactory.propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_Evf_Mode);
        return 1L == state;
    }

    @Override
    public boolean isLiveViewEnabledByDownloadingOneImage(final EdsCameraRef camera) {
        try (final LiveViewReference imageReference = getLiveViewImageReference(camera)) {
            return true;
        } catch (EdsdkErrorException e) {
            log.warn("Live view seems off while testing status", e);
            return false;
        }
    }

    // TODO API ref says to retry download if EDS_ERR_OBJECT_NOTREADY is returned

    @Override
    public BufferedImage getLiveViewImage(final EdsCameraRef camera) {
        byte[] data = getLiveViewImageBuffer(camera);
        try {
            return ImageIO.read(new ByteArrayInputStream(data));
        } catch (final IOException e) {
            log.error("Error to build BufferedImage", e);
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public byte[] getLiveViewImageBuffer(final EdsCameraRef camera) {
        try (final LiveViewReference liveViewImageReference = getLiveViewImageReference(camera)) {

            final LongByReference length = new LongByReference();
            final EdsdkError error = toEdsdkError(edsdkLibrary().EdsGetLength(liveViewImageReference.getStreamRef().getValue(), length));
            if (error != EdsdkError.EDS_ERR_OK) {
                throw error.getException();
            }

            final PointerByReference dataRef = new PointerByReference();
            final EdsdkError dataError = toEdsdkError(edsdkLibrary().EdsGetPointer(liveViewImageReference.getStreamRef().getValue(), dataRef));
            if (dataError != EdsdkError.EDS_ERR_OK) {
                throw error.getException();
            }
            return dataRef.getValue().getByteArray(0, (int) length.getValue());
        }
    }

    @Override
    public LiveViewReference getLiveViewImageReference(final EdsCameraRef camera) {
        final EdsStreamRef.ByReference streamRef = new EdsStreamRef.ByReference();

        final EdsdkError streamError = toEdsdkError(edsdkLibrary().EdsCreateMemoryStream(0, streamRef));
        if (streamError != EdsdkError.EDS_ERR_OK) {
            ReleaseUtil.release(streamRef);
            throw streamError.getException();
        }

        final EdsEvfImageRef.ByReference imageRef = new EdsEvfImageRef.ByReference();
        final EdsdkError imageError = toEdsdkError(edsdkLibrary().EdsCreateEvfImageRef(streamRef.getValue(), imageRef));
        if (imageError != EdsdkError.EDS_ERR_OK) {
            ReleaseUtil.release(imageRef, streamRef);
            throw imageError.getException();
        }

        final EdsdkError downloadError = toEdsdkError(edsdkLibrary().EdsDownloadEvfImage(camera, imageRef.getValue()));
        if (downloadError != EdsdkError.EDS_ERR_OK) {
            ReleaseUtil.release(imageRef, streamRef);
            throw downloadError.getException();
        }

        return new LiveViewReference(streamRef, imageRef);
    }

}
