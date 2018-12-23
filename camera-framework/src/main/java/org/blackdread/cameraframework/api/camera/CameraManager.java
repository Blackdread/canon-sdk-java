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
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.AbstractCanonCommand;
import org.blackdread.cameraframework.api.command.IsConnectedCommand;
import org.blackdread.cameraframework.api.command.OpenSessionCommand;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOption;
import org.blackdread.cameraframework.api.command.builder.OpenSessionOptionBuilder;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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

    private static final CameraAddedListener cameraAddedListener = event -> handleCameraAddedListener();

//    private static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
//        .setNameFormat("camera-refresh-%d")
//        .setDaemon(true)
//        .build();

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

    private CameraManager() {
    }

    private static class FindAndConnectCameraCommand extends AbstractCanonCommand<Void> {

        @Override
        protected Void runInternal() throws InterruptedException {
            return null;
        }
    }
}
