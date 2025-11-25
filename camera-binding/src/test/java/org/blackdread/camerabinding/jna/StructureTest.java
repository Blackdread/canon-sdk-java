package org.blackdread.camerabinding.jna;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class StructureTest {

    @Test
    void test1() {
        final EdsCapacity eds1 = new EdsCapacity();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsCapacity eds2 = new EdsCapacity(new Pointer(0));

        final EdsCapacity eds3 = new EdsCapacity(new NativeLong(0), new NativeLong(0), 0);

        new EdsCapacity.ByReference();
        new EdsCapacity.ByValue();
    }

    @Test
    void test2() {
        final EdsDeviceInfo eds1 = new EdsDeviceInfo();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsDeviceInfo eds2 = new EdsDeviceInfo(new Pointer(0));

        final EdsDeviceInfo eds3 = new EdsDeviceInfo(new byte[256], new byte[256], new NativeLong(0), new NativeLong(0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsDeviceInfo(new byte[1], new byte[256], new NativeLong(0), new NativeLong(0)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsDeviceInfo(new byte[256], new byte[1], new NativeLong(0), new NativeLong(0)));

        new EdsDeviceInfo.ByReference();
        new EdsDeviceInfo.ByValue();
    }

    @Test
    void test3() {
        final EdsDirectoryItemInfo eds1 = new EdsDirectoryItemInfo();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsDirectoryItemInfo eds2 = new EdsDirectoryItemInfo(new Pointer(0));

        final EdsDirectoryItemInfo eds3 = new EdsDirectoryItemInfo(0L, 1, new NativeLong(0), new NativeLong(0), new byte[256], new NativeLong(0), new NativeLong(0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsDirectoryItemInfo(0L, 1, new NativeLong(0), new NativeLong(0), new byte[1], new NativeLong(0), new NativeLong(0)));

        new EdsDirectoryItemInfo.ByReference();
        new EdsDirectoryItemInfo.ByValue();
    }

    @Test
    void test4() {
        final EdsFocusInfo eds1 = new EdsFocusInfo();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsFocusInfo eds2 = new EdsFocusInfo(new Pointer(0));

        final EdsFocusInfo eds3 = new EdsFocusInfo(new EdsRect(), new NativeLong(0), new EdsFrameDesc[1053], new NativeLong(0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsFocusInfo(new EdsRect(), new NativeLong(0), new EdsFrameDesc[1], new NativeLong(0)));

        new EdsFocusInfo.ByReference();
        new EdsFocusInfo.ByValue();
    }

    @Test
    void test5() {
        final EdsFrameDesc eds1 = new EdsFrameDesc();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsFrameDesc eds2 = new EdsFrameDesc(new Pointer(0));

        final EdsFrameDesc eds3 = new EdsFrameDesc(new NativeLong(0), new NativeLong(0),
            new NativeLong(0), new EdsRect(), new NativeLong(0));

        new EdsFrameDesc.ByReference();
        new EdsFrameDesc.ByValue();
    }

    @Test
    void test6() {
        final EdsFramePoint eds1 = new EdsFramePoint();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsFramePoint eds2 = new EdsFramePoint(new Pointer(0));

        final EdsFramePoint eds3 = new EdsFramePoint(new NativeLong(0), new NativeLong(0));

        new EdsFramePoint.ByReference();
        new EdsFramePoint.ByValue();
    }

    @Test
    void test7() {
        final EdsImageInfo eds1 = new EdsImageInfo();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsImageInfo eds2 = new EdsImageInfo(new Pointer(0));

        final EdsImageInfo eds3 = new EdsImageInfo(new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new EdsRect(), new NativeLong(0), new NativeLong(0));

        new EdsImageInfo.ByReference();
        new EdsImageInfo.ByValue();
    }

    @Test
    void test8() {
        final EdsIStream eds1 = new EdsIStream();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsIStream eds2 = new EdsIStream(new Pointer(0));

        final EdsIStream eds3 = new EdsIStream(new Pointer(0), null, null, null, null, null);

        new EdsIStream.ByReference();
        new EdsIStream.ByValue();
    }

    @Test
    void test9() {
        final EdsPictureStyleDesc eds1 = new EdsPictureStyleDesc();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsPictureStyleDesc eds2 = new EdsPictureStyleDesc(new Pointer(0));

        final EdsPictureStyleDesc eds3 = new EdsPictureStyleDesc(new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0));

        new EdsPictureStyleDesc.ByReference();
        new EdsPictureStyleDesc.ByValue();
    }

    @Test
    void test10() {
        final EdsPoint eds1 = new EdsPoint();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsPoint eds2 = new EdsPoint(new Pointer(0));

        final EdsPoint eds3 = new EdsPoint(new NativeLong(0), new NativeLong(0));

        new EdsPoint.ByReference();
        new EdsPoint.ByValue();
    }

    @Test
    void test11() {
        final EdsPropertyDesc eds1 = new EdsPropertyDesc();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsPropertyDesc eds2 = new EdsPropertyDesc(new Pointer(0));

        final EdsPropertyDesc eds3 = new EdsPropertyDesc(new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong[128]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsPropertyDesc(new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong[1]));

        new EdsPropertyDesc.ByReference();
        new EdsPropertyDesc.ByValue();
    }

    @Test
    void test12() {
        final EdsRational eds1 = new EdsRational();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsRational eds2 = new EdsRational(new Pointer(0));

        final EdsRational eds3 = new EdsRational(new NativeLong(0), new NativeLong(0));

        new EdsRational.ByReference();
        new EdsRational.ByValue();
    }

    @Test
    void test13() {
        final EdsRect eds1 = new EdsRect();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsRect eds2 = new EdsRect(new Pointer(0));

        final EdsRect eds3 = new EdsRect(new EdsPoint(), new EdsSize());

        new EdsRect.ByReference();
        new EdsRect.ByValue();
    }

    @Test
    void test14() {
        final EdsSaveImageSetting eds1 = new EdsSaveImageSetting();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsSaveImageSetting eds2 = new EdsSaveImageSetting(new Pointer(0));

        final EdsSaveImageSetting eds3 = new EdsSaveImageSetting(new NativeLong(0), null, new NativeLong(0));

        new EdsSaveImageSetting.ByReference();
        new EdsSaveImageSetting.ByValue();
    }

    @Test
    void test15() {
        final EdsSize eds1 = new EdsSize();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsSize eds2 = new EdsSize(new Pointer(0));

        final EdsSize eds3 = new EdsSize(new NativeLong(0), new NativeLong(0));

        new EdsSize.ByReference();
        new EdsSize.ByValue();
    }

    @Test
    void test16() {
        final EdsTime eds1 = new EdsTime();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsTime eds2 = new EdsTime(new Pointer(0));

        final EdsTime eds3 = new EdsTime(new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0), new NativeLong(0));

        new EdsTime.ByReference();
        new EdsTime.ByValue();
    }

    @Test
    void test17() {
        final EdsUsersetData eds1 = new EdsUsersetData();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsUsersetData eds2 = new EdsUsersetData(new Pointer(0));

        final EdsUsersetData eds3 = new EdsUsersetData(new NativeLong(0), new NativeLong(0), new byte[32], new byte[1]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsUsersetData(new NativeLong(0), new NativeLong(0), new byte[1], new byte[1]));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsUsersetData(new NativeLong(0), new NativeLong(0), new byte[32], new byte[0]));

        new EdsUsersetData.ByReference();
        new EdsUsersetData.ByValue();
    }

    @Test
    void test18() {
        final EdsVolumeInfo eds1 = new EdsVolumeInfo();
        final List<String> fieldOrder = eds1.getFieldOrder();
        Assertions.assertNotNull(fieldOrder);
        Assertions.assertFalse(fieldOrder.isEmpty());

        final EdsVolumeInfo eds2 = new EdsVolumeInfo(new Pointer(0));

        final EdsVolumeInfo eds3 = new EdsVolumeInfo(new NativeLong(0), 1, 0L, 0L, new byte[256]);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EdsVolumeInfo(new NativeLong(0), 1, 0L, 0L, new byte[1]));

        new EdsVolumeInfo.ByReference();
        new EdsVolumeInfo.ByValue();
    }

}
