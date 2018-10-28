package org.blackdread.cameraframework.api.helper.logic;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;

/**
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CameraLogic {

    int DEFAULT_BYTES_PER_SECTOR = 512;

    /**
     * Should be called before each shooting when destination is set to computer, otherwise shooting will be disabled
     *
     * @param ref the reference of the camera which will receive the command
     * @see #setCapacity(EdsCameraRef, int, int)
     */
    default void setCapacity(final EdsCameraRef ref) {
        setCapacity(ref, Integer.MAX_VALUE, DEFAULT_BYTES_PER_SECTOR);
    }

    /**
     * Should be called before each shooting when destination is set to computer, otherwise shooting will be disabled.
     * This method gives more flexibility with capacity
     *
     * @param ref      the reference of the camera which will receive the command
     * @param capacity The remaining capacity of a transmission place
     * @see #setCapacity(EdsCameraRef, int, int)
     */
    default void setCapacity(final EdsCameraRef ref, final int capacity) {
        setCapacity(ref, capacity, DEFAULT_BYTES_PER_SECTOR);
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
     * @param ref            the reference of the camera which will receive the command
     * @param capacity       the remaining capacity of a transmission place.
     * @param bytesPerSector bytes taken for each sector (smaller means more space)
     */
    void setCapacity(final EdsCameraRef ref, final int capacity, final int bytesPerSector);


    /**
     * @param camera the reference of the camera which will receive the command
     * @return true if mirror lockup is enabled
     */
    boolean isMirrorLockupEnabled(final EdsCameraRef camera);
}
