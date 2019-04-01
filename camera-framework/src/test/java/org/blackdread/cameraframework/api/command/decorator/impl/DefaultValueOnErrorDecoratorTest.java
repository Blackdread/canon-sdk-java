package org.blackdread.cameraframework.api.command.decorator.impl;

import org.blackdread.cameraframework.api.command.GenericCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/04/01.</p>
 *
 * @author Yoann CAPLAIN
 */
class DefaultValueOnErrorDecoratorTest {

    private GenericCommand<String> command;
    private GenericCommand<String> commandThrows;

    @BeforeEach
    void setUp() {
        command = new GenericCommand<>(() -> "value");
        commandThrows = new GenericCommand<>(() -> {
            throw new RuntimeException("");
        });
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void get() {
        final DefaultValueOnErrorDecorator<String> decorator = new DefaultValueOnErrorDecorator<>(command);

        decorator.run();

        Assertions.assertNotNull(decorator.get());
        Assertions.assertEquals("value", decorator.get());
    }

    @Test
    void getDefaultOnError() {
        final DefaultValueOnErrorDecorator<String> decorator = new DefaultValueOnErrorDecorator<>(commandThrows, "default");

        decorator.run();

        Assertions.assertNotNull(decorator.get());
        Assertions.assertEquals("default", decorator.get());
    }

    @Test
    void getOpt() {
        final DefaultValueOnErrorDecorator<String> decorator = new DefaultValueOnErrorDecorator<>(command);

        decorator.run();

        Assertions.assertTrue(decorator.getOpt().isPresent());
        Assertions.assertEquals("value", decorator.getOpt().get());
    }

    @Test
    void getOptDefaultOnError() {
        final DefaultValueOnErrorDecorator<String> decorator = new DefaultValueOnErrorDecorator<>(commandThrows, "default");

        decorator.run();

        Assertions.assertTrue(decorator.getOpt().isPresent());
        Assertions.assertEquals("default", decorator.getOpt().get());
    }
}
