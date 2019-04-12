/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
import org.blackdread.cameraframework.exception.error.stream.*;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

import static org.blackdread.cameraframework.api.constant.ErrorType.*;

/**
 * ED-SDK Generic Error IDs
 *
 * <p>Created on 2018/10/04</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsdkError implements NativeEnum<Integer>, NativeErrorEnum<Integer> {

    /*
     * ED-SDK Error Code Masks
     */

    // no reason to put those here...

    /*
     * ED-SDK Base Component IDs
     */

    // no reason to put those here...

    /*
     * ED-SDK Function Success Code
     */

    EDS_ERR_OK("Success, No Errors", NOT_ERROR),

    /*
     * ED-SDK Generic Error IDs
     */

    /* Miscellaneous errors */
    EDS_ERR_UNIMPLEMENTED("Not implemented", GENERAL_ERROR),
    EDS_ERR_INTERNAL_ERROR("Internal error", GENERAL_ERROR),
    EDS_ERR_MEM_ALLOC_FAILED("Memory allocation error", GENERAL_ERROR),
    EDS_ERR_MEM_FREE_FAILED("Memory release error", GENERAL_ERROR),
    EDS_ERR_OPERATION_CANCELLED("Operation canceled", GENERAL_ERROR),
    EDS_ERR_INCOMPATIBLE_VERSION("Version error", GENERAL_ERROR),
    EDS_ERR_NOT_SUPPORTED("Not supported", GENERAL_ERROR),
    EDS_ERR_UNEXPECTED_EXCEPTION("Unexpected exception", GENERAL_ERROR),
    EDS_ERR_PROTECTION_VIOLATION("Protection violation", GENERAL_ERROR),
    EDS_ERR_MISSING_SUBCOMPONENT("Missing subcomponent", GENERAL_ERROR),
    EDS_ERR_SELECTION_UNAVAILABLE("Selection unavailable", GENERAL_ERROR),

    /* File errors */
    EDS_ERR_FILE_IO_ERROR("IO error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_TOO_MANY_OPEN("Too many files open", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_NOT_FOUND("File does not exist", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_OPEN_ERROR("Open error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_CLOSE_ERROR("Close error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_SEEK_ERROR("Seek error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_TELL_ERROR("Tell error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_READ_ERROR("Read error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_WRITE_ERROR("Write error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_PERMISSION_ERROR("Permission error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_DISK_FULL_ERROR("Disk full", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_ALREADY_EXISTS("File already exists", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_FORMAT_UNRECOGNIZED("Format error", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_DATA_CORRUPT("Invalid data", FILE_ACCESS_ERROR),
    EDS_ERR_FILE_NAMING_NA("File naming error", FILE_ACCESS_ERROR),

    /* Directory errors */
    EDS_ERR_DIR_NOT_FOUND("Directory does not exist", DIRECTORY_ERROR),
    EDS_ERR_DIR_IO_ERROR("I/O error", DIRECTORY_ERROR),
    EDS_ERR_DIR_ENTRY_NOT_FOUND("No file in directory", DIRECTORY_ERROR),
    EDS_ERR_DIR_ENTRY_EXISTS("File in directory", DIRECTORY_ERROR),
    EDS_ERR_DIR_NOT_EMPTY("Directory full", DIRECTORY_ERROR),

    /* Property errors */
    EDS_ERR_PROPERTIES_UNAVAILABLE("Property (and additional property information) unavailable", PROPERTY_ERROR),
    EDS_ERR_PROPERTIES_MISMATCH("Property mismatch", PROPERTY_ERROR),
    EDS_ERR_PROPERTIES_NOT_LOADED("Property not loaded", PROPERTY_ERROR),

    /* Function Parameter errors */
    EDS_ERR_INVALID_PARAMETER("Invalid function parameter", FUNCTION_PARAMETER_ERROR),
    EDS_ERR_INVALID_HANDLE("Handle error", FUNCTION_PARAMETER_ERROR),
    EDS_ERR_INVALID_POINTER("Pointer error", FUNCTION_PARAMETER_ERROR),
    EDS_ERR_INVALID_INDEX("Index error", FUNCTION_PARAMETER_ERROR),
    EDS_ERR_INVALID_LENGTH("Length error", FUNCTION_PARAMETER_ERROR),
    EDS_ERR_INVALID_FN_POINTER("FN pointer error", FUNCTION_PARAMETER_ERROR),
    EDS_ERR_INVALID_SORT_FN("Sort FN error", FUNCTION_PARAMETER_ERROR),

    /* Device errors */
    EDS_ERR_DEVICE_NOT_FOUND("Device not found", DEVICE_ERROR),
    EDS_ERR_DEVICE_BUSY("Device busy. If a device busy error occurs, reissue the command after a while. The camera will become unstable", DEVICE_ERROR),
    EDS_ERR_DEVICE_INVALID("Device error", DEVICE_ERROR),
    EDS_ERR_DEVICE_EMERGENCY("Device emergency", DEVICE_ERROR),
    EDS_ERR_DEVICE_MEMORY_FULL("Device memory full", DEVICE_ERROR),
    EDS_ERR_DEVICE_INTERNAL_ERROR("Internal device error", DEVICE_ERROR),
    EDS_ERR_DEVICE_INVALID_PARAMETER("Device parameter invalid", DEVICE_ERROR),
    EDS_ERR_DEVICE_NO_DISK("No disk", DEVICE_ERROR),
    EDS_ERR_DEVICE_DISK_ERROR("Disk error", DEVICE_ERROR),
    EDS_ERR_DEVICE_CF_GATE_CHANGED("The CF gate has been changed", DEVICE_ERROR),
    EDS_ERR_DEVICE_DIAL_CHANGED("The dial has been changed", DEVICE_ERROR),
    EDS_ERR_DEVICE_NOT_INSTALLED("Device not installed", DEVICE_ERROR),
    EDS_ERR_DEVICE_STAY_AWAKE("Device connected in awake mode", DEVICE_ERROR),
    EDS_ERR_DEVICE_NOT_RELEASED("Device not released", DEVICE_ERROR),

    /* Stream errors */
    EDS_ERR_STREAM_IO_ERROR("Stream I/O error", STREAM_ERROR),
    EDS_ERR_STREAM_NOT_OPEN("Stream open error", STREAM_ERROR),
    EDS_ERR_STREAM_ALREADY_OPEN("Stream already open", STREAM_ERROR),
    EDS_ERR_STREAM_OPEN_ERROR("Failed to open stream", STREAM_ERROR),
    EDS_ERR_STREAM_CLOSE_ERROR("Failed to close stream", STREAM_ERROR),
    EDS_ERR_STREAM_SEEK_ERROR("Stream seek error", STREAM_ERROR),
    EDS_ERR_STREAM_TELL_ERROR("Stream tell error", STREAM_ERROR),
    EDS_ERR_STREAM_READ_ERROR("Failed to read stream", STREAM_ERROR),
    EDS_ERR_STREAM_WRITE_ERROR("Failed to write stream", STREAM_ERROR),
    EDS_ERR_STREAM_PERMISSION_ERROR("Permission error", STREAM_ERROR),
    EDS_ERR_STREAM_COULDNT_BEGIN_THREAD("Could not start reading thumbnail", STREAM_ERROR),
    EDS_ERR_STREAM_BAD_OPTIONS("Invalid stream option", STREAM_ERROR),
    EDS_ERR_STREAM_END_OF_STREAM("Invalid stream termination", STREAM_ERROR),

    /* Communications errors */
    EDS_ERR_COMM_PORT_IS_IN_USE("Port in use", COMMUNICATION_ERROR),
    EDS_ERR_COMM_DISCONNECTED("Port disconnected", COMMUNICATION_ERROR),
    EDS_ERR_COMM_DEVICE_INCOMPATIBLE("Incompatible device", COMMUNICATION_ERROR),
    EDS_ERR_COMM_BUFFER_FULL("Buffer full", COMMUNICATION_ERROR),
    EDS_ERR_COMM_USB_BUS_ERR("USB bus error", COMMUNICATION_ERROR),

    /* Lock/Unlock */
    EDS_ERR_USB_DEVICE_LOCK_ERROR("Failed to lock the UI", CAMERA_UI_LOCK_UNLOCK_ERROR),
    EDS_ERR_USB_DEVICE_UNLOCK_ERROR("Failed to unlock the UI", CAMERA_UI_LOCK_UNLOCK_ERROR),

    /* STI/WIA */
    EDS_ERR_STI_UNKNOWN_ERROR("Unknown STI", STI_WIA_ERROR),
    EDS_ERR_STI_INTERNAL_ERROR("Internal STI error", STI_WIA_ERROR),
    EDS_ERR_STI_DEVICE_CREATE_ERROR("Device creation error", STI_WIA_ERROR),
    EDS_ERR_STI_DEVICE_RELEASE_ERROR("Device release error", STI_WIA_ERROR),
    EDS_ERR_DEVICE_NOT_LAUNCHED("Device startup failed", STI_WIA_ERROR),

    /* PTP */
    EDS_ERR_SESSION_NOT_OPEN("Session open error", PTP_ERROR),
    EDS_ERR_INVALID_TRANSACTIONID("Invalid transaction ID", PTP_ERROR),
    EDS_ERR_INCOMPLETE_TRANSFER("Transfer problem", PTP_ERROR),
    EDS_ERR_INVALID_STRAGEID("Storage error", PTP_ERROR),
    EDS_ERR_DEVICEPROP_NOT_SUPPORTED("Unsupported device property", PTP_ERROR),
    EDS_ERR_INVALID_OBJECTFORMATCODE("Invalid object format code", PTP_ERROR),
    EDS_ERR_SELF_TEST_FAILED("Failed self-diagnosis", PTP_ERROR),
    EDS_ERR_PARTIAL_DELETION("Failed in partial deletion", PTP_ERROR),
    EDS_ERR_SPECIFICATION_BY_FORMAT_UNSUPPORTED("Unsupported format specification", PTP_ERROR),
    EDS_ERR_NO_VALID_OBJECTINFO("Invalid object information", PTP_ERROR),
    EDS_ERR_INVALID_CODE_FORMAT("Invalid code format", PTP_ERROR),
    EDS_ERR_UNKNOWN_VENDOR_CODE("Unknown vendor code", PTP_ERROR),
    EDS_ERR_CAPTURE_ALREADY_TERMINATED("Capture already terminated", PTP_ERROR),
    EDS_ERR_PTP_DEVICE_BUSY("PTP device busy", PTP_ERROR),
    EDS_ERR_INVALID_PARENTOBJECT("Invalid parent object", PTP_ERROR),
    EDS_ERR_INVALID_DEVICEPROP_FORMAT("Invalid property format", PTP_ERROR),
    EDS_ERR_INVALID_DEVICEPROP_VALUE("Invalid property value", PTP_ERROR),
    EDS_ERR_SESSION_ALREADY_OPEN("Session already open", PTP_ERROR),
    EDS_ERR_TRANSACTION_CANCELLED("Transaction canceled", PTP_ERROR),
    EDS_ERR_SPECIFICATION_OF_DESTINATION_UNSUPPORTED("Unsupported destination specification", PTP_ERROR),
    EDS_ERR_NOT_CAMERA_SUPPORT_SDK_VERSION("SDK version not supported", PTP_ERROR),

    /* PTP Vendor */
    EDS_ERR_UNKNOWN_COMMAND("Unknown command", PTP_ERROR),
    EDS_ERR_OPERATION_REFUSED("Operation refused", PTP_ERROR),
    EDS_ERR_LENS_COVER_CLOSE("Lens cover closed", PTP_ERROR),
    EDS_ERR_LOW_BATTERY("Low battery", PTP_ERROR),
    EDS_ERR_OBJECT_NOTREADY("Image data set not ready for live view", PTP_ERROR),
    EDS_ERR_CANNOT_MAKE_OBJECT("Cannot Make Object", PTP_ERROR),
    EDS_ERR_MEMORYSTATUS_NOTREADY("Memory not ready", PTP_ERROR),

    /* Take Picture errors */
    EDS_ERR_TAKE_PICTURE_AF_NG("Focus failed", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_RESERVED("Reserved", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG("Currently configuring mirror up", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_SENSOR_CLEANING_NG("Currently cleaning sensor", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_SILENCE_NG("Currently performing silent operations", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_NO_CARD_NG("Card not installed", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_CARD_NG("Error writing to card", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_CARD_PROTECT_NG("Card write protected", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_MOVIE_CROP_NG("Cropping Movie", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_STROBO_CHARGE_NG("Strobe Charging", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_NO_LENS_NG("No lens", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_SPECIAL_MOVIE_MODE_NG("Special movie mode", TAKE_PICTURE_ERROR),
    EDS_ERR_TAKE_PICTURE_LV_REL_PROHIBIT_MODE_NG("LV prohibited mode", TAKE_PICTURE_ERROR),

    /* Other general errors */
    EDS_ERR_ENUM_NA("Enumeration terminated (there was no suitable enumeration item)", OTHER_GENERAL_ERROR),
    EDS_ERR_INVALID_FN_CALL("Called in a mode when the function could not be used", OTHER_GENERAL_ERROR),
    EDS_ERR_HANDLE_NOT_FOUND("Handle not found", OTHER_GENERAL_ERROR),
    EDS_ERR_INVALID_ID("Invalid ID", OTHER_GENERAL_ERROR),
    EDS_ERR_WAIT_TIMEOUT_ERROR("Timeout", OTHER_GENERAL_ERROR),
    EDS_ERR_LAST_GENERIC_ERROR_PLUS_ONE("Not used", OTHER_GENERAL_ERROR);

    private final int value;

    private final String description;

    private final ErrorType errorType;

    EdsdkError(final String description, final ErrorType errorType) {
        this.errorType = errorType;
        this.value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.class, name());
        this.description = description;
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EdsdkErrorException> T getException() {
        switch (this) {
//            case EDS_ERR_UNIMPLEMENTED:
//                break;
            case EDS_ERR_INTERNAL_ERROR:
                return (T) new EdsdkGeneralInternalErrorException();
            case EDS_ERR_MEM_ALLOC_FAILED:
                return (T) new EdsdkGeneralMemAllocErrorException();
            case EDS_ERR_MEM_FREE_FAILED:
                return (T) new EdsdkGeneralMemFreeErrorException();
            case EDS_ERR_OPERATION_CANCELLED:
                return (T) new EdsdkGeneralOperationCanceledErrorException();
            case EDS_ERR_INCOMPATIBLE_VERSION:
                return (T) new EdsdkGeneralIncompatibleVersionErrorException();
            case EDS_ERR_NOT_SUPPORTED:
                return (T) new EdsdkGeneralNotSupportedErrorException();
            case EDS_ERR_UNEXPECTED_EXCEPTION:
                return (T) new EdsdkGeneralUnexpectedErrorException();
            case EDS_ERR_PROTECTION_VIOLATION:
                return (T) new EdsdkGeneralProtectionViolationErrorException();
            case EDS_ERR_MISSING_SUBCOMPONENT:
                return (T) new EdsdkGeneralMissingSubComponentErrorException();
            case EDS_ERR_SELECTION_UNAVAILABLE:
                return (T) new EdsdkGeneralSelectionUnavailableErrorException();
            case EDS_ERR_FILE_IO_ERROR:
                return (T) new EdsdkFileIOErrorException();
            case EDS_ERR_FILE_TOO_MANY_OPEN:
                return (T) new EdsdkFileTooManyOpenErrorException();
            case EDS_ERR_FILE_NOT_FOUND:
                return (T) new EdsdkFileNotFoundErrorException();
            case EDS_ERR_FILE_OPEN_ERROR:
                return (T) new EdsdkFileOpenErrorException();
            case EDS_ERR_FILE_CLOSE_ERROR:
                return (T) new EdsdkFileCloseErrorException();
            case EDS_ERR_FILE_SEEK_ERROR:
                return (T) new EdsdkFileSeekErrorException();
            case EDS_ERR_FILE_TELL_ERROR:
                return (T) new EdsdkFileTellErrorException();
            case EDS_ERR_FILE_READ_ERROR:
                return (T) new EdsdkFileReadErrorException();
            case EDS_ERR_FILE_WRITE_ERROR:
                return (T) new EdsdkFileWriteErrorException();
            case EDS_ERR_FILE_PERMISSION_ERROR:
                return (T) new EdsdkFilePermissionErrorException();
            case EDS_ERR_FILE_DISK_FULL_ERROR:
                return (T) new EdsdkFileDiskFullErrorException();
            case EDS_ERR_FILE_ALREADY_EXISTS:
                return (T) new EdsdkFileAlreadyExistErrorException();
            case EDS_ERR_FILE_FORMAT_UNRECOGNIZED:
                return (T) new EdsdkFileFormatErrorException();
            case EDS_ERR_FILE_DATA_CORRUPT:
                return (T) new EdsdkFileDataCorruptErrorException();
            case EDS_ERR_FILE_NAMING_NA:
                return (T) new EdsdkFileNamingErrorException();
            case EDS_ERR_DIR_NOT_FOUND:
                return (T) new EdsdkDirNotFoundErrorException();
            case EDS_ERR_DIR_IO_ERROR:
                return (T) new EdsdkDirIOErrorException();
            case EDS_ERR_DIR_ENTRY_NOT_FOUND:
                return (T) new EdsdkDirEntryNotFoundErrorException();
            case EDS_ERR_DIR_ENTRY_EXISTS:
                return (T) new EdsdkDirEntryExistsErrorException();
            case EDS_ERR_DIR_NOT_EMPTY:
                return (T) new EdsdkDirNotEmptyErrorException();
//            case EDS_ERR_PROPERTIES_UNAVAILABLE:
//                break;
//            case EDS_ERR_PROPERTIES_MISMATCH:
//                break;
//            case EDS_ERR_PROPERTIES_NOT_LOADED:
//                break;
            case EDS_ERR_INVALID_PARAMETER:
                return (T) new EdsdkFuncInvalidParameterErrorException();
            case EDS_ERR_INVALID_HANDLE:
                return (T) new EdsdkFuncInvalidHandleErrorException();
            case EDS_ERR_INVALID_POINTER:
                return (T) new EdsdkFuncInvalidPointerErrorException();
            case EDS_ERR_INVALID_INDEX:
                return (T) new EdsdkFuncInvalidIndexErrorException();
            case EDS_ERR_INVALID_LENGTH:
                return (T) new EdsdkFuncInvalidLengthErrorException();
            case EDS_ERR_INVALID_FN_POINTER:
                return (T) new EdsdkFuncInvalidPointerFnErrorException();
            case EDS_ERR_INVALID_SORT_FN:
                return (T) new EdsdkFuncInvalidSortFnErrorException();
            case EDS_ERR_DEVICE_NOT_FOUND:
                return (T) new EdsdkDeviceNotFoundErrorException();
            case EDS_ERR_DEVICE_BUSY:
                return (T) new EdsdkDeviceBusyErrorException();
            case EDS_ERR_DEVICE_INVALID:
                return (T) new EdsdkDeviceInvalidErrorException();
            case EDS_ERR_DEVICE_EMERGENCY:
                return (T) new EdsdkDeviceEmergencyErrorException();
            case EDS_ERR_DEVICE_MEMORY_FULL:
                return (T) new EdsdkDeviceMemoryFullErrorException();
            case EDS_ERR_DEVICE_INTERNAL_ERROR:
                return (T) new EdsdkDeviceInternalErrorException();
            case EDS_ERR_DEVICE_INVALID_PARAMETER:
                return (T) new EdsdkDeviceInvalidParameterErrorException();
            case EDS_ERR_DEVICE_NO_DISK:
                return (T) new EdsdkDeviceNoDiskErrorException();
            case EDS_ERR_DEVICE_DISK_ERROR:
                return (T) new EdsdkDeviceDiskErrorException();
            case EDS_ERR_DEVICE_CF_GATE_CHANGED:
                return (T) new EdsdkDeviceCfGateChangedErrorException();
            case EDS_ERR_DEVICE_DIAL_CHANGED:
                return (T) new EdsdkDeviceDialChangedErrorException();
            case EDS_ERR_DEVICE_NOT_INSTALLED:
                return (T) new EdsdkDeviceNotInstalledErrorException();
            case EDS_ERR_DEVICE_STAY_AWAKE:
                return (T) new EdsdkDeviceStayAwakeErrorException();
            case EDS_ERR_DEVICE_NOT_RELEASED:
                return (T) new EdsdkDeviceNotReleasedErrorException();
            case EDS_ERR_STREAM_IO_ERROR:
                return (T) new EdsdkStreamIOErrorException();
            case EDS_ERR_STREAM_NOT_OPEN:
                return (T) new EdsdkStreamNotOpenErrorException();
            case EDS_ERR_STREAM_ALREADY_OPEN:
                return (T) new EdsdkStreamAlreadyOpenErrorException();
            case EDS_ERR_STREAM_OPEN_ERROR:
                return (T) new EdsdkStreamOpenErrorException();
            case EDS_ERR_STREAM_CLOSE_ERROR:
                return (T) new EdsdkStreamCloseErrorException();
            case EDS_ERR_STREAM_SEEK_ERROR:
                return (T) new EdsdkStreamSeekErrorException();
            case EDS_ERR_STREAM_TELL_ERROR:
                return (T) new EdsdkStreamTellErrorException();
            case EDS_ERR_STREAM_READ_ERROR:
                return (T) new EdsdkStreamReadErrorException();
            case EDS_ERR_STREAM_WRITE_ERROR:
                return (T) new EdsdkStreamWriteErrorException();
            case EDS_ERR_STREAM_PERMISSION_ERROR:
                return (T) new EdsdkStreamPermissionErrorException();
            case EDS_ERR_STREAM_COULDNT_BEGIN_THREAD:
                return (T) new EdsdkStreamBeginThreadErrorException();
            case EDS_ERR_STREAM_BAD_OPTIONS:
                return (T) new EdsdkStreamBadOptionsErrorException();
            case EDS_ERR_STREAM_END_OF_STREAM:
                return (T) new EdsdkStreamEndStreamErrorException();
            case EDS_ERR_COMM_PORT_IS_IN_USE:
                return (T) new EdsdkCommPortInUseErrorException();
            case EDS_ERR_COMM_DISCONNECTED:
                return (T) new EdsdkCommDisconnectedErrorException();
            case EDS_ERR_COMM_DEVICE_INCOMPATIBLE:
                return (T) new EdsdkCommDeviceIncompatibleErrorException();
            case EDS_ERR_COMM_BUFFER_FULL:
                return (T) new EdsdkCommBufferFullErrorException();
            case EDS_ERR_COMM_USB_BUS_ERR:
                return (T) new EdsdkCommUsbBusErrorException();
//            case EDS_ERR_USB_DEVICE_LOCK_ERROR:
//                break;
//            case EDS_ERR_USB_DEVICE_UNLOCK_ERROR:
//                break;
//            case EDS_ERR_STI_UNKNOWN_ERROR:
//                break;
//            case EDS_ERR_STI_INTERNAL_ERROR:
//                break;
//            case EDS_ERR_STI_DEVICE_CREATE_ERROR:
//                break;
//            case EDS_ERR_STI_DEVICE_RELEASE_ERROR:
//                break;
//            case EDS_ERR_DEVICE_NOT_LAUNCHED:
//                break;
            case EDS_ERR_SESSION_NOT_OPEN:
                return (T) new EdsdkPtpSessionNotOpenErrorException();
            case EDS_ERR_INVALID_TRANSACTIONID:
                return (T) new EdsdkPtpInvalidTransactionIdErrorException();
            case EDS_ERR_INCOMPLETE_TRANSFER:
                return (T) new EdsdkPtpIncompleteTransferErrorException();
            case EDS_ERR_INVALID_STRAGEID:
                return (T) new EdsdkPtpInvalidStorageIdErrorException();
            case EDS_ERR_DEVICEPROP_NOT_SUPPORTED:
                return (T) new EdsdkPtpUnsupportedDevicePropertyErrorException();
            case EDS_ERR_INVALID_OBJECTFORMATCODE:
                return (T) new EdsdkPtpInvalidObjectFormatCodeErrorException();
            case EDS_ERR_SELF_TEST_FAILED:
                return (T) new EdsdkPtpSelfTestErrorException();
            case EDS_ERR_PARTIAL_DELETION:
                return (T) new EdsdkPtpPartialDeletionErrorException();
            case EDS_ERR_SPECIFICATION_BY_FORMAT_UNSUPPORTED:
                return (T) new EdsdkPtpUnsupportedFormatSpecErrorException();
            case EDS_ERR_NO_VALID_OBJECTINFO:
                return (T) new EdsdkPtpInvalidObjectInformationErrorException();
            case EDS_ERR_INVALID_CODE_FORMAT:
                return (T) new EdsdkPtpInvalidCodeFormatErrorException();
            case EDS_ERR_UNKNOWN_VENDOR_CODE:
                return (T) new EdsdkPtpUnknownVendorCodeErrorException();
            case EDS_ERR_CAPTURE_ALREADY_TERMINATED:
                return (T) new EdsdkPtpCaptureTerminatedErrorException();
            case EDS_ERR_PTP_DEVICE_BUSY:
                return (T) new EdsdkPtpDeviceBusyErrorException();
            case EDS_ERR_INVALID_PARENTOBJECT:
                return (T) new EdsdkPtpInvalidParentObjectErrorException();
            case EDS_ERR_INVALID_DEVICEPROP_FORMAT:
                return (T) new EdsdkPtpInvalidPropertyFormatErrorException();
            case EDS_ERR_INVALID_DEVICEPROP_VALUE:
                return (T) new EdsdkPtpInvalidPropertyValueErrorException();
            case EDS_ERR_SESSION_ALREADY_OPEN:
                return (T) new EdsdkPtpSessionAlreadyOpenErrorException();
            case EDS_ERR_TRANSACTION_CANCELLED:
                return (T) new EdsdkPtpTransactionCancelledErrorException18();
            case EDS_ERR_SPECIFICATION_OF_DESTINATION_UNSUPPORTED:
                return (T) new EdsdkPtpUnsupportedDestinationSpecErrorException();
            case EDS_ERR_NOT_CAMERA_SUPPORT_SDK_VERSION:
                return (T) new EdsdkPtpSdkVersionNotSupportedErrorException();
            case EDS_ERR_UNKNOWN_COMMAND:
                return (T) new EdsdkPtpUnknownCommandErrorException();
            case EDS_ERR_OPERATION_REFUSED:
                return (T) new EdsdkPtpOperationRefusedErrorException();
            case EDS_ERR_LENS_COVER_CLOSE:
                return (T) new EdsdkPtpLensCoverClosedErrorException();
            case EDS_ERR_LOW_BATTERY:
                return (T) new EdsdkPtpLowBatteryErrorException();
            case EDS_ERR_OBJECT_NOTREADY:
                return (T) new EdsdkPtpDataSetNotReadyErrorException();
            case EDS_ERR_CANNOT_MAKE_OBJECT:
                return (T) new EdsdkPtpCannotMakeObjectErrorException();
            case EDS_ERR_MEMORYSTATUS_NOTREADY:
                return (T) new EdsdkPtpMemoryNotReadyErrorException();
            case EDS_ERR_TAKE_PICTURE_AF_NG:
                return (T) new EdsdkPictureAutoFocusFailedErrorException();
//            case EDS_ERR_TAKE_PICTURE_RESERVED:
//                return (T) new EdsdkPicture();
            case EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG:
                return (T) new EdsdkPictureMirrorUpErrorException();
            case EDS_ERR_TAKE_PICTURE_SENSOR_CLEANING_NG:
                return (T) new EdsdkPictureSensorCleaningErrorException();
            case EDS_ERR_TAKE_PICTURE_SILENCE_NG:
                return (T) new EdsdkPictureSilenceErrorException();
            case EDS_ERR_TAKE_PICTURE_NO_CARD_NG:
                return (T) new EdsdkPictureNoCardErrorException();
            case EDS_ERR_TAKE_PICTURE_CARD_NG:
                return (T) new EdsdkPictureWriteCardErrorException();
            case EDS_ERR_TAKE_PICTURE_CARD_PROTECT_NG:
                return (T) new EdsdkPictureCardProtectErrorException();
            case EDS_ERR_TAKE_PICTURE_MOVIE_CROP_NG:
                return (T) new EdsdkPictureMovieCropErrorException();
            case EDS_ERR_TAKE_PICTURE_STROBO_CHARGE_NG:
                return (T) new EdsdkPictureStroboChargeErrorException();
            case EDS_ERR_TAKE_PICTURE_NO_LENS_NG:
                return (T) new EdsdkPictureNoLensErrorException();
            case EDS_ERR_TAKE_PICTURE_SPECIAL_MOVIE_MODE_NG:
                return (T) new EdsdkPictureSpecialMovieErrorException();
            case EDS_ERR_TAKE_PICTURE_LV_REL_PROHIBIT_MODE_NG:
                return (T) new EdsdkPictureLvProhibitedErrorException();
            case EDS_ERR_ENUM_NA:
                return (T) new EdsdkGeneralEnumNaErrorException();
            case EDS_ERR_INVALID_FN_CALL:
                return (T) new EdsdkGeneralInvalidFnCallErrorException();
            case EDS_ERR_HANDLE_NOT_FOUND:
                return (T) new EdsdkGeneralHandleNotFoundErrorException();
            case EDS_ERR_INVALID_ID:
                return (T) new EdsdkGeneralInvalidIdErrorException();
            case EDS_ERR_WAIT_TIMEOUT_ERROR:
                return (T) new EdsdkGeneralWaitTimeoutErrorException();
//            case EDS_ERR_LAST_GENERIC_ERROR_PLUS_ONE:
//                break;
            default:
                return (T) new EdsdkErrorException(this);
        }
    }

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsdkError ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsdkError.class, value);
    }
}

