/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Object Event
 * <p>Created on 2018/10/07.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsObjectEvent implements NativeEnum<Integer> {
    /**
     * Notifies all object events.
     */
    kEdsObjectEvent_All("Notify All"),

    /**
     * Notifies of the creation of objects such as new folders or files
     * on a camera compact flash card or the like.
     * <br>
     * This event is generated if the camera has been set to store captured
     * images simultaneously on the camera and a computer,
     * for example, but not if the camera is set to store images
     * on the computer alone.
     * <br>
     * Newly created objects are indicated by event data.
     * <br>
     * Because objects are not indicated for type 1 protocol standard
     * cameras, (that is, objects are indicated as NULL),
     * you must again retrieve child objects under the camera object to
     * identify the new objects.
     * <br>
     * See API Reference - 4.2.4 kEdsObjectEvent_DirItemCreated
     */
    kEdsObjectEvent_DirItemCreated("Folders/Files Created"),

    /**
     * Notifies of the deletion of objects such as folders or files on a
     * camera compact flash card or the like.
     * <br>
     * Deleted objects are indicated in event data.
     * <br>
     * Because objects are not indicated for type 1 protocol standard
     * cameras, you must again retrieve child objects under the camera object to
     * identify deleted objects.
     * <br>
     * See API Reference - 4.2.5 kEdsObjectEvent_DirItemRemoved
     */
    kEdsObjectEvent_DirItemRemoved("Folders/Files Deleted"),

    /**
     * Notifies that information of DirItem objects has been changed.
     * Changed objects are indicated by event data.
     * <br>
     * The changed value can be retrieved by means of EdsGetDirectoryItemInfo.
     * <br>
     * Notification of this event is not issued for type 1 protocol standard cameras.
     * <br>
     * See API Reference - 4.2.6 kEdsObjectEvent_DirItemInfoChanged
     */
    kEdsObjectEvent_DirItemInfoChanged("Folders/Files Changed"),

    /**
     * Notifies that header information has been updated, as for rotation
     * information of image files on the camera.
     * <br>
     * If this event is received, get the file header information again, as
     * needed.
     * <br>
     * This function is for type 2 protocol standard cameras only.
     * <br>
     * To retrieve image properties, you must obtain them from image objects after
     * using DownloadImage or DownloadThumbnail.
     * <br>
     * See API Reference - 4.2.7 kEdsObjectEvent_DirItemContentChanged
     */
    kEdsObjectEvent_DirItemContentChanged("Images Updated"),


    /**
     * Notifies that the volume object (memory card) state (VolumeInfo) has been changed.
     * <br>
     * Changed objects are indicated by event data.
     * <br>
     * The changed value can be retrieved by means of EdsGetVolumeInfo.
     * <br>
     * Notification of this event is not issued for type 1 protocol standard
     * cameras.
     * <br>
     * See API Reference - 4.2.8 kEdsObjectEvent_VolumeInfoChanged
     */
    kEdsObjectEvent_VolumeInfoChanged("Memory Card Changed"),

    /**
     * Notifies if the designated volume on a camera has been formatted.
     * If notification of this event is received, get sub-items of the
     * designated volume again as needed.
     * <br>
     * Changed volume objects can be retrieved from event data.
     * Objects cannot be identified on cameras earlier than the D30  if files are added or deleted.
     * <br>
     * Thus, these events are subject to notification.
     * <br>
     * See API Reference - 4.2.9 kEdsObjectEvent_VolumeUpdateItems
     */
    kEdsObjectEvent_VolumeUpdateItems("Memory Card Formatted"),

    /**
     * Notifies if many images are deleted in a designated folder on a camera.
     * <br>
     * If notification of this event is received, get sub-items of the designated
     * folder again as needed.
     * <br>
     * Changed folders (specifically, directory item objects) can be retrieved
     * from event data.
     * <br>
     * See API Reference - 4.2.10 kEdsObjectEvent_FolderUpdateItems
     */
    kEdsObjectEvent_FolderUpdateItems("Images Deleted"),

    /**
     * Notifies that there are objects on a camera to be transferred to a
     * computer.
     * <br>
     * This event is generated after remote release from a computer or local
     * release from a camera.
     * <br>
     * If this event is received, objects indicated in the event data must
     * be downloaded.
     * <br>
     * Furthermore, if the application does not require the objects, instead
     * of downloading them,
     * execute EdsDownloadCancel and release resources held by the camera.
     * The order of downloading from type 1 protocol standard cameras must
     * be the order in which the events are received.
     * <br>
     * See API Reference - 4.2.12 kEdsObjectEvent_DirItemRequestTransfer
     */
    kEdsObjectEvent_DirItemRequestTransfer("Folders/Files Ready for Transfer"),

    /**
     * Notifies if the camera's direct transfer button is pressed.
     * <br>
     * If this event is received, objects indicated in the event data must
     * be downloaded.
     * <br>
     * Furthermore, if the application does not require the objects, instead
     * of downloading them,
     * execute EdsDownloadCancel and release resources held by the camera.
     * Notification of this event is not issued for type 1 protocol standard
     * cameras.
     * <br>
     * See API Reference - 4.2.13 kEdsObjectEvent_DirItemRequestTransferDT
     */
    kEdsObjectEvent_DirItemRequestTransferDT("Direct Transfer Pressed"),

    /**
     * Notifies of requests from a camera to cancel object transfer
     * if the button to cancel direct transfer is pressed on the camera.
     * <br>
     * If the parameter is 0, it means that cancellation of transfer is
     * requested for objects still not downloaded,  with these objects indicated by
     * kEdsObjectEvent_DirItemRequestTransferDT.
     * <br>
     * Notification of this event is not issued for type 1 protocol standard
     * cameras.
     * <br>
     * See API Reference - 4.2.14 kEdsObjectEvent_DirItemCancelTransferDT
     */
    kEdsObjectEvent_DirItemCancelTransferDT("Direct Transfer Cancelled"),

    /**
     * Not specified in API Reference
     */
    kEdsObjectEvent_VolumeAdded("Memory Card Added"),
    /**
     * Not specified in API Reference
     */
    kEdsObjectEvent_VolumeRemoved("Memory Card Removed");

    private final int value;
    private final String description;

    EdsObjectEvent(final String description) {
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
    public static EdsObjectEvent ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsObjectEvent.class, value);
    }

}
