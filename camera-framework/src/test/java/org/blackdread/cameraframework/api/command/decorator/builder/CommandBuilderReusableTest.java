package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.AbstractCanonCommand;
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
class CommandBuilderReusableTest {

    @Test
    void doNotThrowsOnNull() {
        final CommandBuilderReusable.ReusableBuilder<Object> builder = new CommandBuilderReusable.ReusableBuilder<>(null);
        assertNotNull(builder);
    }

    @Test
    void throwsIfCanonCommandNotSet() {
        final CommandBuilderReusable.ReusableBuilder<Object> builder = new CommandBuilderReusable.ReusableBuilder<>(null);
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void afterBuildRequireToSetAgainCommand() {
        final CommandBuilderReusable.ReusableBuilder<String> builder = new CommandBuilderReusable.ReusableBuilder<>(new DoNothingCommand());
        final CanonCommand<String> canonCommand = builder.build();
        assertNotNull(canonCommand);
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void builderIsReusable() {
        final CommandBuilderReusable.ReusableBuilder<String> builder = new CommandBuilderReusable.ReusableBuilder<>(new DoNothingCommand());
        final CanonCommand<String> canonCommand = builder.build();
        assertNotNull(canonCommand);
        builder.setCanonCommand(new DoNothingCommand());
        final CanonCommand<String> canonCommand2 = builder.build();
        assertNotNull(canonCommand2);
    }

    @Test
    void decoratorAreReused() {
        final CommandBuilderReusable builder = new CommandBuilderReusable.ReusableBuilder<>()
            .setCanonCommand((AbstractCanonCommand) new DoNothingCommand())
            .errorLogic(ErrorLogic.THROW_ALL_ERRORS)
            .timeout(Duration.ofSeconds(90));

        final CanonCommand command = builder.build();

        assertNotNull(command);
        assertTrue(command.getTimeout().isPresent());
        assertEquals(command.getTimeout().get(), Duration.ofSeconds(90));
        assertTrue(command.getErrorLogic().isPresent());
        assertEquals(command.getErrorLogic().get(), ErrorLogic.THROW_ALL_ERRORS);

        builder.setCanonCommand(new DoNothingCommand());
        final CanonCommand command2 = builder.build();

        assertNotNull(command2);
        assertTrue(command.getTimeout().isPresent());
        assertEquals(command.getTimeout().get(), Duration.ofSeconds(90));
        assertTrue(command2.getErrorLogic().isPresent());
        assertEquals(command2.getErrorLogic().get(), ErrorLogic.THROW_ALL_ERRORS);
    }

    @Test
    void canGetRootCommand() {
        final DoNothingCommand rootExpected = new DoNothingCommand();
        final CanonCommand<String> command = new CommandBuilderReusable.ReusableBuilder<>(rootExpected)
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
        final CanonCommand command = new CommandBuilderReusable<>()
            .setCanonCommand((AbstractCanonCommand) new DoNothingCommand())
            .timeout(Duration.ofSeconds(90))
            .build();
        assertNotNull(command);
        assertTrue(command.getTimeout().isPresent());
        assertEquals(command.getTimeout().get(), Duration.ofSeconds(90));
    }

    @Test
    void canSetErrorLogic() {
        final CanonCommand command = new CommandBuilderReusable<>()
            .setCanonCommand((AbstractCanonCommand) new DoNothingCommand())
            .errorLogic(ErrorLogic.THROW_ALL_ERRORS)
            .build();
        assertNotNull(command);
        assertTrue(command.getErrorLogic().isPresent());
        assertEquals(command.getErrorLogic().get(), ErrorLogic.THROW_ALL_ERRORS);
    }

    @Test
    void canSetDefaultValue() throws ExecutionException, InterruptedException {
        final CanonCommand command = new CommandBuilderReusable<>()
            .setCanonCommand((AbstractCanonCommand) new DoThrowCommand())
            .withDefaultOnException("any")
            .build();
        assertNotNull(command);
        final String value = (String) command.get();
        assertEquals("any", value);
        final Optional<String> valueOpt = command.getOpt();
        assertTrue(valueOpt.isPresent());
        assertEquals("any", valueOpt.get());
    }

    @Test
    void canSetDefaultValueToNull() throws ExecutionException, InterruptedException {
        final CanonCommand command = new CommandBuilderReusable<>()
            .setCanonCommand((AbstractCanonCommand) new DoThrowCommand())
            .withDefaultOnException(null)
            .build();
        assertNotNull(command);
        final String value = (String) command.get();
        assertEquals(null, value);
        final Optional<String> valueOpt = command.getOpt();
        assertFalse(valueOpt.isPresent());
    }

    @Test
    void isNotTypeSafeAndWillThrowOnCast() throws ExecutionException, InterruptedException {
        final CanonCommand command = new CommandBuilderReusable<>()
            .setCanonCommand((AbstractCanonCommand) new DoThrowCommand())
            .withDefaultOnException(55)
            .build();
        assertNotNull(command);

        final Object value = command.get();
        assertEquals(55, value);
        assertThrows(ClassCastException.class, () -> {
            final String willThrow = (String) value;
        });

        final Optional<String> valueOpt = command.getOpt();

        assertTrue(valueOpt.isPresent());
        assertEquals(55, valueOpt.get());
        assertThrows(ClassCastException.class, () -> {
            final String willThrow = valueOpt.get();
        });
    }

}
