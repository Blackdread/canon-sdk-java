package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsdkLibrary;

/**
 * <p>Created on 2019/04/05.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CameraLogicDefaultExtended extends CameraLogicDefault {

    private EdsdkLibrary.EdsCameraListRef.ByReference cameraListRefByRef;
    private NativeLongByReference nativeLongByReference;
    private EdsdkLibrary.EdsCameraRef.ByReference cameraRefByRef;

    public void setCameraListRefByRef(final EdsdkLibrary.EdsCameraListRef.ByReference cameraListRefByRef) {
        this.cameraListRefByRef = cameraListRefByRef;
    }

    public void setNativeLongByReference(final NativeLongByReference nativeLongByReference) {
        this.nativeLongByReference = nativeLongByReference;
    }

    public void setCameraRefByRef(final EdsdkLibrary.EdsCameraRef.ByReference cameraRefByRef) {
        this.cameraRefByRef = cameraRefByRef;
    }

    @Override
    protected EdsdkLibrary.EdsCameraListRef.ByReference buildCameraListRefByRef() {
        if (cameraListRefByRef != null)
            return cameraListRefByRef;
        return super.buildCameraListRefByRef();
    }

    @Override
    protected NativeLongByReference buildNativeLongByReference() {
        if (nativeLongByReference != null)
            return nativeLongByReference;
        return super.buildNativeLongByReference();
    }

    @Override
    protected EdsdkLibrary.EdsCameraRef.ByReference buildCameraRefByRef() {
        if (cameraRefByRef != null)
            return cameraRefByRef;
        return super.buildCameraRefByRef();
    }
}
