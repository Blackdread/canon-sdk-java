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
package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsFocusInfo;
import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsPoint;
import org.blackdread.camerabinding.jna.EdsRational;
import org.blackdread.camerabinding.jna.EdsRect;
import org.blackdread.camerabinding.jna.EdsSize;
import org.blackdread.camerabinding.jna.EdsTime;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.*;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.UnsupportedTargetTypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyGetLogic;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyGetShortcutLogic;

/**
 * Gets a property from the camera.
 * <br>
 * The function EdsSetPropertyData is used to set properties.
 * <br>
 * This API takes objects of undefined type as arguments, the properties that can be retrieved or set
 * differ depending on the given object. In addition, some properties have a list of currently settable values.
 * EdsGetPropertyDesc is used to get this list of settable values.
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class GetPropertyCommand<R> extends AbstractCanonCommand<R> {

    // more fields are necessary to simplify get
    private final EdsPropertyID propertyID;
    private final long inParam;

    private final Class<? extends NativeEnum> enumClass;

    protected GetPropertyCommand() {
        propertyID = null;
        inParam = 0L;
        enumClass = null;
    }

    protected GetPropertyCommand(final EdsPropertyID propertyID, final Class<? extends NativeEnum> enumClass) {
        this.propertyID = propertyID;
        this.inParam = 0L;
        this.enumClass = enumClass;
    }

    protected GetPropertyCommand(final EdsPropertyID propertyID) {
        this.propertyID = propertyID;
        this.inParam = 0L;
        enumClass = null;
    }

    protected GetPropertyCommand(final EdsPropertyID propertyID, final long inParam) {
        this.propertyID = propertyID;
        this.inParam = inParam;
        enumClass = null;
    }

    protected GetPropertyCommand(final GetPropertyCommand<R> toCopy) {
        super(toCopy);
        this.propertyID = toCopy.propertyID;
        this.inParam = toCopy.inParam;
        this.enumClass = toCopy.enumClass;
    }

    @Override
    protected R runInternal() throws InterruptedException {
        /*
         * Implementation to simplify get values so less duplicate code in runInternal() for get commands
         * but we may get ClassCastException if wrong return type is used (shortcut methods are safer from this part).
         * See commands where runInternal() is overridden, quite some boilerplate code.
         *
         * This implementation might not fit for all get, if it is the case then sub-class should override runInternal().
         */
        final TargetRefType targetRefType = getTargetRefType();
        if (!propertyID.getSupportedTargetRefType().contains(targetRefType)) {
            throw new UnsupportedTargetTypeException(targetRefType);
        }

        final Object data = CanonFactory.propertyGetLogic().getPropertyData(getTargetRefInternal(), propertyID, inParam);

        if (enumClass != null) {
            final Long value = (Long) data;
            try {
                // TODO cache
                final Method method = enumClass.getMethod("ofValue", Integer.class);
                method.invoke(null, value.intValue());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Missing method 'ofValue' with 'Integer' parameter", e);
            }
        }

        // Not super safe, class cast exception, etc

        return (R) data;

