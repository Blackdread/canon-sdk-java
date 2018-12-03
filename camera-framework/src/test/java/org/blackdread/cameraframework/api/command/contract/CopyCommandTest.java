package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.cameraframework.api.command.decorator.impl.DefaultValueOnErrorDecorator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.blackdread.cameraframework.api.command.GetPropertyCommand.ProductName;
import static org.blackdread.cameraframework.api.command.decorator.impl.AbstractDecoratorCommand.FakeClassArgument.FAKE;

/**
 * <p>Created on 2018/12/04.</p>
 *
 * @author Yoann CAPLAIN
 */
class CopyCommandTest {

    @BeforeAll
    static void setUpClass() {
    }

    @AfterAll
    static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void copyByConstructor() {
        final ProductName command = new ProductName();
        final ProductName commandCopied = new ProductName(command);
    }

    @Test
    void copyByMethod() {
        final ProductName command = new ProductName();
        final ProductName copy = (ProductName) command.copy();

        Assertions.assertNotNull(copy);
        Assertions.assertNotSame(copy, command);
    }

    @Test
    void copyByConstructor1Decorator() {
        final ProductName command = new ProductName();
        final DefaultValueOnErrorDecorator<String> decorated = new DefaultValueOnErrorDecorator<>(command, "default");

        final DefaultValueOnErrorDecorator<String> copyDecorated = new DefaultValueOnErrorDecorator<>(FAKE, decorated);

        Assertions.assertNotSame(copyDecorated.getRoot(), command);
        Assertions.assertNotNull(copyDecorated.getRoot());
        // TODO for later when command finished to impl
//        Assertions.assertEquals("default", decorated.get());
//        Assertions.assertEquals("default", copyDecorated.get());
    }

    @Test
    void copyByMethod1Decorator() {
        final ProductName command = new ProductName();
        final DefaultValueOnErrorDecorator<String> decorated = new DefaultValueOnErrorDecorator<>(command, "default");

        final DefaultValueOnErrorDecorator<String> copyDecorated = (DefaultValueOnErrorDecorator<String>) decorated.copy();

        Assertions.assertNotSame(copyDecorated, decorated);
        Assertions.assertNotSame(copyDecorated.getRoot(), command);
        Assertions.assertNotNull(copyDecorated.getRoot());
        // TODO for later when command finished to impl
//        Assertions.assertEquals("default", decorated.get());
//        Assertions.assertEquals("default", copyDecorated.get());
    }

    @Test
    void copyByConstructor2Decorator() {
        final ProductName command = new ProductName();
        final DefaultValueOnErrorDecorator<String> decorated = new DefaultValueOnErrorDecorator<>(command, "default");
        final DefaultValueOnErrorDecorator<String> decorated2 = new DefaultValueOnErrorDecorator<>(decorated, "default 2");

        final DefaultValueOnErrorDecorator<String> copyDecorated2 = new DefaultValueOnErrorDecorator<>(FAKE, decorated2);

        Assertions.assertNotSame(copyDecorated2.getRoot(), command);
        Assertions.assertNotNull(copyDecorated2.getRoot());
        // TODO for later when command finished to impl
//        Assertions.assertEquals("default 2", decorated2.get());
//        Assertions.assertEquals("default 2", copyDecorated2.get());
    }

    @Test
    void copyByMethod2Decorator() {
        final ProductName command = new ProductName();
        final DefaultValueOnErrorDecorator<String> decorated = new DefaultValueOnErrorDecorator<>(command, "default");
        final DefaultValueOnErrorDecorator<String> decorated2 = new DefaultValueOnErrorDecorator<>(decorated, "default 2");

        final DefaultValueOnErrorDecorator<String> copyDecorated2 = (DefaultValueOnErrorDecorator<String>) decorated2.copy();

        Assertions.assertNotSame(copyDecorated2, decorated);
        Assertions.assertNotSame(copyDecorated2, decorated2);
        Assertions.assertNotSame(copyDecorated2.getRoot(), command);
        Assertions.assertNotNull(copyDecorated2.getRoot());
        // TODO for later when command finished to impl
//        Assertions.assertEquals("default 2", decorated2.get());
//        Assertions.assertEquals("default 2", copyDecorated2.get());
    }
}
