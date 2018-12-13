package org.blackdread.cameraframework.api;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.blackdread.cameraframework.api.command.AbstractCanonCommand;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.blackdread.cameraframework.api.command.decorator.impl.AbstractDecoratorCommand;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Created on 2018/12/13.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class CommandClassUtil {

    private static final String COMMAND_PACKAGE = AbstractCanonCommand.class.getPackage().getName();
    private static final String DECORATOR_COMMAND_PACKAGE = AbstractDecoratorCommand.class.getPackage().getName();

    private static final Set<? extends Class<? extends CanonCommand>> commandClasses;
    private static final Set<? extends Class<? extends CanonCommand>> commandClassesAndNested;
    private static final Set<? extends Class<? extends CanonCommand>> commandClassesAndNestedMinusAbstract;

    private static final Set<? extends Class<? extends DecoratorCommand>> decoratorCommandClasses;
    private static final Set<? extends Class<? extends DecoratorCommand>> decoratorCommandClassesMinusAbstract;

    static {
        commandClasses = getCommandClassesInternal();
        commandClassesAndNested = (Set<? extends Class<? extends CanonCommand>>) getCommandClassesAndNestedInternal();
        commandClassesAndNestedMinusAbstract = getCommandClassesAndNestedMinusAbstractInternal();
        decoratorCommandClasses = getDecoratorCommandClassesInternal();
        decoratorCommandClassesMinusAbstract = getDecoratorCommandClassesAndNestedMinusAbstractInternal();
    }


    public static Set<? extends Class<? extends CanonCommand>> getCommandClasses() {
        return commandClasses;
    }

    public static Set<? extends Class<? extends CanonCommand>> getCommandClassesAndNested() {
        return commandClassesAndNested;
    }

    public static Set<? extends Class<? extends CanonCommand>> getCommandClassesAndNestedMinusAbstract() {
        return commandClassesAndNestedMinusAbstract;
    }

    public static Set<? extends Class<? extends DecoratorCommand>> getDecoratorCommandClasses() {
        return decoratorCommandClasses;
    }

    public static Set<? extends Class<? extends DecoratorCommand>> getDecoratorCommandClassesMinusAbstract() {
        return decoratorCommandClassesMinusAbstract;
    }

    private static Set<? extends Class<? extends CanonCommand>> getCommandClassesInternal() {
        try {
            final ImmutableSet<ClassPath.ClassInfo> allClasses = ClassPath.from(AbstractCanonCommand.class.getClassLoader()).getTopLevelClasses(COMMAND_PACKAGE);

            final Set<? extends Class<?>> classes = allClasses.asList().stream()
                .filter(info -> !info.getSimpleName().endsWith("Test"))
//                .filter(info -> info.getSimpleName().endsWith("Command")) do not put as not all commands end with that
                .map(ClassPath.ClassInfo::load)
                .filter(CanonCommand.class::isAssignableFrom)
                .filter(aClass -> !aClass.isInterface())
                .collect(ImmutableSet.toImmutableSet());
            return (Set<? extends Class<? extends CanonCommand>>) classes;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read command classes", e);
        }
    }

    private static Set<? extends Class<?>> getCommandClassesAndNestedInternal() {
        final Set<? extends Class<? extends CanonCommand>> commandClasses = getCommandClasses();
        final Set<Class<?>> collect = commandClasses.stream()
            .map(Class::getDeclaredClasses)
            .filter(arrayClasses -> arrayClasses.length > 0)
            .flatMap(Arrays::stream)
            .filter(CanonCommand.class::isAssignableFrom)
            .filter(aClass -> !aClass.isInterface())
            .collect(Collectors.toSet());

        return Stream.concat(commandClasses.stream(), collect.stream())
            .collect(ImmutableSet.toImmutableSet());
    }

    private static Set<? extends Class<? extends CanonCommand>> getCommandClassesAndNestedMinusAbstractInternal() {
        return getCommandClassesAndNested().stream()
            .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
            .collect(ImmutableSet.toImmutableSet());
    }

    private static Set<? extends Class<? extends DecoratorCommand>> getDecoratorCommandClassesInternal() {
        try {
            final ImmutableSet<ClassPath.ClassInfo> allClasses = ClassPath.from(AbstractDecoratorCommand.class.getClassLoader()).getTopLevelClasses(DECORATOR_COMMAND_PACKAGE);

            final Set<? extends Class<?>> classes = allClasses.asList().stream()
                .filter(info -> !info.getSimpleName().endsWith("Test"))
//                .filter(info -> info.getSimpleName().endsWith("Command")) do not put as not all commands end with that
                .map(ClassPath.ClassInfo::load)
                .filter(DecoratorCommand.class::isAssignableFrom)
                .filter(aClass -> !aClass.isInterface())
                .collect(ImmutableSet.toImmutableSet());
            return (Set<? extends Class<? extends DecoratorCommand>>) classes;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read decorator command classes", e);
        }
    }

    private static Set<? extends Class<? extends DecoratorCommand>> getDecoratorCommandClassesAndNestedMinusAbstractInternal() {
        return getDecoratorCommandClasses().stream()
            .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
            .collect(ImmutableSet.toImmutableSet());
    }

    private CommandClassUtil() {
    }
}
