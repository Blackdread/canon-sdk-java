package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.decorator.impl.AbstractDecoratorCommand;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2019/04/01.</p>
 *
 * @author Yoann CAPLAIN
 */
class AbstractCanonCommandTest {

    private DoNothingCommand doNothingCommand;

    @BeforeEach
    void setUp() {
        doNothingCommand = new DoNothingCommand();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void copyThrowsIfCopyConstructorMissing() {
        final BadCommand badCommand = new BadCommand();

        Assertions.assertThrows(IllegalStateException.class, badCommand::copy);
    }

    private static class BadCommand extends AbstractCanonCommand<String> {
        @Override
        protected String runInternal() throws InterruptedException {
            return "nothing";
        }
    }

    @Test
    void runThrowsOnMultipleCall() {
        doNothingCommand.run();
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.run());
    }

    @Test
    void setTargetRef() {
        Assertions.assertThrows(NullPointerException.class, () -> doNothingCommand.setTargetRef(null));

        doNothingCommand.setTargetRef(new EdsdkLibrary.EdsCameraRef());
        Assertions.assertEquals(TargetRefType.CAMERA, doNothingCommand.getTargetRefType());

        doNothingCommand.setTargetRef(new EdsdkLibrary.EdsImageRef());
        Assertions.assertEquals(TargetRefType.IMAGE, doNothingCommand.getTargetRefType());

        doNothingCommand.setTargetRef(new EdsdkLibrary.EdsEvfImageRef());
        Assertions.assertEquals(TargetRefType.EVF_IMAGE, doNothingCommand.getTargetRefType());

        doNothingCommand.setTargetRef(new EdsdkLibrary.EdsVolumeRef());
        Assertions.assertEquals(TargetRefType.VOLUME, doNothingCommand.getTargetRefType());

        doNothingCommand.setTargetRef(new EdsdkLibrary.EdsDirectoryItemRef());
        Assertions.assertEquals(TargetRefType.DIRECTORY_ITEM, doNothingCommand.getTargetRefType());

        Assertions.assertTrue(doNothingCommand.getTargetRef().isPresent());
    }

