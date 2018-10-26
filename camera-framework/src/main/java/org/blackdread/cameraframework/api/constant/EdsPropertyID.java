package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Property IDs
 * <p>Created on 2018/10/06.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsPropertyID implements NativeEnum<Integer> {
    /*----------------------------------
    Camera Setting Properties
    ----------------------------------*/
    kEdsPropID_Unknown("Unknown", EdsDataType.kEdsDataType_Unknown),

    kEdsPropID_ProductName("A string representing the product name", EdsDataType.kEdsDataType_String),
    kEdsPropID_OwnerName("Indicates a string identifying the owner as registered on the camera", EdsDataType.kEdsDataType_String),
    kEdsPropID_MakerName("Indicates a string identifying the manufacturer", EdsDataType.kEdsDataType_String),
    kEdsPropID_DateTime("Indicates the time and date set on the camera or the shooting date and time of images", EdsDataType.kEdsDataType_Time),
    kEdsPropID_FirmwareVersion("Indicates the camera's firmware version", EdsDataType.kEdsDataType_String),
    kEdsPropID_BatteryLevel("Indicates the camera battery level", EdsDataType.kEdsDataType_UInt32),
    /**
     * Not seen on API Reference, EdsUInt32 set as previous project (was seen on different cameras)
     */
    kEdsPropID_CFn("Custom Function #", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_SaveTo("Indicates the destination of images after shooting", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_CurrentStorage("Gets the current storage media for the camera", EdsDataType.kEdsDataType_String),
    kEdsPropID_CurrentFolder("Gets the current folder for the camera", EdsDataType.kEdsDataType_String),
    /**
     * Not specified in API Reference
     */
    kEdsPropID_MyMenu("My Menu", EdsDataType.kEdsDataType_UInt32_Array),

    kEdsPropID_BatteryQuality("Gets the level of degradation of the battery", EdsDataType.kEdsDataType_UInt32),

    kEdsPropID_BodyIDEx("Indicates the product serial number", EdsDataType.kEdsDataType_String),
    kEdsPropID_HDDirectoryStructure("Gets the directory structure information for USB storage", EdsDataType.kEdsDataType_String),

    /*----------------------------------
    Image Properties
    ----------------------------------*/
    kEdsPropID_ImageQuality("Indicates the image quality", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_JpegQuality("Indicates the JPEG compression", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_Orientation("Indicates image rotation information", EdsDataType.kEdsDataType_UInt32),
    /**
     * API Reference 3.8 says kEdsDataType_ByteBlock EdsInt8[], old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_ICCProfile("Indicates the ICC profile data embedded in an image.", EdsDataType.kEdsDataType_ByteBlock),
    kEdsPropID_FocusInfo("Indicates focus information for image data at the time of shooting", EdsDataType.kEdsDataType_FocusInfo),
    kEdsPropID_DigitalExposure("Indicates the digital exposure compensation", EdsDataType.kEdsDataType_Rational),
    kEdsPropID_WhiteBalance("Indicates the white balance type", EdsDataType.kEdsDataType_Int32),
    kEdsPropID_ColorTemperature("Indicates the color temperature setting value (Units: Kelvin)", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_WhiteBalanceShift("Indicates the white balance compensation", EdsDataType.kEdsDataType_Int32_Array),
    kEdsPropID_Contrast("Indicates the contrast", EdsDataType.kEdsDataType_Int32),
    kEdsPropID_ColorSaturation("Indicates the color saturation", EdsDataType.kEdsDataType_Int32),
    kEdsPropID_ColorTone("Indicates the color tone", EdsDataType.kEdsDataType_Int32),
    /**
     * EdsImageRef (Other than 1D/1Ds) kEdsDataType_Int32 EdsInt32
     * <br>
     * EdsImageRef (1D/1Ds) kEdsDataType_Int32_Array EdsInt32[]
     */
    kEdsPropID_Sharpness("Indicates the sharpness setting", EdsDataType.kEdsDataType_Int32),
    kEdsPropID_ColorSpace("Indicates the color space", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_ToneCurve("Indicates the tone curve", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_PhotoEffect("Indicates the photo effect", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_FilterEffect("Indicates the monochrome filter effect", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_ToningEffect("Indicates the monochrome tone", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_ParameterSet("Indicates the current processing parameter set on a camera", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_ColorMatrix("Indicates the color matrix", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_PictureStyle("Indicates the picture style", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_PictureStyleDesc("Indicates settings for each picture style", EdsDataType.kEdsDataType_PictureStyleDesc),
    kEdsPropID_PictureStyleCaption("Returns the user-specified picture style caption name at the time of shooting", EdsDataType.kEdsDataType_String),

    /*----------------------------------
    Image Processing Properties
    ----------------------------------*/
    kEdsPropID_Linear("Indicates if linear processing is activated or not", EdsDataType.kEdsDataType_Bool),
    kEdsPropID_ClickWBPoint("Indicates the coordinates when an image is clicked to set the white balance", EdsDataType.kEdsDataType_Point),
    /**
     * API Reference 3.8 says kEdsDataType_ByteBlock EdsInt8[], old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_WBCoeffs("Indicates the white balance value", EdsDataType.kEdsDataType_ByteBlock),

    /*----------------------------------
    Image GPS Properties
    ----------------------------------*/
    kEdsPropID_GPSVersionID("Indicates the version of GPSInfoIFD. The version is given as 2.2.0.0", EdsDataType.kEdsDataType_UInt8),
    kEdsPropID_GPSLatitudeRef("Indicates whether the latitude is north or south latitude. The value 'N' indicates north latitude,and 'S' is south latitude", EdsDataType.kEdsDataType_String),
    kEdsPropID_GPSLatitude("Indicates the latitude. The latitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively", EdsDataType.kEdsDataType_Rational_Array),
    kEdsPropID_GPSLongitudeRef("Indicates whether the longitude is east or west longitude. 'E' indicates east longitude, and 'W'is west longitude", EdsDataType.kEdsDataType_String),
    kEdsPropID_GPSLongitude("Indicates the longitude. The longitude is expressed as three RATIONAL values giving the degrees,minutes, and seconds, respectively", EdsDataType.kEdsDataType_Rational_Array),
    kEdsPropID_GPSAltitudeRef("Indicates the altitude used as the reference altitude. If the reference is sea level and the altitude is above sea level, 0 is given. If the altitude is below sea level, a value of 1 is given and the altitude is indicated as an absolute value in the GPSAltitude. The reference unit is meters", EdsDataType.kEdsDataType_UInt8),
    kEdsPropID_GPSAltitude("Indicates the altitude based on the reference in GPSAltitudeRef. Altitude is expressed as one RATIONAL value. The reference unit is meters", EdsDataType.kEdsDataType_Rational),
    kEdsPropID_GPSTimeStamp("Indicates the time as UTC (Coordinated Universal Time). TimeStamp is expressed as three RATIONAL values giving the hour, minute, and second", EdsDataType.kEdsDataType_Rational_Array),
    kEdsPropID_GPSSatellites("Indicates the GPS satellites used for measurements", EdsDataType.kEdsDataType_String),
    kEdsPropID_GPSStatus("Indicates the status of the GPS receiver when the image is recorded. 'A' means measurement is in progress, and 'V' means the measurement is Interoperability", EdsDataType.kEdsDataType_String),
    kEdsPropID_GPSMapDatum("Indicates the geodetic survey data used by the GPS receiver", EdsDataType.kEdsDataType_String),
    kEdsPropID_GPSDateStamp("A character string recording date and time information relative to UTC (Coordinated Universal Time). The format is \"YYYY:MM:DD.\" The length of the string is 11 bytes including NULL", EdsDataType.kEdsDataType_String),

    /*----------------------------------
    Property Mask
    ----------------------------------*/
    kEdsPropID_AtCapture_Flag("Get Properties at Time of Shooting, only a mask. This property ID cannot be used by itself", EdsDataType.kEdsDataType_UInt32),

    /*----------------------------------
    Capture Properties
    ----------------------------------*/
    /**
     * Shooting Mode
     */
    kEdsPropID_AEMode("Indicates settings values of the camera in shooting mode", EdsDataType.kEdsDataType_UInt32),
    /**
     * Drive Mode
     */
    kEdsPropID_DriveMode("Indicates settings values of the camera in drive mode", EdsDataType.kEdsDataType_UInt32),
    /**
     * ISO Speed
     */
    kEdsPropID_ISOSpeed("Indicates ISO sensitivity settings values", EdsDataType.kEdsDataType_UInt32),
    /**
     * Metering Mode
     */
    kEdsPropID_MeteringMode("Indicates the metering mode", EdsDataType.kEdsDataType_UInt32),
    /**
     * Auto-Focus Mode
     */
    kEdsPropID_AFMode("Indicates AF mode settings values", EdsDataType.kEdsDataType_UInt32),
    /**
     * Aperture Value
     * <br>
     * For EdsImageRef it uses kEdsType_Rational EdsRational
     */
    kEdsPropID_Av("Indicates the camera's aperture value", EdsDataType.kEdsDataType_UInt32),
    /**
     * Shutter Speed
     * <br>
     * For EdsImageRef it uses kEdsType_Rational EdsRational
     */
    kEdsPropID_Tv("Indicates the shutter speed", EdsDataType.kEdsDataType_UInt32),
    /**
     * Exposure Compensation
     * <br>
     * For EdsImageRef it uses kEdsType_Rational EdsRational
     */
    kEdsPropID_ExposureCompensation("Indicates the exposure compensation", EdsDataType.kEdsDataType_UInt32),
    /**
     * Flash Compensation
     */
    kEdsPropID_FlashCompensation("Indicates the flash compensation", EdsDataType.kEdsDataType_UInt32),
    /**
     * Focal Length
     */
    kEdsPropID_FocalLength("Indicates the focal length of the lens", EdsDataType.kEdsDataType_Rational_Array),
    /**
     * Available Shots
     */
    kEdsPropID_AvailableShots("Indicates the number of shots available on a camera", EdsDataType.kEdsDataType_UInt32),
    /**
     * Bracket
     */
    kEdsPropID_Bracket("Indicates the current bracket type", EdsDataType.kEdsDataType_UInt32),
    /**
     * White Balance Bracket
     */
    kEdsPropID_WhiteBalanceBracket("Indicates the white balance bracket amount", EdsDataType.kEdsDataType_Int32_Array),
    /**
     * Lens Name
     */
    kEdsPropID_LensName("Returns the lens name at the time of shooting", EdsDataType.kEdsDataType_String),
    /**
     * AE Bracket
     */
    kEdsPropID_AEBracket("Indicates the AE bracket compensation of image data", EdsDataType.kEdsDataType_Rational),
    /**
     * FE Bracket
     */
    kEdsPropID_FEBracket("Indicates the FE bracket compensation at the time of shooting of image data", EdsDataType.kEdsDataType_Rational),
    /**
     * ISO Bracket
     */
    kEdsPropID_ISOBracket("Indicates the ISO bracket compensation at the time of shooting of image data", EdsDataType.kEdsDataType_Rational),
    /**
     * Noise Reduction
     */
    kEdsPropID_NoiseReduction("Indicates noise reduction", EdsDataType.kEdsDataType_UInt32),
    /**
     * Flash Status
     */
    kEdsPropID_FlashOn("Indicates if the flash was on at the time of shooting", EdsDataType.kEdsDataType_UInt32),
    /**
     * Red Eye Status
     */
    kEdsPropID_RedEye("Indicates red-eye reduction status", EdsDataType.kEdsDataType_UInt32),
    /**
     * Flash Mode
     */
    kEdsPropID_FlashMode("Indicates the flash type at the time of shooting", EdsDataType.kEdsDataType_UInt32_Array),
    /**
     * Lens Status
     */
    kEdsPropID_LensStatus("Indicates the flash type at the time of shooting", EdsDataType.kEdsDataType_UInt32),
    /**
     * Artist
     */
    kEdsPropID_Artist("Indicates a string identifying the photographer as registered on the camera", EdsDataType.kEdsDataType_String),
    /**
     * Copyright
     */
    kEdsPropID_Copyright("Indicates a string identifying the copyright information as registered on the camera", EdsDataType.kEdsDataType_String),
    /**
     * Depth of Field
     * <br>
     * Not specified in API Reference
     */
    kEdsPropID_DepthOfField("Depth of Field", EdsDataType.kEdsDataType_UInt32),
    /**
     * EF Compensation
     * <br>
     * Not specified in API Reference
     */
    kEdsPropID_EFCompensation("EF Compensation", EdsDataType.kEdsDataType_Unknown),
    /**
     * Shooting Mode Select
     */
    kEdsPropID_AEModeSelect("Indicates settings values of the camera in shooting mode", EdsDataType.kEdsDataType_UInt32),
    /**
     * Movie Shooting Status
     */
    kEdsPropID_Record("You can begin/end movie shooting", EdsDataType.kEdsDataType_UInt32),

    /*----------------------------------
    EVF Properties
    ----------------------------------*/
    kEdsPropID_Evf_OutputDevice("Starts/ends live view. Live View Output Device", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_Evf_Mode("Gets/sets live view function settings", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_Evf_WhiteBalance("Gets/sets the white balance of the live view image", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_Evf_ColorTemperature("Gets/sets the color temperature of the live view image", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_Evf_DepthOfFieldPreview("Turns the depth of field ON/OFF during Preview mode", EdsDataType.kEdsDataType_UInt32),

    // EVF IMAGE DATA Properties
    kEdsPropID_Evf_Zoom("Gets/sets the zoom ratio for the live view", EdsDataType.kEdsDataType_UInt32),
    kEdsPropID_Evf_ZoomPosition("Gets/sets the focus and zoom border position for live view", EdsDataType.kEdsDataType_Point),
    /**
     * Live View Focus Aid
     * <br>
     * Not specified in API Reference
     */
    kEdsPropID_Evf_FocusAid("Live View Focus Aid", EdsDataType.kEdsDataType_Unknown),
    /**
     * Old project: API lists EdsUInt32[]
     * <br>
     * Not specified in API Reference
     */
    kEdsPropID_Evf_Histogram("Live View Histogram", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Crop Position
     */
    kEdsPropID_Evf_ImagePosition("Gets the cropping position of the enlarged live view image", EdsDataType.kEdsDataType_Point),
    /**
     * Live View Histogram Status
     * <br>
     * API Reference 3.8 says kEdsDataType_Uint32 EdsUInt32, old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_HistogramStatus("Gets the display status of the histogram", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Auto-Focus Mode
     */
    kEdsPropID_Evf_AFMode("Set/Get the AF mode for the live view", EdsDataType.kEdsDataType_UInt32),

    /**
     * Live View Histogram Y
     * <br>
     * API Reference 3.8 says kEdsDataType_ByteBlock EdsUInt32[], old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_HistogramY("Gets the histogram for live view image data. The histogram can be used to obtain Y", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Histogram R
     * <br>
     * API Reference 3.8 says kEdsDataType_ByteBlock EdsUInt32[], old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_HistogramR("Gets the histogram for live view image data. The histogram can be used to obtain R", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Histogram G
     * <br>
     * API Reference 3.8 says kEdsDataType_ByteBlock EdsUInt32[], old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_HistogramG("Gets the histogram for live view image data. The histogram can be used to obtain G", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Histogram B
     * <br>
     * API Reference 3.8 says kEdsDataType_ByteBlock EdsUInt32[], old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_HistogramB("Gets the histogram for live view image data. The histogram can be used to obtain B", EdsDataType.kEdsDataType_ByteBlock),

    /**
     * Live View Coordinate System
     * <br>
     * API Reference 3.8 says kEdsDataType_Point EdsPoint, old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_CoordinateSystem("Get the coordinate system of the live view image", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Zoom Rectangle
     * <br>
     * API Reference 3.8 says kEdsDataType_Point EdsRect, old project used EdsDataType.kEdsDataType_ByteBlock
     */
    kEdsPropID_Evf_ZoomRect("Gets the focus and zoom border rectangle for live view", EdsDataType.kEdsDataType_ByteBlock),
    /**
     * Live View Crop Rectangle
     * <br>
     * Not specified in API Reference
     */
    kEdsPropID_Evf_ImageClipRect("Live View Crop Rectangle", EdsDataType.kEdsDataType_ByteBlock);

    private final int value;
    private final String description;
    private final EdsDataType type;

    EdsPropertyID(final String description, final EdsDataType type) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.class, name());
        this.description = description;
        this.type = type;
    }

    @Override
    public final Integer value() {
        return value;
    }

    @Override
    public final String description() {
        return description;
    }

    public final EdsDataType type() {
        return type;
    }

    public static EdsPropertyID ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsPropertyID.class, value);
    }

}
