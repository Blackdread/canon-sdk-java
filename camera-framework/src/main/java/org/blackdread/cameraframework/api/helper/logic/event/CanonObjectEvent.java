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
package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;

/**
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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
     * <table border="1">
     * <caption>none</caption>
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
     *
     * @return reference to object created by the event
     */
    EdsBaseRef getBaseRef();

}
