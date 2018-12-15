package org.blackdread.cameraframework.api.helper.logic;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.constant.EdsCameraCommand;
import org.blackdread.cameraframework.api.constant.EdsCameraStatusCommand;
import org.blackdread.cameraframework.api.constant.EdsEvfAf;
import org.blackdread.cameraframework.api.constant.EdsEvfDriveLens;
import org.blackdread.cameraframework.api.constant.EdsShutterButton;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface CameraLogic {

    int DEFAULT_BYTES_PER_SECTOR = 512;

    /**
     * Should be called before each shooting when destination is set to computer, otherwise shooting may not work as camera will consider destination as full.
     *
     * @param camera the reference of the camera which will receive the command
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see #setCapacity(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef, int, int)
     */
    default void setCapacity(final EdsCameraRef camera) {
        setCapacity(camera, Integer.MAX_VALUE, DEFAULT_BYTES_PER_SECTOR);
    }

    /**
     * Should be called before each shooting when destination is set to computer, otherwise shooting may not work as camera will consider destination as full.
     * <br>
     * This method gives more flexibility with capacity.
     *
     * @param camera   the reference of the camera which will receive the command
     * @param capacity The remaining capacity of a transmission place
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see #setCapacity(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef, int, int)
     */
    default void setCapacity(final EdsCameraRef camera, final int capacity) {
        setCapacity(camera, capacity, DEFAULT_BYTES_PER_SECTOR);
    }

    /**
     * Sets the remaining HDD capacity on the host computer (excluding the portion from image transfer), as calculated by subtracting the portion from the previous time.
     * <br>
     * Set a reset flag initially and designate the cluster length and number of free clusters.
     * <br>
     * Some cameras can display the number of shots left on the camera based on the available disk capacity of the host computer.
     * <br>
     * For these cameras, after the storage destination is set to the computer, use this API to notify the camera of the available disk capacity of the host computer.
     *
     * @param camera         the reference of the camera which will receive the command
     * @param capacity       the remaining capacity of a transmission place.
     * @param bytesPerSector bytes taken for each sector (smaller means more space)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void setCapacity(final EdsCameraRef camera, final int capacity, final int bytesPerSector);


    /**
     * @param camera the reference of the camera which will receive the command
     * @return true if mirror lockup is enabled
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    boolean isMirrorLockupEnabled(final EdsCameraRef camera);


    /**
     * Send a command that does not require any parameter.
     *
     * @param camera  the reference of the camera which will receive the command
     * @param command command to send
     * @throws IllegalArgumentException if command is not supported by this method
     * @throws EdsdkErrorException      if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void sendCommand(final EdsCameraRef camera, final EdsCameraCommand command) {
        // Fail fast
        switch (command) {
            case kEdsCameraCommand_DoEvfAf:
            case kEdsCameraCommand_DriveLensEvf:
            case kEdsCameraCommand_DoClickWBEvf:
            case kEdsCameraCommand_PressShutterButton:
                throw new IllegalArgumentException("Command requires extra parameters");
        }
        sendCommand(camera, EdsCameraCommand.kEdsCameraCommand_TakePicture, 0);
    }

    /**
     * Count of camera connected to pc
     *
     * @return count of camera connected to pc
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default int getCameraConnectedCount() {
        final EdsdkLibrary.EdsCameraListRef.ByReference listRef = new EdsdkLibrary.EdsCameraListRef.ByReference();
        try {
            final EdsdkError cameraListError = toEdsdkError(edsdkLibrary().EdsGetCameraList(listRef));
            if (cameraListError != EdsdkError.EDS_ERR_OK) {
                throw cameraListError.getException();
            }

            final NativeLongByReference outRef = new NativeLongByReference();
            final EdsdkError childCountError = toEdsdkError(edsdkLibrary().EdsGetChildCount(listRef.getValue(), outRef));
            if (childCountError != EdsdkError.EDS_ERR_OK) {
                throw childCountError.getException();
            }
            final long numCams = outRef.getValue().longValue();
            return numCams < 0 ? 0 : (int) numCams;
        } finally {
            ReleaseUtil.release(listRef);
        }
    }

    /**
     * @return camera ref with session opened
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default EdsCameraRef openSession() {
        return openSession(OpenSessionOption.DEFAULT_OPEN_SESSION_OPTION);
    }

    /**
     * @param option option
     * @return camera ref with session opened
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    EdsCameraRef openSession(final OpenSessionOption option);

    /**
     * @param option option
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void closeSession(final CloseSessionOption option);

    /**
     * Controls shutter button operations.
     * <br>
     * This command is supported by the EOS 50D or EOS 5D Mark II or later cameras
     *
     * @param camera        the reference of the camera which will receive the command
     * @param shutterButton shutter parameter
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void sendCommand(final EdsCameraRef camera, final EdsShutterButton shutterButton) {
        sendCommand(camera, EdsCameraCommand.kEdsCameraCommand_PressShutterButton, shutterButton.value());
    }

    /**
     * Controls auto focus in live view mode.
     * <br>
     * This command is supported only in live view mode.
     *
     * @param camera the reference of the camera which will receive the command
     * @param evfAf  AF parameter
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void sendCommand(final EdsCameraRef camera, final EdsEvfAf evfAf) {
        sendCommand(camera, EdsCameraCommand.kEdsCameraCommand_DoEvfAf, evfAf.value());
    }

    /**
     * Drives the lens and adjusts focus.
     * <br>
     * This command is supported only in live view mode.
     *
     * @param camera       the reference of the camera which will receive the command
     * @param evfDriveLens drive lens parameter
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void sendCommand(final EdsCameraRef camera, final EdsEvfDriveLens evfDriveLens) {
        sendCommand(camera, EdsCameraCommand.kEdsCameraCommand_DriveLensEvf, evfDriveLens.value());
    }

    // TODO live view DoClickWBEvf

    /**
     * Sends a command such as "Shoot" to a remote camera.
     *
     * @param camera  the reference of the camera which will receive the command
     * @param command command to send
     * @param inParam additional property information (see Reference API for possible values)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void sendCommand(final EdsCameraRef camera, final EdsCameraCommand command, final long inParam) {
        final EdsdkError error = toEdsdkError(edsdkLibrary().EdsSendCommand(camera, new NativeLong(command.value()), new NativeLong(inParam)));
        if (error != EdsdkError.EDS_ERR_OK)
            throw error.getException();
    }

    /**
     * Sets the remote camera state or mode.
     *
     * @param camera        the reference of the camera which will receive the command
     * @param statusCommand designate the particular mode ID to set the camera to
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    default void sendStatusCommand(final EdsCameraRef camera, final EdsCameraStatusCommand statusCommand) {
        final EdsdkError error = toEdsdkError(edsdkLibrary().EdsSendStatusCommand(camera, new NativeLong(statusCommand.value()), new NativeLong(0)));
        if (error != EdsdkError.EDS_ERR_OK)
            throw error.getException();
    }
}
