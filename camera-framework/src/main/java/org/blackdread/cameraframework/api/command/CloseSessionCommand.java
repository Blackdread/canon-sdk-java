package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.util.Objects;

/**
 * Close session of a camera
 *
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CloseSessionCommand extends AbstractCanonCommand<Void> {

    private final CloseSessionOption option;

    public CloseSessionCommand(final CloseSessionOption option) {
        this.option = Objects.requireNonNull(option);
    }

    public CloseSessionCommand(final CloseSessionCommand toCopy) {
        super(toCopy);
        this.option = toCopy.option;
    }

    @Override
    protected Void runInternal() {
        CanonFactory.cameraLogic().closeSession(option);
        return null;
    }
}
