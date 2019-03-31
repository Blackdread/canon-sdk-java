package org.blackdread.camerabinding.jna;

import com.sun.jna.Pointer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
public class PointerTest {

    @Test
    void test() {
        final EdsdkLibrary.EdsVoid eds1 = new EdsdkLibrary.EdsVoid();

        final EdsdkLibrary.EdsVoid eds2 = new EdsdkLibrary.EdsVoid(new Pointer(0));
    }

    @Test
    void test1() {
        final EdsdkLibrary.EdsBaseRef eds1 = new EdsdkLibrary.EdsBaseRef();

        final EdsdkLibrary.EdsBaseRef eds2 = new EdsdkLibrary.EdsBaseRef(new Pointer(0));

        final EdsdkLibrary.EdsBaseRef.ByReference eds3 = new EdsdkLibrary.EdsBaseRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test2() {
        final EdsdkLibrary.EdsCameraListRef eds1 = new EdsdkLibrary.EdsCameraListRef();

        final EdsdkLibrary.EdsCameraListRef eds2 = new EdsdkLibrary.EdsCameraListRef(new Pointer(0));

        final EdsdkLibrary.EdsCameraListRef.ByReference eds3 = new EdsdkLibrary.EdsCameraListRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test3() {
        final EdsdkLibrary.EdsCameraRef eds1 = new EdsdkLibrary.EdsCameraRef();

        final EdsdkLibrary.EdsCameraRef eds2 = new EdsdkLibrary.EdsCameraRef(new Pointer(0));

        final EdsdkLibrary.EdsCameraRef.ByReference eds3 = new EdsdkLibrary.EdsCameraRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test4() {
        final EdsdkLibrary.EdsVolumeRef eds1 = new EdsdkLibrary.EdsVolumeRef();

        final EdsdkLibrary.EdsVolumeRef eds2 = new EdsdkLibrary.EdsVolumeRef(new Pointer(0));

        final EdsdkLibrary.EdsVolumeRef.ByReference eds3 = new EdsdkLibrary.EdsVolumeRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test5() {
        final EdsdkLibrary.EdsDirectoryItemRef eds1 = new EdsdkLibrary.EdsDirectoryItemRef();

        final EdsdkLibrary.EdsDirectoryItemRef eds2 = new EdsdkLibrary.EdsDirectoryItemRef(new Pointer(0));

        final EdsdkLibrary.EdsDirectoryItemRef.ByReference eds3 = new EdsdkLibrary.EdsDirectoryItemRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test6() {
        final EdsdkLibrary.EdsStreamRef eds1 = new EdsdkLibrary.EdsStreamRef();

        final EdsdkLibrary.EdsStreamRef eds2 = new EdsdkLibrary.EdsStreamRef(new Pointer(0));

        final EdsdkLibrary.EdsStreamRef.ByReference eds3 = new EdsdkLibrary.EdsStreamRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test7() {
        final EdsdkLibrary.EdsImageRef eds1 = new EdsdkLibrary.EdsImageRef();

        final EdsdkLibrary.EdsImageRef eds2 = new EdsdkLibrary.EdsImageRef(new Pointer(0));

        final EdsdkLibrary.EdsImageRef.ByReference eds3 = new EdsdkLibrary.EdsImageRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

    @Test
    void test8() {
        final EdsdkLibrary.EdsEvfImageRef eds1 = new EdsdkLibrary.EdsEvfImageRef();

        final EdsdkLibrary.EdsEvfImageRef eds2 = new EdsdkLibrary.EdsEvfImageRef(new Pointer(0));

        final EdsdkLibrary.EdsEvfImageRef.ByReference eds3 = new EdsdkLibrary.EdsEvfImageRef.ByReference();

        Assertions.assertNull(eds3.getValue());
    }

}
