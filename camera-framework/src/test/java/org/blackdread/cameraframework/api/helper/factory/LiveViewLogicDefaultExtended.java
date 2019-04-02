package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.helper.logic.LiveViewReference;

/**
 * <p>Created on 2019/04/02.</p>
 *
 * @author Yoann CAPLAIN
 */
class LiveViewLogicDefaultExtended extends LiveViewLogicDefault {

    private LiveViewReference liveViewReference;

    private RuntimeException throwOnGetLiveViewImageReference;

    private byte[] liveViewBuffer;

    @Override
    public byte[] getLiveViewImageBuffer(final EdsdkLibrary.EdsCameraRef camera) {
        if (liveViewBuffer != null)
            return liveViewBuffer;
        return super.getLiveViewImageBuffer(camera);
    }

    @Override
    public LiveViewReference getLiveViewImageReference(final EdsdkLibrary.EdsCameraRef camera) {
        if (throwOnGetLiveViewImageReference != null)
            throw throwOnGetLiveViewImageReference;
        if (liveViewReference != null)
            return liveViewReference;
        return super.getLiveViewImageReference(camera);
    }

    public void setLiveViewReference(final LiveViewReference liveViewReference) {
        this.liveViewReference = liveViewReference;
    }

    public void setThrowOnGetLiveViewImageReference(final RuntimeException throwOnGetLiveViewImageReference) {
        this.throwOnGetLiveViewImageReference = throwOnGetLiveViewImageReference;
    }

    public void setLiveViewBuffer(final byte[] liveViewBuffer) {
        this.liveViewBuffer = liveViewBuffer;
    }
}
