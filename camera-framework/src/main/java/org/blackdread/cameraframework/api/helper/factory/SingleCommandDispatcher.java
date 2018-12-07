package org.blackdread.cameraframework.api.helper.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.helper.logic.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Dispatcher that use only one thread to execute commands, <s>a new thread is created if the thread is interrupted or throws an un-expected exception</s> -> all exception are caught and ignored, as a result commands will always be executed unless the dispatcher is stopped.
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
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

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
        .setNameFormat("cmd-dispatcher-%d")
        .setDaemon(true)
        .build();

    private final BlockingQueue<CanonCommand> commandQueue = new LinkedBlockingQueue<>();

    private volatile Thread currentThread;

//    private volatile boolean stopRun = false;

    private void commandDispatcher() {
        try {
            while (true) {
                try {
                    final CanonCommand cmd = commandQueue.take();
                    cmd.run();
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

    /**
     * Start dispatcher only not already started
     */
    private void startDispatcher() {
        if (currentThread != null) {
            return;
        }
        synchronized (threadFactory) {
            if (currentThread == null) {
                currentThread = threadFactory.newThread(this::commandDispatcher);
                currentThread.start();
            }
        }
    }


    private SingleCommandDispatcher() {
    }
}
