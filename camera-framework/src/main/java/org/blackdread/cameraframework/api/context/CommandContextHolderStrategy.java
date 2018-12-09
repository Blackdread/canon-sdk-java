package org.blackdread.cameraframework.api.context;

/**
 * A strategy for storing command context information against a thread.
 *
 * <p>
 * The preferred strategy is loaded by {@link CommandContextHolder}.
 * <p>Created on 2018/10/23.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 * @deprecated not used and not sure if would be useful
 */
@Deprecated
public interface CommandContextHolderStrategy {

    /**
     * Clears the current context.
     */
    void clearContext();

    /**
     * Obtains the current context.
     *
     * @return a context (never <code>null</code> - create a default implementation if
     * necessary)
     */
    CommandContext getContext();

    /**
     * Sets the current context.
     *
     * @param context to the new argument (should never be <code>null</code>, although
     *                implementations must check if <code>null</code> has been passed and throw an
     *                <code>IllegalArgumentException</code> in such cases)
     */
    void setContext(CommandContext context);

    /**
     * Creates a new, empty context implementation, for use by
     * <tt>CommandContextHolder</tt> implementations, when creating a new context for
     * the first time.
     *
     * @return the empty context.
     */
    CommandContext createEmptyContext();
}
