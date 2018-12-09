package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsBaseRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsDirectoryItemRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsEvfImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsVolumeRef;
import org.blackdread.cameraframework.api.command.contract.TargetRefCommand;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import static org.blackdread.cameraframework.util.TimeUtil.currentInstant;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class AbstractCanonCommand<R> implements CanonCommand<R>, TargetRefCommand {

    protected static final Logger log = LoggerFactory.getLogger(AbstractCanonCommand.class);

    private final Instant createTime = currentInstant();

    private volatile Instant executionStartTime = null;

    private volatile Instant executionEndTime = null;

    /**
     * Can be the cameraRef, imageRef, VolumeRef, etc
     */
    private EdsBaseRef targetRef;

    private TargetRefType targetRefType;

    // the type should maybe be CanonCommand<R> but with DecoratorCommand it is clearer
    private DecoratorCommand<R> decoratorCommand;

    private final CountDownLatch resultBlocker = new CountDownLatch(1);

    // Could do something similar to CompletableFuture
    private volatile R result;
    private volatile Throwable resultException;

    protected AbstractCanonCommand() {
    }

    /**
     * Allow to re-send a command as a command cannot be re-sent without making a copy
     * <p>Not all fields need to be copied</p>
     *
     * @param toCopy command to copy
     */
    protected AbstractCanonCommand(final AbstractCanonCommand<R> toCopy) {
        // By default target ref is copied, it can be modified later with the public setter
        targetRef = toCopy.targetRef;
        targetRefType = toCopy.targetRefType;

        // Values below not copied on purpose
        executionStartTime = null;
        executionEndTime = null;
        result = null;
        resultException = null;

        // set to null, supposed to be set by camera or dispatcher but this is not used and maybe not necessary to have
        decoratorCommand = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractCanonCommand<R> copy() {
        try {
            final Class<? extends AbstractCanonCommand> currentClass = getClass();
            return currentClass.getConstructor(currentClass).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // Either sub-class override copy() or provide a copy constructor that is accessible, etc
            // Copy constructor must be public and argument class match its type
            throw new IllegalStateException("Did not define a copy constructor in sub-classes", e);
        }
    }

    @Override
    public final void run() {
        throwIfRunAlreadyCalled();
        executionStartTime = currentInstant();
        try {
            // no timeout here, the dispatcher takes care of that
            result = runInternal();
        } catch (InterruptedException e) {
            resultException = e;
        } catch (Exception e) {
            // same as interrupted catch for now
            resultException = e;
        } finally {
            executionEndTime = currentInstant();
            resultBlocker.countDown();
        }
    }

    /**
     * @return result of command, may be null
     * @throws InterruptedException in case that internal logic is waiting/joining/etc and that executing thread is interrupted
     */
    protected abstract R runInternal() throws InterruptedException;

    private void throwIfRunAlreadyCalled() {
        if (executionStartTime != null) {
            throw new IllegalStateException("Run method already called");
        }
    }

    @Override
    public void setTargetRef(final EdsBaseRef targetRef) {
        throwIfRunAlreadyCalled();
        this.targetRef = Objects.requireNonNull(targetRef);

        if (targetRef instanceof EdsCameraRef) {
            this.targetRefType = TargetRefType.CAMERA;
        } else if (targetRef instanceof EdsImageRef) {
            this.targetRefType = TargetRefType.IMAGE;
        } else if (targetRef instanceof EdsEvfImageRef) {
            this.targetRefType = TargetRefType.EVF_IMAGE;
        } else if (targetRef instanceof EdsVolumeRef) {
            this.targetRefType = TargetRefType.VOLUME;
        } else if (targetRef instanceof EdsDirectoryItemRef) {
            this.targetRefType = TargetRefType.DIRECTORY_ITEM;
        } else {
            throw new IllegalArgumentException("Target ref not supported");
        }
    }

    public final Optional<EdsBaseRef> getTargetRef() {
        return Optional.of(targetRef);
    }

    @Override
    public TargetRefType getTargetRefType() {
        if (targetRefType == null)
            throw new IllegalStateException("TargetRefType have not been set yet");
        return targetRefType;
    }

    protected final EdsBaseRef getTargetRefInternal() {
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
    public boolean hasExecutionStarted() {
        return executionStartTime != null;
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
        if (executionEndTime != null) {
            if (resultException != null) {
                throw new ExecutionException(resultException);
            }
            return result;
        } else {
            resultBlocker.await();
            if (resultException != null) {
                throw new ExecutionException(resultException);
            }
            return result;
        }
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
     * It is called only by decorators, call instead {@link #getTimeoutInternal()}
     *
     * @return e
     */
    @Override
    public Optional<Duration> getTimeout() {
        // TODO get from global settings
        return Optional.empty();
    }

    protected Duration getTimeoutInternal() {
        return decoratorCommand.getTimeout()
            .orElseGet(() -> {
                // TODO get the default logic from global settings
                return Duration.ofMinutes(2);
            });
    }
}
