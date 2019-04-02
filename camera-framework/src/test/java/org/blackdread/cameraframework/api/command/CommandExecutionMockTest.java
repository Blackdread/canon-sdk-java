package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsImageRef;
import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.api.TestUtil;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

/**
 * Test execution of commands, make sure logic stays unchanged and match expected behavior
 * <p>Created on 2019/04/02.</p>
 *
 * @author Yoann CAPLAIN
 */
class CommandExecutionMockTest extends AbstractMockTest {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutionMockTest.class);

    private EdsCameraRef fakeCamera;

    private EdsImageRef fakeImage;

    @BeforeEach
    void setUp() {
        fakeCamera = new EdsCameraRef();

        fakeImage = new EdsImageRef();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRegisterCameraAddedEventCommand() {
        final CanonCommand command = new RegisterCameraAddedEventCommand();

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraAddedEventLogic()).registerCameraAddedEvent();
        });
    }

    @Test
    void test() {
        final CanonCommand command = new RegisterObjectEventCommand(fakeCamera);

        runAndAssertCommand(command, (canonCommand, o) -> {
            Assertions.assertNull(o);
            verify(CanonFactory.cameraObjectEventLogic()).registerCameraObjectEvent(fakeCamera);
        });
    }

    private static <T extends CanonCommand<R>, R> void runAndAssertCommand(final T command, final Consumer<T> assertConsumer) {
        command.run();

        assertConsumer.accept(command);
    }

    private static <T extends CanonCommand<R>, R> void runAndAssertCommand(final T command, final BiConsumer<T, R> assertConsumer) {
        command.run();

        final R result = getResult(command);

        assertConsumer.accept(command, result);
    }

    private static <R> R getResult(final CanonCommand<R> command) {
        try {
            return command.get();
        } catch (InterruptedException | ExecutionException e) {
            TestUtil.throwUnchecked(e);
            throw new RuntimeException("do not reach");
        }
    }
}
