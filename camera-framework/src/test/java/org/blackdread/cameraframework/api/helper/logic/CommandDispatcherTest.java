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
package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.NeverFinishCommand;
import org.blackdread.cameraframework.api.command.decorator.impl.TimeoutCommandDecorator;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
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

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/12/09.</p>
 *
 * @author Yoann CAPLAIN
 */
class CommandDispatcherTest {

    private static final Logger log = LoggerFactory.getLogger(LiveViewLogicCameraTest.class);

    private static final Duration TIMEOUT = Duration.ofSeconds(2);

    private CanonCommand timeoutCommand;

    @BeforeAll
    static void setUpClass() {

    }

    @AfterAll
    static void tearDownClass() {

    }

    @BeforeEach
    void setUp() {
        timeoutCommand = new TimeoutCommandDecorator<>(new NeverFinishCommand(), TIMEOUT);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void timeoutDoesInterruptCommand() throws InterruptedException {
        CanonFactory.commandDispatcher().scheduleCommand(timeoutCommand);
        Thread.sleep(TIMEOUT.dividedBy(2).toMillis());

        Assertions.assertThrows(IllegalStateException.class, () -> timeoutCommand.getExecutionEndTime());

        Thread.sleep(TIMEOUT.dividedBy(2).plusSeconds(1).toMillis());

        final Duration executionDuration = timeoutCommand.getExecutionDuration();
        log.info("executionDuration: {}", executionDuration);
        Assertions.assertTrue(executionDuration.compareTo(TIMEOUT) > 0);

        final ExecutionException exThrown = assertThrows(ExecutionException.class, () -> timeoutCommand.get());
        assertTrue(exThrown.getCause() instanceof InterruptedException);
        log.debug("Stack of ex:", exThrown);
    }

    @Test
    void timeoutDoesInterruptCommandButNotDispatcher() throws InterruptedException {
        CanonFactory.commandDispatcher().scheduleCommand(timeoutCommand);
        Thread.sleep(TIMEOUT.plusSeconds(1).toMillis());

        final Duration executionDuration = timeoutCommand.getExecutionDuration();
        log.info("executionDuration: {}", executionDuration);
        Assertions.assertTrue(executionDuration.compareTo(TIMEOUT) > 0);


        CanonFactory.commandDispatcher().scheduleCommand(timeoutCommand.copy());
        Thread.sleep(TIMEOUT.plusSeconds(1).toMillis());

        final Duration executionDuration2 = timeoutCommand.getExecutionDuration();
        log.info("executionDuration2: {}", executionDuration2);
        Assertions.assertTrue(executionDuration2.compareTo(TIMEOUT) > 0);
    }

}
