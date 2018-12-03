package org.blackdread.cameraframework.api.command;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class LiveViewCommand<R> extends AbstractCanonCommand<R> {

    public LiveViewCommand() {
    }

    public LiveViewCommand(final LiveViewCommand toCopy) {
        super(toCopy);
    }
}
