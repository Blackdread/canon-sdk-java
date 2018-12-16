/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.context;

import java.util.Objects;

/**
 * A <code>ThreadLocal</code>-based implementation of
 * {@link CommandContextHolderStrategy}.
 * <p>Created on 2018/10/23.</p>
 *
 * @author Yoann CAPLAIN
 * @see ThreadLocal
 * @since 1.0.0
 * @deprecated not used and not sure if would be useful
 */
@Deprecated
final class ThreadLocalCommandContextHolderStrategy implements CommandContextHolderStrategy {

    private static final ThreadLocal<CommandContext> contextHolder = new ThreadLocal<>();

    public void clearContext() {
        contextHolder.remove();
    }

    public CommandContext getContext() {
        CommandContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public void setContext(CommandContext context) {
        Objects.requireNonNull(context, "Only non-null CommandContext instances are permitted");
        contextHolder.set(context);
    }

    public CommandContext createEmptyContext() {
        return new CommandContextImpl();
    }
}
