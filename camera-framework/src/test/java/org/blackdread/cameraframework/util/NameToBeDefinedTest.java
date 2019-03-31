package org.blackdread.cameraframework.util;

import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceBusyErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class NameToBeDefinedTest {

    private NameToBeDefined countOnly;
    private NameToBeDefined throwRuntime;
    private NameToBeDefined throwBusy;
    private NameToBeDefined throwCommUsbBus;

    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger countThrows = new AtomicInteger(0);

    @BeforeEach
    void setUp() {
        countOnly = NameToBeDefined.wrap(count::getAndIncrement);
        throwRuntime = NameToBeDefined.wrap(() -> {
            countThrows.getAndIncrement();
            throw new RuntimeException("");
        });
        throwBusy = NameToBeDefined.wrap(() -> {
            countThrows.getAndIncrement();
            throw new EdsdkDeviceBusyErrorException();
        });
        throwCommUsbBus = NameToBeDefined.wrap(() -> {
            countThrows.getAndIncrement();
            throw new EdsdkCommUsbBusErrorException();
        });
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void wrap() {
    }

    @Test
    void retryOnBusy() {
    }

    @Test
    void retryOnBusy1() {
    }

    @Test
    void retryOnBusy2() {
    }

    @Test
    void retryOnBusy3() {
    }

    @Test
    void retryOnError() {
    }

    @Test
    void retryOnError1() {
    }

    @Test
    void retryOnError2() {
    }

    @Test
    void retryOnError3() {
    }

    @Test
    void retryOnError4() {
    }

    @Test
    void runOnError() {
    }

    @Test
    void runOnError1() {
    }

    @Test
    void runOnError2() {
    }

    @Test
    void run() {
    }

    @Test
    void handle() {
    }

    @Test
    void handle1() {
    }
}
