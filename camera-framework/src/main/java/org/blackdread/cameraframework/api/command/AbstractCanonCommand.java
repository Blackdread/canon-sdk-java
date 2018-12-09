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
import java.util.concurrent.ExecutionException;

import static org.blackdread.cameraframework.util.TimeUtil.currentInstant;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class AbstractCanonCommand<R> implements CanonCommand<R>, TargetRefCommand {

    protected static final Logger log = LoggerFactory.getLogger(AbstractCanonCommand.class);

    // TODO timing to know execution time, delay time between creation and time run is called, etc...

    private final Instant createTime = currentInstant();

    // might require volatile
    private Instant executionStartTime = null;

    private Instant executionEndTime = null;

    /**
     * Can be the cameraRef, imageRef, VolumeRef, etc
     */
    private EdsBaseRef targetRef;

    private TargetRefType targetRefType;

    // the type should maybe be CanonCommand<R> but with DecoratorCommand it is clearer
    private DecoratorCommand<R> decoratorCommand;

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

        // Values below not copied on purpose
        executionStartTime = null;
        executionEndTime = null;

        // TODO will do later for decoratorCommand
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
            // TODO try catch, etc
            // no timeout here, the dispatcher takes care of that
            runInternal();
        } catch (InterruptedException e) {
            // TODO
        } catch (Exception e) {
            // TODO
        } finally {
            executionEndTime = currentInstant();
        }
    }

    protected abstract void runInternal() throws InterruptedException;

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
            this.targetRefType = TargetRefType.VOLUME;
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
