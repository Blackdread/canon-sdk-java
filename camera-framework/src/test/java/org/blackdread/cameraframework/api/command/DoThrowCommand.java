package org.blackdread.cameraframework.api.command;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Command that always throw when ran
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
public class DoThrowCommand extends AbstractCanonCommand<String> {

    @Override
    protected void runInternal() {
        throw new RuntimeException("I always throw");
    }

    @Override
    public String get() throws InterruptedException, ExecutionException {
        throw new ExecutionException(new RuntimeException("I always throw"));
    }

    @Override
    public Optional<String> getOpt() throws InterruptedException, ExecutionException {
        throw new ExecutionException(new RuntimeException("I always throw"));
    }
}
