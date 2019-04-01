package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.cameraframework.api.command.GenericCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * <p>Created on 2019/04/01.</p>
 *
 * @author Yoann CAPLAIN
 */
class TimeoutCommandDecoratorTest {

    private GenericCommand<String> command;

    @BeforeEach
    void setUp() {
        command = new GenericCommand<>(() -> "value");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTimeout() {
        final TimeoutCommandDecorator<String> decorator = new TimeoutCommandDecorator<>(command, Duration.ofSeconds(5));

        Assertions.assertTrue(decorator.getTimeout().isPresent());
        Assertions.assertEquals(Duration.ofSeconds(5), decorator.getTimeout().get());
    }

    @Test
    void setTimeout() {
        final TimeoutCommandDecorator<String> decorator = new TimeoutCommandDecorator<>(command, Duration.ofSeconds(5));

        decorator.setTimeout(Duration.ofSeconds(50));

        Assertions.assertTrue(decorator.getTimeout().isPresent());
        Assertions.assertEquals(Duration.ofSeconds(50), decorator.getTimeout().get());
    }
}
