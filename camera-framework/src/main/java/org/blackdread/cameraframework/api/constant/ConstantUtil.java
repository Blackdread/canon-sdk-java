package org.blackdread.cameraframework.api.constant;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
final class ConstantUtil {

    private static final Logger log = LoggerFactory.getLogger(ConstantUtil.class);

    //    private static final String NATIVE_ENUM_PACKAGE = "org.blackdread.cameraframework.api.constant";
    private static final String NATIVE_ENUM_PACKAGE = EdsAccess.class.getPackage().getName();

    /**
     * Allow to find faster an enum by its value when given its class
     */
    private static final Map<Class<? extends NativeEnum<Integer>>, Map<Integer, NativeEnum<Integer>>> enumsByValue = new HashMap<>(50);

//    private static final SetMultimap<Class<? extends NativeEnum<Integer>>, NativeEnum<Integer>> enums = MultimapBuilder.SetMultimapBuilder.hashKeys().hashSetValues().build();

    static {
        final Set<? extends Class<? extends NativeEnum<Integer>>> nativeEnumClasses = (Set<? extends Class<? extends NativeEnum<Integer>>>) getNativeEnumClasses();
//        for (final Class<? extends NativeEnum<Integer>> nativeEnumClass : nativeEnumClasses) {
//            enums.putAll(nativeEnumClass, Arrays.asList(nativeEnumClass.getEnumConstants()));
//        }

        for (final Class<? extends NativeEnum<Integer>> nativeEnumClass : nativeEnumClasses) {
            final HashMap<Integer, NativeEnum<Integer>> valueAndEnum = new HashMap<>();
            for (final NativeEnum<Integer> enumConstant : nativeEnumClass.getEnumConstants()) {
                // Few native enum have duplicate values, will see later what can do for that
                valueAndEnum.put(enumConstant.value(), enumConstant);
            }
            enumsByValue.put(nativeEnumClass, valueAndEnum);
        }
    }

    /**
     * @param klass class in which to search
     * @param value value to search
     * @param <T>   T type of return value (allows to not cast at the caller)
     * @return
     * @throws NullPointerException     if value is null
     * @throws IllegalArgumentException if value was not found in class passed
     */
    //    static NativeEnum<Integer> ofValue(final Class<? extends NativeEnum<Integer>> klass, final Integer value) {
    static <T extends NativeEnum<Integer>> T ofValue(final Class<? extends NativeEnum<Integer>> klass, final Integer value) {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
        final NativeEnum<Integer> nativeEnum = enumsByValue.get(klass).get(value);
        if (nativeEnum == null) {
            log.error("No native enum found for value {} for enum {}", value, klass.getSimpleName());
            throw new IllegalArgumentException("No native enum found for value " + value + " for enum " + klass.getSimpleName());
        }
        return (T) nativeEnum;
    }

    // return a list to make sure that classes that overload equals/hashcode will not use name or other
    static List<NativeEnum> getNativeEnums() {
        final Set<? extends Class<? extends NativeEnum>> classes = getNativeEnumClasses();

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

    static Set<? extends Class<? extends NativeEnum>> getNativeEnumClasses() {
        try {
            final ImmutableSet<ClassPath.ClassInfo> allClasses = ClassPath.from(EdsAccess.class.getClassLoader()).getTopLevelClasses(NATIVE_ENUM_PACKAGE);
            final Set<? extends Class<?>> classes = allClasses.asList().stream()
                .filter(info -> !info.getSimpleName().endsWith("Test"))
                .filter(info -> !info.getSimpleName().equalsIgnoreCase(NativeEnum.class.getSimpleName()))
                .filter(info -> !info.getSimpleName().equalsIgnoreCase(NativeErrorEnum.class.getSimpleName()))
                .map(ClassPath.ClassInfo::load)
                .filter(NativeEnum.class::isAssignableFrom)
                .collect(Collectors.toSet());
            return (Set<? extends Class<? extends NativeEnum>>) classes;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read native enum classes", e);
        }
    }

    private ConstantUtil() {

    }
}
