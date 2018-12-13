package org.blackdread.cameraframework.api.command;

/**
 * <p>Created on 2018/12/13.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class EventCommand extends AbstractCanonCommand<Void> {

    protected EventCommand() {

    }

    protected EventCommand(final EventCommand toCopy) {
        super(toCopy);
    }

}
