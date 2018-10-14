package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.DoNothingCommand;
import org.blackdread.cameraframework.api.command.DoThrowCommand;
import org.blackdread.cameraframework.api.command.contract.ErrorLogic;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
class CommandBuilderTest {

    @Test
    void throwsOnNull() {
        assertThrows(NullPointerException.class, () -> new CommandBuilder<>(null));
    }

    @Test
    void builderIsNotReusable() {
        final CommandBuilder.SimpleBuilder<String> builder = new CommandBuilder.SimpleBuilder<>(new DoNothingCommand());
        final CanonCommand<String> canonCommand = builder.build();
        assertNotNull(canonCommand);
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void builderDoNotDecorateByDefault() {
        final CommandBuilder.SimpleBuilder<String> builder = new CommandBuilder.SimpleBuilder<>(new DoNothingCommand());
        final CanonCommand<String> canonCommand = builder.build();
        assertNotNull(canonCommand);
        assertFalse(canonCommand.getErrorLogic().isPresent());
        assertFalse(canonCommand.getTimeout().isPresent());

        final CanonCommand<String> canonCommand1 = new CommandBuilder<>(new DoNothingCommand())
            .build();
        assertNotNull(canonCommand1);
        assertFalse(canonCommand1.getErrorLogic().isPresent());
        assertFalse(canonCommand1.getTimeout().isPresent());
    }

    @Test
    void canGetRootCommand() {
        final DoNothingCommand rootExpected = new DoNothingCommand();
        final CanonCommand<String> command = new CommandBuilder<>(rootExpected)
            .errorLogic(ErrorLogic.THROW_ALL_ERRORS)
            .withDefaultOnException("aaaa")
            .timeout(Duration.ofHours(54))
            .build();
        assertNotNull(command);
        assertTrue(command.getTimeout().isPresent());
        assertTrue(command.getErrorLogic().isPresent());
        final DecoratorCommand<String> castCommand = (DecoratorCommand<String>) command;
        assertEquals(rootExpected, castCommand.getRoot());
    }

    @Test
    void canSetTimeout() {
        final CanonCommand<String> command = new CommandBuilder<>(new DoNothingCommand())
            .timeout(Duration.ofSeconds(90))
            .build();
        assertNotNull(command);
        assertTrue(command.getTimeout().isPresent());
        assertEquals(command.getTimeout().get(), Duration.ofSeconds(90));
    }

    @Test
    void canSetErrorLogic() {
        CanonCommand<String> command = new CommandBuilder<>(new DoNothingCommand())
            .errorLogic(ErrorLogic.THROW_ALL_ERRORS)
            .build();
        assertNotNull(command);
        assertTrue(command.getErrorLogic().isPresent());
        assertEquals(command.getErrorLogic().get(), ErrorLogic.THROW_ALL_ERRORS);

        command = new CommandBuilder<>(new DoNothingCommand())
            .errorLogic(ErrorLogic.THROW_ALL_ERRORS)
            .errorLogic(ErrorLogic.SKIP_ERRORS)
            .build();
        assertNotNull(command);
        assertTrue(command.getErrorLogic().isPresent());
        assertEquals(command.getErrorLogic().get(), ErrorLogic.SKIP_ERRORS);
    }

    @Test
    void canSetDefaultValue() throws ExecutionException, InterruptedException {
        final CanonCommand<String> command = new CommandBuilder<>(new DoThrowCommand())
            .withDefaultOnException("any")
            .build();
        assertNotNull(command);
        final String value = command.get();
        assertEquals("any", value);
        final Optional<String> valueOpt = command.getOpt();
        assertTrue(valueOpt.isPresent());
        assertEquals("any", valueOpt.get());
    }

    @Test
    void canSetDefaultValueToNull() throws ExecutionException, InterruptedException {
        final CanonCommand<String> command = new CommandBuilder<>(new DoThrowCommand())
            .withDefaultOnException(null)
            .build();
        assertNotNull(command);
        final String value = command.get();
        assertEquals(null, value);
        final Optional<String> valueOpt = command.getOpt();
        assertFalse(valueOpt.isPresent());
    }

}
