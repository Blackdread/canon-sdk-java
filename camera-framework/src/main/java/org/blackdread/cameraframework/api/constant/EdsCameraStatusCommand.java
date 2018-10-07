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
    kEdsCameraStatusCommand_UILock("Locks the UI"),
    kEdsCameraStatusCommand_UIUnLock("Unlocks the UI"),
    kEdsCameraStatusCommand_EnterDirectTransfer("Puts the camera in direct transfer mode"),
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

}
