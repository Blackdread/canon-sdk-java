package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.command.contract.ErrorLogicCommand;
import org.blackdread.cameraframework.api.command.contract.TimeoutCommand;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @param <R> Return type of command
 * @author Yoann CAPLAIN
 */
public interface CanonCommand<R> extends TimeoutCommand, ErrorLogicCommand/*, RootDecoratorCommand<R>/*, Future<R>*/ {

    R get() throws InterruptedException, ExecutionException;

    Optional<R> getOpt() throws InterruptedException, ExecutionException;

}
