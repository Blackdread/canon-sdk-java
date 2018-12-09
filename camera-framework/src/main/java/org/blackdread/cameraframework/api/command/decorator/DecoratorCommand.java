package org.blackdread.cameraframework.api.command.decorator;

import org.blackdread.cameraframework.api.command.CanonCommand;

/**
 * A command decorator
 * <p>Created on 2018/10/11.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public interface DecoratorCommand<R> extends CanonCommand<R>, RootDecoratorCommand<R> {

    DecoratorCommand<R> copy();

}
