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
package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsDirectoryItemInfo;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;

import javax.annotation.Nullable;
import java.io.File;

/**
 * <p>Created on 2018/11/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface FileLogic {

    File SYSTEM_TEMP_DIR = new File(System.getProperty("java.io.tmpdir", "."));

    /**
     * Downloads a file on a remote camera (in the camera memory or on a memory card) to the host computer in the system temp directory and filename provided by camera.
     *
     * @param directoryItem item to download
     * @return final destination of the downloaded file on success (may differ from expected result)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see FileLogic#download(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef, File, String) download(EdsDirectoryItemRef, File, String) for complete documentation
     */
    default File download(final EdsDirectoryItemRef directoryItem) {
        return download(directoryItem, SYSTEM_TEMP_DIR, null);
    }

    /**
     * Downloads a file on a remote camera (in the camera memory or on a memory card) to the host computer in the system temp directory with the filename provided.
     *
     * @param directoryItem item to download
     * @param filename      filename of downloaded file, value can be any of "myFileName" or "myFileName.myExtension", if first example is used then extension given by camera is used
     * @return final destination of the downloaded file on success (may differ from expected result)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see FileLogic#download(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef, File, String) download(EdsDirectoryItemRef, File, String) for complete documentation
     */
    default File download(final EdsDirectoryItemRef directoryItem, final String filename) {
        return download(directoryItem, SYSTEM_TEMP_DIR, filename);
    }

    /**
     * Downloads a file on a remote camera (in the camera memory or on a memory card) to the host computer in the directory passed and filename provided by camera.
     *
     * @param directoryItem     item to download
     * @param folderDestination destination folder where downloaded file should be saved
     * @return final destination of the downloaded file on success (may differ from expected result)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     * @see FileLogic#download(org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef, File, String) download(EdsDirectoryItemRef, File, String) for complete documentation
     */
    default File download(final EdsDirectoryItemRef directoryItem, final File folderDestination) {
        return download(directoryItem, folderDestination, null);
    }

    /**
     * Downloads a file on a remote camera (in the camera memory or on a memory card) to the host computer in the directory and filename passed.
     * <br>
     * Automatically handle file extension if one provided differs from the file extension of file being downloaded.
     * It appends the extension of the file given by the camera. It is very useful when camera is set to shoot multiple files (RAW, JPEG, etc) as order of files to download is unknown.
     * <br>
     * Automatically complete the download on success to release resources on camera.
     * <br>
     * In case of an exception thrown by this method, caller should release the item (otherwise camera might stay stuck in state to wait to send file to host computer).
     * The code should follow:
     * <pre>
     *     final EdsDirectoryItemRef directoryItem = ....
     *     final File folderDestination = ....
     *     final String filename = ....
     *     try{
     *         download(directoryItem, folderDestination, filename);
     *     } catch(EdsdkErrorException e){
     *         downloadCancel(directoryItem);
     *     } catch(Exception e){
     *         downloadCancel(directoryItem);
     *         // handle other exceptions
     *     }
     * </pre>
     *
     * @param directoryItem     item to download
     * @param folderDestination destination folder where downloaded file should be saved. If null, a default folder will be used (default is system temp directory)
     * @param filename          filename of downloaded file, value can be any of "myFileName" or "myFileName.myExtension", if first example is used then extension given by camera is used. If null, filename will be the one given by the camera
     * @return final destination of the downloaded file on success (may differ from expected result)
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    File download(final EdsDirectoryItemRef directoryItem, @Nullable final File folderDestination, @Nullable final String filename);

    EdsDirectoryItemInfo getDirectoryItemInfo(final EdsDirectoryItemRef directoryItem);

    /**
     * Must be called when downloading of directory items is complete. Executing this API makes the camera recognize that file transmission is complete.
     * <br>
     * This operation need not be executed when using EdsDownloadThumbnail.
     *
     * @param directoryItem designate the file for which to complete the downloading process
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void downloadComplete(final EdsDirectoryItemRef directoryItem);

    /**
     * Must be executed when downloading of a directory item is canceled. Calling this API makes the camera cancel file transmission. It also releases resources.
     * <br>
     * This operation need not be executed when using EdsDownloadThumbnail
     * <br>
     * In case that camera is set to save files on host computer, then on event like {@link org.blackdread.cameraframework.api.constant.EdsObjectEvent#kEdsObjectEvent_DirItemRequestTransfer} or {@link org.blackdread.cameraframework.api.constant.EdsObjectEvent#kEdsObjectEvent_DirItemRequestTransferDT} that are not needed, this method should be called.
     *
     * @param directoryItem designate the file for which to cancel downloading
     * @throws EdsdkErrorException if a command to the library result with a return value different than {@link org.blackdread.cameraframework.api.constant.EdsdkError#EDS_ERR_OK}
     */
    void downloadCancel(final EdsDirectoryItemRef directoryItem);
}
