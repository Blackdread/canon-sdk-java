package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.constant.EdsAv;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsTv;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * Sets a property on the camera.
 *
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class SetPropertyCommand<R> extends AbstractCanonCommand<R> {

    public SetPropertyCommand() {
    }

    public SetPropertyCommand(final SetPropertyCommand<R> toCopy) {
        super(toCopy);
    }

    public static class IsoSpeed extends SetPropertyCommand<EdsISOSpeed> {

        private final EdsISOSpeed value;

        public IsoSpeed(final EdsISOSpeed value) {
            this.value = value;
        }

        public IsoSpeed(final IsoSpeed toCopy) {
            super(toCopy);
            this.value = toCopy.value;
        }

        @Override
        protected void runInternal() {
            CanonFactory.propertySetLogic().setPropertyData(getTargetRefInternal(), EdsPropertyID.kEdsPropID_ISOSpeed, value);
        }
    }

    public static class Aperture extends SetPropertyCommand<EdsAv> {

        private final EdsAv value;

        public Aperture(final EdsAv value) {
            this.value = value;
        }

        public Aperture(final Aperture toCopy) {
            super(toCopy);
            this.value = toCopy.value;
        }

        @Override
        protected void runInternal() {
            CanonFactory.propertySetLogic().setPropertyData(getTargetRefInternal(), EdsPropertyID.kEdsPropID_Av, value);
        }
    }

    public static class ShutterSpeed extends SetPropertyCommand<EdsTv> {

        private final EdsTv value;

        public ShutterSpeed(final EdsTv value) {
            this.value = value;
        }

        public ShutterSpeed(final ShutterSpeed toCopy) {
            super(toCopy);
            this.value = toCopy.value;
        }

        @Override
        protected void runInternal() {
            CanonFactory.propertySetLogic().setPropertyData(getTargetRefInternal(), EdsPropertyID.kEdsPropID_Tv, value);
        }
    }
}
