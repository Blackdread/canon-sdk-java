package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.exception.EdsdkErrorException;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

import static org.blackdread.cameraframework.api.constant.ErrorType.*;

/**
 * ED-SDK Generic Error IDs
 *
 * <p>Created on 04/10/2018</p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsdkError implements NativeEnum<Integer>, NativeErrorEnum<Integer> {

    /*
     * ED-SDK Error Code Masks
     */

    // TODO no reason to put those here...

    /*
     * ED-SDK Base Component IDs
     */

    // TODO no reason to put those here...

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
        // simple implementation for now
        return (T) new EdsdkErrorException(this);
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

