package org.blackdread.cameraframework.api.helper.factory;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.CanonEvent;
import org.blackdread.cameraframework.api.helper.logic.event.EmptyEvent;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default implementation of camera added event logic
 * <p>Created on 2018/11/04.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CameraAddedEventLogicDefault implements CameraAddedEventLogic {

    private final EdsdkLibrary.EdsCameraAddedHandler handler = inContext -> {
        this.handle(new EmptyEvent());
        return new NativeLong(0);
    };

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
                cameraAddedListener.handleCameraAddedEvent(event);
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
        // between found and add, it is not thread safe but does not matter
        boolean found = false;
        for (final WeakReference<CameraAddedListener> weakReference : listeners) {
            final CameraAddedListener cameraAddedListener = weakReference.get();
            if (cameraAddedListener != null && cameraAddedListener.equals(listener)) {
                found = true;
                break;
            }
        }
        if (!found)
            listeners.add(new WeakReference<>(listener));
        cleanNullListeners();
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
        cleanNullListeners();
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

    private void cleanNullListeners() {
        listeners.removeIf(weakReference -> weakReference.isEnqueued() || weakReference.get() == null);
    }
}
