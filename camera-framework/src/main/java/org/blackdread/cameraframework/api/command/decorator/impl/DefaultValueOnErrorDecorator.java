/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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

import org.blackdread.cameraframework.api.command.CanonCommand;

import java.util.Optional;

/**
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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
