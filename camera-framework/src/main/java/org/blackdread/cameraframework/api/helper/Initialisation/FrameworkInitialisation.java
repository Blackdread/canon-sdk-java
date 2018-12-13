package org.blackdread.cameraframework.api.helper.Initialisation;

import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.command.RegisterCameraAddedEventCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Helper for initializing the framework with specific options.
 * <br>
 * NOTE: not finished but usable
 * <p>Created on 2018/12/07.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public final class FrameworkInitialisation {

    private boolean useEventFetcher = false;

    /**
     * If null then left with default value of implementation
     */
    private CanonLibrary.ArchLibrary archLibrary;

    private boolean registerCameraAddedEvent = false;

    private Set<CameraAddedListener> cameraAddedListeners = new LinkedHashSet<>();

    public FrameworkInitialisation withoutEventFetcherLogic() {
        useEventFetcher = false;
        return this;
    }

    public FrameworkInitialisation withEventFetcherLogic() {
        useEventFetcher = true;
        return this;
    }

    public FrameworkInitialisation withArchLibrary(final CanonLibrary.ArchLibrary archLibrary) {
        this.archLibrary = Objects.requireNonNull(archLibrary);
        return this;
    }

    public FrameworkInitialisation registerCameraAddedEvent() {
        this.registerCameraAddedEvent = true;
        return this;
    }

    /**
     * Can be called many times for multiple different listeners.
     *
     * @param listener listener to add at initialize
     * @return initializer
     */
    public FrameworkInitialisation withCameraAddedListener(final CameraAddedListener listener) {
        cameraAddedListeners.add(Objects.requireNonNull(listener));
        this.registerCameraAddedEvent = true;
        return this;
    }


    public void initialize() {
        if (archLibrary != null) {
            CanonFactory.canonLibrary().setArchLibraryToUse(archLibrary);
        }

        if (useEventFetcher) {
            CanonFactory.eventFetcherLogic().start();
        }

        if (registerCameraAddedEvent) {
            CanonFactory.commandDispatcher().scheduleCommand(new RegisterCameraAddedEventCommand());
        }

        if (!cameraAddedListeners.isEmpty()) {
            cameraAddedListeners.forEach(CanonFactory.cameraAddedEventLogic()::addCameraAddedListener);
        }


        cameraAddedListeners.clear();
    }

    private void validateOptions() {

    }
}
