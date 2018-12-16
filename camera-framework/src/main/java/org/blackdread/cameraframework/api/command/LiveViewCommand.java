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
@SuppressWarnings("unused")
public abstract class LiveViewCommand<R> extends AbstractCanonCommand<R> {

    protected LiveViewCommand() {
    }

    protected LiveViewCommand(final LiveViewCommand toCopy) {
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
