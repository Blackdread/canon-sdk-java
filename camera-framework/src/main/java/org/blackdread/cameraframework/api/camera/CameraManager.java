/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
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
package org.blackdread.cameraframework.api.camera;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.AbstractCanonCommand;
import org.blackdread.cameraframework.api.command.IsConnectedCommand;
import org.blackdread.cameraframework.api.command.OpenSessionCommand;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.exception.error.EdsdkErrorException;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * Simple camera manager to simplify camera cable connection and disconnection.
 * <br>
 * If manager is used then user should usually not require to openSession himself neither create cameras.
 * <br>
 * It is optional to use and it will actually initialize itself only once, when one of the public method has been called.
 * <br>
 * <b>The framework must be initialized before this manager is used</b>, via {@link org.blackdread.cameraframework.api.helper.initialisation.FrameworkInitialisation} or manually.
 * <p>Created on 2018/12/19.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.1.0
 */
public final class CameraManager {

    private static final Logger log = LoggerFactory.getLogger(CameraManager.class);

    private static final Duration CONNECT_FAST_TIMEOUT = Duration.ofSeconds(10);

    private static final Map<String, CanonCamera> camerasBySerialNumberMap = new ConcurrentHashMap<>(10);

    private static final AtomicBoolean initOnce = new AtomicBoolean(false);

    /**
     * Refresh cannot be multi-threaded
     */
    private static final Object refreshLock = new Object();

    private static final CameraAddedListener cameraAddedListener = event -> handleCameraAddedListener();

    private static final AtomicReference<CameraSupplier> cameraSupplier = new AtomicReference<>(CanonCamera::new);

    private static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setNameFormat("camera-refresh-%d")
        .setDaemon(true)
        .build();

    private static volatile Thread refreshThread;

    private static volatile int refreshIntervalSeconds = 30;

    private static volatile boolean stopRun = false;

