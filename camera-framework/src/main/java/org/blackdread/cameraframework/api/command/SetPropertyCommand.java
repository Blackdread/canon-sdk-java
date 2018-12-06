package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.constant.EdsISOSpeed;

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

        public IsoSpeed(final EdsISOSpeed value) {

        }

        @Override
        protected void runInternal() {

        }
    }
}
