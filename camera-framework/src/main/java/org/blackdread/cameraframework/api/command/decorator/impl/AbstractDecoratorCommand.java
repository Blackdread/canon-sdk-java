/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.TargetRefType;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/10/11.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public abstract class AbstractDecoratorCommand<R> implements DecoratorCommand<R> {

    private final CanonCommand<R> delegate;

    private final DecoratorCommand<R> decoratorCommand;

    /**
     * Decorate a command.
     *
     * @param delegate command to decorate
     */
    protected AbstractDecoratorCommand(final CanonCommand<R> delegate) {
        this.delegate = Objects.requireNonNull(delegate);
        if (delegate instanceof DecoratorCommand)
            this.decoratorCommand = (DecoratorCommand<R>) delegate;
        else
            this.decoratorCommand = null;
    }

    /**
     * Allow to re-send a command as a command cannot be re-sent without making a copy
     * <p>Not all fields need to be copied</p>
     * <p>Decorators are also copied and applied on the command copied</p>
     * <p><b>IMPORTANT:</b> It is required to add a first argument {@code FakeClassArgument} to this method as to make sure constructors are not wrongly used between decorate a command and copy a command</p>
     *
     * @param fake   fake parameter, should be null or global instance
     * @param toCopy command to copy
     */
    protected AbstractDecoratorCommand(final FakeClassArgument fake, final AbstractDecoratorCommand<R> toCopy) {
        if (toCopy.decoratorCommand == null) {
            // this is the root object
            // the AbstractCanonCommand
            delegate = toCopy.delegate.copy();
            decoratorCommand = null;
        } else {
            decoratorCommand = toCopy.decoratorCommand.copy();
            delegate = decoratorCommand;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public DecoratorCommand<R> copy() {
        try {
            final Class<? extends AbstractDecoratorCommand> currentClass = getClass();
            return currentClass.getConstructor(FakeClassArgument.class, currentClass).newInstance(null, this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // Either sub-class override copy() or provide a copy constructor that is accessible, etc
            // Copy constructor must be public and argument class match its type
            throw new IllegalStateException("Did not define a copy constructor in decorator sub-classes", e);
        }
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
    public void run() {
        getDelegate().run();
    }

    @Override
    public Instant getCreateTime() {
        return getDelegate().getCreateTime();
    }

    @Override
    public Instant getExecutionStartTime() {
        return getDelegate().getExecutionStartTime();
    }

    @Override
    public boolean hasExecutionStarted() {
        return getDelegate().hasExecutionStarted();
    }

    @Override
    public Instant getExecutionEndTime() {
        return getDelegate().getExecutionEndTime();
    }


    @Override
    public boolean hasExecutionEnded() {
        return getDelegate().hasExecutionEnded();
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
    public Optional<Duration> getTimeout() {
        return getDelegate().getTimeout();
    }

    @Override
    public CanonCommand<R> setTimeout(final Duration timeout) {
        getDelegate().setTimeout(timeout);
        return this;
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

    @Override
    public CanonCommand<R> setTargetRef(final EdsdkLibrary.EdsBaseRef targetRef) {
        getDelegate().setTargetRef(targetRef);
        return this;
    }

    @Override
    public Optional<EdsdkLibrary.EdsBaseRef> getTargetRef() {
        return getDelegate().getTargetRef();
    }

    @Override
    public TargetRefType getTargetRefType() {
        return getDelegate().getTargetRefType();
    }

    /**
     * Only used to differentiate copy constructor from decorate constructor
     */
    public final static class FakeClassArgument {
        public static final FakeClassArgument FAKE = new FakeClassArgument();

        private FakeClassArgument() {
        }
    }
}