    private static void init() {
        final boolean previousValue = initOnce.getAndSet(true);
        if (!previousValue) {
            CanonFactory.cameraAddedEventLogic().addCameraAddedListener(cameraAddedListener);
            try {
                refreshCameras();
            } catch (ExecutionException e) {
                // can happen if library not initialized
                throw new IllegalStateException(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            } finally {
                setRefreshInterval(30);
            }
        }
    }

    /**
     * Useful if want to customize camera instances created by this manager
     *
     * @param supplier supplier to use
     */
    public static void setCameraSupplier(final CameraSupplier supplier) {
        CameraManager.cameraSupplier.set(Objects.requireNonNull(supplier));
    }

    /**
     * 0 will stop the refresh thread.
     * <br>
     * Another value than 0 will restart the refresh thread if was previously stopped, otherwise simply change the interval.
     *
     * @param intervalSeconds time between refresh in second
     */
    public static void setRefreshInterval(final int intervalSeconds) {
        if (intervalSeconds < 0) {
            throw new IllegalArgumentException("Interval must be >= 0");
        }
        synchronized (threadFactory) {
            if (intervalSeconds == 0) {
                stopRun = true;
                if (refreshThread != null) {
                    refreshThread.interrupt();
                }
                refreshThread = null;
            } else {
                refreshIntervalSeconds = intervalSeconds;
                if (refreshThread == null) {
                    stopRun = false;
                    refreshThread = threadFactory.newThread(CameraManager::refreshRunner);
                    refreshThread.start();
                }
            }
        }
    }

    /**
     * Return a camera that has been connected at least once to the pc with this serial number, empty otherwise.
     *
     * @param serialNumber camera serial number to look for
     * @return camera found with serial number (not necessarily still connected to pc)
     */
    public static Optional<CanonCamera> getCameraBySerialNumber(final String serialNumber) {
        init();
        return Optional.ofNullable(camerasBySerialNumberMap.get(serialNumber));
    }

    /**
     * @return A snapshot of current cameras that have been connected to pc (not necessarily still connected to pc)
     */
    public static List<CanonCamera> getAllCameras() {
        init();
        return ImmutableList.copyOf(camerasBySerialNumberMap.values());
    }

    /**
     * @return A snapshot of current camera serial number that have been connected to pc (not necessarily still connected to pc)
     */
    public static List<String> getAllSerialNumbers() {
        init();
        return ImmutableList.copyOf(camerasBySerialNumberMap.keySet());
    }

    /**
     * Do not call this too often as it sends commands and timeouts may be long
     *
     * @return current cameras that are connected to pc
     */
    public static List<CanonCamera> getAllConnected() {
        final List<CanonCamera> snapshot = getAllCameras();
        return snapshot.stream()
            .filter(camera -> {
//                try {
//                    return camera.isConnectedAsync().get();
//                } catch (InterruptedException | ExecutionException ignored) {
//                    return false;
//                }
                try {
                    return reconnectIfNotConnected(camera);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            })
            .collect(Collectors.toList());
    }

    private static void handleCameraAddedListener() {
        try {
            refreshCameras();
        } catch (ExecutionException | InterruptedException e) {
            log.warn("Exception while refresh camera from added event", e);
        }
    }

    /**
     * Cannot be called from dispatcher thread
     *
     * @param camera camera to reconnect if is not
     * @return true if camera is connected (was already or has been reconnected), false otherwise
     * @throws InterruptedException if interrupted while waiting to check status or reconnect
     */
    private static boolean reconnectIfNotConnected(final CanonCamera camera) throws InterruptedException {
        final IsConnectedCommand connected = camera.isConnectedAsync();
        try {
            if (!connected.get()) {
                return reconnect(camera);
            }
            return true;
        } catch (ExecutionException ignored) {
        }
        return false;
    }

    /**
     * Cannot be called from dispatcher thread
     *
     * @param camera camera to reconnect
     * @return true if camera is connected, false otherwise
     * @throws InterruptedException if interrupted while waiting to check status or reconnect
     */
    private static boolean reconnect(final CanonCamera camera) throws InterruptedException {
        final String serialNumber = camera.getSerialNumber()
            .orElseThrow(() -> new IllegalStateException("Cameras managed always have the serial number set"));
        final OpenSessionOption openSessionOption = new OpenSessionOptionBuilder()
            .setCameraBySerialNumber(serialNumber)
            .setRegisterObjectEvent(true)
            .setRegisterPropertyEvent(true)
            .setRegisterStateEvent(true)
            .build();
        final OpenSessionCommand openSessionCommand = new OpenSessionCommand(openSessionOption);
        openSessionCommand.setTimeout(CONNECT_FAST_TIMEOUT);
        CanonFactory.commandDispatcher().scheduleCommand(openSessionCommand);
        try {
            final EdsdkLibrary.EdsCameraRef cameraRef = openSessionCommand.get();
            final Optional<EdsdkLibrary.EdsCameraRef> cameraCameraRef = camera.getCameraRef();
            // we replace if ref has changed
            camera.setCameraRef(cameraRef);
            cameraCameraRef.ifPresent(ref -> {
                if (ref != cameraRef) {
                    log.warn("On reconnect, cameraRef was different than previous one for camera: {}", serialNumber);
                }
                // The open session command has returned a ref with an incremented count of 1 so we always need to decrement once as well (not an open only command)
                ReleaseUtil.release(ref);
            });
            return true;
        } catch (ExecutionException ignored) {
            log.warn("Failed to reopen session for camera {}", serialNumber);
        }
        return false;
    }

    private static void refreshCameras() throws ExecutionException, InterruptedException {
        final FindAndConnectCameraCommand command = new FindAndConnectCameraCommand();
        if (CanonFactory.commandDispatcher().isDispatcherThread()) {
            command.run();
        } else {
            CanonFactory.commandDispatcher().scheduleCommand(command);
            command.get();
        }
    }

    private static void refreshRunner() {
        log.info("Command refresh thread started");
        try {
            // if stopped but restarted right after, we might end up with multiple refresh thread :)
            while (!stopRun) {
                try {
                    refreshCameras();
                } catch (Exception e) {
                    log.warn("Ignored exception in refresh runner", e);
                }
                try {
                    Thread.sleep(refreshIntervalSeconds * 1000L + 1L);
                } catch (InterruptedException ignored) {
                    // if interrupted then stopRun should be true
                }
            }
        } finally {
            log.warn("Command refresh thread ended");
        }
    }

    private CameraManager() {
    }

    private static class FindAndConnectCameraCommand extends AbstractCanonCommand<Void> {

        @Override
        protected Void runInternal() {
            synchronized (refreshLock) {
                final EdsdkLibrary.EdsCameraListRef.ByReference listRef = new EdsdkLibrary.EdsCameraListRef.ByReference();

                EdsdkError edsdkError;
                try {
                    edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetCameraList(listRef));
                    if (edsdkError != EdsdkError.EDS_ERR_OK) {
                        throw edsdkError.getException();
                    }

                    final NativeLongByReference outRef = new NativeLongByReference();
                    edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetChildCount(listRef.getValue(), outRef));
                    if (edsdkError != EdsdkError.EDS_ERR_OK) {
                        throw edsdkError.getException();
                    }

                    final long numCams = outRef.getValue().longValue();
                    if (numCams <= 0) {
                        log.debug("No camera detected");
                        return null;
                    }

                    for (int i = 0; i < numCams; i++) {
                        final EdsdkLibrary.EdsCameraRef.ByReference cameraRef = new EdsdkLibrary.EdsCameraRef.ByReference();

                        edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetChildAtIndex(listRef.getValue(), new NativeLong(i), cameraRef));
                        if (edsdkError != EdsdkError.EDS_ERR_OK) {
                            throw edsdkError.getException();
                        }

                        boolean sessionAlreadyOpen = false;
                        String bodyIDEx;
                        try {
                            bodyIDEx = CanonFactory.propertyGetShortcutLogic().getBodyIDEx(cameraRef.getValue());
                            sessionAlreadyOpen = true;
                        } catch (EdsdkErrorException e) {
                            // From tests, should throw EdsdkCommDisconnectedErrorException if not connected
                            log.info("Error ignored while testing if camera is connected to open session", e);
                            bodyIDEx = "";
                        }

                        if (sessionAlreadyOpen) {
                            // It means that the camera may be managed by this class already (or not), that it was already refreshed or opened via another way then the manager will skip as to not impact other logic
                            log.trace("Camera session already open ({}), we skip", bodyIDEx);
                            ReleaseUtil.release(cameraRef);
                            continue;
                        }

                        edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsOpenSession(cameraRef.getValue()));
                        if (edsdkError != EdsdkError.EDS_ERR_OK) {
                            ReleaseUtil.release(cameraRef);
                            log.warn("Failed to open session: {}", edsdkError);
                            continue;
                        }

                        try {
                            bodyIDEx = CanonFactory.propertyGetShortcutLogic().getBodyIDEx(cameraRef.getValue());
                        } catch (EdsdkErrorException e) {
                            ReleaseUtil.release(cameraRef);
                            log.warn("Failed to get serial number: {}", e);
                            continue;
                        }

                        if (StringUtils.isBlank(bodyIDEx)) {
                            log.error("BodyIDEx returned is blank: {}", bodyIDEx);
                            CanonFactory.edsdkLibrary().EdsCloseSession(cameraRef.getValue());
                            ReleaseUtil.release(cameraRef);
                            // we continue next iteration but no reason for BodyIDEx to be null...
                            continue;
                        } else {

                            // Could check EdsCameraRef exist but not really useful

                            final CanonCamera cameraInMap = camerasBySerialNumberMap.get(bodyIDEx);
                            if (cameraInMap != null) {
                                // If reach here, it means:
                                // - camera was previously managed (serial number) and camera
                                // was disconnected/reconnected with cable
                                try {
                                    final Optional<EdsdkLibrary.EdsCameraRef> currentCameraRef = cameraInMap.getCameraRef();
                                    cameraInMap.setCameraRef(cameraRef.getValue());
                                    currentCameraRef.ifPresent(ReleaseUtil::release);
                                } catch (Exception e) {
                                    log.error("Exception while replacing cameraRef in camera, should not happen", e);
                                    ReleaseUtil.release(cameraRef);
                                }
                            } else {
                                // If reach here, it means:
                                // - camera was not connected before
                                // - no instance of that camera should exist (CanonCamera)
                                // - no instance of that camera's serial number should exist
                                try {
                                    final CanonCamera camera = cameraSupplier.get().createCamera(bodyIDEx);
                                    camera.setCameraRef(cameraRef.getValue());
                                    camerasBySerialNumberMap.put(bodyIDEx, camera);
                                    log.info("New camera created: {}", camera);
                                } catch (Exception e) {
                                    log.error("Supplier had exception, should not happen", e);
                                    ReleaseUtil.release(cameraRef);
                                }
                            }
                        }
                    }
                } finally {
                    ReleaseUtil.release(listRef);
                }

                return null;
            }
        }
    }
}
