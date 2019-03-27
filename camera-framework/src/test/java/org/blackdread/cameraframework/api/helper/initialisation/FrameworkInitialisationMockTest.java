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
package org.blackdread.cameraframework.api.helper.initialisation;

import org.blackdread.cameraframework.AbstractMockTest;
import org.blackdread.cameraframework.MockFactory;
import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class FrameworkInitialisationMockTest extends AbstractMockTest implements MockFactory {

    private final AtomicInteger eventCount = new AtomicInteger(0);

    private final CameraAddedListener cameraAddedListener = event -> eventCount.getAndIncrement();

    @BeforeAll
    static void setUpClass() {
    }

    @AfterAll
    static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
        eventCount.set(0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initializeDefault() throws ExecutionException, InterruptedException {
        final List<CanonCommand> commands = new FrameworkInitialisation()
            .initialize();

        Assertions.assertEquals(0, commands.size());

        for (CanonCommand canonCommand : commands) {
            canonCommand.get();
        }
    }

    @Test
    void initializeAll() throws ExecutionException, InterruptedException {
        final CameraAddedListener customListener = event -> {
        };
        final List<CanonCommand> commands = new FrameworkInitialisation()
            .registerCameraAddedEvent()
            .withArchLibrary(CanonLibrary.ArchLibrary.FORCE_32)
            .withoutEventFetcherLogic()
            .withEventFetcherLogic()
            .withCameraAddedListener(this.cameraAddedListener)
            .withCameraAddedListener(customListener)
            .initialize();

        verify(canonLibrary, times(1)).setArchLibraryToUse(CanonLibrary.ArchLibrary.FORCE_32);
        verify(commandDispatcher, times(2)).scheduleCommand(any());
        verify(eventFetcherLogic, times(1)).start();
        verify(cameraAddedEventLogic, times(1)).addCameraAddedListener(this.cameraAddedListener);
        verify(cameraAddedEventLogic, times(1)).addCameraAddedListener(customListener);
        verify(cameraAddedEventLogic, times(2)).addCameraAddedListener(any());
        verify(cameraAddedEventLogic, times(0)).addCameraAddedListener(event -> {
        });

        Assertions.assertEquals(2, commands.size());

        for (CanonCommand canonCommand : commands) {
            // infinite wait as commands are not actually processed for now
//            canonCommand.get();
        }
    }
}
