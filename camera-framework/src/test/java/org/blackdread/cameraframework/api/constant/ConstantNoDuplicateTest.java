package org.blackdread.cameraframework.api.constant;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class ConstantNoDuplicateTest {

    private static final Logger log = LoggerFactory.getLogger(ConstantNoDuplicateTest.class);

    //    private static final String NATIVE_ENUM_PACKAGE = "org.blackdread.cameraframework.api.constant";
    private static final String NATIVE_ENUM_PACKAGE = EdsAccess.class.getPackage().getName();

    @Test
    void noDuplicateEnumName() {
        final Map<String, List<NativeEnum>> groupByNativeEnumName = getNativeEnums().stream()
            .collect(Collectors.groupingBy(NativeEnum::name));
        final Map<String, List<NativeEnum>> duplicateNames = groupByNativeEnumName.values().stream()
            .filter(list -> list.size() > 1)
            .flatMap(Collection::stream)
            .collect(Collectors.groupingBy(NativeEnum::name));
        if (!duplicateNames.isEmpty()) {
            log.error("Duplicate enum name found: {}", duplicateNames);
            Assertions.fail("Duplicate enum name found: " + String.join(",", duplicateNames.keySet()));
        }
    }

    // will be moved if necessary to a public class
    // return a list to make sure that classes that overload equals/hashcode will not use name or other
    private List<NativeEnum> getNativeEnums() {
        final Set<? extends Class<?>> classes = getNativeEnumClasses();

        final List<NativeEnum> nativeEnumList = classes.stream()
            // this suppose that NativeEnum are always enums but if not then will change this code to work with anything
            .map(Class::getEnumConstants)
            .peek(array -> {
                if (array == null)
                    throw new IllegalStateException("Was not a enum");
            })
            .flatMap(Arrays::stream)
            .map(e -> (NativeEnum) e)
            .collect(Collectors.toList());

        final HashSet<NativeEnum> nativeEnums = new HashSet<>(nativeEnumList);

        if (nativeEnumList.size() != nativeEnums.size())
            throw new IllegalStateException("Duplicate values");

        return nativeEnumList;
    }

    // will be moved if necessary to a public class
    private Set<? extends Class<?>> getNativeEnumClasses() {
        try {
            final ImmutableSet<ClassPath.ClassInfo> allClasses = ClassPath.from(EdsAccess.class.getClassLoader()).getTopLevelClasses(NATIVE_ENUM_PACKAGE);
            final Set<? extends Class<?>> classes = allClasses.asList().stream()
                .filter(info -> !info.getSimpleName().endsWith("Test"))
                .filter(info -> !info.getSimpleName().equalsIgnoreCase(NativeEnum.class.getSimpleName()))
                .filter(info -> !info.getSimpleName().equalsIgnoreCase(NativeErrorEnum.class.getSimpleName()))
                .map(ClassPath.ClassInfo::load)
                .filter(NativeEnum.class::isAssignableFrom)
                .collect(Collectors.toSet());
            return classes;
        } catch (IOException e) {
            throw new IllegalStateException("", e);
        }
    }
}
