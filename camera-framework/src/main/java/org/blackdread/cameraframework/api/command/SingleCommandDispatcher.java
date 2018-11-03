package org.blackdread.cameraframework.api.command;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.blackdread.cameraframework.api.helper.logic.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Dispatcher that use only one thread to execute commands, a new thread is created if the thread is interrupted or throws an un-expected exception, as a result commands will always be executed unless the dispatcher is stopped.
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class SingleCommandDispatcher implements CommandDispatcher {

    protected static final Logger log = LoggerFactory.getLogger(SingleCommandDispatcher.class);

    // we delay as much as possible the instance
    private volatile static SingleCommandDispatcher instance;

    private final ExecutorService commandExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
        .setNameFormat("cmd-executor-%d")
        .setDaemon(true)
        .build());

    private final Queue<CanonCommand> commandQueue = new ConcurrentLinkedQueue<>();

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

    public void e() {

    }


    private SingleCommandDispatcher() {
    }

}
