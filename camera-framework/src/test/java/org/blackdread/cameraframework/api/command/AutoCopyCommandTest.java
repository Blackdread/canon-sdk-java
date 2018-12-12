package org.blackdread.cameraframework.api.command;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 */
public class AutoCopyCommandTest {

    private static final Logger log = LoggerFactory.getLogger(AutoCopyCommandTest.class);

    private static final String COMMAND_PACKAGE = AbstractCanonCommand.class.getPackage().getName();

    @Test
    void allCommandHaveCopyConstructor() {
        final Set<? extends Class<?>> commandClasses = getCommandClassesAndNested();

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
        Assertions.assertTrue(commandClasses.size() > 100);
    }

    private static Set<? extends Class<?>> getCommandClassesAndNested() {
        final Set<? extends Class<? super CanonCommand>> commandClasses = getCommandClasses();
        final Set<Class<?>> collect = commandClasses.stream()
            .map(Class::getDeclaredClasses)
            .filter(arrayClasses -> arrayClasses.length > 0)
            .flatMap(Arrays::stream)
            .filter(CanonCommand.class::isAssignableFrom)
            .filter(aClass -> !aClass.isInterface())
            .collect(Collectors.toSet());

        return Stream.concat(commandClasses.stream(), collect.stream())
            .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
            .collect(Collectors.toSet());
    }

    private static Set<? extends Class<? super CanonCommand>> getCommandClasses() {
        try {
            final ImmutableSet<ClassPath.ClassInfo> allClasses = ClassPath.from(AbstractCanonCommand.class.getClassLoader()).getTopLevelClasses(COMMAND_PACKAGE);

            final Set<? extends Class<?>> classes = allClasses.asList().stream()
                .filter(info -> !info.getSimpleName().endsWith("Test"))
//                .filter(info -> info.getSimpleName().endsWith("Command")) do not put as not all commands end with that
                .map(ClassPath.ClassInfo::load)
                .filter(CanonCommand.class::isAssignableFrom)
                .filter(aClass -> !aClass.isInterface())
                .collect(Collectors.toSet());
            return (Set<? extends Class<? super CanonCommand>>) classes;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read native enum classes", e);
        }
    }

}
