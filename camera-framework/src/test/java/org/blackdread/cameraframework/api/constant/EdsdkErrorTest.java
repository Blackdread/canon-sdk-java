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
import org.blackdread.cameraframework.exception.error.picture.*;
import org.blackdread.cameraframework.exception.error.stream.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsdkErrorTest extends ConstantValueFromLibraryTest<EdsdkError> {

    @Override
    EdsdkError getOneEnumValue() {
        return EdsdkError.EDS_ERR_OK;
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
            arguments(EdsdkError.EDS_ERR_OK, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNIMPLEMENTED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INTERNAL_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_MEM_ALLOC_FAILED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_MEM_FREE_FAILED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_OPERATION_CANCELLED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INCOMPATIBLE_VERSION, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_NOT_SUPPORTED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNEXPECTED_EXCEPTION, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROTECTION_VIOLATION, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_MISSING_SUBCOMPONENT, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_SELECTION_UNAVAILABLE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_IO_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_TOO_MANY_OPEN, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_NOT_FOUND, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_OPEN_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_CLOSE_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_SEEK_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_TELL_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_READ_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_WRITE_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_PERMISSION_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_DISK_FULL_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_ALREADY_EXISTS, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_FORMAT_UNRECOGNIZED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_DATA_CORRUPT, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_FILE_NAMING_NA, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_NOT_FOUND, EdsdkDirNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_IO_ERROR, EdsdkDirIOErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_ENTRY_NOT_FOUND, EdsdkDirEntryNotFoundErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_ENTRY_EXISTS, EdsdkDirEntryExistsErrorException.class),
            arguments(EdsdkError.EDS_ERR_DIR_NOT_EMPTY, EdsdkDirNotEmptyErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROPERTIES_UNAVAILABLE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROPERTIES_MISMATCH, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PROPERTIES_NOT_LOADED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_PARAMETER, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_HANDLE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_POINTER, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_INDEX, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_LENGTH, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_FN_POINTER, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_SORT_FN, EdsdkErrorException.class),
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
            arguments(EdsdkError.EDS_ERR_STI_UNKNOWN_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_INTERNAL_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_DEVICE_CREATE_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_STI_DEVICE_RELEASE_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICE_NOT_LAUNCHED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_SESSION_NOT_OPEN, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_TRANSACTIONID, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INCOMPLETE_TRANSFER, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_STRAGEID, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_DEVICEPROP_NOT_SUPPORTED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_OBJECTFORMATCODE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_SELF_TEST_FAILED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PARTIAL_DELETION, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_SPECIFICATION_BY_FORMAT_UNSUPPORTED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_NO_VALID_OBJECTINFO, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_CODE_FORMAT, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNKNOWN_VENDOR_CODE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_CAPTURE_ALREADY_TERMINATED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_PTP_DEVICE_BUSY, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_PARENTOBJECT, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_DEVICEPROP_FORMAT, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_DEVICEPROP_VALUE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_SESSION_ALREADY_OPEN, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_TRANSACTION_CANCELLED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_SPECIFICATION_OF_DESTINATION_UNSUPPORTED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_NOT_CAMERA_SUPPORT_SDK_VERSION, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_UNKNOWN_COMMAND, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_OPERATION_REFUSED, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_LENS_COVER_CLOSE, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_LOW_BATTERY, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_OBJECT_NOTREADY, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_CANNOT_MAKE_OBJECT, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_MEMORYSTATUS_NOTREADY, EdsdkErrorException.class),
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
            arguments(EdsdkError.EDS_ERR_ENUM_NA, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_FN_CALL, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_HANDLE_NOT_FOUND, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_INVALID_ID, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_WAIT_TIMEOUT_ERROR, EdsdkErrorException.class),
            arguments(EdsdkError.EDS_ERR_LAST_GENERIC_ERROR_PLUS_ONE, EdsdkErrorException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("errorAndExceptionClass")
    void errorTypeMatchItsClass(final EdsdkError error, final Class errorClassExpected) {
        final Class<? extends EdsdkErrorException> classReturned = error.getException().getClass();
        Assertions.assertEquals(errorClassExpected, classReturned);
    }
}
