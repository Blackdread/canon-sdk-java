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
package org.blackdread.cameraframework.demo;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.TerminateSdkCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.initialisation.FrameworkInitialisation;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateListener;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>Created on 2018/12/16.</p>
 *
 * @author Yoann CAPLAIN
 */
public class DemoFullLiveView {

    private static final Logger log = LoggerFactory.getLogger(DemoFullLiveView.class);

    private CameraAddedListener cameraAddedListener;
    private CameraObjectListener cameraObjectListener;
    private CameraPropertyListener cameraPropertyListener;
    private CameraStateListener cameraStateListener;

    private final AtomicBoolean propertyEvent = new AtomicBoolean(false);

    @Test
    @CameraIsConnected
    void demo() throws InterruptedException {
        cameraAddedListener = event -> {
            log.info("Camera added detected: {}", event);
        };
        cameraObjectListener = event -> {
            log.info("Object event: {}", event);
        };
        cameraPropertyListener = event -> {
            log.info("Property event: {}", event);
            propertyEvent.set(true);
        };
        cameraStateListener = event -> {
            log.info("State event: {}", event);
        };
        new FrameworkInitialisation()
            .registerCameraAddedEvent()
            .withEventFetcherLogic()
            .withCameraAddedListener(cameraAddedListener)
            .initialize();

        final CanonCamera camera = new CanonCamera();

        final EdsdkLibrary.EdsCameraRef edsCameraRef = get(camera.openSession());
//        camera.setCameraRef(edsCameraRef); // not necessary as by default on success the ref is set by the openSession command

        get(camera.getEvent().registerObjectEventCommand());
        get(camera.getEvent().registerPropertyEventCommand());
        get(camera.getEvent().registerStateEventCommand());

        CanonFactory.cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        CanonFactory.cameraPropertyEventLogic().addCameraPropertyListener(edsCameraRef, cameraPropertyListener);
        CanonFactory.cameraStateEventLogic().addCameraStateListener(edsCameraRef, cameraStateListener);

        propertyEvent.set(false);
        get(camera.getLiveView().beginLiveViewAsync());

        while (!propertyEvent.get()) {
            Thread.sleep(100);
        }

        while (!get(camera.getLiveView().isLiveViewActiveAsync())) {
            Thread.sleep(500);
        }

        // if try to download too quick live view then will get "EDS_ERR_OBJECT_NOTREADY"
        Thread.sleep(200);

        try {
            for (int i = 0; i < 10; i++) {
                final BufferedImage bufferedImage = get(camera.getLiveView().downloadLiveViewAsync());
                // if try to download too quick live view then will get "EDS_ERR_OBJECT_NOTREADY"
                Thread.sleep(200);

                final int tempI = i;
                CompletableFuture.runAsync(() -> {
                    try {
                        ImageIO.write(bufferedImage, "jpeg", new File("target/image-" + tempI + ".jpg"));
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                });
            }
        } finally {

            get(camera.getLiveView().endLiveViewAsync());

            get(camera.closeSession());

            CanonFactory.commandDispatcher().scheduleCommand(new TerminateSdkCommand());
        }

    }

    private static <R> R get(final CanonCommand<R> command) {
        try {
            return command.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }
}
