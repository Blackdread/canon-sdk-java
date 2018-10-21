package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
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
    public boolean beginLiveView(final EdsdkLibrary.EdsCameraRef camera) {
        return false;
    }

    @Override
    public boolean endLiveView(final EdsdkLibrary.EdsCameraRef camera) {
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
        return null;
    }

    @Override
    public byte[] getLiveViewImageBuffer(final EdsdkLibrary.EdsCameraRef camera) {
        return new byte[0];
    }

}
