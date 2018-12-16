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
package org.blackdread.cameraframework;

import com.google.common.collect.Lists;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;
import org.blackdread.camerabinding.jna.*;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.junit.jupiter.api.Assertions;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock the library, very basic logic to mock, it might behaves differently but general idea is to get something that might help during tests without a camera.
 * If more behavior is required then should use Mockito is the appropriate test class
 * <p>Created on 2018/10/29.</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated it is impossible to mock unless JNA classes are also mocked
 */
public class EdsdkLibraryMock implements EdsdkLibrary {

    private boolean sdkInitialized = false;

    private final Map<EdsBaseRef, Integer> countRetain = new HashMap<>();

    private final List<EdsCameraRef> camerasConnected = Lists.newArrayList(new EdsCameraRef(), new EdsCameraRef());

    private NativeLong returnOk() {
        return new NativeLong(EdsdkError.EDS_ERR_OK.value());
    }

    private void assertWasInitialized() {
        Assertions.assertTrue(sdkInitialized, "EDSDK not initialized");
    }

    @Override
    public NativeLong EdsInitializeSDK() {
        sdkInitialized = true;
        return returnOk();
    }

    @Override
    public NativeLong EdsTerminateSDK() {
        sdkInitialized = false;
        return returnOk();
    }

    @Override
    public NativeLong EdsRetain(final EdsBaseRef inRef) {
        assertWasInitialized();
        final Integer total = countRetain.compute(inRef, (edsBaseRef, integer) -> {
            if (integer == null)
                return 1;
            else
                return integer + 1;
        });
        return new NativeLong(total);
    }

    @Override
    public NativeLong EdsRelease(final EdsBaseRef inRef) {
        assertWasInitialized();
        final Integer total = countRetain.compute(inRef, (edsBaseRef, integer) -> {
            if (integer == null)
                return -1;
            else
                return integer - 1;
        });
        return new NativeLong(total);
    }

    @Override
    public NativeLong EdsGetChildCount(final EdsBaseRef inRef, final NativeLongByReference outCount) {
        return null;
    }

    @Override
    public NativeLong EdsGetChildAtIndex(final EdsBaseRef inRef, final NativeLong inIndex, final EdsBaseRef.ByReference outRef) {
        return null;
    }

    @Override
    public NativeLong EdsGetParent(final EdsBaseRef inRef, final EdsBaseRef.ByReference outParentRef) {
        return null;
    }

    @Override
    public NativeLong EdsGetPropertySize(final EdsBaseRef inRef, final NativeLong inPropertyID, final NativeLong inParam, final IntByReference outDataType, final NativeLongByReference outSize) {
        return null;
    }

    @Override
    public NativeLong EdsGetPropertySize(final EdsBaseRef inRef, final NativeLong inPropertyID, final NativeLong inParam, final IntBuffer outDataType, final NativeLongByReference outSize) {
        return null;
    }

    @Override
    public NativeLong EdsGetPropertyData(final EdsBaseRef inRef, final NativeLong inPropertyID, final NativeLong inParam, final NativeLong inPropertySize, final Pointer outPropertyData) {
        return null;
    }

    @Override
    public NativeLong EdsGetPropertyData(final EdsBaseRef inRef, final NativeLong inPropertyID, final NativeLong inParam, final NativeLong inPropertySize, final EdsVoid outPropertyData) {
        return null;
    }

    @Override
    public NativeLong EdsSetPropertyData(final EdsBaseRef inRef, final NativeLong inPropertyID, final NativeLong inParam, final NativeLong inPropertySize, final Pointer inPropertyData) {
        return null;
    }

    @Override
    public NativeLong EdsSetPropertyData(final EdsBaseRef inRef, final NativeLong inPropertyID, final NativeLong inParam, final NativeLong inPropertySize, final EdsVoid inPropertyData) {
        return null;
    }

    @Override
    public NativeLong EdsGetPropertyDesc(final EdsBaseRef inRef, final NativeLong inPropertyID, final EdsPropertyDesc outPropertyDesc) {
        return null;
    }

    @Override
    public NativeLong EdsGetCameraList(final EdsCameraListRef.ByReference outCameraListRef) {
        return null;
    }

