package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.constant.EdsImageQuality;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
import org.blackdread.cameraframework.util.NameToBeDefined;

import java.io.File;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.cameraObjectEventLogic;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.fileLogic;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.liveViewLogic;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class ShootLogicDefault implements ShootLogic {

    protected ShootLogicDefault() {
    }

    // if also handle download of files then:
    //    - Get how many jpgs expected after shoot
    //    - register listeners
    //    - download when events are received
    //    - unregister listeners
    //    - return File list


    // Set save to of camera
    // if save to is PC or both then run setCapacity
    // if live view is ON then turn it off (optional)
    // take picture:
    //    - with V0, if no error then just do with that (if busy, retry)
    //    - if V0 does not work, try with AF or NoAF, retry on busy
    //    - if saveTo is PC or both, fetch events to get items then download then return File

    // could use ThreadLocal to help for some state logic here

    public void shoot(final EdsCameraRef camera) {
        // TODO call other shoot method with default option
    }

    public void shoot(final EdsCameraRef camera, final ShootOption option) {

        final EdsImageQuality imageQuality;
        final int expectedFileCount;
        final CameraObjectListener cameraObjectListener;

        if (option.isWaitForItemDownloadEvent()) {
            imageQuality = CanonFactory.propertyGetShortcutLogic().getImageQuality(camera);
            expectedFileCount = imageQuality.getExpectedFileCount();
            cameraObjectListener = handleObjectEvent(option, expectedFileCount);
            cameraObjectEventLogic().addCameraObjectListener(camera, cameraObjectListener);
        }

        // check evf state
        final boolean liveViewWasOn = checkLiveViewState(camera, option);
        try {
            final int shootAttempts = option.getShootAttemptCount();
            for (int shootAttempt = 0; shootAttempt < shootAttempts; shootAttempt++) {

                // TODO not finished

                NameToBeDefined.wrap(() -> shootV0(camera))
                    .retryOnBusy(200, 200, 100, 150, 200)
                    .handle(e -> 55);

                try {
                    shootV0(camera);
                } catch (EdsdkErrorException e) {

                }

                try {
                    shootNoAF(camera);
                } catch (EdsdkErrorException e) {

                }

            }
        } finally {
            // restore evf state
            restoreLiveViewState(camera, option, liveViewWasOn);
        }
    }

    protected CameraObjectListener handleObjectEvent(final ShootOption option, final int expectedFileCount) {
        return event -> {
            if (event.getObjectEvent() == EdsObjectEvent.kEdsObjectEvent_DirItemCreated
                || event.getObjectEvent() == EdsObjectEvent.kEdsObjectEvent_DirItemRequestTransfer) {

                final EdsdkLibrary.EdsDirectoryItemRef itemRef = new EdsdkLibrary.EdsDirectoryItemRef(event.getBaseRef().getPointer());

                final File downloadedFile = fileLogic().download(itemRef, option.getFolderDestination(), option.getFilename());

            }
        };
    }

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

    protected void restoreLiveViewState(final EdsCameraRef camera, final ShootOption option, final boolean liveViewWasOn) {
        if (option.isCheckLiveViewState() && liveViewWasOn) {
            liveViewLogic().enableLiveView(camera);
        }
    }

    protected boolean shootWithV0(final EdsCameraRef camera, final ShootOption option) {
        try {
            NameToBeDefined.wrap(() -> shootV0(camera))
                .retryOnBusy(200, 200, 100, 150, 200)
                .handle(e -> 55);
        } catch (EdsdkErrorException e) {
            return false;
        }
        return true;
    }

}
