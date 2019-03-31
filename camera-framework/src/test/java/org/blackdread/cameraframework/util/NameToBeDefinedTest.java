package org.blackdread.cameraframework.util;

import com.google.common.collect.Lists;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceBusyErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
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

    private final AtomicInteger countRunOnError = new AtomicInteger(0);
    private final Runnable runOnError = countRunOnError::incrementAndGet;

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
        count.set(0);
        countThrows.set(0);
        countRunOnError.set(0);
    }

    private void assertCounts(final int count, final int countThrow) {
        Assertions.assertEquals(count, this.count.get());
        Assertions.assertEquals(countThrow, this.countThrows.get());
    }

//    private void assertCounts(final int count, final int countRuntime, final int countBusy, final int countCommUsb) {
//        Assertions.assertEquals(count, this.count.get());
//        // none specific counts for now
//    }

    @Test
    void retryOnBusy() {
        countOnly.retryOnBusy()
            .run();
        assertCounts(1, 0);
    }

    @Test
    void retryOnBusyWithDelay() {
        countOnly.retryOnBusy(20)
            .run();
        assertCounts(1, 0);
    }

    @Test
    void retryOnBusyWithDelayArray() {
        countOnly.retryOnBusy(10, 10)
            .run();
        assertCounts(1, 0);
    }

    @Test
    void retryOnBusyWithSameDelay() {
        countOnly.retryOnBusy(5, 4)
            .run();
        assertCounts(1, 0);
    }

    @Test
    void retryOnBusyThrowsOnBadParams() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.retryOnBusy(-1, 4));
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.retryOnBusy(1, -1));

        assertCounts(0, 0);
    }

    @Test
    void retryOnBusyThrowsOtherErrors() {
        Assertions.assertThrows(RuntimeException.class, () -> throwRuntime.retryOnBusy(1, 4).run());
        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> throwCommUsbBus.retryOnBusy(1, 4).run());

        assertCounts(0, 2);
    }

    @Test
    void retryOnBusyRepeatAction() {
        throwBusy.retryOnBusy(1, 4)
            .run();

        assertCounts(0, 5);
    }

    @Test
    void retryOnError() {
        countOnly.retryOnError(10, EdsdkError.EDS_ERR_DEVICE_BUSY)
            .run();
        assertCounts(1, 0);


        countOnly.retryOnError(10, EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkError.EDS_ERR_DEVICE_DISK_ERROR)
            .run();
        assertCounts(2, 0);


        countOnly.retryOnError(5, 2, EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkError.EDS_ERR_DEVICE_DISK_ERROR)
            .run();
        assertCounts(3, 0);


        countOnly.retryOnError(5, 2, EdsdkError.EDS_ERR_DEVICE_DISK_ERROR)
            .run();
        assertCounts(4, 0);
    }

    @Test
    void retryOnErrorThrowsOnBadParams() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.retryOnError(-1, 4));
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.retryOnError(1, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.retryOnError(1, 1, Lists.newArrayList((EdsdkError) null)));

        assertCounts(0, 0);
    }

    @Test
    void retryOnErrorRetryAndRethrowAfterAllAttempt() {
        Assertions.assertThrows(EdsdkDeviceBusyErrorException.class, () -> throwBusy.retryOnError(10, EdsdkError.EDS_ERR_DEVICE_BUSY).run());
        assertCounts(0, 1);

        Assertions.assertThrows(EdsdkDeviceBusyErrorException.class, () -> throwBusy.retryOnError(1, 5, EdsdkError.EDS_ERR_DEVICE_BUSY).run());
        assertCounts(0, 7);

        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> throwCommUsbBus.retryOnError(1, 3, EdsdkError.EDS_ERR_DEVICE_BUSY).run());
        assertCounts(0, 8);
    }

    @Test
    void runOnErrorThrowsOnBadParams() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.runOnError(null, EdsdkError.EDS_ERR_COMM_USB_BUS_ERR));
        Assertions.assertThrows(IllegalArgumentException.class, () -> countOnly.runOnError(runOnError, Lists.newArrayList((EdsdkError) null)));

        assertCounts(0, 0);
    }

    @Test
    void runOnErrorThrowsOtherErrors() {
        Assertions.assertThrows(RuntimeException.class, () -> throwRuntime.runOnError(runOnError).run());
    }

    @Test
    void runOnErrorForGivenError() {
        throwBusy.runOnError(runOnError, EdsdkError.EDS_ERR_DEVICE_BUSY)
            .run();

        Assertions.assertEquals(1, countRunOnError.get());

        throwCommUsbBus.runOnError(runOnError, EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkError.EDS_ERR_COMM_USB_BUS_ERR)
            .run();

        Assertions.assertEquals(2, countRunOnError.get());

        countOnly.runOnError(runOnError, (EdsdkError[]) null).run();
    }

    @Test
    void runOnErrorRethrow() {
        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> throwCommUsbBus.runOnError(runOnError, EdsdkError.EDS_ERR_DEVICE_BUSY).run());
    }

    @Test
    void handle() {
        final AtomicBoolean bo = new AtomicBoolean(false);
        throwRuntime
            .handle(throwable -> {
                bo.set(true);
            });

        Assertions.assertTrue(bo.get());

        countOnly
            .handle(throwable -> {
                bo.set(false);
            });

        Assertions.assertTrue(bo.get());
    }

    @Test
    void handleWithReturnValue() {
        final Boolean handle = throwRuntime
            .handle(throwable -> true);

        Assertions.assertTrue(handle);

        final Boolean handle2 = countOnly
            .handle(throwable -> true);

        Assertions.assertNull(handle2);
    }
}
