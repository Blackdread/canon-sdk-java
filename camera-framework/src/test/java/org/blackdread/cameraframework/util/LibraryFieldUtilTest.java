package org.blackdread.cameraframework.util;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsFileAttributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class LibraryFieldUtilTest {

    @Test
    void getNativeIntValue() {
        final int nativeIntValue = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFileAttributes.class,
            EdsFileAttributes.kEdsFileAttribute_Archive.name());
        Assertions.assertTrue(nativeIntValue > 0);
    }

    @Test
    void getNativeIntValueThrowsOnNotFound() {
        Assertions.assertThrows(IllegalStateException.class, () -> LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.EdsFileAttributes.class, "doNoExist"));
    }

    @Test
    void countClassField() {
    }
}
