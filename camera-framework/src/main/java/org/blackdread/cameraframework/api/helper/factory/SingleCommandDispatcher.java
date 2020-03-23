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

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.helper.logic.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Dispatcher that use only one thread to execute commands, all exception are caught and ignored, as a result commands will always be executed unless the dispatcher is stopped.
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@ThreadSafe
public final class SingleCommandDispatcher implements CommandDispatcher {

    protected static final Logger log = LoggerFactory.getLogger(SingleCommandDispatcher.class);

    // we delay as much as possible the instance
    private volatile static SingleCommandDispatcher instance;

    public static CommandDispatcher getInstance() {
        if (instance == null) {
            synchronized (SingleCommandDispatcher.class) {
                if (instance == null) {
                    instance = new SingleCommandDispatcher();
                }
            }
        }
        return instance;
    }

    private final ThreadFactory watchDogFactory = new ThreadFactoryBuilder()
        .setNameFormat("watchdog-dispatcher-%d")
        .setDaemon(true)
        .build();

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setNameFormat("cmd-dispatcher-%d")
        .setDaemon(true)
        .build();

    private final BlockingQueue<CanonCommand> commandQueue = new LinkedBlockingQueue<>();

    private final Semaphore waitForCommandSemaphore = new Semaphore(0);
    private volatile Thread watchDogTimeoutThread;

    private volatile Thread commandDispatcherThread;

//    private volatile boolean stopRun = false;

    /**
     * Dispatcher current command running or null
     */
    private final AtomicReference<CanonCommand> currentCommand = new AtomicReference<>();

    private void watchDogRunner() {
        // TODO There may be some multi-thread issue between timeout and interrupt, can see later to minimize it more
        try {
            CanonCommand<?> previousCommand = null;
            while (true) {
                try {
                    if (previousCommand == null) {
                        waitForCommandSemaphore.acquireUninterruptibly();
                        waitForCommandSemaphore.drainPermits();
                        previousCommand = currentCommand.get();
                    } else {
                        // can get rid of localCurrentCommand
                        final CanonCommand localCurrentCommand = currentCommand.get();
                        if (localCurrentCommand == null) {
                            previousCommand = null;
                            continue;
                        }
                        if (previousCommand != localCurrentCommand) {
                            previousCommand = localCurrentCommand;
                        }

                        final Duration timeout = previousCommand.getTimeout()
                            .orElse(null);// Later we can add a default timeout -> around 60 sec is way more than enough

                        if (timeout == null) {
                            previousCommand = null;
                            continue;
                        }

                        if (!previousCommand.hasExecutionStarted()) {
                            sleep(5);
                            continue;
                        }

                        final Duration executionDurationSinceNow = previousCommand.getExecutionDurationSinceNow();

                        if (executionDurationSinceNow.compareTo(timeout) > 0) {
                            commandDispatcherThread.interrupt();
                            log.warn("A command has exceeded timeout, interrupt was triggered");
                            sleep(1);
                        } else {
                            sleep(5);
                        }
                    }
                } catch (Exception e) {
                    log.warn("Ignored exception in watchdog dispatcher runner", e);
                }
            }
        } finally {
            log.warn("Watchdog dispatcher thread ended");
        }
    }

    private void commandDispatcher() {
        try {
            while (true) {
                try {
                    final CanonCommand cmd = commandQueue.take();
                    try {
                        currentCommand.set(cmd);
                        waitForCommandSemaphore.release();
                        cmd.run();
                    } finally {
                        currentCommand.set(null);
                    }
                } catch (Exception e) {
                    log.warn("Ignored exception in command dispatcher runner", e);
                }
            }
        } finally {
            log.warn("Command dispatcher thread ended");
        }
    }

    @Override
    public void scheduleCommand(final CanonCommand<?> command) {
        commandQueue.add(command);
        startDispatcher();
    }

    @Override
    public void scheduleCommand(final EdsdkLibrary.EdsCameraRef owner, final CanonCommand<?> command) {
        // this implementation does not care about owner
        scheduleCommand(command);
    }

    @Override
    public boolean isDispatcherThread() {
        return this.commandDispatcherThread == Thread.currentThread();
    }

    /**
     * Start dispatcher only not already started
     */
    private void startDispatcher() {
        if (commandDispatcherThread != null) {
            return;
        }
        synchronized (threadFactory) {
            if (commandDispatcherThread == null) {
                commandDispatcherThread = threadFactory.newThread(this::commandDispatcher);
                commandDispatcherThread.start();
                watchDogTimeoutThread = watchDogFactory.newThread(this::watchDogRunner);
                watchDogTimeoutThread.start();
            }
        }
    }

    private void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }


    private SingleCommandDispatcher() {
    }
}
