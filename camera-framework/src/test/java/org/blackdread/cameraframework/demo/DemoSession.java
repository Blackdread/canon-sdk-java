/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.DllOnPath;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.blackdread.cameraframework.api.command.CloseSessionCommand;
import org.blackdread.cameraframework.api.command.GetPropertyCommand;
import org.blackdread.cameraframework.api.command.InitializeSdkCommand;
import org.blackdread.cameraframework.api.command.OpenSessionCommand;
import org.blackdread.cameraframework.api.command.TerminateSdkCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceNotFoundErrorException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.blackdread.cameraframework.api.TestUtil.assertExecutionThrows;

/**
 * <p>Created on 2018/12/15.</p>
 *
 * @author Yoann CAPLAIN
 */
public class DemoSession {

    @BeforeAll
    static void setUpClass() throws ExecutionException, InterruptedException {
        final InitializeSdkCommand initializeSdkCommand = new InitializeSdkCommand();
        CanonFactory.commandDispatcher().scheduleCommand(initializeSdkCommand);
        initializeSdkCommand.get();
    }

    @AfterAll
    static void tearDownClass() {
        CanonFactory.commandDispatcher().scheduleCommand(new TerminateSdkCommand());
    }

    @Test
    @DllOnPath
    void openWhenNoCameraConnected() throws ExecutionException, InterruptedException {
        final CanonCamera camera = new CanonCamera();
        final OpenSessionCommand command = camera.openSession();
        assertExecutionThrows(EdsdkDeviceNotFoundErrorException.class, command::get);
    }

    @Test
    @CameraIsConnected
    void openSessionSuccess() throws ExecutionException, InterruptedException {
        final CanonCamera camera = new CanonCamera();
        final OpenSessionCommand command = camera.openSession();
        final EdsCameraRef cameraRef = command.get();
        camera.setCameraRef(cameraRef); // not necessary is option is set

        final GetPropertyCommand.ProductName productNameAsync = camera.getProperty().getProductNameAsync();
        final String productName = productNameAsync.get();
        Assertions.assertNotNull(productName);

        final CloseSessionCommand closeSessionCommand = camera.closeSession();
        closeSessionCommand.get();
    }
}
