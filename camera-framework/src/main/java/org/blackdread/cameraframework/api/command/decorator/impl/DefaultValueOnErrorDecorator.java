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

    public DefaultValueOnErrorDecorator(final CanonCommand<R> delegate) {
        super(delegate);
        this.defaultValue = null;
    }

    public DefaultValueOnErrorDecorator(final CanonCommand<R> delegate, final R defaultValue) {
        super(delegate);
        this.defaultValue = defaultValue;
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
