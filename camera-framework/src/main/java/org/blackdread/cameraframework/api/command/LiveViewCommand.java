package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.awt.image.BufferedImage;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.liveViewLogic;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class LiveViewCommand<R> extends AbstractCanonCommand<R> {

    public LiveViewCommand() {
    }

    public LiveViewCommand(final LiveViewCommand toCopy) {
        super(toCopy);
    }


    public static class Begin extends LiveViewCommand<Void> {

        public Begin() {
        }

        public Begin(final Begin toCopy) {
            super(toCopy);
        }

        @Override
        protected Void runInternal() {
            liveViewLogic().beginLiveView((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
            return null;
        }
    }

    public static class End extends LiveViewCommand<Void> {

        public End() {
        }

        public End(final End toCopy) {
            super(toCopy);
        }

        @Override
        protected Void runInternal() {
            liveViewLogic().endLiveView((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
            return null;
        }
    }

    public static class Download extends LiveViewCommand<BufferedImage> {

        public Download() {
        }

        public Download(final Download toCopy) {
            super(toCopy);
        }

        @Override
        protected BufferedImage runInternal() {
            return liveViewLogic().getLiveViewImage((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class DownloadBuffer extends LiveViewCommand<byte[]> {

        public DownloadBuffer() {
        }

        public DownloadBuffer(final DownloadBuffer toCopy) {
            super(toCopy);
        }

        @Override
        protected byte[] runInternal() {
            return liveViewLogic().getLiveViewImageBuffer((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class IsLiveViewEnabled extends LiveViewCommand<Boolean> {

        public IsLiveViewEnabled() {
        }

        public IsLiveViewEnabled(final IsLiveViewEnabled toCopy) {
            super(toCopy);
        }

        @Override
        protected Boolean runInternal() {
            return liveViewLogic().isLiveViewEnabled((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class IsLiveViewActive extends LiveViewCommand<Boolean> {

        public IsLiveViewActive() {
        }

        public IsLiveViewActive(final IsLiveViewActive toCopy) {
            super(toCopy);
        }

        @Override
        protected Boolean runInternal() {
            return liveViewLogic().isLiveViewEnabledByDownloadingOneImage((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }
}
