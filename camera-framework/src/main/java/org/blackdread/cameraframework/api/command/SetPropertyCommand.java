package org.blackdread.cameraframework.api.command;

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
}
