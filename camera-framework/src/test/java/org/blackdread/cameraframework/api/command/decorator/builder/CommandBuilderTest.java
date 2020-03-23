/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.AbstractCanonCommand;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.DoNothingCommand;
import org.blackdread.cameraframework.api.command.DoThrowCommand;
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
        assertTrue(canonCommand instanceof AbstractCanonCommand);

        final CanonCommand<String> canonCommand1 = new CommandBuilder<>(new DoNothingCommand())
            .build();
        assertNotNull(canonCommand1);
        assertTrue(canonCommand1 instanceof AbstractCanonCommand);
    }

    @Test
    void canGetRootCommand() {
        final DoNothingCommand rootExpected = new DoNothingCommand();
        final CanonCommand<String> command = new CommandBuilder<>(rootExpected)
            .withDefaultOnException("aaaa")
            .timeout(Duration.ofHours(54))
            .build();
        assertNotNull(command);
        assertTrue(command.getTimeout().isPresent());
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
