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
 * @since 1.0.0
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

    /**
     * @param klass class in which to search
     * @param value value to search
     * @param <T>   T type of return value (allows to not cast at the caller)
     * @return Native enum with value and class passed
     * @throws NullPointerException     if value is null
     * @throws IllegalArgumentException if value was not found in class passed
     * @throws ClassCastException       if type of data retrieved is not of what caller expects
     */
    @SuppressWarnings("unchecked")
    //    static NativeEnum<Integer> ofValue(final Class<? extends NativeEnum<Integer>> klass, final Integer value) {
    static <T extends NativeEnum<Integer>> T ofValue(final Class<? extends NativeEnum<Integer>> klass, final Integer value) throws IllegalArgumentException {
        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }
        final Map<Integer, NativeEnum<Integer>> nativeEnumMap = enumsByValue.get(klass);
        final NativeEnum<Integer> nativeEnum = nativeEnumMap.get(value);
        if (nativeEnum == null) {
            log.error("No native enum found for value {} for enum {}", value, klass.getSimpleName());
            throw new IllegalArgumentException("No native enum found for value " + value + " for enum " + klass.getSimpleName());
        }
        return (T) nativeEnum;
    }

    // return a list to make sure that classes that overload equals/hashcode will not use name or other
    static List<NativeEnum> getNativeEnums() throws IllegalStateException {
//        final Set<? extends Class<? extends NativeEnum>> classes = getNativeEnumClasses();
        final Set<? extends Class<? extends NativeEnum>> classes = getNativeEnumClassesManually();

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

    /**
     * Kept for test purposes (might move it to test package).
     * <br>
     * ClassPath class does not support jar in jar -> spring boot jar layout does not work for example
     *
     * @return native enum classes that contains native constant values
     * @throws IllegalStateException On IO error
     * @deprecated better to use the manual alternative as this method does not support Spring boot jar packaging
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    static Set<? extends Class<? extends NativeEnum>> getNativeEnumClasses() throws IllegalStateException {
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

    /**
     * Manual way in opposite to {@link #getNativeEnumClasses()} which does not support all possibilities of packaging.
     * See <a href="https://github.com/Blackdread/canon-sdk-java/issues/4">Issue 4</a>
     * See <a href="https://github.com/spring-projects/spring-boot/issues/16218">Spring boot issue 16218</a>
     *
     * @return native enum classes that contains native constant values
     */
    static Set<? extends Class<? extends NativeEnum>> getNativeEnumClassesManually() {
        return NATIVE_ENUM_CLASSES;
    }

    private static final Set<? extends Class<? extends NativeEnum>> NATIVE_ENUM_CLASSES = ImmutableSet.of(
        EdsPropertyEvent.class,
        EdsSeekOrigin.class,
        EdsBracket.class,
        EdsDataType.class,
        EdsTv.class,
        EdsBatteryQuality.class,
        EdsStateEvent.class,
        EdsColorSpace.class,
        EdsStroboMode.class,
        EdsEvfDriveLens.class,
        EdsTargetImageType.class,
        EdsStorageType.class,
        EdsSaveTo.class,
        EdsWhiteBalance.class,
        EdsImageSource.class,
        EdsObjectEvent.class,
        EdsBatteryLevel2.class,
        EdsTonigEffect.class,
        EdsAFMode.class,
        EdsISOSpeed.class,
        EdsCustomFunction.class,
        EdsEvfHistogramStatus.class,
        EdsCameraStatusCommand.class,
        EdsImageQualityForLegacy.class,
        EdsMeteringMode.class,
        EdsAEMode.class,
        EdsEvfAFMode.class,
        EdsAEModeSelect.class,
        EdsFileAttributes.class,
        EdsShutterButton.class,
        EdsETTL2Mode.class,
        EdsFileCreateDisposition.class,
        EdsPhotoEffect.class,
        EdsImageSize.class,
        EdsdkError.class,
        EdsConstant.class,
        EdsExposureCompensation.class,
        EdsColorMatrix.class,
        EdsTransferOption.class,
        EdsEvfDepthOfFieldPreview.class,
        EdsImageType.class,
        EdsAccess.class,
        EdsPropertyID.class,
        EdsDriveMode.class,
        EdsEvfZoom.class,
        EdsProgressOption.class,
        EdsPictureStyle.class,
        EdsFilterEffect.class,
        EdsCompressQuality.class,
        EdsEvfAf.class,
        EdsAv.class,
        EdsEvfOutputDevice.class,
        EdsImageQuality.class,
        EdsCameraCommand.class
    );

    static {
//        @SuppressWarnings("unchecked") final Set<? extends Class<? extends NativeEnum<Integer>>> nativeEnumClasses = (Set<? extends Class<? extends NativeEnum<Integer>>>) getNativeEnumClasses();
        @SuppressWarnings("unchecked") final Set<? extends Class<? extends NativeEnum<Integer>>> nativeEnumClasses = (Set<? extends Class<? extends NativeEnum<Integer>>>) getNativeEnumClassesManually();
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

    private ConstantUtil() {

    }
}
