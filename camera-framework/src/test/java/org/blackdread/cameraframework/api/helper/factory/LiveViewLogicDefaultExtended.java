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

    @Override
    public LiveViewReference getLiveViewImageReference(final EdsdkLibrary.EdsCameraRef camera) {
        if (liveViewReference != null)
            return liveViewReference;
        return super.getLiveViewImageReference(camera);
    }

    public void setLiveViewReference(final LiveViewReference liveViewReference) {
        this.liveViewReference = liveViewReference;
    }
}
