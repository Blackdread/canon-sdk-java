package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.*;

import java.util.List;

import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.propertyDescShortcutLogic;

/**
 * Gets properties available to be set for the camera.
 * <p>Created on 2018/12/09.<p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings("unused")
public abstract class GetPropertyDescCommand<R> extends AbstractCanonCommand<R> {

    public GetPropertyDescCommand() {
    }

    public GetPropertyDescCommand(final GetPropertyDescCommand<R> toCopy) {
        super(toCopy);
    }

    /**
     * @deprecated not sure this one is supported
     */
    @Deprecated
    public static class AEModeDesc extends GetPropertyDescCommand<List<EdsAEMode>> {

        public AEModeDesc() {
        }

        public AEModeDesc(final AEModeDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsAEMode> runInternal() {
            return propertyDescShortcutLogic().getAEModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    /**
     * @deprecated not sure this one is supported
     */
    @Deprecated
    public static class AEModeSelectDesc extends GetPropertyDescCommand<List<EdsAEModeSelect>> {

        public AEModeSelectDesc() {
        }

        public AEModeSelectDesc(final AEModeSelectDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsAEModeSelect> runInternal() {
            return propertyDescShortcutLogic().getAEModeSelectDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class ApertureDesc extends GetPropertyDescCommand<List<EdsAv>> {

        public ApertureDesc() {
        }

        public ApertureDesc(final ApertureDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsAv> runInternal() {
            return propertyDescShortcutLogic().getAvDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class ColorTemperatureDesc extends GetPropertyDescCommand<List<Integer>> {

        public ColorTemperatureDesc() {
        }

        public ColorTemperatureDesc(final ColorTemperatureDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<Integer> runInternal() {
            return propertyDescShortcutLogic().getColorTemperatureDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class DriveModeDesc extends GetPropertyDescCommand<List<EdsDriveMode>> {

        public DriveModeDesc() {
        }

        public DriveModeDesc(final DriveModeDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsDriveMode> runInternal() {
            return propertyDescShortcutLogic().getDriveModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class ExposureCompensationDesc extends GetPropertyDescCommand<List<EdsExposureCompensation>> {

        public ExposureCompensationDesc() {
        }

        public ExposureCompensationDesc(final ExposureCompensationDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsExposureCompensation> runInternal() {
            return propertyDescShortcutLogic().getExposureCompensationDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class ImageQualityDesc extends GetPropertyDescCommand<List<EdsImageQuality>> {

        public ImageQualityDesc() {
        }

        public ImageQualityDesc(final ImageQualityDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsImageQuality> runInternal() {
            return propertyDescShortcutLogic().getImageQualityDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class IsoSpeedDesc extends GetPropertyDescCommand<List<EdsISOSpeed>> {

        public IsoSpeedDesc() {
        }

        public IsoSpeedDesc(final IsoSpeedDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsISOSpeed> runInternal() {
            return propertyDescShortcutLogic().getISOSpeedDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class MeteringModeDesc extends GetPropertyDescCommand<List<EdsMeteringMode>> {

        public MeteringModeDesc() {
        }

        public MeteringModeDesc(final MeteringModeDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsMeteringMode> runInternal() {
            return propertyDescShortcutLogic().getMeteringModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class PictureStyleDesc extends GetPropertyDescCommand<List<EdsPictureStyle>> {

        public PictureStyleDesc() {
        }

        public PictureStyleDesc(final PictureStyleDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsPictureStyle> runInternal() {
            switch (getTargetRefType()) {
                case CAMERA:
                    return propertyDescShortcutLogic().getPictureStyleDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                case IMAGE:
                    return propertyDescShortcutLogic().getPictureStyleDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                default:
                    throw new IllegalStateException("Unsupported type");
            }
        }
    }

    public static class ShutterSpeedDesc extends GetPropertyDescCommand<List<EdsTv>> {

        public ShutterSpeedDesc() {
        }

        public ShutterSpeedDesc(final ShutterSpeedDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsTv> runInternal() {
            return propertyDescShortcutLogic().getTvDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class WhiteBalanceDesc extends GetPropertyDescCommand<List<EdsWhiteBalance>> {

        public WhiteBalanceDesc() {
        }

        public WhiteBalanceDesc(final WhiteBalanceDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsWhiteBalance> runInternal() {
            return propertyDescShortcutLogic().getWhiteBalanceDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    /* ============================
     *          Live view
     * ============================
     */

    public static class LiveViewAutoFocusModeDesc extends GetPropertyDescCommand<List<EdsEvfAFMode>> {

        public LiveViewAutoFocusModeDesc() {
        }

        public LiveViewAutoFocusModeDesc(final LiveViewAutoFocusModeDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsEvfAFMode> runInternal() {
            return propertyDescShortcutLogic().getEvfAFModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class LiveViewColorTemperatureDesc extends GetPropertyDescCommand<List<Integer>> {

        public LiveViewColorTemperatureDesc() {
        }

        public LiveViewColorTemperatureDesc(final LiveViewColorTemperatureDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<Integer> runInternal() {
            return propertyDescShortcutLogic().getEvfColorTemperatureDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

    public static class LiveViewWhiteBalanceDesc extends GetPropertyDescCommand<List<EdsWhiteBalance>> {

        public LiveViewWhiteBalanceDesc() {
        }

        public LiveViewWhiteBalanceDesc(final LiveViewWhiteBalanceDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected List<EdsWhiteBalance> runInternal() {
            return propertyDescShortcutLogic().getEvfWhiteBalanceDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
        }
    }

}
