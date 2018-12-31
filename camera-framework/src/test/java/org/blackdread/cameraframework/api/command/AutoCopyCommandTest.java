/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.blackdread.cameraframework.api.command.decorator.impl.AbstractDecoratorCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    void copyConstructorThrowsOnNullToCopy() {
        final Set<? extends Class<?>> commandClasses = getCommandClassesAndNestedMinusAbstract();

        final ArrayList<Class<?>> doesNotThrow = new ArrayList<>(30);

        final Object[] params = new Object[1];
        params[0] = null;
        for (final Class<?> commandClass : commandClasses) {
            try {
                final Constructor<?> constructor = commandClass.getConstructor(commandClass);
                try {
                    constructor.newInstance(params);
                    doesNotThrow.add(commandClass);
                } catch (InvocationTargetException e) {
                    if (!(e.getCause() instanceof NullPointerException)) {
                        doesNotThrow.add(commandClass);
                    }
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                Assertions.fail("Error", e);
            }
        }
        if (!doesNotThrow.isEmpty()) {
            log.error("Copy constructor does not throw on null: {}", doesNotThrow);
            Assertions.fail("Copy constructor does not throw on null");
        }
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

    @Test
    void decoratorCopyConstructorThrowsOnNullToCopy() {
        final Set<? extends Class<?>> decoratorCommandClasses = getDecoratorCommandClassesMinusAbstract();

        final ArrayList<Class<?>> doesNotThrow = new ArrayList<>(30);

        final Object[] params = new Object[2];
        params[0] = null;
        params[1] = null;
        for (final Class<?> commandClass : decoratorCommandClasses) {
            try {
                final Constructor<?> constructor = commandClass.getConstructor(AbstractDecoratorCommand.FakeClassArgument.class, commandClass);
                try {
                    constructor.newInstance(params);
                    doesNotThrow.add(commandClass);
                } catch (InvocationTargetException e) {
                    if (!(e.getCause() instanceof NullPointerException)) {
                        doesNotThrow.add(commandClass);
                    }
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                Assertions.fail("Error", e);
            }
        }
        if (!doesNotThrow.isEmpty()) {
            log.error("Copy constructor does not throw on null: {}", doesNotThrow);
            Assertions.fail("Copy constructor does not throw on null");
        }
    }

}
