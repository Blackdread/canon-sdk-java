package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsStateEventHandler;
import org.blackdread.cameraframework.api.constant.EdsStateEvent;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonStateEvent;
import org.blackdread.cameraframework.api.helper.logic.event.CanonStateEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.*;

/**
 * <p>Created on 2018/11/09.</p>
 *
 * @author Yoann CAPLAIN
 */
@ThreadSafe
public class CameraStateEventLogicDefault implements CameraStateEventLogic {

    private static final Logger log = LoggerFactory.getLogger(CameraStateEventLogicDefault.class);

    /**
     * Lock when interacting with handlers
     */
    private final ReentrantReadWriteLock handlerLock = new ReentrantReadWriteLock();

    /**
     * Lock when interacting with listeners
     */
    private final ReentrantReadWriteLock listenerLock = new ReentrantReadWriteLock();

    private final WeakHashMap<EdsCameraRef, EdsStateEventHandler> handlerMap = new WeakHashMap<>();

    private final List<WeakReference<CameraStateListener>> anyCameraListeners = new ArrayList<>();

    private final WeakHashMap<EdsCameraRef, List<WeakReference<CameraStateListener>>> listenersMap = new WeakHashMap<>();

    private EdsStateEventHandler buildHandler(final EdsCameraRef cameraRef) {
        final WeakReference<EdsCameraRef> cameraRefWeakReference = new WeakReference<>(cameraRef);
        return (inEvent, inEventData, inContext) -> {
            final EdsCameraRef edsCameraRef = cameraRefWeakReference.get();
            if (edsCameraRef == null) {
                // this should not happen but who knows...
                log.error("Received an event from a camera ref that is not referenced in the code anymore");
                throw new IllegalStateException("Received an event from a camera ref that is not referenced in the code anymore");
                // TODO we throw or we just return doing nothing
            }
            this.handle(new CanonStateEventImpl(edsCameraRef, EdsStateEvent.ofValue(inEvent.intValue()), inEventData.longValue()));
            return new NativeLong(0);
        };
    }

    /**
     * Handle the event by notifying listeners
     *
     * @param event event to send
     */
    protected void handle(final CanonStateEvent event) {
        // TODO handle in this current thread or use common pool or custom thread for that...
        listenerLock.readLock().lock();
        try {
            notifyListeners(anyCameraListeners, event);
            final List<WeakReference<CameraStateListener>> weakReferences = listenersMap.get(event.getCameraRef());
            if (weakReferences != null) {
                notifyListeners(weakReferences, event);
            }
        } finally {
            listenerLock.readLock().unlock();
        }
    }

    @Override
    public void registerCameraStateEvent(final EdsCameraRef cameraRef) {
        final EdsStateEventHandler stateEventHandler = buildHandler(cameraRef);
        handlerLock.writeLock().lock();
        try {
            handlerMap.put(cameraRef, stateEventHandler);
            CanonFactory.edsdkLibrary().EdsSetCameraStateEventHandler(cameraRef, new NativeLong(EdsStateEvent.kEdsStateEvent_All.value()), stateEventHandler, Pointer.NULL);
        } finally {
            handlerLock.writeLock().unlock();
        }
    }

    @Override
    public void unregisterCameraStateEvent(final EdsCameraRef cameraRef) {
        handlerLock.writeLock().lock();
        try {
            CanonFactory.edsdkLibrary().EdsSetCameraStateEventHandler(cameraRef, new NativeLong(EdsStateEvent.kEdsStateEvent_All.value()), null, Pointer.NULL);
            handlerMap.remove(cameraRef);
        } finally {
            handlerLock.writeLock().unlock();
        }
    }

    @Override
    public void addCameraStateListener(final CameraStateListener listener) {
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
    public void addCameraStateListener(final EdsCameraRef cameraRef, final CameraStateListener listener) {
        listenerLock.writeLock().lock();
        try {
            listenersMap.compute(cameraRef, (cameraRef1, weakReferences) -> {
                if (weakReferences == null) {
                    final List<WeakReference<CameraStateListener>> weakRefs = new ArrayList<>();
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
    public void removeCameraStateListener(final CameraStateListener listener) {
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
    public void removeCameraStateListener(final EdsCameraRef cameraRef, final CameraStateListener listener) {
        listenerLock.writeLock().lock();
        try {
            final List<WeakReference<CameraStateListener>> weakReferences = listenersMap.get(cameraRef);
            if (weakReferences != null) {
                remove(weakReferences, listener);
            }
            cleanNullListeners();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void clearCameraStateListeners() {
        listenerLock.writeLock().lock();
        try {
            anyCameraListeners.clear();
            listenersMap.clear();
        } finally {
            listenerLock.writeLock().unlock();
        }
    }

    @Override
    public void clearCameraStateListeners(final EdsCameraRef cameraRef) {
        listenerLock.writeLock().lock();
        try {
            final List<WeakReference<CameraStateListener>> weakReferences = listenersMap.get(cameraRef);
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

    private static void notifyListeners(final List<WeakReference<CameraStateListener>> weakReferences, final CanonStateEvent event) {
        for (final WeakReference<CameraStateListener> listener : weakReferences) {
            final CameraStateListener cameraStateListener = listener.get();
            if (cameraStateListener != null) {
                try {
                    cameraStateListener.handleCameraStateEvent(event);
                } catch (Exception ex) {
                    log.warn("Listeners should not throw exceptions", ex);
                }
            }
        }
    }
}
