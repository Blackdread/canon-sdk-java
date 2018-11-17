package org.blackdread.cameraframework.util;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.EdsdkErrorException;

import java.util.concurrent.Callable;

/**
 * TODO Not impl yet, will be used to add fluent error handling and more
 * <p>Created on 2018/11/17.</p>
 *
 * @author Yoann CAPLAIN
 */
public class NameToBeDefined {

    public NameToBeDefined(Runnable runnableToDecorate) {

    }

    public NameToBeDefined(Callable callableToDecorate) {

    }

    public NameToBeDefined retryOnBusy() {
        // can be called many times to add many retries
        return this;
    }

    public NameToBeDefined retryOnBusy(long delayMillis) {
        // can be called many times to add many retries with different delay
        return this;
    }

    public NameToBeDefined retryOnBusy(long delayMillis, int retryTimes) {
        // can be called many times to add many retries with different delay
        return this;
    }

    public NameToBeDefined onError(Runnable runnable, EdsdkErrorException exception) {
        // can be called many times to add different handlers
        return this;
    }

    public NameToBeDefined onError(Runnable runnable, EdsdkErrorException... exceptions) {
        // can be called many times to add different handlers
        return this;
    }

    public NameToBeDefined onError(Runnable runnable, EdsdkError error) {
        // can be called many times to add different handlers
        return this;
    }

    public NameToBeDefined onError(Runnable runnable, EdsdkError... errors) {
        // can be called many times to add different handlers
        return this;
    }
}
