package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.contract.ErrorLogic;
import org.blackdread.cameraframework.api.command.contract.ErrorLogicCommand;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 */
public class ErrorLogicCommandDecorator<R> extends AbstractDecoratorCommand<R> implements ErrorLogicCommand, CanonCommand<R> {

    private final ErrorLogic errorLogic;

    public ErrorLogicCommandDecorator(final CanonCommand<R> delegate, final ErrorLogic errorLogic) {
        super(delegate);
        this.errorLogic = Objects.requireNonNull(errorLogic);
    }

    @Override
    public Optional<ErrorLogic> getErrorLogic() {
        return Optional.of(errorLogic);
    }

}
