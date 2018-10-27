package org.blackdread.cameraframework.api;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.constant.EdsdkErrors;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.api.TestUtil.assertNoError;
import static org.blackdread.cameraframework.api.helper.factory.CanonFactory.edsdkLibrary;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class TestShortcutUtil {

    private static final Logger log = LoggerFactory.getLogger(TestShortcutUtil.class);


    public static void initLibrary() {
        final EdsdkErrors error = toEdsdkError(edsdkLibrary().EdsInitializeSDK());
        Assertions.assertEquals(EdsdkErrors.EDS_ERR_OK, error);
    }

    public static void terminateLibrary() {
        final EdsdkErrors error = toEdsdkError(edsdkLibrary().EdsTerminateSDK());
        Assertions.assertEquals(EdsdkErrors.EDS_ERR_OK, error);
    }

    public static void reloadLibrary() {
        terminateLibrary();
        initLibrary();
    }

    /**
     * Do not forget to release the ref on fail
     *
     * @return Camera ref (not connected yet)
     */
    public static EdsdkLibrary.EdsCameraRef.ByReference getFirstCamera() {
        final EdsdkLibrary.EdsCameraListRef.ByReference cameraListRef = new EdsdkLibrary.EdsCameraListRef.ByReference();
        assertNoError(edsdkLibrary().EdsGetCameraList(cameraListRef));
        try {
            final NativeLongByReference outRef = new NativeLongByReference();
            assertNoError(edsdkLibrary().EdsGetChildCount(cameraListRef.getValue(), outRef));

            final long numCams = outRef.getValue().longValue();
            Assertions.assertTrue(numCams > 0, "No camera connected");

            final EdsdkLibrary.EdsCameraRef.ByReference cameraRef = new EdsdkLibrary.EdsCameraRef.ByReference();

            assertNoError(edsdkLibrary().EdsGetChildAtIndex(cameraListRef.getValue(), new NativeLong(0), cameraRef));
            return cameraRef;
        } finally {
            ReleaseUtil.release(cameraListRef);
        }
    }

    public static void openSession(final EdsdkLibrary.EdsCameraRef.ByReference camera) {
        assertNoError(edsdkLibrary().EdsOpenSession(camera.getValue()));
    }

    public static void closeSession(final EdsdkLibrary.EdsCameraRef.ByReference camera) {
        assertNoError(edsdkLibrary().EdsCloseSession(camera.getValue()));
    }

    private TestShortcutUtil() {
    }
}
