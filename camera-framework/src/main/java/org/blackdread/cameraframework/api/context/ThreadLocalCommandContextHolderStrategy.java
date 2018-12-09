package org.blackdread.cameraframework.api.context;

import java.util.Objects;

/**
 * A <code>ThreadLocal</code>-based implementation of
 * {@link CommandContextHolderStrategy}.
 * <p>Created on 2018/10/23.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 * @see ThreadLocal
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
