package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsObjectEventHandler;
import org.blackdread.cameraframework.api.constant.EdsObjectEvent;
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
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.cleanNullReferences;
import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.contains;
import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.remove;

/**
 * <p>Created on 2018/11/06.</p>
 *
 * @author Yoann CAPLAIN
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
                // TODO we throw or we just return doing nothing
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
        // TODO handle in this current thread or use common pool or custom thread for that...
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
        final EdsObjectEventHandler objectEventHandler = buildHandler(cameraRef);
        handlerLock.writeLock().lock();
        try {
            handlerMap.put(cameraRef, objectEventHandler);
            CanonFactory.edsdkLibrary().EdsSetObjectEventHandler(cameraRef, new NativeLong(EdsObjectEvent.kEdsObjectEvent_All.value()), objectEventHandler, Pointer.NULL);
        } finally {
            handlerLock.writeLock().unlock();
        }
    }

    @Override
    public void unregisterCameraObjectEvent(final EdsCameraRef cameraRef) {
        handlerLock.writeLock().lock();
        try {
            CanonFactory.edsdkLibrary().EdsSetObjectEventHandler(cameraRef, new NativeLong(EdsObjectEvent.kEdsObjectEvent_All.value()), null, Pointer.NULL);
            handlerMap.remove(cameraRef);
        } finally {
            handlerLock.writeLock().unlock();
        }
    }

    @Override
    public void addCameraObjectListener(final CameraObjectListener listener) {
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
                } catch (Exception ex) {
                    log.warn("Listeners should not throw exceptions", ex);
                }
            }
        }
    }
}
