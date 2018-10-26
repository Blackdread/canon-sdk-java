package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.exception.EdsdkException;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * ED-SDK Generic Error IDs
 *
 * <p>Created on 04/10/2018</p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsdkErrors implements NativeEnum<Integer>, NativeErrorEnum<Integer> {

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

    EDS_ERR_OK("Success, No Errors"),

    /*
     * ED-SDK Generic Error IDs
     */

    /* Miscellaneous errors */
    EDS_ERR_UNIMPLEMENTED("Not implemented"),
    EDS_ERR_INTERNAL_ERROR("Internal error"),
    EDS_ERR_MEM_ALLOC_FAILED("Memory allocation error"),
    EDS_ERR_MEM_FREE_FAILED("Memory release error"),
    EDS_ERR_OPERATION_CANCELLED("Operation canceled"),
    EDS_ERR_INCOMPATIBLE_VERSION("Version error"),
    EDS_ERR_NOT_SUPPORTED("Not supported"),
    EDS_ERR_UNEXPECTED_EXCEPTION("Unexpected exception"),
    EDS_ERR_PROTECTION_VIOLATION("Protection violation"),
    EDS_ERR_MISSING_SUBCOMPONENT("Missing subcomponent"),
    EDS_ERR_SELECTION_UNAVAILABLE("Selection unavailable"),

    /* File errors */
    EDS_ERR_FILE_IO_ERROR("IO error"),
    EDS_ERR_FILE_TOO_MANY_OPEN("Too many files open"),
    EDS_ERR_FILE_NOT_FOUND("File does not exist"),
    EDS_ERR_FILE_OPEN_ERROR("Open error"),
    EDS_ERR_FILE_CLOSE_ERROR("Close error"),
    EDS_ERR_FILE_SEEK_ERROR("Seek error"),
    EDS_ERR_FILE_TELL_ERROR("Tell error"),
    EDS_ERR_FILE_READ_ERROR("Read error"),
    EDS_ERR_FILE_WRITE_ERROR("Write error"),
    EDS_ERR_FILE_PERMISSION_ERROR("Permission error"),
    EDS_ERR_FILE_DISK_FULL_ERROR("Disk full"),
    EDS_ERR_FILE_ALREADY_EXISTS("File already exists"),
    EDS_ERR_FILE_FORMAT_UNRECOGNIZED("Format error"),
    EDS_ERR_FILE_DATA_CORRUPT("Invalid data"),
    EDS_ERR_FILE_NAMING_NA("File naming error"),

    /* Directory errors */
    EDS_ERR_DIR_NOT_FOUND("Directory does not exist"),
    EDS_ERR_DIR_IO_ERROR("I/O error"),
    EDS_ERR_DIR_ENTRY_NOT_FOUND("No file in directory"),
    EDS_ERR_DIR_ENTRY_EXISTS("File in directory"),
    EDS_ERR_DIR_NOT_EMPTY("Directory full"),

    /* Property errors */
    EDS_ERR_PROPERTIES_UNAVAILABLE("Property (and additional property information) unavailable"),
    EDS_ERR_PROPERTIES_MISMATCH("Property mismatch"),
    EDS_ERR_PROPERTIES_NOT_LOADED("Property not loaded"),

    /* Function Parameter errors */
    EDS_ERR_INVALID_PARAMETER("Invalid function parameter"),
    EDS_ERR_INVALID_HANDLE("Handle error"),
    EDS_ERR_INVALID_POINTER("Pointer error"),
    EDS_ERR_INVALID_INDEX("Index error"),
    EDS_ERR_INVALID_LENGTH("Length error"),
    EDS_ERR_INVALID_FN_POINTER("FN pointer error"),
    EDS_ERR_INVALID_SORT_FN("Sort FN error"),

    /* Device errors */
    EDS_ERR_DEVICE_NOT_FOUND("Device not found"),
    EDS_ERR_DEVICE_BUSY("Device busy. If a device busy error occurs, reissue the command after a while. The camera will become unstable"),
    EDS_ERR_DEVICE_INVALID("Device error"),
    EDS_ERR_DEVICE_EMERGENCY("Device emergency"),
    EDS_ERR_DEVICE_MEMORY_FULL("Device memory full"),
    EDS_ERR_DEVICE_INTERNAL_ERROR("Internal device error"),
    EDS_ERR_DEVICE_INVALID_PARAMETER("Device parameter invalid"),
    EDS_ERR_DEVICE_NO_DISK("No disk"),
    EDS_ERR_DEVICE_DISK_ERROR("Disk error"),
    EDS_ERR_DEVICE_CF_GATE_CHANGED("The CF gate has been changed"),
    EDS_ERR_DEVICE_DIAL_CHANGED("The dial has been changed"),
    EDS_ERR_DEVICE_NOT_INSTALLED("Device not installed"),
    EDS_ERR_DEVICE_STAY_AWAKE("Device connected in awake mode"),
    EDS_ERR_DEVICE_NOT_RELEASED("Device not released"),

    /* Stream errors */
    EDS_ERR_STREAM_IO_ERROR("Stream I/O error"),
    EDS_ERR_STREAM_NOT_OPEN("Stream open error"),
    EDS_ERR_STREAM_ALREADY_OPEN("Stream already open"),
    EDS_ERR_STREAM_OPEN_ERROR("Failed to open stream"),
    EDS_ERR_STREAM_CLOSE_ERROR("Failed to close stream"),
    EDS_ERR_STREAM_SEEK_ERROR("Stream seek error"),
    EDS_ERR_STREAM_TELL_ERROR("Stream tell error"),
    EDS_ERR_STREAM_READ_ERROR("Failed to read stream"),
    EDS_ERR_STREAM_WRITE_ERROR("Failed to write stream"),
    EDS_ERR_STREAM_PERMISSION_ERROR("Permission error"),
    EDS_ERR_STREAM_COULDNT_BEGIN_THREAD("Could not start reading thumbnail"),
    EDS_ERR_STREAM_BAD_OPTIONS("Invalid stream option"),
    EDS_ERR_STREAM_END_OF_STREAM("Invalid stream termination"),

    /* Communications errors */
    EDS_ERR_COMM_PORT_IS_IN_USE("Port in use"),
    EDS_ERR_COMM_DISCONNECTED("Port disconnected"),
    EDS_ERR_COMM_DEVICE_INCOMPATIBLE("Incompatible device"),
    EDS_ERR_COMM_BUFFER_FULL("Buffer full"),
    EDS_ERR_COMM_USB_BUS_ERR("USB bus error"),

    /* Lock/Unlock */
    EDS_ERR_USB_DEVICE_LOCK_ERROR("Failed to lock the UI"),
    EDS_ERR_USB_DEVICE_UNLOCK_ERROR("Failed to unlock the UI"),

    /* STI/WIA */
    EDS_ERR_STI_UNKNOWN_ERROR("Unknown STI"),
    EDS_ERR_STI_INTERNAL_ERROR("Internal STI error"),
    EDS_ERR_STI_DEVICE_CREATE_ERROR("Device creation error"),
    EDS_ERR_STI_DEVICE_RELEASE_ERROR("Device release error"),
    EDS_ERR_DEVICE_NOT_LAUNCHED("Device startup failed"),

    EDS_ERR_ENUM_NA("Enumeration terminated (there was no suitable enumeration item)"),
    EDS_ERR_INVALID_FN_CALL("Called in a mode when the function could not be used"),
    EDS_ERR_HANDLE_NOT_FOUND("Handle not found"),
    EDS_ERR_INVALID_ID("Invalid ID"),
    EDS_ERR_WAIT_TIMEOUT_ERROR("Timeout"),

    /* PTP */
    EDS_ERR_SESSION_NOT_OPEN("Session open error"),
    EDS_ERR_INVALID_TRANSACTIONID("Invalid transaction ID"),
    EDS_ERR_INCOMPLETE_TRANSFER("Transfer problem"),
    EDS_ERR_INVALID_STRAGEID("Storage error"),
    EDS_ERR_DEVICEPROP_NOT_SUPPORTED("Unsupported device property"),
    EDS_ERR_INVALID_OBJECTFORMATCODE("Invalid object format code"),
    EDS_ERR_SELF_TEST_FAILED("Failed self-diagnosis"),
    EDS_ERR_PARTIAL_DELETION("Failed in partial deletion"),
    EDS_ERR_SPECIFICATION_BY_FORMAT_UNSUPPORTED("Unsupported format specification"),
    EDS_ERR_NO_VALID_OBJECTINFO("Invalid object information"),
    EDS_ERR_INVALID_CODE_FORMAT("Invalid code format"),
    EDS_ERR_UNKNOWN_VENDOR_CODE("Unknown vendor code"),
    EDS_ERR_CAPTURE_ALREADY_TERMINATED("Capture already terminated"),
    EDS_ERR_PTP_DEVICE_BUSY("PTP device busy"),
    EDS_ERR_INVALID_PARENTOBJECT("Invalid parent object"),
    EDS_ERR_INVALID_DEVICEPROP_FORMAT("Invalid property format"),
    EDS_ERR_INVALID_DEVICEPROP_VALUE("Invalid property value"),
    EDS_ERR_SESSION_ALREADY_OPEN("Session already open"),
    EDS_ERR_TRANSACTION_CANCELLED("Transaction canceled"),
    EDS_ERR_SPECIFICATION_OF_DESTINATION_UNSUPPORTED("Unsupported destination specification"),
    EDS_ERR_NOT_CAMERA_SUPPORT_SDK_VERSION("SDK version not supported"),

    /* PTP Vendor */
    EDS_ERR_UNKNOWN_COMMAND("Unknown command"),
    EDS_ERR_OPERATION_REFUSED("Operation refused"),
    EDS_ERR_LENS_COVER_CLOSE("Lens cover closed"),
    EDS_ERR_LOW_BATTERY("Low battery"),
    EDS_ERR_OBJECT_NOTREADY("Image data set not ready for live view"),
    EDS_ERR_CANNOT_MAKE_OBJECT("Cannot Make Object"),
    EDS_ERR_MEMORYSTATUS_NOTREADY("Memory not ready"),

    /* Take Picture errors */
    EDS_ERR_TAKE_PICTURE_AF_NG("Focus failed"),
    EDS_ERR_TAKE_PICTURE_RESERVED("Reserved"),
    EDS_ERR_TAKE_PICTURE_MIRROR_UP_NG("Currently configuring mirror up"),
    EDS_ERR_TAKE_PICTURE_SENSOR_CLEANING_NG("Currently cleaning sensor"),
    EDS_ERR_TAKE_PICTURE_SILENCE_NG("Currently performing silent operations"),
    EDS_ERR_TAKE_PICTURE_NO_CARD_NG("Card not installed"),
    EDS_ERR_TAKE_PICTURE_CARD_NG("Error writing to card"),
    EDS_ERR_TAKE_PICTURE_CARD_PROTECT_NG("Card write protected"),
    EDS_ERR_TAKE_PICTURE_MOVIE_CROP_NG("Cropping Movie"),
    EDS_ERR_TAKE_PICTURE_STROBO_CHARGE_NG("Strobe Charging"),
    EDS_ERR_TAKE_PICTURE_NO_LENS_NG("No lens"),
    EDS_ERR_TAKE_PICTURE_SPECIAL_MOVIE_MODE_NG("Special movie mode"),
    EDS_ERR_TAKE_PICTURE_LV_REL_PROHIBIT_MODE_NG("LV prohibited mode"),


    EDS_ERR_LAST_GENERIC_ERROR_PLUS_ONE("Not used");

    private final int value;

    private final String description;

    EdsdkErrors(final String description) {
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
    public <T extends EdsdkException> T getException() {
        throw new IllegalStateException("not impl");
    }

    public static EdsdkErrors ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsdkErrors.class, value);
    }
}

