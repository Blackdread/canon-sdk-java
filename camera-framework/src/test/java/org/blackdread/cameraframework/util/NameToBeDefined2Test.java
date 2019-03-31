package org.blackdread.cameraframework.util;

import com.google.common.collect.Lists;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.exception.error.communication.EdsdkCommUsbBusErrorException;
import org.blackdread.cameraframework.exception.error.device.EdsdkDeviceBusyErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created on 2019/03/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class NameToBeDefined2Test {

    private NameToBeDefined2 countOnly;
    private NameToBeDefined2 throwRuntime;
    private NameToBeDefined2 throwBusy;
    private NameToBeDefined2 throwCommUsbBus;

    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger countThrows = new AtomicInteger(0);

    private final AtomicInteger countRunOnError = new AtomicInteger(0);
    private final Callable<Object> runOnError = countRunOnError::incrementAndGet;

    @BeforeEach
    void setUp() {
        countOnly = NameToBeDefined2.wrap(count::getAndIncrement);
        throwRuntime = NameToBeDefined2.wrap(() -> {
            countThrows.getAndIncrement();
            throw new RuntimeException("");
        });
        throwBusy = NameToBeDefined2.wrap(() -> {
            countThrows.getAndIncrement();
            throw new EdsdkDeviceBusyErrorException();
        });
        throwCommUsbBus = NameToBeDefined2.wrap(() -> {
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
    void retryOnBusy() throws Exception {
        countOnly.retryOnBusy()
            .call();
        assertCounts(1, 0);
    }

    @Test
    void retryOnBusyWithDelay() throws Exception {
        countOnly.retryOnBusy(20)
            .call();
        assertCounts(1, 0);

        countOnly.retryOnBusy(5, 5, 5)
            .call();
        assertCounts(2, 0);
    }

    @Test
    void retryOnBusyWithDelayArray() throws Exception {
        countOnly.retryOnBusy(10, 10)
            .call();
        assertCounts(1, 0);
    }

    @Test
    void retryOnBusyWithSameDelay() throws Exception {
        countOnly.retryOnBusy(5, 4)
            .call();
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
        Assertions.assertThrows(RuntimeException.class, () -> throwRuntime.retryOnBusy(1, 4).call());
        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> throwCommUsbBus.retryOnBusy(1, 4).call());

        assertCounts(0, 2);
    }

    @Test
    void retryOnBusyRepeatAction() throws Exception {
        throwBusy.retryOnBusy(1, 4)
            .call();

        assertCounts(0, 5);
    }

    @Test
    void retryOnError() throws Exception {
        countOnly.retryOnError(10, EdsdkError.EDS_ERR_DEVICE_BUSY)
            .call();
        assertCounts(1, 0);


        countOnly.retryOnError(10, EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkError.EDS_ERR_DEVICE_DISK_ERROR)
            .call();
        assertCounts(2, 0);


        countOnly.retryOnError(5, 2, EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkError.EDS_ERR_DEVICE_DISK_ERROR)
            .call();
        assertCounts(3, 0);


        countOnly.retryOnError(5, 2, EdsdkError.EDS_ERR_DEVICE_DISK_ERROR)
            .call();
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
        Assertions.assertThrows(EdsdkDeviceBusyErrorException.class, () -> throwBusy.retryOnError(10, EdsdkError.EDS_ERR_DEVICE_BUSY).call());
        assertCounts(0, 1);

        Assertions.assertThrows(EdsdkDeviceBusyErrorException.class, () -> throwBusy.retryOnError(1, 5, EdsdkError.EDS_ERR_DEVICE_BUSY).call());
        assertCounts(0, 7);

        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> throwCommUsbBus.retryOnError(1, 3, EdsdkError.EDS_ERR_DEVICE_BUSY).call());
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
        Assertions.assertThrows(RuntimeException.class, () -> throwRuntime.runOnError(runOnError).call());
    }

    @Test
    void runOnErrorForGivenError() throws Exception {
        throwBusy.runOnError(runOnError, EdsdkError.EDS_ERR_DEVICE_BUSY)
            .call();

        Assertions.assertEquals(1, countRunOnError.get());

        throwCommUsbBus.runOnError(runOnError, EdsdkError.EDS_ERR_DEVICE_BUSY, EdsdkError.EDS_ERR_COMM_USB_BUS_ERR)
            .call();

        Assertions.assertEquals(2, countRunOnError.get());

        countOnly.runOnError(runOnError, (EdsdkError[]) null).call();
    }

    @Test
    void runOnErrorRethrow() {
        Assertions.assertThrows(EdsdkCommUsbBusErrorException.class, () -> throwCommUsbBus.runOnError(runOnError, EdsdkError.EDS_ERR_DEVICE_BUSY).call());
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
        final Boolean handle = (Boolean) throwRuntime
            .handle(throwable -> true);

        Assertions.assertTrue(handle);

        final int count = (int) countOnly
            .handle(throwable -> true);

        Assertions.assertEquals(this.count.get() - 1, count);
    }
}
