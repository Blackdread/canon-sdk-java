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
package org.blackdread.cameraframework.api.helper.logic.event;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;

import java.util.Objects;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class CanonStateEventImpl implements CanonStateEvent {

    private final EdsCameraRef cameraRef;
    private final EdsStateEvent stateEvent;
    private final long inEventData;

    public CanonStateEventImpl(final EdsCameraRef cameraRef, final EdsStateEvent stateEvent, final long inEventData) {
        this.cameraRef = Objects.requireNonNull(cameraRef);
        this.stateEvent = Objects.requireNonNull(stateEvent);
        this.inEventData = inEventData;
    }

    @Override
    public EdsCameraRef getCameraRef() {
        return cameraRef;
    }

    @Override
    public EdsStateEvent getStateEvent() {
        return stateEvent;
    }

    @Override
    public long getEventData() {
        return inEventData;
    }

    @Override
    public String toString() {
        return "CanonStateEventImpl{" +
            "cameraRef=" + cameraRef +
            ", stateEvent=" + stateEvent +
            ", inEventData=" + inEventData +
            '}';
    }
}