    @Test
    void setTargetRefIfCommandRan() {
        doNothingCommand.setTargetRef(new EdsdkLibrary.EdsCameraRef());

        doNothingCommand.run();

        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.setTargetRef(new EdsdkLibrary.EdsCameraRef()));
    }

    @Test
    void setTargetRefThrowsOnUnhandled() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> doNothingCommand.setTargetRef(new EdsdkLibrary.EdsBaseRef()));
    }

    @Test
    void getTargetRef() {
        Assertions.assertFalse(doNothingCommand.getTargetRef().isPresent());
    }

    @Test
    void getTargetRefTypeThrowsIfNotSetYet() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getTargetRefType());
    }

    @Test
    void getTargetRefInternal() {
        final EdsdkLibrary.EdsImageRef targetRef = new EdsdkLibrary.EdsImageRef();
        doNothingCommand.setTargetRef(targetRef);
        Assertions.assertNotNull(doNothingCommand.getTargetRefInternal());
        Assertions.assertEquals(targetRef, doNothingCommand.getTargetRefInternal());
    }

    @Test
    void getTargetRefInternalThrowsIfNotSetYet() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getTargetRefInternal());
    }

    @Test
    void getCreateTime() {
        Assertions.assertNotNull(doNothingCommand.getCreateTime());
    }

    @Test
    void getExecutionStartTime() {
        doNothingCommand.run();
        Assertions.assertNotNull(doNothingCommand.getExecutionStartTime());
    }

    @Test
    void getExecutionStartTimeThrowsWhenNotRan() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getExecutionStartTime());
    }

    @Test
    void hasExecutionStarted() {
        Assertions.assertFalse(doNothingCommand.hasExecutionStarted());

        doNothingCommand.run();

        Assertions.assertTrue(doNothingCommand.hasExecutionStarted());
    }

    @Test
    void getExecutionEndTime() {
        doNothingCommand.run();
        Assertions.assertNotNull(doNothingCommand.getExecutionEndTime());
    }

    @Test
    void getExecutionEndTimeThrowsWhenNotRan() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getExecutionEndTime());
    }

    @Test
    void hasExecutionEnded() {
        Assertions.assertFalse(doNothingCommand.hasExecutionEnded());

        doNothingCommand.run();

        Assertions.assertTrue(doNothingCommand.hasExecutionEnded());
    }

    @RepeatedTest(10)
    void hasExecutionEndedIsFalseInBetweenRun() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        final GenericCommand<String> command = new GenericCommand<>(() -> {
            latch.countDown();
            Thread.sleep(10000);
            return "end";
        });

        Assertions.assertFalse(command.hasExecutionEnded());

        final Thread thread = new Thread(command::run);
        thread.start();

        latch.await();

        Assertions.assertFalse(command.hasExecutionEnded());

        thread.interrupt();

        Thread.sleep(50);

        Assertions.assertTrue(command.hasExecutionEnded());
    }

    @Test
    void getRethrowAfterWait() throws InterruptedException {
        final GenericCommand<String> command = new GenericCommand<>(() -> {
            Thread.sleep(300);
            throw new RuntimeException("bad");
        });

        final Thread thread = new Thread(command::run);
        thread.start();

        Assertions.assertThrows(ExecutionException.class, command::get);

    }

    @Test
    void getCreatedDurationSinceNow() {
        final Duration createdDurationSinceNow = doNothingCommand.getCreatedDurationSinceNow();

        Assertions.assertNotNull(createdDurationSinceNow);

    }

    @Test
    void getExecutionDurationSinceNow() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getExecutionDurationSinceNow());

        doNothingCommand.run();

        final Duration duration = doNothingCommand.getExecutionDurationSinceNow();

        Assertions.assertNotNull(duration);
    }

    @Test
    void getExecutionDuration() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getExecutionDuration());

        doNothingCommand.run();

        final Duration duration = doNothingCommand.getExecutionDuration();

        Assertions.assertNotNull(duration);
    }

    @Test
    void getExecutionDurationTotal() {
        Assertions.assertThrows(IllegalStateException.class, () -> doNothingCommand.getExecutionDurationTotal());

        doNothingCommand.run();

        final Duration duration = doNothingCommand.getExecutionDurationTotal();

        Assertions.assertNotNull(duration);

    }

    @Test
    void get() throws ExecutionException, InterruptedException {
        final GenericCommand<String> command = new GenericCommand<>(() -> "end");

        command.run();

        final String s1 = command.get();
        final String s2 = command.get();

        Assertions.assertNotNull(s1);
        Assertions.assertEquals(s1, s2);
    }

    @Test
    void getOpt() throws ExecutionException, InterruptedException {
        final GenericCommand<String> command = new GenericCommand<>(() -> "end");

        command.run();

        final Optional<String> s1 = command.getOpt();
        final Optional<String> s2 = command.getOpt();

        Assertions.assertTrue(s1.isPresent());
        Assertions.assertTrue(s2.isPresent());
        Assertions.assertEquals(s1.get(), s2.get());
    }

    @Test
    void getTimeout() {
        Assertions.assertTrue(doNothingCommand.getTimeout().isPresent());
    }

    @Test
    void setTimeout() {
        final Duration duration = Duration.ofSeconds(5);
        doNothingCommand.setTimeout(duration);

        Assertions.assertEquals(duration, doNothingCommand.getTimeout().get());
        Assertions.assertEquals(duration, doNothingCommand.getTimeoutInternal());
    }

    @Test
    void getTimeoutInternal() {
        Assertions.assertNotNull(doNothingCommand.getTimeoutInternal());
    }

    @Test
    void allMethodActuallyDelegate() {
        final ThrowAllCommand baseCommand = new ThrowAllCommand();
        final DelegateDecoratorCommand decoratorCommand = new DelegateDecoratorCommand(baseCommand);

        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::run);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getCreateTime);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getExecutionStartTime);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::hasExecutionStarted);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getExecutionEndTime);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::hasExecutionEnded);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::get);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getOpt);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getTimeout);
        Assertions.assertThrows(EdsdkErrorException.class, () -> decoratorCommand.setTimeout(null));
        Assertions.assertEquals(baseCommand, decoratorCommand.getRoot());
        Assertions.assertThrows(EdsdkErrorException.class, () -> decoratorCommand.setTargetRef(null));
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getTargetRef);
        Assertions.assertThrows(EdsdkErrorException.class, decoratorCommand::getTargetRefType);
    }

    private static class DelegateDecoratorCommand extends AbstractDecoratorCommand {

        protected DelegateDecoratorCommand(final CanonCommand delegate) {
            super(delegate);
        }
    }

    private static class ThrowAllCommand implements CanonCommand {

        @Override
        public void run() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Instant getCreateTime() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Instant getExecutionStartTime() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public boolean hasExecutionStarted() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Instant getExecutionEndTime() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public boolean hasExecutionEnded() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Duration getCreatedDurationSinceNow() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Duration getExecutionDurationSinceNow() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Duration getExecutionDuration() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Duration getExecutionDurationTotal() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Object get() throws InterruptedException, ExecutionException {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Optional getOpt() throws InterruptedException, ExecutionException {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public CanonCommand copy() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public CanonCommand setTargetRef(final EdsdkLibrary.EdsBaseRef targetRef) {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Optional<EdsdkLibrary.EdsBaseRef> getTargetRef() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public TargetRefType getTargetRefType() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public Optional<Duration> getTimeout() {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }

        @Override
        public CanonCommand setTimeout(final Duration timeout) {
            throw new EdsdkErrorException(EdsdkError.EDS_ERR_OK);
        }
    }
}
