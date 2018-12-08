package org.blackdread.cameraframework.api.helper.factory;

import com.google.common.io.Files;
import com.sun.jna.Native;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.camerabinding.jna.EdsDirectoryItemInfo;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.cameraframework.api.constant.EdsAccess;
import org.blackdread.cameraframework.api.constant.EdsFileCreateDisposition;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.FileLogic;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStreamRef;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
public class FileLogicDefault implements FileLogic {

    private static final Logger log = LoggerFactory.getLogger(FileLogicDefault.class);

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * On windows, application cannot write directly files in the temp folder so must create a fake file
     */
    private static final String TEMP_FILE_PREFIX = "edsdk-framework-";

    protected FileLogicDefault() {
    }

    @Override
    public File download(final EdsDirectoryItemRef directoryItem, @Nullable final File folderDestination, @Nullable final String filename) {
        Objects.requireNonNull(directoryItem);

        final EdsDirectoryItemInfo dirItemInfo = getDirectoryItemInfo(directoryItem);

        final File fileDestination = getDestinationOfDownloadFile(dirItemInfo, folderDestination, filename);

        final File securedFileDestination = secureFileDestination(fileDestination, folderDestination, filename);

        return download(directoryItem, dirItemInfo, securedFileDestination);
    }

    /**
     * On Windows, if destination is temp folder, it is not possible to create/rename files in it.
     * This will secure this use case by changing the provided destination and filename
     *
     * @param fileDestination   previous file destination found with folder and filename
     * @param folderDestination destination folder where downloaded file should be saved
     * @param filename          desired filename of downloaded file
     * @return secured file destination as temp folder cannot be written to (without invoking specific methods)
     */
    protected File secureFileDestination(final File fileDestination, @Nullable final File folderDestination, @Nullable final String filename) {
        if (fileDestination.getAbsolutePath().startsWith(FileLogic.SYSTEM_TEMP_DIR.getAbsolutePath()) || folderDestination == null || folderDestination.equals(FileLogic.SYSTEM_TEMP_DIR)) {
            final String fileExtension = Files.getFileExtension(fileDestination.getName());
            final File tempFile;
            try {
                tempFile = File.createTempFile(TEMP_FILE_PREFIX, StringUtils.isBlank(fileExtension) ? null : "." + fileExtension);
            } catch (IOException e) {
                // TODO throw or just ignore and it might succeed or not with camera tries to open a stream to download the file
                throw new IllegalStateException(e);
            }
            final File destinationDesired = new File(FileLogic.SYSTEM_TEMP_DIR, fileDestination.getName());
            if (!tempFile.renameTo(destinationDesired)) {
                return tempFile;
            }
            return destinationDesired;
        }
        return fileDestination;
    }

    /**
     * Download the file
     *
     * @param directoryItem   item to download
     * @param dirItemInfo     info about the item
     * @param fileDestination destination that file should be saved (path + filename with extension)
     * @return final destination of the downloaded file on success (not mandatory identical as {@code fileDestination})
     */
    protected File download(final EdsDirectoryItemRef directoryItem, final EdsDirectoryItemInfo dirItemInfo, final File fileDestination) {
        final File parentFile = fileDestination.getParentFile();
        if (parentFile != null && !parentFile.exists() && !fileDestination.mkdirs()) {
            log.warn("Failed to create directories of path: {}", fileDestination);
        }

        final String saveFileCanonicalPath;
        try {
            saveFileCanonicalPath = fileDestination.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException("Canonical pah cannot be resolved", e);
        }

        final EdsStreamRef.ByReference stream = new EdsStreamRef.ByReference();
        try {
            // As in API reference, we always create/replace the destination file
            final EdsdkError createStreamError = toEdsdkError(CanonFactory.edsdkLibrary().EdsCreateFileStream(Native.toByteArray(saveFileCanonicalPath, DEFAULT_CHARSET.name()), EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value(), EdsAccess.kEdsAccess_ReadWrite.value(), stream));
            if (createStreamError != EdsdkError.EDS_ERR_OK)
                throw createStreamError.getException();

            final EdsdkError downloadError = toEdsdkError(CanonFactory.edsdkLibrary().EdsDownload(directoryItem, dirItemInfo.size, stream.getValue()));
            if (downloadError != EdsdkError.EDS_ERR_OK)
                throw downloadError.getException();

            downloadComplete(directoryItem);

            return fileDestination;
        } finally {
            ReleaseUtil.release(stream);
        }
    }

    public EdsDirectoryItemInfo getDirectoryItemInfo(final EdsDirectoryItemRef directoryItem) {
        final EdsDirectoryItemInfo dirItemInfo = new EdsDirectoryItemInfo();

        final EdsdkError dirItemInfoError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetDirectoryItemInfo(directoryItem, dirItemInfo));
        if (dirItemInfoError != EdsdkError.EDS_ERR_OK)
            throw dirItemInfoError.getException();
        return dirItemInfo;
    }

    public void downloadComplete(final EdsDirectoryItemRef directoryItem) {
        final EdsdkError downloadCompleteError = toEdsdkError(CanonFactory.edsdkLibrary().EdsDownloadComplete(directoryItem));
        if (downloadCompleteError != EdsdkError.EDS_ERR_OK)
            throw downloadCompleteError.getException();
    }

    public void downloadCancel(final EdsDirectoryItemRef directoryItem) {
        final EdsdkError downloadCancelError = toEdsdkError(CanonFactory.edsdkLibrary().EdsDownloadCancel(directoryItem));
        if (downloadCancelError != EdsdkError.EDS_ERR_OK)
            throw downloadCancelError.getException();
    }

    protected File getDestinationOfDownloadFile(final EdsDirectoryItemInfo dirItemInfo, @Nullable final File folderDestination, @Nullable final String filename) {
        final String itemFilename = Native.toString(dirItemInfo.szFileName, DEFAULT_CHARSET.name());
        final String itemFileExtension = Files.getFileExtension(itemFilename);

        final File saveFolder = folderDestination == null ? SYSTEM_TEMP_DIR : folderDestination;
        final String saveFilename;
        if (StringUtils.isBlank(filename)) {
            saveFilename = itemFilename;
        } else {
            final String fileExtension = Files.getFileExtension(filename);
            if (StringUtils.isBlank(fileExtension)) {
                saveFilename = filename + "." + itemFileExtension;
            } else {
                if (fileExtension.equalsIgnoreCase(itemFileExtension)) {
                    saveFilename = filename;
                } else {
                    saveFilename = filename + "." + itemFileExtension;
                }
            }
        }

        saveFolder.mkdirs();
        return new File(saveFolder, saveFilename);
    }

}
