package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.cameraframework.api.command.CanonCommand;

import java.util.Optional;

/**
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 */
public class DefaultValueOnErrorDecorator<R> extends AbstractDecoratorCommand<R> implements CanonCommand<R> {

    private final R defaultValue;

    /**
     * Return null or empty on exception
     *
     * @param delegate command to decorate
     */
    public DefaultValueOnErrorDecorator(final CanonCommand<R> delegate) {
        super(delegate);
        this.defaultValue = null;
    }

    /**
     * Return the value passed on exception
     *
     * @param delegate     command to decorate
     * @param defaultValue value on exception
     */
    public DefaultValueOnErrorDecorator(final CanonCommand<R> delegate, final R defaultValue) {
        super(delegate);
        this.defaultValue = defaultValue;
    }

    public DefaultValueOnErrorDecorator(final FakeClassArgument fake, final DefaultValueOnErrorDecorator<R> toCopy) {
        super(fake, toCopy);
        this.defaultValue = toCopy.defaultValue;
    }

    @Override
    public R get() {
        try {
            return getDelegate().get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @Override
    public Optional<R> getOpt() {
        try {
            return getDelegate().getOpt();
        } catch (Exception e) {
            return Optional.ofNullable(defaultValue);
        }
    }

}
