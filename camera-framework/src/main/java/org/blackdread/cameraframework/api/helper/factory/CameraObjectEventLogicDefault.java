/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
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

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsObjectEventHandler;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonObjectEvent;
import org.blackdread.cameraframework.api.helper.logic.event.CanonObjectEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.*;
import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
@ThreadSafe
public class CameraObjectEventLogicDefault implements CameraObjectEventLogic {

    private static final Logger log = LoggerFactory.getLogger(CameraObjectEventLogicDefault.class);

    /**
     * Lock when interacting with handlers
     */
    private final ReentrantReadWriteLock handlerLock = new ReentrantReadWriteLock();

    /**
     * Lock when interacting with listeners
     */
    private final ReentrantReadWriteLock listenerLock = new ReentrantReadWriteLock();

    /**
     * Used to keep handlers as strong reference but can be GC if EdsCameraRef is not anymore
     */
    private final WeakHashMap<EdsCameraRef, EdsObjectEventHandler> handlerMap = new WeakHashMap<>();

    private final List<WeakReference<CameraObjectListener>> anyCameraListeners = new ArrayList<>();

    private final WeakHashMap<EdsCameraRef, List<WeakReference<CameraObjectListener>>> listenersMap = new WeakHashMap<>();

    private EdsObjectEventHandler buildHandler(final EdsCameraRef cameraRef) {
        final WeakReference<EdsCameraRef> cameraRefWeakReference = new WeakReference<>(cameraRef);
        return (inEvent, inRef, inContext) -> {
            final EdsCameraRef edsCameraRef = cameraRefWeakReference.get();
            if (edsCameraRef == null) {
                // this should not happen but who knows...
                log.error("Received an event from a camera ref that is not referenced in the code anymore");
                throw new IllegalStateException("Received an event from a camera ref that is not referenced in the code anymore");
                // we throw or we just return doing nothing
            }
            this.handle(new CanonObjectEventImpl(edsCameraRef, EdsObjectEvent.ofValue(inEvent.intValue()), inRef));
            return new NativeLong(0);
        };
    }

    /**
     * Handle the event by notifying listeners
     *
     * @param event event to send
     */
    protected void handle(final CanonObjectEvent event) {
        // Must handle in current thread, is the one that called EdsGetEvent
        listenerLock.readLock().lock();
        try {
            notifyListeners(anyCameraListeners, event);
            final List<WeakReference<CameraObjectListener>> weakReferences = listenersMap.get(event.getCameraRef());
            if (weakReferences != null) {
                notifyListeners(weakReferences, event);
            }
        } finally {
            listenerLock.readLock().unlock();
        }
    }

    @Override
    public void registerCameraObjectEvent(final EdsCameraRef cameraRef) {
        Objects.requireNonNull(cameraRef);
        final EdsObjectEventHandler objectEventHandler = buildHandler(cameraRef);
        handlerLock.writeLock().lock();
        try {
            handlerMap.put(cameraRef, objectEventHandler);
            final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsSetObjectEventHandler(cameraRef, new NativeLong(EdsObjectEvent.kEdsObjectEvent_All.value()), objectEventHandler, Pointer.NULL));
            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }
        } finally {
            handlerLock.writeLock().unlock();
        }
    }

    @Override
    public void unregisterCameraObjectEvent(final EdsCameraRef cameraRef) {
        Objects.requireNonNull(cameraRef);
        handlerLock.writeLock().lock();
        try {
            final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsSetObjectEventHandler(cameraRef, new NativeLong(EdsObjectEvent.kEdsObjectEvent_All.value()), null, Pointer.NULL));
            handlerMap.remove(cameraRef);

            if (edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }
        } finally {
            handlerLock.writeLock().unlock();
        }
    }

    @Override
    public void addCameraObjectListener(final CameraObjectListener listener) {
        Objects.requireNonNull(listener);
        listenerLock.writeLock().lock();
        try {
            if (!contains(anyCameraListeners, listener))
                anyCameraListeners.add(new WeakReference<>(listener));
            cleanNullListeners();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void addCameraObjectListener(final EdsCameraRef cameraRef, final CameraObjectListener listener) {
        Objects.requireNonNull(cameraRef);
        Objects.requireNonNull(listener);
        listenerLock.writeLock().lock();
        try {
            listenersMap.compute(cameraRef, (cameraRef1, weakReferences) -> {
                if (weakReferences == null) {
                    final List<WeakReference<CameraObjectListener>> weakRefs = new ArrayList<>();
                    weakRefs.add(new WeakReference<>(listener));
                    return weakRefs;
                } else {
                    if (!contains(weakReferences, listener)) {
                        weakReferences.add(new WeakReference<>(listener));
                    }
                    return weakReferences;
                }
            });
            cleanNullListeners();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void removeCameraObjectListener(final CameraObjectListener listener) {
        Objects.requireNonNull(listener);
        listenerLock.writeLock().lock();
        try {
            remove(anyCameraListeners, listener);
            listenersMap.values().forEach(weakReferences -> {
                if (weakReferences != null) {
                    remove(weakReferences, listener);
                }
            });
            cleanNullListeners();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void removeCameraObjectListener(final EdsCameraRef cameraRef, final CameraObjectListener listener) {
        Objects.requireNonNull(cameraRef);
        Objects.requireNonNull(listener);
        listenerLock.writeLock().lock();
        try {
            final List<WeakReference<CameraObjectListener>> weakReferences = listenersMap.get(cameraRef);
            if (weakReferences != null) {
                remove(weakReferences, listener);
            }
            cleanNullListeners();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void clearCameraObjectListeners() {
        listenerLock.writeLock().lock();
        try {
            anyCameraListeners.clear();
            listenersMap.clear();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void clearCameraObjectListeners(final EdsCameraRef cameraRef) {
        Objects.requireNonNull(cameraRef);
        listenerLock.writeLock().lock();
        try {
            final List<WeakReference<CameraObjectListener>> weakReferences = listenersMap.get(cameraRef);
            if (weakReferences != null)
                weakReferences.clear();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    private void cleanNullListeners() {
        cleanNullReferences(anyCameraListeners);
        listenersMap.values().forEach(weakReferences -> {
            if (weakReferences != null)
                cleanNullReferences(weakReferences);
        });
    }

    private static void notifyListeners(final List<WeakReference<CameraObjectListener>> weakReferences, final CanonObjectEvent event) {
        for (final WeakReference<CameraObjectListener> listener : weakReferences) {
            final CameraObjectListener cameraObjectListener = listener.get();
            if (cameraObjectListener != null) {
                try {
                    cameraObjectListener.handleCameraObjectEvent(event);
                } catch (Exception e) {
                    log.warn("Listeners should not throw exceptions", e);
                }
            }
        }
    }
}
