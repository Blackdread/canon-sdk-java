package org.blackdread.cameraframework.api.command.decorator;

import org.blackdread.cameraframework.api.command.CanonCommand;

/**
 * Allow to get the abstract root command to call internal methods from the manager class for threads executing commands
 * <p>Created on 2018/10/10.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface RootDecoratorCommand<R> {

    /**
     * First command of the chain of decorators, it should always return the {@link org.blackdread.cameraframework.api.command.AbstractCanonCommand}
     *
     * @return root command that is decorated
     */
    CanonCommand<R> getRoot();

}
