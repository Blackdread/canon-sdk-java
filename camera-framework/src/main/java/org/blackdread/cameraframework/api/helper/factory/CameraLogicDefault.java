/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.blackdread.camerabinding.jna.EdsCapacity;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.CloseSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.constant.EdsCustomFunction;
import org.blackdread.cameraframework.api.constant.EdsPropertyID;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CameraLogicDefault implements CameraLogic {

    private static final Logger log = LoggerFactory.getLogger(CameraLogicDefault.class);

    protected CameraLogicDefault() {
    }

    @Override
    public void setCapacity(final EdsCameraRef camera, final int capacity, final int bytesPerSector) {
        final EdsCapacity.ByValue edsCapacity = new EdsCapacity.ByValue();
        edsCapacity.bytesPerSector = new NativeLong(bytesPerSector);
        edsCapacity.numberOfFreeClusters = new NativeLong(capacity / edsCapacity.bytesPerSector.intValue());
        //  To make sure that flag is reset at each set capacity, so can keep shooting images
        edsCapacity.reset = 1;
        final EdsdkError error = toEdsdkError(CanonFactory.edsdkLibrary().EdsSetCapacity(camera, edsCapacity));
        if (error != EdsdkError.EDS_ERR_OK)
            throw error.getException();
    }

    @Override
    public boolean isMirrorLockupEnabled(final EdsCameraRef camera) {
        try {
            final Long result = CanonFactory.propertyGetLogic().getPropertyData(camera, EdsPropertyID.kEdsPropID_CFn, EdsCustomFunction.kEdsCustomFunction_MirrorLockup.value());
            return 1L == result;
        } catch (final IllegalArgumentException e) {
            log.warn("Could not check if mirror lockup enabled", e);
        }
        return false;
    }

    /**
     * Provided only to be able to test methods
     *
     * @return camera list by ref
     */
    protected EdsdkLibrary.EdsCameraListRef.ByReference buildCameraListRefByRef() {
        return new EdsdkLibrary.EdsCameraListRef.ByReference();
    }

    /**
     * Provided only to be able to test methods
     *
     * @return native long by ref
     */
    protected NativeLongByReference buildNativeLongByReference() {
        return new NativeLongByReference();
    }

    /**
     * Provided only to be able to test methods
     *
     * @return camera ref
     */
    protected EdsdkLibrary.EdsCameraRef.ByReference buildCameraRefByRef() {
        return new EdsdkLibrary.EdsCameraRef.ByReference();
    }

    @Override
    public EdsCameraRef openSession(final OpenSessionOption option) {

        EdsdkError edsdkError;

        if (option.isOpenSessionOnly()) {
            final EdsCameraRef cameraRef = option.getCameraRef()
                .orElseThrow(() -> new IllegalStateException("CameraRef is null while trying to only open session"));
            edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsOpenSession(cameraRef));
            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }
            return cameraRef;
        }

        final EdsdkLibrary.EdsCameraListRef.ByReference listRef = buildCameraListRefByRef();

        try {
            edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetCameraList(listRef));
            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }

            final NativeLongByReference outRef = buildNativeLongByReference();
            edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetChildCount(listRef.getValue(), outRef));
            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }

            final long numCams = outRef.getValue().longValue();
            if (numCams <= 0) {
                throw EdsdkError.EDS_ERR_DEVICE_NOT_FOUND.getException();
            }

            if (option.getCameraByIndex().isPresent() && option.getCameraByIndex().get() >= numCams) {
                throw EdsdkError.EDS_ERR_DEVICE_NOT_FOUND.getException();
            }

            for (int i = 0; i < numCams; i++) {
                if (option.getCameraByIndex().isPresent() && option.getCameraByIndex().get() != i) {
                    continue;
                }

                final EdsdkLibrary.EdsCameraRef.ByReference cameraRef = buildCameraRefByRef();

                edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetChildAtIndex(listRef.getValue(), new NativeLong(i), cameraRef));
                if (edsdkError != EdsdkError.EDS_ERR_OK) {
                    throw edsdkError.getException();
                }

                boolean sessionAlreadyOpen = false;
                String bodyIDEx;

                final EdsCameraRef cameraRefValue = cameraRef.getValue();

                try {
                    bodyIDEx = CanonFactory.propertyGetShortcutLogic().getBodyIDEx(cameraRefValue);
                    sessionAlreadyOpen = true;
                } catch (EdsdkErrorException e) {
                    // From tests, should throw EdsdkCommDisconnectedErrorException if not connected
                    log.info("Error ignored while testing if camera is connected to open session", e);
                }

                if (!sessionAlreadyOpen) {
                    edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsOpenSession(cameraRefValue));
                    if (edsdkError != EdsdkError.EDS_ERR_OK) {
                        ReleaseUtil.release(cameraRef);
                        throw edsdkError.getException();
                    }
                }

                try {
                    bodyIDEx = CanonFactory.propertyGetShortcutLogic().getBodyIDEx(cameraRefValue);
                } catch (EdsdkErrorException e) {
                    ReleaseUtil.release(cameraRef);
                    throw e;
                }

                if (bodyIDEx == null) {
                    log.warn("BodyIDEx returned was null");
                    if (!sessionAlreadyOpen) {
                        CanonFactory.edsdkLibrary().EdsCloseSession(cameraRefValue);
                    }
                    ReleaseUtil.release(cameraRef);
                    // TODO we can throw or continue next iteration but no reason for BodyIDEx to be null...
                    throw new IllegalStateException("BodyIDEx is null");
                } else {
                    if (option.getCameraBySerialNumber().isPresent() && option.getCameraBySerialNumber().get().equalsIgnoreCase(bodyIDEx)) {
                        // serial number match what we are looking for
                        setCameraRefToCamera(cameraRefValue, option);
                        registerEvents(cameraRefValue, option);
                        return cameraRefValue;
                    }
                    if (option.getCameraByIndex().isPresent()) {
                        // it means we are looking by index
                        setCameraRefToCamera(cameraRefValue, option);
                        registerEvents(cameraRefValue, option);
                        return cameraRefValue;
                    }

                    // No match then next iteration of loop
                    if (!sessionAlreadyOpen) {
                        CanonFactory.edsdkLibrary().EdsCloseSession(cameraRefValue);
                    }
                    ReleaseUtil.release(cameraRef);
                }
            }

            throw new IllegalStateException("No camera found matching index or serial number");
        } finally {
            ReleaseUtil.release(listRef);
        }
    }

    protected void setCameraRefToCamera(final EdsCameraRef cameraRef, final OpenSessionOption option) {
        option.getCamera().ifPresent(camera -> camera.setCameraRef(cameraRef));
    }

    protected void registerEvents(final EdsCameraRef cameraRef, final OpenSessionOption option) {
        if (option.isRegisterObjectEvent()) {
            CanonFactory.cameraObjectEventLogic().registerCameraObjectEvent(cameraRef);
        }
        if (option.isRegisterPropertyEvent()) {
            CanonFactory.cameraPropertyEventLogic().registerCameraPropertyEvent(cameraRef);
        }
        if (option.isRegisterStateEvent()) {
            CanonFactory.cameraStateEventLogic().registerCameraStateEvent(cameraRef);
        }
    }

    @Override
    public void closeSession(final CloseSessionOption option) {
        final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsCloseSession(option.getCameraRef()));
        if (edsdkError != EdsdkError.EDS_ERR_OK) {
            throw edsdkError.getException();
        }

        if (option.isReleaseCameraRef()) {
            ReleaseUtil.release(option.getCameraRef());
        }

        option.getCamera().ifPresent(camera -> camera.setCameraRef(null));
    }
}
