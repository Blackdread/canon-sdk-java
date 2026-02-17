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
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.builder.ShootOptionBuilder;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsSaveTo;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.initialisation.FrameworkInitialisation;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateListener;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/12/16.</p>
 *
 * @author Yoann CAPLAIN
 */
public class DemoTakeAndSavePicture {

    private static final Logger log = LoggerFactory.getLogger(DemoTakeAndSavePicture.class);

    private CameraAddedListener cameraAddedListener;
    private CameraObjectListener cameraObjectListener;
    private CameraPropertyListener cameraPropertyListener;
    private CameraStateListener cameraStateListener;

    @Test
    @CameraIsConnected
    void demo() {
        new FrameworkInitialisation()
            .registerCameraAddedEvent()
            .initialize();

        final CanonCamera camera = new CanonCamera();
        get(camera.openSession());

        List<File> files;
        try {
            ShootOption options = new ShootOptionBuilder()
                .setSaveTo(EdsSaveTo.kEdsSaveTo_Host)
                .setFolderDestination(new File(System.getProperty("user.dir"))) // current working directory
                .setFilename("canon_sdk_capture.jpg")
                .build();
            files = camera.getShoot().shoot(options);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        files.stream().forEach(file -> {
            log.info("File saved at: {}", file);
            boolean deleted = file.delete();
            if (deleted) {
                log.info("Deleted file (to clean up this test): {}", file);
            }
        });

        get(camera.closeSession());

        CanonFactory.commandDispatcher().scheduleCommand(new TerminateSdkCommand());
    }

    private static <R> R get(final CanonCommand<R> command) {
        try {
            return command.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }
}