//        throw new NotImplementedException("that's a pity...");
    }

    /**
     * Default get command to get a property by its property, useful if current API does not provide a command for that property.
     * <br>
     * NOTE: This is provided as is but <b>not all properties may work</b>
     */
    public static class ByProperty<R> extends GetPropertyCommand<R> {
        public ByProperty(final EdsPropertyID propertyID) {
            super(propertyID);
        }

        public ByProperty(final EdsPropertyID propertyID, final long inParam) {
            super(propertyID, inParam);
        }

        public ByProperty(final ByProperty toCopy) {
            super(toCopy);
        }
    }

    /**
     * Default get command to get a property from an enum, useful if current API does not provide a command for that property.
     */
    public static class ByEnum<T extends NativeEnum> extends GetPropertyCommand<T> {
        public ByEnum(final EdsPropertyID propertyID, final Class<T> enumClass) {
            super(propertyID, enumClass);
        }

        public ByEnum(final ByEnum toCopy) {
            super(toCopy);
        }
    }

    public static class Aperture extends GetPropertyCommand<EdsAv> {
        public Aperture() {
        }

        public Aperture(final Aperture toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsAv runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getAv((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ApertureImage extends GetPropertyCommand<EdsRational> {
        public ApertureImage() {
        }

        public ApertureImage(final ApertureImage toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsRational runInternal() {
            switch (getTargetRefType()) {
                case IMAGE:
                    return propertyGetShortcutLogic().getAv((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class Artist extends GetPropertyCommand<String> {
        public Artist() {
        }

        public Artist(final Artist toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getArtist((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getArtist((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class AutoFocusMode extends GetPropertyCommand<EdsAFMode> {
        public AutoFocusMode() {
        }

        public AutoFocusMode(final AutoFocusMode toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsAFMode runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getAFMode((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getAFMode((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class AvailableShots extends GetPropertyCommand<Long> {
        public AvailableShots() {
        }

        public AvailableShots(final AvailableShots toCopy) {
            super(toCopy);
        }

        @Override
        protected Long runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getAvailableShots((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class BatteryLevel extends GetPropertyCommand<EdsBatteryLevel2> {
        public BatteryLevel() {
        }

        public BatteryLevel(final BatteryLevel toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsBatteryLevel2 runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getBatteryLevel((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class BodyIDEx extends GetPropertyCommand<String> {
        public BodyIDEx() {
        }

        public BodyIDEx(final BodyIDEx toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getBodyIDEx((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getBodyIDEx((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class SerialNumber extends BodyIDEx {
        public SerialNumber() {
        }

        public SerialNumber(final SerialNumber toCopy) {
            super(toCopy);
        }
    }

    public static class Bracket extends GetPropertyCommand<EdsBracket> {
        public Bracket() {
        }

        public Bracket(final Bracket toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsBracket runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getBracket((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getBracket((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ColorSpace extends GetPropertyCommand<EdsColorSpace> {
        public ColorSpace() {
        }

        public ColorSpace(final ColorSpace toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsColorSpace runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getColorSpace((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getColorSpace((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ColorTemperature extends GetPropertyCommand<Long> {
        public ColorTemperature() {
        }

        public ColorTemperature(final ColorTemperature toCopy) {
            super(toCopy);
        }

        @Override
        protected Long runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getColorTemperature((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getColorTemperature((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class Copyright extends GetPropertyCommand<String> {
        public Copyright() {
        }

        public Copyright(final Copyright toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getCopyright((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getCopyright((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class CurrentFolder extends GetPropertyCommand<String> {
        public CurrentFolder() {
        }

        public CurrentFolder(final CurrentFolder toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getCurrentFolder((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class CurrentStorage extends GetPropertyCommand<String> {
        public CurrentStorage() {
        }

        public CurrentStorage(final CurrentStorage toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getCurrentStorage((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class DateTime extends GetPropertyCommand<EdsTime> {
        public DateTime() {
        }

        public DateTime(final DateTime toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsTime runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getDateTime((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getDateTime((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class DcLensBarrelState extends GetPropertyCommand<EdsDcLensBarrelState> {
        public DcLensBarrelState() {
        }

        public DcLensBarrelState(final DcLensBarrelState toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsDcLensBarrelState runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getDcLensBarrelStatus((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class DcStrobe extends GetPropertyCommand<EdsDcStrobe> {
        public DcStrobe() {
        }

        public DcStrobe(final DcStrobe toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsDcStrobe runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getDcStrobe((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class DcZoom extends GetPropertyCommand<Long> {
        public DcZoom() {
        }

        public DcZoom(final DcZoom toCopy) {
            super(toCopy);
        }

        @Override
        protected Long runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getDcZoom((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class FlashCompensation extends GetPropertyCommand<EdsExposureCompensation> {
        public FlashCompensation() {
        }

        public FlashCompensation(final FlashCompensation toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsExposureCompensation runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getFlashCompensation((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class FirmwareVersion extends GetPropertyCommand<String> {
        public FirmwareVersion() {
        }

        public FirmwareVersion(final FirmwareVersion toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getFirmwareVersion((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getFirmwareVersion((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class FocusInfo extends GetPropertyCommand<EdsFocusInfo> {
        public FocusInfo() {
        }

        public FocusInfo(final FocusInfo toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsFocusInfo runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getFocusInfo((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getFocusInfo((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class HardDriveDirectoryStructure extends GetPropertyCommand<String> {
        public HardDriveDirectoryStructure() {
        }

        public HardDriveDirectoryStructure(final HardDriveDirectoryStructure toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getHDDirectoryStructure((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ImageQuality extends GetPropertyCommand<EdsImageQuality> {
        public ImageQuality() {
        }

        public ImageQuality(final ImageQuality toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsImageQuality runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getImageQuality((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class IsoSpeed extends GetPropertyCommand<EdsISOSpeed> {
        public IsoSpeed() {
        }

        public IsoSpeed(final IsoSpeed toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsISOSpeed runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getISOSpeed((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getISOSpeed((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class JpegQuality extends GetPropertyCommand<Integer> {
        public JpegQuality() {
        }

        public JpegQuality(final JpegQuality toCopy) {
            super(toCopy);
        }

        @Override
        protected Integer runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getJpegQuality((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getJpegQuality((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LensName extends GetPropertyCommand<String> {
        public LensName() {
        }

        public LensName(final LensName toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case IMAGE:
                    return propertyGetShortcutLogic().getLensName((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LensStatus extends GetPropertyCommand<Long> {
        public LensStatus() {
        }

        public LensStatus(final LensStatus toCopy) {
            super(toCopy);
        }

        @Override
        protected Long runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getLensStatus((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LensStatusAsBoolean extends GetPropertyCommand<Boolean> {
        public LensStatusAsBoolean() {
        }

        public LensStatusAsBoolean(final LensStatusAsBoolean toCopy) {
            super(toCopy);
        }

        @Override
        protected Boolean runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getLensStatus((EdsdkLibrary.EdsCameraRef) getTargetRefInternal()) != 0;
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewAutoFocusMode extends GetPropertyCommand<EdsEvfAFMode> {
        public LiveViewAutoFocusMode() {
        }

        public LiveViewAutoFocusMode(final LiveViewAutoFocusMode toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsEvfAFMode runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getEvfAFMode((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewColorTemperature extends GetPropertyCommand<Long> {
        public LiveViewColorTemperature() {
        }

        public LiveViewColorTemperature(final LiveViewColorTemperature toCopy) {
            super(toCopy);
        }

        @Override
        protected Long runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getEvfColorTemperature((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewCoordinateSystem extends GetPropertyCommand<EdsSize> {
        public LiveViewCoordinateSystem() {
        }

        public LiveViewCoordinateSystem(final LiveViewCoordinateSystem toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsSize runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfCoordinateSystem((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewImagePosition extends GetPropertyCommand<EdsPoint> {
        public LiveViewImagePosition() {
        }

        public LiveViewImagePosition(final LiveViewImagePosition toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsPoint runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfImagePosition((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewHistogram extends GetPropertyCommand<int[]> {
        public LiveViewHistogram() {
        }

        public LiveViewHistogram(final LiveViewHistogram toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetLogic().getPropertyData(getTargetRefInternal(), EdsPropertyID.kEdsPropID_Evf_Histogram);
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewHistogramB extends GetPropertyCommand<int[]> {
        public LiveViewHistogramB() {
        }

        public LiveViewHistogramB(final LiveViewHistogramB toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfHistogramB((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewHistogramG extends GetPropertyCommand<int[]> {
        public LiveViewHistogramG() {
        }

        public LiveViewHistogramG(final LiveViewHistogramG toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfHistogramG((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewHistogramR extends GetPropertyCommand<int[]> {
        public LiveViewHistogramR() {
        }

        public LiveViewHistogramR(final LiveViewHistogramR toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfHistogramR((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewHistogramY extends GetPropertyCommand<int[]> {
        public LiveViewHistogramY() {
        }

        public LiveViewHistogramY(final LiveViewHistogramY toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfHistogramY((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewHistogramStatus extends GetPropertyCommand<EdsEvfHistogramStatus> {
        public LiveViewHistogramStatus() {
        }

        public LiveViewHistogramStatus(final LiveViewHistogramStatus toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsEvfHistogramStatus runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfHistogramStatus((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewOutputDevice extends GetPropertyCommand<Boolean> {
        public LiveViewOutputDevice() {
            super(EdsPropertyID.kEdsPropID_Evf_OutputDevice, EdsEvfOutputDevice.class);
        }

        public LiveViewOutputDevice(final LiveViewOutputDevice toCopy) {
            super(toCopy);
        }
    }

    public static class LiveViewWhiteBalance extends GetPropertyCommand<EdsWhiteBalance> {
        public LiveViewWhiteBalance() {
        }

        public LiveViewWhiteBalance(final LiveViewWhiteBalance toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsWhiteBalance runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getEvfWhiteBalance((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewZoomPosition extends GetPropertyCommand<EdsPoint> {
        public LiveViewZoomPosition() {
        }

        public LiveViewZoomPosition(final LiveViewZoomPosition toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsPoint runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvfZoomPosition((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class LiveViewZoomRectangle extends GetPropertyCommand<EdsRect> {
        public LiveViewZoomRectangle() {
        }

        public LiveViewZoomRectangle(final LiveViewZoomRectangle toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsRect runInternal() {
            switch (getTargetRefType()) {
                case EVF_IMAGE:
                    return propertyGetShortcutLogic().getEvf_ZoomRect((EdsdkLibrary.EdsEvfImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class MeteringMode extends GetPropertyCommand<EdsMeteringMode> {
        public MeteringMode() {
        }

        public MeteringMode(final MeteringMode toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsMeteringMode runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getMeteringMode((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getMeteringMode((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class OwnerName extends GetPropertyCommand<String> {
        public OwnerName() {
        }

        public OwnerName(final OwnerName toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getOwnerName((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getOwnerName((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class PictureStyle extends GetPropertyCommand<EdsPictureStyle> {
        public PictureStyle() {
        }

        public PictureStyle(final PictureStyle toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsPictureStyle runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getPictureStyle((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getPictureStyle((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class PictureStyleDescription extends GetPropertyCommand<EdsPictureStyleDesc> {
        public PictureStyleDescription() {
            super(EdsPropertyID.kEdsPropID_PictureStyleDesc);
        }

        public PictureStyleDescription(final PictureStyleDescription toCopy) {
            super(toCopy);
        }

        // TODO Check if this actually correct thing to do, there is already propertyDescShortcutLogic().getPictureStyleDesc
//        @Override
//        protected EdsPictureStyleDesc runInternal() {
//            switch (getTargetRefType()) {
//                case CAMERA:
//                    return propertyGetShortcutLogic().getPictureStyleDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
//                case IMAGE:
//                    return propertyGetShortcutLogic().getPictureStyleDesc((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
//                default:
//                    throw new UnsupportedTargetTypeException(getTargetRefType());
//            }
//        }
    }

    /**
     * A string representing the product name.
     * <br>
     * If the target object is EdsCameraRef, this property indicates the name of the remote camera.
     * <br>
     * If the target object is EdsImageRef, this property indicates the name of the camera used to shoot the image.
     * <br>
     * ASCII text strings up to 32 characters, including null-terminated strings.
     */
    public static class ProductName extends GetPropertyCommand<String> {
        public ProductName() {
        }

        public ProductName(final ProductName toCopy) {
            super(toCopy);
        }

        @Override
        protected String runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getProductName((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getProductName((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class SaveTo extends GetPropertyCommand<EdsSaveTo> {
        public SaveTo() {
        }

        public SaveTo(final SaveTo toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsSaveTo runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getSaveTo((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ShootingMode extends GetPropertyCommand<EdsAEMode> {
        public ShootingMode() {
        }

        public ShootingMode(final ShootingMode toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsAEMode runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getAEMode((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getAEMode((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ShutterSpeed extends GetPropertyCommand<EdsTv> {
        public ShutterSpeed() {
        }

        public ShutterSpeed(final ShutterSpeed toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsTv runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getTv((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class ShutterSpeedImage extends GetPropertyCommand<EdsRational> {
        public ShutterSpeedImage() {
        }

        public ShutterSpeedImage(final ShutterSpeedImage toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsRational runInternal() {
            switch (getTargetRefType()) {
                case IMAGE:
                    return propertyGetShortcutLogic().getTv((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class WhiteBalance extends GetPropertyCommand<EdsWhiteBalance> {
        public WhiteBalance() {
        }

        public WhiteBalance(final WhiteBalance toCopy) {
            super(toCopy);
        }

        @Override
        protected EdsWhiteBalance runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getWhiteBalance((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getWhiteBalance((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class WhiteBalanceBracket extends GetPropertyCommand<int[]> {
        public WhiteBalanceBracket() {
        }

        public WhiteBalanceBracket(final WhiteBalanceBracket toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getWhiteBalanceBracket((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getWhiteBalanceBracket((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

    public static class WhiteBalanceShift extends GetPropertyCommand<int[]> {
        public WhiteBalanceShift() {
        }

        public WhiteBalanceShift(final WhiteBalanceShift toCopy) {
            super(toCopy);
        }

        @Override
        protected int[] runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyGetShortcutLogic().getWhiteBalanceShift((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyGetShortcutLogic().getWhiteBalanceShift((EdsdkLibrary.EdsImageRef) getTargetRefInternal());
                default:
                    throw new UnsupportedTargetTypeException(getTargetRefType());
            }
        }
    }

}
