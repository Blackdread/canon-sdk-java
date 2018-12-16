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

import com.google.common.collect.ImmutableList;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.constant.EdsImageQuality;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.NameToBeDefined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.*;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class ShootLogicDefault implements ShootLogic {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected ShootLogicDefault() {
    }

    public List<File> shoot(final EdsCameraRef camera, final ShootOption option) throws InterruptedException {
        final EdsImageQuality imageQuality;
        final int expectedFileCount;
        final CameraObjectListener cameraObjectListener;
        /*
         * Mutable as to let event listener populate with files downloaded
         */
        final List<File> filesSavedOnPc = new CopyOnWriteArrayList<>();
        /*
         * Receiver of exception that may happen in objectListener.
         * We should be able to stop waiting to receive file events in case of error.
         */
        final AtomicReference<RuntimeException> cameraObjectListenerException = new AtomicReference<>();

        if (option.isWaitForItemDownloadEvent()) {
            imageQuality = CanonFactory.propertyGetShortcutLogic().getImageQuality(camera);
            expectedFileCount = imageQuality.getExpectedFileCount();
            if (expectedFileCount <= 0) {
                log.error("No files is expected from the shooting mode: {}", imageQuality);
                throw new IllegalStateException("No files is expected from the shooting mode: " + imageQuality);
            }
            cameraObjectListener = handleObjectEvent(option, expectedFileCount, filesSavedOnPc, cameraObjectListenerException);
            cameraObjectEventLogic().addCameraObjectListener(camera, cameraObjectListener);
        } else {
            expectedFileCount = 0;
            cameraObjectListener = null;
        }

        option.getSaveTo().ifPresent(edsSaveTo -> propertySetLogic().setPropertyData(camera, EdsPropertyID.kEdsPropID_SaveTo, edsSaveTo));

        cameraLogic().setCapacity(camera);

        // check evf state
        final boolean liveViewWasOn = checkLiveViewState(camera, option);
        try {
            try {
                shootLoopLogic(camera, option);

                // if no error then block until receive events
                if (option.isWaitForItemDownloadEvent()) {
                    // TODO we could add a timeout in ShootOption, this timeout is very specific (command also has a timeout) as shoot needs more fine-grained timeouts on this part of the code
                    while (filesSavedOnPc.size() < expectedFileCount) {
                        if (cameraObjectListenerException.get() != null) {
                            throw cameraObjectListenerException.get();
                        }
                        if (option.isFetchEvents()) {
                            // Fetch events can work for here but should be careful with thread that can or not get events
                            // See documentation for event logic
                            fetchEvents();
                        }
                        Thread.sleep(option.getBusyWaitMillis());
                    }
                    return ImmutableList.copyOf(filesSavedOnPc);
                }
                return Collections.emptyList();
            } finally {
                if (option.isWaitForItemDownloadEvent()) {
                    cameraObjectEventLogic().removeCameraObjectListener(camera, cameraObjectListener);
                }
            }
        } finally {
            // restore evf state
            if (liveViewWasOn) {
                restoreLiveViewState(camera, option);
            }
        }
    }

    /**
     * Listener of a shooting, should be unregistered after shooting (error or no error)
     *
     * @param option                        options for shoot
     * @param expectedFileCount             file count expected to be seen by this handler (created per shooting)
     * @param filesSavedOnPc                mutable list of files, files are added by handler when it receives an event and successfully download a file
     * @param cameraObjectListenerException atomic ref to receive exception if any occurred
     * @return new listener instance to receive events for one shooting
     */
    protected CameraObjectListener handleObjectEvent(final ShootOption option, final int expectedFileCount, final List<File> filesSavedOnPc, final AtomicReference<RuntimeException> cameraObjectListenerException) {
        return event -> {
            if (event.getObjectEvent() == EdsObjectEvent.kEdsObjectEvent_DirItemCreated
                || event.getObjectEvent() == EdsObjectEvent.kEdsObjectEvent_DirItemRequestTransfer) {

                final EdsdkLibrary.EdsDirectoryItemRef itemRef = new EdsdkLibrary.EdsDirectoryItemRef(event.getBaseRef().getPointer());

                try {
                    final File downloadedFile = fileLogic().download(itemRef, option.getFolderDestination().orElse(null), option.getFilename().orElse(null));
                    filesSavedOnPc.add(downloadedFile);
                } catch (RuntimeException e) {
                    cameraObjectListenerException.set(e);
                    fileLogic().downloadCancel(itemRef);
                    // TODO not sure to throw, handlers are not supposed to throw exceptions
                    throw e;
                }
            }
        };
    }

    /**
     * Actual logic to send shoot command to camera
     * <p>
     * In case of success to execute a shoot command then no exception should be thrown (even if one was previously caught).
     * On the opposite, if no shoot command succeed then an exception must be thrown (best to throw the last exception returned by the camera).
     * </p>
     *
     * @param camera ref of camera
     * @param option options for shoot
     */
    protected void shootLoopLogic(final EdsCameraRef camera, final ShootOption option) {
        EdsdkErrorException lastExceptionOfShoot = null;
        final int shootAttempts = option.getShootAttemptCount();
        for (int shootAttempt = 0; shootAttempt < shootAttempts; shootAttempt++) {

            if (option.isShootWithV0()) {
                final ShootResultWrapper resultWrapper = shootWithV0(camera, option);
                if (resultWrapper.isShootSuccess()) {
                    return;
                } else {
                    if (resultWrapper.getException().isPresent()) { // should always be present if here
                        lastExceptionOfShoot = resultWrapper.getException().get();
                    }
                }
            }

            if (option.isShootWithNoAF()) {
                final ShootResultWrapper resultWrapper = shootWithNoAF(camera, option);
                if (resultWrapper.isShootSuccess()) {
                    return;
                } else {
                    if (resultWrapper.getException().isPresent()) { // should always be present if here
                        lastExceptionOfShoot = resultWrapper.getException().get();
                    }
                }
            }

            if (option.isShootWithAF()) {
                final ShootResultWrapper resultWrapper = shootWithAF(camera, option);
                if (resultWrapper.isShootSuccess()) {
                    return;
                } else {
                    if (resultWrapper.getException().isPresent()) { // should always be present if here
                        lastExceptionOfShoot = resultWrapper.getException().get();
                    }
                }
            }
        }
        if (lastExceptionOfShoot != null) {
            throw lastExceptionOfShoot;
        }
        throw new IllegalStateException("No exception was thrown but reached end of shoot loop logic");
    }

    /**
     * Checks if live view is active, if yes then is paused then restarted after shoot.
     * <b>Always called</b>.
     *
     * @param camera ref of camera
     * @param option options for shoot
     * @return true if live view was ON
     */
    protected boolean checkLiveViewState(final EdsCameraRef camera, final ShootOption option) {
        final boolean liveViewWasOn;
        if (option.isCheckLiveViewState()) {
            liveViewWasOn = liveViewLogic().isLiveViewEnabledByDownloadingOneImage(camera);
            if (liveViewWasOn) {
                liveViewLogic().disableLiveView(camera);
                // Might want to sleep a bit here so try to not have busy error after
            }
        } else {
            liveViewWasOn = false;
        }
        return liveViewWasOn;
    }

    /**
     * Called if live view was previously checked and was active
     *
     * @param camera ref of camera
     * @param option options for shoot
     */
    protected void restoreLiveViewState(final EdsCameraRef camera, final ShootOption option) {
        liveViewLogic().enableLiveView(camera);
    }

    protected ShootResultWrapper shootWithV0(final EdsCameraRef camera, final ShootOption option) {
        try {
            NameToBeDefined.wrap(() -> shootV0(camera))
                .retryOnBusy(200, 200, 100, 150, 200)
                .run();
        } catch (EdsdkErrorException e) {
            log.warn("Exception while trying to use shootV0", e);
            return new ShootResultWrapper(e);
        }
        return new ShootResultWrapper();
    }

    protected ShootResultWrapper shootWithNoAF(final EdsCameraRef camera, final ShootOption option) {
        try {
            NameToBeDefined.wrap(() -> shootNoAF(camera))
                .retryOnBusy(200, 200, 100, 150, 200)
                .run();
        } catch (EdsdkErrorException e) {
            log.warn("Exception while trying to use shootNoAF", e);
            return new ShootResultWrapper(e);
        }
        return new ShootResultWrapper();
    }

    protected ShootResultWrapper shootWithAF(final EdsCameraRef camera, final ShootOption option) {
        try {
            NameToBeDefined.wrap(() -> shootAF(camera))
                .retryOnBusy(200, 200, 100, 150, 200)
                .run();
        } catch (EdsdkErrorException e) {
            log.warn("Exception while trying to use shootAF", e);
            return new ShootResultWrapper(e);
        }
        return new ShootResultWrapper();
    }

    protected void fetchEvents() {
        CanonFactory.edsdkLibrary().EdsGetEvent();
    }

    protected class ShootResultWrapper {

        private final boolean shootSuccess;
        private final EdsdkErrorException exception;

        /**
         * Shoot success
         */
        public ShootResultWrapper() {
            this.shootSuccess = true;
            this.exception = null;
        }

        /**
         * Shoot failed with exception
         *
         * @param exception exception that was thrown
         */
        public ShootResultWrapper(final EdsdkErrorException exception) {
            this.shootSuccess = false;
            this.exception = Objects.requireNonNull(exception);
        }

        public boolean isShootSuccess() {
            return shootSuccess;
        }

        public Optional<EdsdkErrorException> getException() {
            return Optional.ofNullable(exception);
        }

        @Override
        public String toString() {
            return "ShootResultWrapper{" +
                "shootSuccess=" + shootSuccess +
                ", exception=" + exception +
                '}';
        }
    }

}
