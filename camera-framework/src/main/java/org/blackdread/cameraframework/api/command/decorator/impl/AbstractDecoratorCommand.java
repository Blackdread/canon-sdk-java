package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.contract.ErrorLogic;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/10/11.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class AbstractDecoratorCommand<R> implements DecoratorCommand<R> {

    private final CanonCommand<R> delegate;

    private final DecoratorCommand<R> decoratorCommand;

    protected AbstractDecoratorCommand(final CanonCommand<R> delegate) {
        this.delegate = Objects.requireNonNull(delegate);
        if (delegate instanceof DecoratorCommand)
            this.decoratorCommand = (DecoratorCommand<R>) delegate;
        else
            this.decoratorCommand = null;
    }

    /**
     * NOTE: Not final method but might be later, no reason to allow to override this logic
     *
     * @return delegate of this decorator
     */
    protected CanonCommand<R> getDelegate() {
        return delegate;
    }

    @Override
    public R get() throws InterruptedException, ExecutionException {
        return getDelegate().get();
    }

    @Override
    public Optional<R> getOpt() throws InterruptedException, ExecutionException {
        return getDelegate().getOpt();
    }

    @Override
    public Optional<ErrorLogic> getErrorLogic() {
        return getDelegate().getErrorLogic();
    }

    @Override
    public Optional<Duration> getTimeout() {
        return getDelegate().getTimeout();
    }

    @Override
    public CanonCommand<R> getRoot() {
        if (decoratorCommand == null) {
            // this is the root object
            // the AbstractCanonCommand
            return getDelegate();
        }
        return decoratorCommand.getRoot();
    }
}
