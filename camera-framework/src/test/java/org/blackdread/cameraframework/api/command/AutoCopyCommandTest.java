package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.blackdread.cameraframework.api.command.decorator.impl.AbstractDecoratorCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Set;

import static org.blackdread.cameraframework.api.CommandClassUtil.getCommandClassesAndNestedMinusAbstract;
import static org.blackdread.cameraframework.api.CommandClassUtil.getDecoratorCommandClassesMinusAbstract;

/**
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 */
public class AutoCopyCommandTest {

    private static final Logger log = LoggerFactory.getLogger(AutoCopyCommandTest.class);


    @Test
    void allCommandHaveCopyConstructor() {
        final Set<? extends Class<?>> commandClasses = getCommandClassesAndNestedMinusAbstract();

        final ArrayList<Class<?>> missingCopyConstructorClasses = new ArrayList<>(30);

        for (final Class<?> commandClass : commandClasses) {
            try {
                final Constructor<?> constructor = commandClass.getConstructor(commandClass);
            } catch (NoSuchMethodException e) {
                missingCopyConstructorClasses.add(commandClass);
            }
        }
        if (!missingCopyConstructorClasses.isEmpty()) {
            log.error("Classes are missing copy constructor: {}", missingCopyConstructorClasses);
            Assertions.fail("Classes are missing copy constructor");
        }
        log.info("{} classes tested for copy constructor presence", commandClasses.size());
        Assertions.assertTrue(commandClasses.size() > 120);
    }

    @Test
    void allDecoratorCommandHaveCopyConstructor() {
        final Set<? extends Class<? extends DecoratorCommand>> decoratorCommandClasses = getDecoratorCommandClassesMinusAbstract();

        final ArrayList<Class<?>> missingCopyConstructorClasses = new ArrayList<>(30);

        for (final Class<?> commandClass : decoratorCommandClasses) {
            try {
                final Constructor<?> constructor = commandClass.getConstructor(AbstractDecoratorCommand.FakeClassArgument.class, commandClass);
            } catch (NoSuchMethodException e) {
                missingCopyConstructorClasses.add(commandClass);
            }
        }
        if (!missingCopyConstructorClasses.isEmpty()) {
            log.error("Classes are missing copy constructor: {}", missingCopyConstructorClasses);
            Assertions.fail("Classes are missing copy constructor");
        }
        log.info("{} classes tested for copy constructor presence", decoratorCommandClasses.size());
        Assertions.assertTrue(decoratorCommandClasses.size() > 1);
    }

}
