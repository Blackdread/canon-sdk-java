package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraAddedHandler;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonEvent;
import org.blackdread.cameraframework.api.helper.logic.event.EmptyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.cleanNullReferences;
import static org.blackdread.cameraframework.api.helper.factory.WeakReferenceUtil.contains;

/**
 * Default implementation of camera added event logic
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
@ThreadSafe
public class CameraAddedEventLogicDefault implements CameraAddedEventLogic {

    private static final Logger log = LoggerFactory.getLogger(CameraAddedEventLogicDefault.class);

    private final EdsCameraAddedHandler handler = inContext -> {
        this.handle(new EmptyEvent());
        return new NativeLong(0);
    };

    /**
     * Lock for add only, just to make it thread safe
     */
    private final Object addLock = new Object();

    private final CopyOnWriteArrayList<WeakReference<CameraAddedListener>> listeners = new CopyOnWriteArrayList<>();

    protected CameraAddedEventLogicDefault() {
    }

    /**
     * Handle the event by notifying listeners
     *
     * @param event event to send
     */
    protected void handle(final CanonEvent event) {
        // TODO handle in this current thread or use common pool or custom thread for that...
        for (final WeakReference<CameraAddedListener> listener : listeners) {
            final CameraAddedListener cameraAddedListener = listener.get();
            if (cameraAddedListener != null) {
                try {
                    cameraAddedListener.handleCameraAddedEvent(event);
                } catch (Exception e) {
                    log.warn("Listeners should not throw exceptions", e);
                }
            }
        }
    }

    @Override
    public void registerCameraAddedEvent() {
        // We could make this once with a variable to remember but anyway, library will discard previous one registered if any
        // Good to let it be registered again if for some reason library was terminated then re-initialized
        CanonFactory.edsdkLibrary().EdsSetCameraAddedHandler(handler, Pointer.NULL);
    }

    @Override
    public void addCameraAddedListener(final CameraAddedListener listener) {
        synchronized (addLock) {
            if (!contains(listeners, listener))
                listeners.add(new WeakReference<>(listener));
        }
        cleanNullReferences(listeners);
    }

    @Override
    public void removeCameraAddedListener(final CameraAddedListener listener) {
        listeners.removeIf(weakReference -> {
            final CameraAddedListener cameraAddedListener = weakReference.get();
            if (weakReference.isEnqueued() || cameraAddedListener == null) {
                return true;
            }
            return cameraAddedListener.equals(listener);
        });
        cleanNullReferences(listeners);
    }

    @Override
    public void clearCameraAddedListeners() {
        listeners.clear();
    }

    /**
     * Give access to listeners if extends
     *
     * @return mutable list of listeners
     */
    protected List<WeakReference<CameraAddedListener>> getListeners() {
        return listeners;
    }

}
