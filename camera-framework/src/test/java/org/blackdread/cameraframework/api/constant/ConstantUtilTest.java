package org.blackdread.cameraframework.api.constant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * <p>Created on 2019/03/13.</p>
 *
 * @author Yoann CAPLAIN
 */
class ConstantUtilTest {

    private static final Logger log = LoggerFactory.getLogger(ConstantUtilTest.class);

    /*
    @Test
    void ofValue() {
    }

    @Test
    void getNativeEnums() {
    }

    @Test
    void getNativeEnumClasses() {
    }
    //*/

    @Test
    void getNativeEnumClassesManuallyIsImmutable() {
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually = ConstantUtil.getNativeEnumClassesManually();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> nativeEnumClassesManually.add(null));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> nativeEnumClassesManually.removeIf(o -> true));
    }

    @Test
    void getNativeEnumClassesManuallyIsSameInstance() {
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually = ConstantUtil.getNativeEnumClassesManually();
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually2 = ConstantUtil.getNativeEnumClassesManually();

        Assertions.assertSame(nativeEnumClassesManually, nativeEnumClassesManually2);
    }

    @Test
    void getNativeEnumClassesManuallyReturnsSameAsRuntimeClassPath() {
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClasses = ConstantUtil.getNativeEnumClasses();
        final Set<? extends Class<? extends NativeEnum>> nativeEnumClassesManually = ConstantUtil.getNativeEnumClassesManually();

        Assertions.assertEquals(nativeEnumClasses.size(), nativeEnumClassesManually.size());
        Assertions.assertEquals(nativeEnumClasses, nativeEnumClassesManually);
    }
}
