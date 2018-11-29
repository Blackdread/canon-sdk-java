package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsSaveTo;

import java.io.File;
import java.util.Optional;

/**
 * Options for shoot commands, instead of having many different methods (with different parameters) to shoot, we provide one wrapper of options
 * <p>Created on 2018/11/24.</p>
 *
 * @author Yoann CAPLAIN
 */
public class ShootOption {

    /**
     * Number of times shoot will be retried on error returned by shoot command.
     * That is number of times the complete logic of shooting retried, not each different type of shooting
     */
    private final int shootAttemptCount;

    private final int shootRetryCount;

    /**
     * If true the live view state will be <b>checked</b> and <b>paused</b> for shoot commands (re-enabled after shoot if was ON).
     * <br>
     * It is not mandatory to check as the camera will switch automatically from EVF mode to shoot then back to EVF mode but in case that some camera does not have same behavior, this option is available.
     */
    private final boolean checkLiveViewState;

    /**
     * If true then use {@link org.blackdread.cameraframework.api.helper.logic.ShootLogic#shootV0(EdsdkLibrary.EdsCameraRef)}.
     * Order in which shoots are done is implementation dependent, default is V0 then NoAF then AF.
     */
    private final boolean shootWithV0;

    /**
     * If true then use {@link org.blackdread.cameraframework.api.helper.logic.ShootLogic#shootNoAF(EdsdkLibrary.EdsCameraRef)} (EdsdkLibrary.EdsCameraRef)}.
     * Order in which shoots are done is implementation dependent, default is V0 then NoAF then AF.
     */
    private final boolean shootWithNoAF;

    /**
     * If true then use {@link org.blackdread.cameraframework.api.helper.logic.ShootLogic#shootAF(EdsdkLibrary.EdsCameraRef)} (EdsdkLibrary.EdsCameraRef)}.
     * Order in which shoots are done is implementation dependent, default is V0 then NoAF then AF.
     */
    private final boolean shootWithAF;

    /**
     * If not null, set saveTo of camera.
     * If PC is destination part of the destination, user should also set to true {@link #waitForItemDownloadEvent} to let implementation to automatically download the shot file.
     */
    private final EdsSaveTo saveTo;

    /**
     * If true, command will wait (register an event listener) until it receives {@link EdsObjectEvent#kEdsObjectEvent_DirItemCreated} or {@link EdsObjectEvent#kEdsObjectEvent_DirItemRequestTransfer} to return, after successfully executed one of the shoot commands.
     * <br>
     * If false, command will not register an event listener and will not wait for any event after successfully executed one of the shoot commands.
     */
    private final boolean waitForItemDownloadEvent;

    /**
     * If shoot is to wait for download events, it will sleep in a loop
     */
    private final long busyWaitMillis;

    private final File folderDestination;

    private final String filename;

    // TODO will use a builder after (current constructor is to let it compile)
    private ShootOption(final int shootAttemptCount, final int shootRetryCount, final boolean checkLiveViewState, final boolean shootWithV0, final boolean shootWithNoAF, final boolean shootWithAF, final EdsSaveTo saveTo, final boolean waitForItemDownloadEvent, final long busyWaitMillis, final File folderDestination, final String filename) {
        this.shootAttemptCount = shootAttemptCount;
        this.shootRetryCount = shootRetryCount;
        this.checkLiveViewState = checkLiveViewState;
        this.shootWithV0 = shootWithV0;
        this.shootWithNoAF = shootWithNoAF;
        this.shootWithAF = shootWithAF;
        this.saveTo = saveTo;
        this.waitForItemDownloadEvent = waitForItemDownloadEvent;
        this.busyWaitMillis = busyWaitMillis >= 1 ? busyWaitMillis : 1;
        this.folderDestination = folderDestination;
        this.filename = filename;
    }

    public int getShootAttemptCount() {
        return shootAttemptCount;
    }

    public int getShootRetryCount() {
        return shootRetryCount;
    }

    public boolean isCheckLiveViewState() {
        return checkLiveViewState;
    }

    public boolean isShootWithV0() {
        return shootWithV0;
    }

    public boolean isShootWithNoAF() {
        return shootWithNoAF;
    }

    public boolean isShootWithAF() {
        return shootWithAF;
    }

    public boolean isWaitForItemDownloadEvent() {
        return waitForItemDownloadEvent;
    }

    public long getBusyWaitMillis() {
        return busyWaitMillis;
    }

    public Optional<EdsSaveTo> getSaveTo() {
        return Optional.ofNullable(saveTo);
    }

    public File getFolderDestination() {
        return folderDestination;
    }

    public String getFilename() {
        return filename;
    }
}
