package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.CallableCommand;

/**
 * Allow to run provide generic commands as not all are defined in the framework, this can help to "create" new commands without having to define a new class.
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class GenericCommand<R> extends AbstractCanonCommand<R> {

    private final CallableCommand<R> callable;

    public GenericCommand(final CallableCommand<R> callable) {
        this.callable = callable;
    }

    /**
     * Extreme care with variable passed in the callable as it will reuse it
     *
     * @param toCopy command to copy
     */
    public GenericCommand(final GenericCommand<R> toCopy) {
        super(toCopy);
        this.callable = toCopy.callable;
    }

    @Override
    protected R runInternal() throws InterruptedException {
        return callable.call();
    }
}