    @Override
    public NativeLong EdsGetDeviceInfo(final EdsCameraRef inCameraRef, final EdsDeviceInfo outDeviceInfo) {
        return null;
    }

    @Override
    public NativeLong EdsOpenSession(final EdsCameraRef inCameraRef) {
        return null;
    }

    @Override
    public NativeLong EdsCloseSession(final EdsCameraRef inCameraRef) {
        return null;
    }

    @Override
    public NativeLong EdsSendCommand(final EdsCameraRef inCameraRef, final NativeLong inCommand, final NativeLong inParam) {
        return null;
    }

    @Override
    public NativeLong EdsSendStatusCommand(final EdsCameraRef inCameraRef, final NativeLong inStatusCommand, final NativeLong inParam) {
        return null;
    }

    @Override
    public NativeLong EdsSetCapacity(final EdsCameraRef inCameraRef, final EdsCapacity.ByValue inCapacity) {
        return null;
    }

    @Override
    public NativeLong EdsGetVolumeInfo(final EdsVolumeRef inVolumeRef, final EdsVolumeInfo outVolumeInfo) {
        return null;
    }

    @Override
    public NativeLong EdsFormatVolume(final EdsVolumeRef inVolumeRef) {
        return null;
    }

    @Override
    public NativeLong EdsGetDirectoryItemInfo(final EdsDirectoryItemRef inDirItemRef, final EdsDirectoryItemInfo outDirItemInfo) {
        return null;
    }

    @Override
    public NativeLong EdsDeleteDirectoryItem(final EdsDirectoryItemRef inDirItemRef) {
        return null;
    }

    @Override
    public NativeLong EdsDownload(final EdsDirectoryItemRef inDirItemRef, final long inReadSize, final EdsStreamRef outStream) {
        return null;
    }

    @Override
    public NativeLong EdsDownloadCancel(final EdsDirectoryItemRef inDirItemRef) {
        return null;
    }

    @Override
    public NativeLong EdsDownloadComplete(final EdsDirectoryItemRef inDirItemRef) {
        return null;
    }

    @Override
    public NativeLong EdsDownloadThumbnail(final EdsDirectoryItemRef inDirItemRef, final EdsStreamRef outStream) {
        return null;
    }

    @Override
    public NativeLong EdsGetAttribute(final EdsDirectoryItemRef inDirItemRef, final IntByReference outFileAttribute) {
        return null;
    }

    @Override
    public NativeLong EdsGetAttribute(final EdsDirectoryItemRef inDirItemRef, final IntBuffer outFileAttribute) {
        return null;
    }

    @Override
    public NativeLong EdsSetAttribute(final EdsDirectoryItemRef inDirItemRef, final int inFileAttribute) {
        return null;
    }

