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
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommBufferFullErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommDeviceIncompatibleErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommDisconnectedErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommPortInUseErrorException;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.*;
import org.blackdread.cameraframework.exception.error.dir.EdsdkDirEntryExistsErrorException;
import org.blackdread.cameraframework.exception.error.dir.EdsdkDirEntryNotFoundErrorException;
import org.blackdread.cameraframework.exception.error.dir.EdsdkDirIOErrorException;
import org.blackdread.cameraframework.exception.error.dir.EdsdkDirNotEmptyErrorException;
import org.blackdread.cameraframework.exception.error.dir.EdsdkDirNotFoundErrorException;
import org.blackdread.cameraframework.exception.error.file.*;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidHandleErrorException;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidIndexErrorException;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidLengthErrorException;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidParameterErrorException;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidPointerErrorException;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidPointerFnErrorException;
import org.blackdread.cameraframework.exception.error.function.EdsdkFuncInvalidSortFnErrorException;
import org.blackdread.cameraframework.exception.error.general.*;
import org.blackdread.cameraframework.exception.error.picture.*;
import org.blackdread.cameraframework.exception.error.ptp.*;
import org.blackdread.cameraframework.exception.error.sti.EdsdkStiDeviceCreateErrorException;
import org.blackdread.cameraframework.exception.error.sti.EdsdkStiDeviceNotLaunchedErrorException;
import org.blackdread.cameraframework.exception.error.sti.EdsdkStiDeviceReleaseErrorException;
import org.blackdread.cameraframework.exception.error.sti.EdsdkStiInternalErrorErrorException;
import org.blackdread.cameraframework.exception.error.sti.EdsdkStiUnknownErrorErrorException;
import org.blackdread.cameraframework.exception.error.stream.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.blackdread.cameraframework.api.constant.EdsdkError.EDS_ERR_OK;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsdkErrorTest extends ConstantValueFromLibraryTest<EdsdkError> {

    private static final Logger log = LoggerFactory.getLogger(EdsdkErrorTest.class);

    @Override
    EdsdkError getOneEnumValue() {
        return EDS_ERR_OK;
    }

    @Override
    EdsdkError[] getAllEnumValues() {
        return EdsdkError.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.class;
    }

    @Override
    int countClassField() {
        return (int) Arrays.stream(EdsdkLibrary.class.getDeclaredFields())
            .filter(name -> name.getName().startsWith("EDS_ERR_"))
            .count();
    }

    static Stream<Arguments> errorAndExceptionClass() {
        return Stream.of(
            arguments(EDS_ERR_OK, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNIMPLEMENTED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INTERNAL_ERROR, EdsdkGeneralInternalErrorException.class),
            arguments(EdsdkError.EDS_ERR_MEM_ALLOC_FAILED, EdsdkGeneralMemAllocErrorException.class),
            arguments(EdsdkError.EDS_ERR_MEM_FREE_FAILED, EdsdkGeneralMemFreeErrorException.class),
            arguments(EdsdkError.EDS_ERR_OPERATION_CANCELLED, EdsdkGeneralOperationCanceledErrorException.class),
            arguments(EdsdkError.EDS_ERR_INCOMPATIBLE_VERSION, EdsdkGeneralIncompatibleVersionErrorException.class),
            arguments(EdsdkError.EDS_ERR_NOT_SUPPORTED, EdsdkGeneralNotSupportedErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNEXPECTED_EXCEPTION, EdsdkGeneralUnexpectedErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROTECTION_VIOLATION, EdsdkGeneralProtectionViolationErrorException.class),
            arguments(EdsdkError.EDS_ERR_MISSING_SUBCOMPONENT, EdsdkGeneralMissingSubComponentErrorException.class),
            arguments(EdsdkError.EDS_ERR_SELECTION_UNAVAILABLE, EdsdkGeneralSelectionUnavailableErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_IO_ERROR, EdsdkFileIOErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_TOO_MANY_OPEN, EdsdkFileTooManyOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_NOT_FOUND, EdsdkFileNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_OPEN_ERROR, EdsdkFileOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_CLOSE_ERROR, EdsdkFileCloseErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_SEEK_ERROR, EdsdkFileSeekErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_TELL_ERROR, EdsdkFileTellErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_READ_ERROR, EdsdkFileReadErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_WRITE_ERROR, EdsdkFileWriteErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_PERMISSION_ERROR, EdsdkFilePermissionErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_DISK_FULL_ERROR, EdsdkFileDiskFullErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_ALREADY_EXISTS, EdsdkFileAlreadyExistErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_FORMAT_UNRECOGNIZED, EdsdkFileFormatErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_DATA_CORRUPT, EdsdkFileDataCorruptErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_NAMING_NA, EdsdkFileNamingErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_NOT_FOUND, EdsdkDirNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_IO_ERROR, EdsdkDirIOErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_ENTRY_NOT_FOUND, EdsdkDirEntryNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_ENTRY_EXISTS, EdsdkDirEntryExistsErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_NOT_EMPTY, EdsdkDirNotEmptyErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROPERTIES_UNAVAILABLE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROPERTIES_MISMATCH, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROPERTIES_NOT_LOADED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_PARAMETER, EdsdkFuncInvalidParameterErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_HANDLE, EdsdkFuncInvalidHandleErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_POINTER, EdsdkFuncInvalidPointerErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_INDEX, EdsdkFuncInvalidIndexErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_LENGTH, EdsdkFuncInvalidLengthErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_FN_POINTER, EdsdkFuncInvalidPointerFnErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_SORT_FN, EdsdkFuncInvalidSortFnErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_NOT_FOUND, EdsdkDeviceNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkDeviceBusyErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_INVALID, EdsdkDeviceInvalidErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_EMERGENCY, EdsdkDeviceEmergencyErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_MEMORY_FULL, EdsdkDeviceMemoryFullErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_INTERNAL_ERROR, EdsdkDeviceInternalErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_INVALID_PARAMETER, EdsdkDeviceInvalidParameterErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_NO_DISK, EdsdkDeviceNoDiskErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_DISK_ERROR, EdsdkDeviceDiskErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_CF_GATE_CHANGED, EdsdkDeviceCfGateChangedErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_DIAL_CHANGED, EdsdkDeviceDialChangedErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_NOT_INSTALLED, EdsdkDeviceNotInstalledErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_STAY_AWAKE, EdsdkDeviceStayAwakeErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_NOT_RELEASED, EdsdkDeviceNotReleasedErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_IO_ERROR, EdsdkStreamIOErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_NOT_OPEN, EdsdkStreamNotOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_ALREADY_OPEN, EdsdkStreamAlreadyOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_OPEN_ERROR, EdsdkStreamOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_CLOSE_ERROR, EdsdkStreamCloseErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_SEEK_ERROR, EdsdkStreamSeekErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_TELL_ERROR, EdsdkStreamTellErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_READ_ERROR, EdsdkStreamReadErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_WRITE_ERROR, EdsdkStreamWriteErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_PERMISSION_ERROR, EdsdkStreamPermissionErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_COULDNT_BEGIN_THREAD, EdsdkStreamBeginThreadErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_BAD_OPTIONS, EdsdkStreamBadOptionsErrorException.class),
            arguments(EdsdkError.EDS_ERR_STREAM_END_OF_STREAM, EdsdkStreamEndStreamErrorException.class),
            arguments(EdsdkError.EDS_ERR_COMM_PORT_IS_IN_USE, EdsdkCommPortInUseErrorException.class),
            arguments(EdsdkError.EDS_ERR_COMM_DISCONNECTED, EdsdkCommDisconnectedErrorException.class),
            arguments(EdsdkError.EDS_ERR_COMM_DEVICE_INCOMPATIBLE, EdsdkCommDeviceIncompatibleErrorException.class),
            arguments(EdsdkError.EDS_ERR_COMM_BUFFER_FULL, EdsdkCommBufferFullErrorException.class),
            arguments(EdsdkError.EDS_ERR_COMM_USB_BUS_ERR, EdsdkCommUsbBusErrorException.class),
            arguments(EdsdkError.EDS_ERR_USB_DEVICE_LOCK_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_USB_DEVICE_UNLOCK_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_UNKNOWN_ERROR, EdsdkStiUnknownErrorErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_INTERNAL_ERROR, EdsdkStiInternalErrorErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_DEVICE_CREATE_ERROR, EdsdkStiDeviceCreateErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_DEVICE_RELEASE_ERROR, EdsdkStiDeviceReleaseErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_NOT_LAUNCHED, EdsdkStiDeviceNotLaunchedErrorException.class),
            arguments(EdsdkError.EDS_ERR_SESSION_NOT_OPEN, EdsdkPtpSessionNotOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_TRANSACTIONID, EdsdkPtpInvalidTransactionIdErrorException.class),
            arguments(EdsdkError.EDS_ERR_INCOMPLETE_TRANSFER, EdsdkPtpIncompleteTransferErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_STRAGEID, EdsdkPtpInvalidStorageIdErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICEPROP_NOT_SUPPORTED, EdsdkPtpUnsupportedDevicePropertyErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_OBJECTFORMATCODE, EdsdkPtpInvalidObjectFormatCodeErrorException.class),
            arguments(EdsdkError.EDS_ERR_SELF_TEST_FAILED, EdsdkPtpSelfTestErrorException.class),
            arguments(EdsdkError.EDS_ERR_PARTIAL_DELETION, EdsdkPtpPartialDeletionErrorException.class),
            arguments(EdsdkError.EDS_ERR_SPECIFICATION_BY_FORMAT_UNSUPPORTED, EdsdkPtpUnsupportedFormatSpecErrorException.class),
            arguments(EdsdkError.EDS_ERR_NO_VALID_OBJECTINFO, EdsdkPtpInvalidObjectInformationErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_CODE_FORMAT, EdsdkPtpInvalidCodeFormatErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNKNOWN_VENDOR_CODE, EdsdkPtpUnknownVendorCodeErrorException.class),
            arguments(EdsdkError.EDS_ERR_CAPTURE_ALREADY_TERMINATED, EdsdkPtpCaptureTerminatedErrorException.class),
            arguments(EdsdkError.EDS_ERR_PTP_DEVICE_BUSY, EdsdkPtpDeviceBusyErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_PARENTOBJECT, EdsdkPtpInvalidParentObjectErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_DEVICEPROP_FORMAT, EdsdkPtpInvalidPropertyFormatErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_DEVICEPROP_VALUE, EdsdkPtpInvalidPropertyValueErrorException.class),
            arguments(EdsdkError.EDS_ERR_SESSION_ALREADY_OPEN, EdsdkPtpSessionAlreadyOpenErrorException.class),
            arguments(EdsdkError.EDS_ERR_TRANSACTION_CANCELLED, EdsdkPtpTransactionCancelledErrorException18.class),
            arguments(EdsdkError.EDS_ERR_SPECIFICATION_OF_DESTINATION_UNSUPPORTED, EdsdkPtpUnsupportedDestinationSpecErrorException.class),
            arguments(EdsdkError.EDS_ERR_NOT_CAMERA_SUPPORT_SDK_VERSION, EdsdkPtpSdkVersionNotSupportedErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNKNOWN_COMMAND, EdsdkPtpUnknownCommandErrorException.class),
            arguments(EdsdkError.EDS_ERR_OPERATION_REFUSED, EdsdkPtpOperationRefusedErrorException.class),
            arguments(EdsdkError.EDS_ERR_LENS_COVER_CLOSE, EdsdkPtpLensCoverClosedErrorException.class),
            arguments(EdsdkError.EDS_ERR_LOW_BATTERY, EdsdkPtpLowBatteryErrorException.class),
            arguments(EdsdkError.EDS_ERR_OBJECT_NOTREADY, EdsdkPtpDataSetNotReadyErrorException.class),
            arguments(EdsdkError.EDS_ERR_CANNOT_MAKE_OBJECT, EdsdkPtpCannotMakeObjectErrorException.class),
            arguments(EdsdkError.EDS_ERR_MEMORYSTATUS_NOTREADY, EdsdkPtpMemoryNotReadyErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_AF_NG, EdsdkPictureAutoFocusFailedErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_RESERVED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG, EdsdkPictureMirrorUpErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_SENSOR_CLEANING_NG, EdsdkPictureSensorCleaningErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_SILENCE_NG, EdsdkPictureSilenceErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_NO_CARD_NG, EdsdkPictureNoCardErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_CARD_NG, EdsdkPictureWriteCardErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_CARD_PROTECT_NG, EdsdkPictureCardProtectErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_MOVIE_CROP_NG, EdsdkPictureMovieCropErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_STROBO_CHARGE_NG, EdsdkPictureStroboChargeErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_NO_LENS_NG, EdsdkPictureNoLensErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_SPECIAL_MOVIE_MODE_NG, EdsdkPictureSpecialMovieErrorException.class),
            arguments(EdsdkError.EDS_ERR_TAKE_PICTURE_LV_REL_PROHIBIT_MODE_NG, EdsdkPictureLvProhibitedErrorException.class),
            arguments(EdsdkError.EDS_ERR_ENUM_NA, EdsdkGeneralEnumNaErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_FN_CALL, EdsdkGeneralInvalidFnCallErrorException.class),
            arguments(EdsdkError.EDS_ERR_HANDLE_NOT_FOUND, EdsdkGeneralHandleNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_ID, EdsdkGeneralInvalidIdErrorException.class),
            arguments(EdsdkError.EDS_ERR_WAIT_TIMEOUT_ERROR, EdsdkGeneralWaitTimeoutErrorException.class),
            arguments(EdsdkError.EDS_ERR_LAST_GENERIC_ERROR_PLUS_ONE, EdsdkErrorException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("errorAndExceptionClass")
    void errorTypeMatchItsClass(final EdsdkError error, final Class errorClassExpected) throws IllegalAccessException, InstantiationException {
        final Class<? extends EdsdkErrorException> classReturned = error.getException().getClass();
        Assertions.assertEquals(errorClassExpected, classReturned);

        if (EdsdkErrorException.class.equals(errorClassExpected)) {
            return;
        }

        final EdsdkErrorException exception = classReturned.newInstance();
        Assertions.assertEquals(error, exception.getEdsdkError());
    }

    @Test
    void errorType() {
        for (final EdsdkError value : EdsdkError.values()) {
            Assertions.assertNotNull(value.getErrorType());
        }
    }

    @Test
    void messageCanBeCustomized() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        for (final EdsdkError value : EdsdkError.values()) {
            final Class<? extends EdsdkErrorException> aClass = value.getException().getClass();

            if (aClass.equals(EdsdkErrorException.class)) {
                continue;
            }

            final Constructor<? extends EdsdkErrorException> constructor;
            try {
                constructor = aClass.getConstructor(String.class);
            } catch (NoSuchMethodException e) {
                log.error("Missing constructor with string (message) for {}", value);
                throw e;
            }
            final EdsdkErrorException exception = constructor.newInstance("AnyMessage");
            Assertions.assertEquals("AnyMessage", exception.getMessage());
        }
    }
}
