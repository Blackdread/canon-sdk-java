package org.blackdread.cameraframework.api;

import org.blackdread.cameraframework.api.constant.EdsdkErrors;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class TestShorcutUtil {

    private static final Logger log = LoggerFactory.getLogger(TestShorcutUtil.class);

    public static void initLibrary() {
        final EdsdkErrors error = toEdsdkError(CanonFactory.edsdkLibrary().EdsInitializeSDK());
        Assertions.assertEquals(EdsdkErrors.EDS_ERR_OK, error);
    }

    public static void terminateLibrary() {
        final EdsdkErrors error = toEdsdkError(CanonFactory.edsdkLibrary().EdsTerminateSDK());
        Assertions.assertEquals(EdsdkErrors.EDS_ERR_OK, error);
    }

    public static void reloadLibrary() {
        terminateLibrary();
        initLibrary();
    }

    private TestShorcutUtil() {
    }
}
