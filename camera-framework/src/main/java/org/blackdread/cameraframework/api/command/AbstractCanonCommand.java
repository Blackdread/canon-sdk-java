package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.cameraframework.api.command.contract.ErrorLogic;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.blackdread.cameraframework.util.TimeUtil.currentInstant;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class AbstractCanonCommand<R> implements CanonCommand<R> {

    protected static final Logger log = LoggerFactory.getLogger(AbstractCanonCommand.class);

    // TODO timing to know execution time, delay time between creation and time run is called, etc...

    private final Instant createTime = currentInstant();

    private Instant executionStartTime = null;

    private Instant executionEndTime = null;

    /**
     * Can be the cameraRef, imageRef, VolumeRef, etc
     */
    private EdsBaseRef targetRef;

    // the type should maybe be CanonCommand<R> but with DecoratorCommand it is clearer
    private DecoratorCommand<R> decoratorCommand;

    /**
     * Called only by command executor thread(s)
     */
    final void run() {
        executionStartTime = currentInstant();
        try {
            // TODO try catch, etc
            // no timeout here, the dispatcher takes care of that
            runInternal();
        } finally {
            executionEndTime = currentInstant();
        }
    }

    protected abstract void runInternal();

    protected final EdsBaseRef getTargetRef() {
        if (targetRef == null)
            throw new IllegalStateException("TargetRef have not been set yet");
        return targetRef;
    }

    @Override
    public Instant getCreateTime() {
        return createTime;
    }

    @Override
    public Instant getExecutionStartTime() {
        if (executionStartTime == null)
            throw new IllegalStateException("Command not started yet");
        return executionStartTime;
    }

    @Override
    public Instant getExecutionEndTime() {
        if (executionStartTime == null)
            throw new IllegalStateException("Command not started yet");
        if (executionEndTime == null)
            throw new IllegalStateException("Command not finished yet");
        return executionEndTime;
    }

    @Override
    public R get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Optional<R> getOpt() throws InterruptedException, ExecutionException {
        return Optional.empty();
    }

    /*
     * <b>Must not be called from itself neither sub-classes of {@link AbstractCanonCommand}</b>
     *
     * @return itself
     */
    /*
    @Override
    public final CanonCommand<R> getRoot() {
        return this;
    }
    //*/

    /**
     * <b>Must not be called from itself neither sub-classes of {@link AbstractCanonCommand}</b>
     * It is called only by decorators, call instead {@link #getErrorLogicInternal()}
     *
     * @return e
     */
    @Override
    public Optional<ErrorLogic> getErrorLogic() {
        // TODO get from global settings
        return Optional.empty();
    }

    /**
     * <b>Must not be called from itself neither sub-classes of {@link AbstractCanonCommand}</b>
     * It is called only by decorators, call instead {@link #getTimeoutInternal()}
     *
     * @return e
     */
    @Override
    public Optional<Duration> getTimeout() {
        // TODO get from global settings
        return Optional.empty();
    }

    protected ErrorLogic getErrorLogicInternal() {
        return decoratorCommand.getErrorLogic()
            .orElseGet(() -> {
                // TODO get the default logic from global settings
                return ErrorLogic.THROW_ALL_ERRORS;
            });
    }

    protected Duration getTimeoutInternal() {
        return decoratorCommand.getTimeout()
            .orElseGet(() -> {
                // TODO get the default logic from global settings
                return Duration.ofMinutes(2);
            });
    }
}
