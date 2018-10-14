package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.cameraframework.api.command.CanonCommand;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 */
public class TimeoutCommandDecorator<R> extends AbstractDecoratorCommand<R> {

    private final Duration timeout;

    public TimeoutCommandDecorator(final CanonCommand<R> delegate, final Duration timeout) {
        super(delegate);
        this.timeout = Objects.requireNonNull(timeout);
    }

    @Override
    public Optional<Duration> getTimeout() {
        return Optional.of(timeout);
    }

}
