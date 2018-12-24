package org.blackdread.cameraframework.api.camera;

/**
 * Camera provider for {@link CameraManager} so camera instance can be customized by user of the framework.
 * <p>Created on 2018/12/24.</p>
 *
 * @author Yoann CAPLAIN
 */
@FunctionalInterface
public interface CameraSupplier {

    /**
     * @param serialNumber serialNumber of the camera
     * @return a camera with the serialNumber set
     */
    CanonCamera createCamera(final String serialNumber);
}