    @Override
    public NativeLong EdsCreateFileStream(final ByteBuffer inFileName, final int inCreateDisposition, final int inDesiredAccess, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsCreateFileStream(final byte[] inFileName, final int inCreateDisposition, final int inDesiredAccess, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsCreateMemoryStream(final long inBufferSize, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsCreateFileStreamEx(final short[] inFileName, final int inCreateDisposition, final int inDesiredAccess, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsCreateFileStreamEx(final ShortByReference inFileName, final int inCreateDisposition, final int inDesiredAccess, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsCreateMemoryStreamFromPointer(final Pointer inUserBuffer, final long inBufferSize, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsCreateMemoryStreamFromPointer(final EdsVoid inUserBuffer, final long inBufferSize, final EdsStreamRef.ByReference outStream) {
        return null;
    }

    @Override
    public NativeLong EdsGetPointer(final EdsStreamRef inStream, final PointerByReference outPointer) {
        return null;
    }

    @Override
    public NativeLong EdsRead(final EdsStreamRef inStreamRef, final long inReadSize, final Pointer outBuffer, final LongByReference outReadSize) {
        return null;
    }

    @Override
    public NativeLong EdsRead(final EdsStreamRef inStreamRef, final long inReadSize, final EdsVoid outBuffer, final LongByReference outReadSize) {
        return null;
    }

    @Override
    public NativeLong EdsWrite(final EdsStreamRef inStreamRef, final long inWriteSize, final Pointer inBuffer, final LongByReference outWrittenSize) {
        return null;
    }

    @Override
    public NativeLong EdsWrite(final EdsStreamRef inStreamRef, final long inWriteSize, final EdsVoid inBuffer, final LongByReference outWrittenSize) {
        return null;
    }

    @Override
    public NativeLong EdsSeek(final EdsStreamRef inStreamRef, final long inSeekOffset, final int inSeekOrigin) {
        return null;
    }

    @Override
    public NativeLong EdsGetPosition(final EdsStreamRef inStreamRef, final LongByReference outPosition) {
        return null;
    }

    @Override
    public NativeLong EdsGetLength(final EdsStreamRef inStreamRef, final LongByReference outLength) {
        return null;
    }

    @Override
    public NativeLong EdsCopyData(final EdsStreamRef inStreamRef, final long inWriteSize, final EdsStreamRef outStreamRef) {
        return null;
    }

    @Override
    public NativeLong EdsSetProgressCallback(final EdsBaseRef inRef, final EdsProgressCallback inProgressCallback, final int inProgressOption, final Pointer inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetProgressCallback(final EdsBaseRef inRef, final EdsProgressCallback inProgressCallback, final int inProgressOption, final EdsVoid inContext) {
        return null;
    }

    @Override
    public NativeLong EdsCreateImageRef(final EdsStreamRef inStreamRef, final EdsImageRef.ByReference outImageRef) {
        return null;
    }

    @Override
    public NativeLong EdsGetImageInfo(final EdsImageRef inImageRef, final int inImageSource, final EdsImageInfo outImageInfo) {
        return null;
    }

    @Override
    public NativeLong EdsGetImage(final EdsImageRef inImageRef, final int inImageSource, final int inImageType, final EdsRect.ByValue inSrcRect, final EdsSize.ByValue inDstSize, final EdsStreamRef outStreamRef) {
        return null;
    }

    @Override
    public NativeLong EdsSaveImage(final EdsImageRef inImageRef, final int inImageType, final EdsSaveImageSetting.ByValue inSaveSetting, final EdsStreamRef outStreamRef) {
        return null;
    }

    @Override
    public NativeLong EdsCacheImage(final EdsImageRef inImageRef, final int inUseCache) {
        return null;
    }

    @Override
    public NativeLong EdsReflectImageProperty(final EdsImageRef inImageRef) {
        return null;
    }

    @Override
    public NativeLong EdsCreateEvfImageRef(final EdsStreamRef inStreamRef, final EdsEvfImageRef.ByReference outEvfImageRef) {
        return null;
    }

    @Override
    public NativeLong EdsDownloadEvfImage(final EdsCameraRef inCameraRef, final EdsEvfImageRef inEvfImageRef) {
        return null;
    }

    @Override
    public NativeLong EdsSetCameraAddedHandler(final EdsCameraAddedHandler inCameraAddedHandler, final Pointer inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetCameraAddedHandler(final EdsCameraAddedHandler inCameraAddedHandler, final EdsVoid inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetPropertyEventHandler(final EdsCameraRef inCameraRef, final NativeLong inEvnet, final EdsPropertyEventHandler inPropertyEventHandler, final Pointer inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetPropertyEventHandler(final EdsCameraRef inCameraRef, final NativeLong inEvnet, final EdsPropertyEventHandler inPropertyEventHandler, final EdsVoid inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetObjectEventHandler(final EdsCameraRef inCameraRef, final NativeLong inEvnet, final EdsObjectEventHandler inObjectEventHandler, final Pointer inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetObjectEventHandler(final EdsCameraRef inCameraRef, final NativeLong inEvnet, final EdsObjectEventHandler inObjectEventHandler, final EdsVoid inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetCameraStateEventHandler(final EdsCameraRef inCameraRef, final NativeLong inEvnet, final EdsStateEventHandler inStateEventHandler, final Pointer inContext) {
        return null;
    }

    @Override
    public NativeLong EdsSetCameraStateEventHandler(final EdsCameraRef inCameraRef, final NativeLong inEvnet, final EdsStateEventHandler inStateEventHandler, final EdsVoid inContext) {
        return null;
    }

    @Override
    public NativeLong EdsCreateStream(final EdsIStream inStream, final EdsStreamRef.ByReference outStreamRef) {
        return null;
    }

    @Override
    public NativeLong EdsGetEvent() {
        return null;
    }
}
