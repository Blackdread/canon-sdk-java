package org.blackdread.cameraframework.api.command;

import com.sun.jna.NativeLong;
import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.util.Objects;

/**
 * Sets a property on the camera.
 *
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class SetPropertyCommand<R> extends AbstractCanonCommand<R> {

    private final EdsPropertyID propertyID;

    private final NativeEnum<? extends Number> nativeEnum;

    private final long inParam;

    /**
     * Different than NativeEnum
     */
    private final Object value;

    public SetPropertyCommand() {
        this.propertyID = null;
        this.nativeEnum = null;
        this.inParam = 0L;
        this.value = null;
    }

    public SetPropertyCommand(final EdsPropertyID propertyID, final NativeEnum<? extends Number> nativeEnum) {
        this.propertyID = Objects.requireNonNull(propertyID);
        this.nativeEnum = Objects.requireNonNull(nativeEnum);
        this.inParam = 0L;
        this.value = null;
    }

    public SetPropertyCommand(final EdsPropertyID propertyID, final Object value) {
        this.propertyID = Objects.requireNonNull(propertyID);
        this.nativeEnum = null;
        this.inParam = 0L;
        this.value = Objects.requireNonNull(value);
    }

    /*
    public SetPropertyCommand(final EdsPropertyID propertyID, final Object value, final long inParam) {
        this.propertyID = Objects.requireNonNull(propertyID);
        this.nativeEnum = null;
        this.inParam = inParam;
        this.value = Objects.requireNonNull(value);
    }
    //*/

    public SetPropertyCommand(final SetPropertyCommand<R> toCopy) {
        super(toCopy);
        this.propertyID = toCopy.propertyID;
        this.nativeEnum = toCopy.nativeEnum;
        this.inParam = toCopy.inParam;
        this.value = toCopy.value;
    }

    @Override
    protected R runInternal() {
        /*
         * Implementation to simplify set values so less duplicate code in runInternal() for set commands.
         *
         * This implementation might not fit for all set, if it is the case then sub-class should override runInternal().
         */
        final EdsdkLibrary.EdsBaseRef targetRef = getTargetRefInternal();

        if (nativeEnum != null) {
            CanonFactory.propertySetLogic().setPropertyData(targetRef, propertyID, nativeEnum);
            return null;
        }

        if (value != null) {
            CanonFactory.propertySetLogic().setPropertyDataAdvanced(targetRef, propertyID, value);
            return null;
        }

        // well not sure there is much to do or change
        throw new NotImplementedException("that's a pity...");
    }

    /**
     * Default set command to set a property from a value, useful if current API does not provide a command for that property.
     */
    public static class ByValue extends SetPropertyCommand<Void> {
        public ByValue(final EdsPropertyID propertyID, final Object value) {
            super(propertyID, value);
        }

        public ByValue(final ByValue toCopy) {
            super(toCopy);
        }
    }

    /**
     * Default set command to set a property from an enum, useful if current API does not provide a command for that property.
     */
    public static class ByEnum extends SetPropertyCommand<Void> {
        public ByEnum(final EdsPropertyID propertyID, final NativeEnum<? extends Number> value) {
            super(propertyID, value);
        }

        public ByEnum(final ByEnum toCopy) {
            super(toCopy);
        }
    }

    public static class Aperture extends SetPropertyCommand<Void> {
        public Aperture(final EdsAv value) {
            super(EdsPropertyID.kEdsPropID_Av, value);
        }

        public Aperture(final Aperture toCopy) {
            super(toCopy);
        }
    }

    public static class Artist extends SetPropertyCommand<Void> {
        public Artist(final String value) {
            super(EdsPropertyID.kEdsPropID_Artist, value);
        }

        public Artist(final Artist toCopy) {
            super(toCopy);
        }
    }

    public static class ColorSpace extends SetPropertyCommand<Void> {
        public ColorSpace(final EdsColorSpace value) {
            super(EdsPropertyID.kEdsPropID_ColorSpace, value);
        }

        public ColorSpace(final ColorSpace toCopy) {
            super(toCopy);
        }
    }

    public static class ColorTemperature extends SetPropertyCommand<Void> {
        public ColorTemperature(final long value) {
            super(EdsPropertyID.kEdsPropID_ColorTemperature, value);
        }

        public ColorTemperature(final ColorTemperature toCopy) {
            super(toCopy);
        }
    }

    public static class Copyright extends SetPropertyCommand<Void> {
        public Copyright(final String value) {
            super(EdsPropertyID.kEdsPropID_Copyright, value);
        }

        public Copyright(final Copyright toCopy) {
            super(toCopy);
        }
    }

    public static class DriveMode extends SetPropertyCommand<Void> {
        public DriveMode(final EdsDriveMode value) {
            super(EdsPropertyID.kEdsPropID_DriveMode, value);
        }

        public DriveMode(final DriveMode toCopy) {
            super(toCopy);
        }
    }

    public static class ExposureCompensation extends SetPropertyCommand<Void> {
        public ExposureCompensation(final EdsExposureCompensation value) {
            super(EdsPropertyID.kEdsPropID_ExposureCompensation, value);
        }

        public ExposureCompensation(final ExposureCompensation toCopy) {
            super(toCopy);
        }
    }

    public static class HardDriveDirectoryStructure extends SetPropertyCommand<Void> {
        public HardDriveDirectoryStructure(final String value) {
            super(EdsPropertyID.kEdsPropID_HDDirectoryStructure, value);
        }

        public HardDriveDirectoryStructure(final HardDriveDirectoryStructure toCopy) {
            super(toCopy);
        }
    }

    public static class ImageQuality extends SetPropertyCommand<Void> {
        public ImageQuality(final EdsImageQuality value) {
            super(EdsPropertyID.kEdsPropID_ImageQuality, value);
        }

        public ImageQuality(final ImageQuality toCopy) {
            super(toCopy);
        }
    }

    public static class IsoSpeed extends SetPropertyCommand<Void> {
        public IsoSpeed(final EdsISOSpeed value) {
            super(EdsPropertyID.kEdsPropID_ISOSpeed, value);
        }

        public IsoSpeed(final IsoSpeed toCopy) {
            super(toCopy);
        }
    }

    public static class JpegQuality extends SetPropertyCommand<Void> {
        public JpegQuality(final int value) {
            super(EdsPropertyID.kEdsPropID_JpegQuality, value);
        }

        public JpegQuality(final JpegQuality toCopy) {
            super(toCopy);
        }
    }

    public static class LiveViewAutoFocusMode extends SetPropertyCommand<Void> {
        public LiveViewAutoFocusMode(final EdsEvfAFMode value) {
            super(EdsPropertyID.kEdsPropID_Evf_AFMode, value);
        }

        public LiveViewAutoFocusMode(final LiveViewAutoFocusMode toCopy) {
            super(toCopy);
        }
    }

    public static class LiveViewColorTemperature extends SetPropertyCommand<Void> {
        public LiveViewColorTemperature(final long value) {
            super(EdsPropertyID.kEdsPropID_Evf_ColorTemperature, value);
        }

        public LiveViewColorTemperature(final LiveViewColorTemperature toCopy) {
            super(toCopy);
        }
    }

    /*
    public static class LiveViewMode extends SetPropertyCommand<Void> {
        public LiveViewMode(final int value) {
            super(EdsPropertyID.kEdsPropID_Evf_OutputDevice, value);
        }

        public LiveViewMode(final LiveViewMode toCopy) {
            super(toCopy);
        }

        @Override
        protected Void runInternal() {

        }
    }
    //*/

    public static class LiveViewOutputDevice extends SetPropertyCommand<Void> {
        public LiveViewOutputDevice(final EdsEvfOutputDevice value) {
            super(EdsPropertyID.kEdsPropID_Evf_OutputDevice, value);
        }

        public LiveViewOutputDevice(final LiveViewOutputDevice toCopy) {
            super(toCopy);
        }
    }

    public static class LiveViewWhiteBalance extends SetPropertyCommand<Void> {
        public LiveViewWhiteBalance(final EdsWhiteBalance value) {
            super(EdsPropertyID.kEdsPropID_Evf_WhiteBalance, value);
        }

        public LiveViewWhiteBalance(final LiveViewWhiteBalance toCopy) {
            super(toCopy);
        }
    }

    public static class LiveViewZoomPosition extends SetPropertyCommand<Void> {
        public LiveViewZoomPosition(final EdsPoint value) {
            super(EdsPropertyID.kEdsPropID_Evf_ZoomPosition, value);
        }

        public LiveViewZoomPosition(final long x, final long y) {
            super(EdsPropertyID.kEdsPropID_Evf_ZoomPosition, new EdsPoint(new NativeLong(x), new NativeLong(y)));
        }

        public LiveViewZoomPosition(final LiveViewZoomPosition toCopy) {
            super(toCopy);
        }
    }

    public static class LiveViewZoomRatio extends SetPropertyCommand<Void> {
        public LiveViewZoomRatio(final EdsEvfZoom value) {
            super(EdsPropertyID.kEdsPropID_Evf_Zoom, value);
        }

        public LiveViewZoomRatio(final LiveViewZoomRatio toCopy) {
            super(toCopy);
        }
    }

    public static class MeteringMode extends SetPropertyCommand<Void> {
        public MeteringMode(final EdsMeteringMode value) {
            super(EdsPropertyID.kEdsPropID_MeteringMode, value);
        }

        public MeteringMode(final MeteringMode toCopy) {
            super(toCopy);
        }
    }

    public static class OwnerName extends SetPropertyCommand<Void> {
        public OwnerName(final String value) {
            super(EdsPropertyID.kEdsPropID_OwnerName, value);
        }

        public OwnerName(final OwnerName toCopy) {
            super(toCopy);
        }
    }

    public static class PictureStyle extends SetPropertyCommand<Void> {
        public PictureStyle(final EdsPictureStyle value) {
            super(EdsPropertyID.kEdsPropID_PictureStyle, value);
        }

        public PictureStyle(final PictureStyle toCopy) {
            super(toCopy);
        }
    }

    public static class PictureStyleDescription extends SetPropertyCommand<Void> {
        public PictureStyleDescription(final EdsPictureStyleDesc value) {
            super(EdsPropertyID.kEdsPropID_PictureStyle, value);
        }

        public PictureStyleDescription(final PictureStyleDescription toCopy) {
            super(toCopy);
        }
    }

    public static class SaveTo extends SetPropertyCommand<Void> {
        public SaveTo(final EdsSaveTo value) {
            super(EdsPropertyID.kEdsPropID_SaveTo, value);
        }

        public SaveTo(final SaveTo toCopy) {
            super(toCopy);
        }
    }

    /**
     * ShootingMode = AEMode
     * <br>
     * <br>
     * Indicates settings values of the camera in shooting mode.
     * <br>
     * You cannot set (that is, Write) this property on cameras with a mode dial.
     * <br>
     * If the target object is EdsCameraRef, you can use GetPropertyDesc to access this property and get a list of property values that can currently be set.
     * <br>
     * However, you cannot get a list of settable values from models featuring a dial.
     * The GetPropertyDesc return value will be EDS_ERR_OK, and no items will be listed as values you can set.
     * <br>
     * The shooting mode is in either an applied or simple shooting zone. When a camera is in a shooting mode of the simple shooting zone, a variety of capture-related properties (such as for auto focus, drive mode, and metering mode) are automatically set to the optimal values. Thus, when the camera is in a shooting mode of a simple shooting zone, capture-related properties cannot be set on the camera.
     * <br>
     * <br>
     * <p>Values defined in Enum EdsAEMode. From EOS 5DMarkIII, in addition to EdsAEMode we added EdsAEModeSelect. For the models before EOS 60D, you cannot get the AE mode which is registered to camera user settings.</p>
     */
    public static class ShootingMode extends SetPropertyCommand<Void> {
        public ShootingMode(final EdsAEMode value) {
            super(EdsPropertyID.kEdsPropID_AEModeSelect, value);
        }

        public ShootingMode(final ShootingMode toCopy) {
            super(toCopy);
        }
    }

    public static class ShutterSpeed extends SetPropertyCommand<Void> {
        public ShutterSpeed(final EdsTv value) {
            super(EdsPropertyID.kEdsPropID_Tv, value);
        }

        public ShutterSpeed(final ShutterSpeed toCopy) {
            super(toCopy);
        }
    }

    public static class WhiteBalance extends SetPropertyCommand<Void> {
        public WhiteBalance(final EdsWhiteBalance value) {
            super(EdsPropertyID.kEdsPropID_WhiteBalance, value);
        }

        public WhiteBalance(final WhiteBalance toCopy) {
            super(toCopy);
        }
    }
}
