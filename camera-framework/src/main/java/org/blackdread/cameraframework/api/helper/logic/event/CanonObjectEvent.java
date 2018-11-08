package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CanonObjectEvent extends CanonEvent {

    /**
     * @return camera from which event is
     */
    EdsCameraRef getCameraRef();

    /**
     * @return event type that occurred
     */
    EdsObjectEvent getObjectEvent();

    /**
     * For example:
     * <p>
     * <table border="1">
     * <tr>
     * <td>kEdsObjectEvent_DirItemCreated</td> <td>EdsDirectoryItemRef</td> <td>Pointer to the directory or file object</td>
     * </tr>
     * <tr>
     * <td>kEdsObjectEvent_DirItemRemoved</td> <td>EdsDirectoryItemRef</td> <td>Pointer to the directory or file object</td>
     * </tr>
     * <tr>
     * <td>kEdsObjectEvent_VolumeInfoChanged</td> <td>EdsVolumeRef</td> <td>Pointer to the volume object</td>
     * </tr>
     * <tr>
     * <td>kEdsObjectEvent_DirItemRequestTransferDT</td> <td>EdsDirectoryItemRef[]</td> <td>Array of directories and file objects</td>
     * </tr>
     * </table>
     * </p>
     *
     * @return reference to object created by the event
     */
    EdsBaseRef getBaseRef();

}
