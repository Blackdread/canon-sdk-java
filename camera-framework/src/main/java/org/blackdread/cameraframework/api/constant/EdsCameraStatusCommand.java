package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Camera Status Commands
 * <br>
 * Sets the remote camera state or mode.
 * <p>These are pairs of commands to lock and unlock the UI, as well as to put the camera in direct transfer mode
 * and exit this mode. If you switch modes by means of EdsSendStatusCommand, use
 * EdsSendStatusCommand again to restore the original mode.</p>
 * <br>
 * See API Reference - 3.1.15 EdsSendStatusCommand
 * <p>Created on 2018/10/07.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsCameraStatusCommand implements NativeEnum<Integer> {
    /**
     * In this state, all operations of the camera unit are disabled and only operations from the host PC are accepted.
     * <br>
     * This allows data and instructions to be safely sent from the host PC to the camera
     */
    kEdsCameraStatusCommand_UILock("Locks the UI"),
    /**
     * In this state, operations of the camera unit are enabled. Although data and instructions
     * can be sent from the host PC to the camera in this state, conflicts may arise
     */
    kEdsCameraStatusCommand_UIUnLock("Unlocks the UI"),
    /**
     * In this state, the camera is currently directly transferring data.
     * <br>
     * Available camera operations are limited to those functions related to the direct transfer.
     * <br>
     * It is possible to send instructions from the PC to the camera in this state.
     * <br>
     * A direct transfer request event notification (kEdsObjectEvent_DirItemRequestTransferDT) is issued to the
     * EDSDK client application connected to the camera when an operation for starting image download is initiated
     * using camera controls. The EDSDK client application receives this event and begins processing for downloading
     * images from the camera.
     */
    kEdsCameraStatusCommand_EnterDirectTransfer("Puts the camera in direct transfer mode"),
    /**
     * This state indicates that direct transfer is not currently being carried out.
     */
    kEdsCameraStatusCommand_ExitDirectTransfer("Ends direct transfer mode");

    private final int value;
    private final String description;

    EdsCameraStatusCommand(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.class, name());
        this.description = description;
    }

    @Override
    public final Integer value() {
        return value;
    }

    @Override
    public final String description() {
        return description;
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsCameraStatusCommand ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsCameraStatusCommand.class, value);
    }

}
