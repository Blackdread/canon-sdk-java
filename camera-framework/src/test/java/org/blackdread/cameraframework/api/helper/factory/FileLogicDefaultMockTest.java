package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import org.blackdread.camerabinding.jna.EdsDirectoryItemInfo;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.constant.EdsAccess;
import org.blackdread.cameraframework.api.constant.EdsFileCreateDisposition;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.FileLogic;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceInvalidErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class FileLogicDefaultMockTest extends AbstractMockTest {

    private static final Logger log = LoggerFactory.getLogger(FileLogicDefaultMockTest.class);

    private EdsDirectoryItemRef fakeItemRef;

    private FileLogic spyFileLogic;

    @BeforeEach
    void setUp() {
        mockEdsdkLibrary();

        fakeItemRef = new EdsDirectoryItemRef();

        spyFileLogic = spy(MockFactory.initialCanonFactory.getFileLogic());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void downloadDefault() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownload(eq(fakeItemRef), eq(0L), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownloadComplete(fakeItemRef)).thenReturn(new NativeLong(0));

        final File downloadedFile = spyFileLogic.download(fakeItemRef);

        Assertions.assertNotNull(downloadedFile);

        final String downloadedFileName = downloadedFile.getName();

        log.info("Download: {}, filename: {}", downloadedFile, downloadedFileName);

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
        verify(edsdkLibrary()).EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any());
        verify(edsdkLibrary()).EdsDownload(eq(fakeItemRef), eq(0L), any());
        verify(edsdkLibrary()).EdsDownloadComplete(eq(fakeItemRef));

        verify(edsdkLibrary(), times(0)).EdsDownloadCancel(any());
    }

    @Test
    void downloadDefaultWithFilename() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownload(eq(fakeItemRef), eq(0L), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownloadComplete(fakeItemRef)).thenReturn(new NativeLong(0));

        final File downloadedFile = spyFileLogic.download(fakeItemRef, "filename.jpg");

        Assertions.assertNotNull(downloadedFile);

        final String downloadedFileName = downloadedFile.getName();

        log.info("Download: {}, filename: {}", downloadedFile, downloadedFileName);

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
        verify(edsdkLibrary()).EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any());
        verify(edsdkLibrary()).EdsDownload(eq(fakeItemRef), eq(0L), any());
        verify(edsdkLibrary()).EdsDownloadComplete(eq(fakeItemRef));

        verify(edsdkLibrary(), times(0)).EdsDownloadCancel(any());
    }

    @Test
    void downloadDefaultWithFolder() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownload(eq(fakeItemRef), eq(0L), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownloadComplete(fakeItemRef)).thenReturn(new NativeLong(0));

        final File downloadedFile = spyFileLogic.download(fakeItemRef, new File("./dest"));

        Assertions.assertNotNull(downloadedFile);

        final String downloadedFileName = downloadedFile.getName();

        log.info("Download: {}, filename: {}", downloadedFile, downloadedFileName);

//        Assertions.assertTrue(downloadedFile.getName().startsWith("./dest")
//            || downloadedFile.getName().startsWith(".\\dest"));

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
        verify(edsdkLibrary()).EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any());
        verify(edsdkLibrary()).EdsDownload(eq(fakeItemRef), eq(0L), any());
        verify(edsdkLibrary()).EdsDownloadComplete(eq(fakeItemRef));

        verify(edsdkLibrary(), times(0)).EdsDownloadCancel(any());
    }

    @Test
    void downloadThrowsOnErrorStream() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyFileLogic.download(fakeItemRef, null, null));

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
        verify(edsdkLibrary()).EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any());
        verify(edsdkLibrary(), times(0)).EdsDownload(eq(fakeItemRef), eq(0L), any());
        verify(edsdkLibrary(), times(0)).EdsDownloadComplete(eq(fakeItemRef));

        verify(edsdkLibrary(), times(0)).EdsDownloadCancel(any());
    }

    @Test
    void downloadThrowsOnErrorDownload() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any())).thenReturn(new NativeLong(0));

        when(edsdkLibrary().EdsDownload(eq(fakeItemRef), eq(0L), any())).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyFileLogic.download(fakeItemRef, null, null));

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
        verify(edsdkLibrary()).EdsCreateFileStream(any(byte[].class), eq(EdsFileCreateDisposition.kEdsFileCreateDisposition_CreateAlways.value()), eq(EdsAccess.kEdsAccess_ReadWrite.value()), any());
        verify(edsdkLibrary()).EdsDownload(eq(fakeItemRef), eq(0L), any());
        verify(edsdkLibrary(), times(0)).EdsDownloadComplete(eq(fakeItemRef));

        verify(edsdkLibrary(), times(0)).EdsDownloadCancel(any());
    }

    @Test
    void secureFileDestination() {
    }

    @Test
    void getDirectoryItemInfo() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(0));

        spyFileLogic.getDirectoryItemInfo(fakeItemRef);

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
    }

    @Test
    void getDirectoryItemInfoThrowsOnError() {
        when(edsdkLibrary().EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class))).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyFileLogic.getDirectoryItemInfo(fakeItemRef));

        verify(edsdkLibrary()).EdsGetDirectoryItemInfo(eq(fakeItemRef), any(EdsDirectoryItemInfo.class));
    }

    @Test
    void downloadComplete() {
        when(edsdkLibrary().EdsDownloadComplete(fakeItemRef)).thenReturn(new NativeLong(0));

        spyFileLogic.downloadComplete(fakeItemRef);

        verify(edsdkLibrary()).EdsDownloadComplete(fakeItemRef);
    }

    @Test
    void downloadCompleteThrowsOnError() {
        when(edsdkLibrary().EdsDownloadComplete(fakeItemRef)).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyFileLogic.downloadComplete(fakeItemRef));

        verify(edsdkLibrary()).EdsDownloadComplete(fakeItemRef);
    }

    @Test
    void downloadCancel() {
        when(edsdkLibrary().EdsDownloadCancel(fakeItemRef)).thenReturn(new NativeLong(0));

        spyFileLogic.downloadCancel(fakeItemRef);

        verify(edsdkLibrary()).EdsDownloadCancel(fakeItemRef);
    }

    @Test
    void downloadCancelThrowsOnError() {
        when(edsdkLibrary().EdsDownloadCancel(fakeItemRef)).thenReturn(new NativeLong(EdsdkError.EDS_ERR_DEVICE_INVALID.value()));

        Assertions.assertThrows(EdsdkDeviceInvalidErrorException.class, () -> spyFileLogic.downloadCancel(fakeItemRef));

        verify(edsdkLibrary()).EdsDownloadCancel(fakeItemRef);
    }

    @Test
    void getDestinationOfDownloadFile() {
//        final File dest = TestUtil.callReturnMethod(MockFactory.initialCanonFactory.getFileLogic(), "getDestinationOfDownloadFile", new Class[]{EdsDirectoryItemInfo.class, File.class, String.class}, fakeItemRef, null, null);
    }
}
