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
package org.blackdread.cameraframework.api.command.builder;

import org.blackdread.cameraframework.api.constant.EdsSaveTo;

import java.io.File;


/**
 * <p>Created on 2018/11/29.</p>
 *
 * @author Yoann CAPLAIN
 * @see ShootOption ShootOption for documentation on options
 * @since 1.0.0
 */
public class ShootOptionBuilder {
    private int shootAttemptCount = 5;
    private boolean checkLiveViewState = false;
    private boolean shootWithV0 = true;
    private boolean shootWithNoAF = true;
    private boolean shootWithAF = true;
    private EdsSaveTo saveTo = EdsSaveTo.kEdsSaveTo_Both;
    private boolean waitForItemDownloadEvent = true;
    private long busyWaitMillis = 200;
    // Defult is true due to non multi-thread model
    private boolean fetchEvents = true;
    private File folderDestination = null;
    private String filename = null;

    public ShootOptionBuilder setShootAttemptCount(final int shootAttemptCount) {
        this.shootAttemptCount = shootAttemptCount;
        return this;
    }

    public ShootOptionBuilder setCheckLiveViewState(final boolean checkLiveViewState) {
        this.checkLiveViewState = checkLiveViewState;
        return this;
    }

    public ShootOptionBuilder setShootWithV0(final boolean shootWithV0) {
        this.shootWithV0 = shootWithV0;
        return this;
    }

    public ShootOptionBuilder setShootWithNoAF(final boolean shootWithNoAF) {
        this.shootWithNoAF = shootWithNoAF;
        return this;
    }

    public ShootOptionBuilder setShootWithAF(final boolean shootWithAF) {
        this.shootWithAF = shootWithAF;
        return this;
    }

    public ShootOptionBuilder setSaveTo(final EdsSaveTo saveTo) {
        this.saveTo = saveTo;
        return this;
    }

    public ShootOptionBuilder setWaitForItemDownloadEvent(final boolean waitForItemDownloadEvent) {
        this.waitForItemDownloadEvent = waitForItemDownloadEvent;
        return this;
    }

    public ShootOptionBuilder setBusyWaitMillis(final long busyWaitMillis) {
        this.busyWaitMillis = busyWaitMillis;
        return this;
    }

    public ShootOptionBuilder setFetchEvents(final boolean fetchEvents) {
        this.fetchEvents = fetchEvents;
        if (fetchEvents) {
            this.waitForItemDownloadEvent = true;
        }
        return this;
    }

    public ShootOptionBuilder setFolderDestination(final File folderDestination) {
        this.folderDestination = folderDestination;
        return this;
    }

    public ShootOptionBuilder setFilename(final String filename) {
        this.filename = filename;
        return this;
    }

    public ShootOption build() {
        validate();
        return new ShootOption(shootAttemptCount, checkLiveViewState, shootWithV0, shootWithNoAF, shootWithAF, saveTo, waitForItemDownloadEvent, busyWaitMillis, fetchEvents, folderDestination, filename);
    }

    private void validate() {
        if (shootAttemptCount < 1) {
            throw new IllegalArgumentException("Shoot attempt must be more than 0");
        }
        if (!shootWithV0 && !shootWithNoAF && !shootWithAF) {
            throw new IllegalStateException("At least one type of shoot must be true");
        }
        if (busyWaitMillis <= 0) {
            throw new IllegalArgumentException("Sleep time must be more than 0 millis");
        }
        if (fetchEvents && !waitForItemDownloadEvent) {
            throw new IllegalStateException("If fetchEvents is true then waitForItemDownloadEvent must be as well");
        }
    }
}
