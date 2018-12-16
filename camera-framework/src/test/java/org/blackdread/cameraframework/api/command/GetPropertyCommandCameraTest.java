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
package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsPictureStyleDesc;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.TestShortcutUtil;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.util.ReleaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/12/11.</p>
 *
 * @author Yoann CAPLAIN
 */
@CameraIsConnected
class GetPropertyCommandCameraTest {

    private static final Logger log = LoggerFactory.getLogger(GetPropertyCommandCameraTest.class);

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private static EdsdkLibrary.EdsCameraRef.ByReference camera;

    @BeforeAll
    static void setUpClass() {
        TestShortcutUtil.initLibrary();
        camera = TestShortcutUtil.getFirstCamera();
        TestShortcutUtil.openSession(camera);
    }

    @AfterAll
    static void tearDownClass() {
        try {
            TestShortcutUtil.closeSession(camera);
        } finally {
            ReleaseUtil.release(camera);
        }
        TestShortcutUtil.terminateLibrary();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void pictureStyleDescriptionCommand() throws ExecutionException, InterruptedException {
        final GetPropertyCommand.PictureStyleDescription command = new GetPropertyCommand.PictureStyleDescription();
        command.setTargetRef(camera.getValue());
        CanonFactory.commandDispatcher().scheduleCommand(command);

        final EdsPictureStyleDesc result = command.get();

        log.debug("result: {}", result);

        Assertions.assertNotNull(result);
    }

    @Test
    void artistCommand() throws ExecutionException, InterruptedException {
        final GetPropertyCommand.Artist command = new GetPropertyCommand.Artist();
        command.setTargetRef(camera.getValue());
        command.setTimeout(TIMEOUT);
        CanonFactory.commandDispatcher().scheduleCommand(command);

        // TODO hangs forever if not dispatcher thread that initialize SDK
        final String result = command.get();

        log.debug("result: {}", result);

        Assertions.assertNotNull(result);
    }

    @Test
    void firmwareVersionCommand() throws ExecutionException, InterruptedException {
        final GetPropertyCommand.FirmwareVersion command = new GetPropertyCommand.FirmwareVersion();
        command.setTargetRef(camera.getValue());
        command.setTimeout(TIMEOUT);
        CanonFactory.commandDispatcher().scheduleCommand(command);

        // TODO hangs forever if not dispatcher thread that initialize SDK
        final String result = command.get();

        log.debug("result: {}", result);

        Assertions.assertNotNull(result);
    }

}
