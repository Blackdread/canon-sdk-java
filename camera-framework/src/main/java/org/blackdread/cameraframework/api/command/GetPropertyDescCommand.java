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
        protected void runInternal() {
            final List<EdsAEMode> aeModes = propertyDescShortcutLogic().getAEModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

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
        protected void runInternal() {
            final List<EdsAEModeSelect> aeModeSelects = propertyDescShortcutLogic().getAEModeSelectDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class ApertureDesc extends GetPropertyDescCommand<List<EdsAv>> {

        public ApertureDesc() {
        }

        public ApertureDesc(final ApertureDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsAv> apertures = propertyDescShortcutLogic().getAvDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class ColorTemperatureDesc extends GetPropertyDescCommand<List<Integer>> {

        public ColorTemperatureDesc() {
        }

        public ColorTemperatureDesc(final ColorTemperatureDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<Integer> colorTemperatures = propertyDescShortcutLogic().getColorTemperatureDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class DriveModeDesc extends GetPropertyDescCommand<List<EdsDriveMode>> {

        public DriveModeDesc() {
        }

        public DriveModeDesc(final DriveModeDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsDriveMode> driveModes = propertyDescShortcutLogic().getDriveModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class ExposureCompensationDesc extends GetPropertyDescCommand<List<EdsExposureCompensation>> {

        public ExposureCompensationDesc() {
        }

        public ExposureCompensationDesc(final ExposureCompensationDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsExposureCompensation> exposureCompensations = propertyDescShortcutLogic().getExposureCompensationDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class ImageQualityDesc extends GetPropertyDescCommand<List<EdsImageQuality>> {

        public ImageQualityDesc() {
        }

        public ImageQualityDesc(final ImageQualityDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsImageQuality> imageQualities = propertyDescShortcutLogic().getImageQualityDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class IsoSpeedDesc extends GetPropertyDescCommand<List<EdsISOSpeed>> {

        public IsoSpeedDesc() {
        }

        public IsoSpeedDesc(final IsoSpeedDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsISOSpeed> isoSpeeds = propertyDescShortcutLogic().getISOSpeedDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class MeteringModeDesc extends GetPropertyDescCommand<List<EdsMeteringMode>> {

        public MeteringModeDesc() {
        }

        public MeteringModeDesc(final MeteringModeDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsMeteringMode> meteringModes = propertyDescShortcutLogic().getMeteringModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class PictureStyleDesc extends GetPropertyDescCommand<List<EdsPictureStyle>> {

        public PictureStyleDesc() {
        }

        public PictureStyleDesc(final PictureStyleDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsPictureStyle> pictureStyles;
            switch (getTargetRefType()) {
                case CAMERA:
                    pictureStyles = propertyDescShortcutLogic().getPictureStyleDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                    break;
                case IMAGE:
                    pictureStyles = propertyDescShortcutLogic().getPictureStyleDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());
                    break;
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
        protected void runInternal() {
            final List<EdsTv> tvs = propertyDescShortcutLogic().getTvDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class WhiteBalanceDesc extends GetPropertyDescCommand<List<EdsWhiteBalance>> {

        public WhiteBalanceDesc() {
        }

        public WhiteBalanceDesc(final WhiteBalanceDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsWhiteBalance> whiteBalances = propertyDescShortcutLogic().getWhiteBalanceDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

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
        protected void runInternal() {
            final List<EdsEvfAFMode> evfAFModes = propertyDescShortcutLogic().getEvfAFModeDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class LiveViewColorTemperatureDesc extends GetPropertyDescCommand<List<Integer>> {

        public LiveViewColorTemperatureDesc() {
        }

        public LiveViewColorTemperatureDesc(final LiveViewColorTemperatureDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<Integer> evfColorTemperatures = propertyDescShortcutLogic().getEvfColorTemperatureDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

    public static class LiveViewWhiteBalanceDesc extends GetPropertyDescCommand<List<EdsWhiteBalance>> {

        public LiveViewWhiteBalanceDesc() {
        }

        public LiveViewWhiteBalanceDesc(final LiveViewWhiteBalanceDesc toCopy) {
            super(toCopy);
        }

        @Override
        protected void runInternal() {
            final List<EdsWhiteBalance> evfWhiteBalances = propertyDescShortcutLogic().getEvfWhiteBalanceDesc((EdsdkLibrary.EdsCameraRef) getTargetRefInternal());

        }
    }

}
