package org.blackdread.cameraframework.api.helper.Initialisation;

import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.InitializeSdkCommand;
import org.blackdread.cameraframework.api.command.RegisterCameraAddedEventCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
     * Auto set to true to register camera added event.
     *
     * @param listener listener to add at initialize
     * @return initializer
     */
    public FrameworkInitialisation withCameraAddedListener(final CameraAddedListener listener) {
        cameraAddedListeners.add(Objects.requireNonNull(listener));
        this.registerCameraAddedEvent = true;
        return this;
    }


    /**
     * Initialize the framework.
     * Caller should check each command returned to make sure no error occurred.
     *
     * @return list of commands that were created and sent
     */
    public List<CanonCommand> initialize() {
        validate();
        final ArrayList<CanonCommand> commandSent = new ArrayList<>();

        if (archLibrary != null) {
            CanonFactory.canonLibrary().setArchLibraryToUse(archLibrary);
        }

        if (shouldInitializeSdk()) {
            final InitializeSdkCommand command = new InitializeSdkCommand();
            commandSent.add(command);
            CanonFactory.commandDispatcher().scheduleCommand(command);
        }

        if (useEventFetcher) {
            CanonFactory.eventFetcherLogic().start();
        }

        if (registerCameraAddedEvent) {
            final RegisterCameraAddedEventCommand command = new RegisterCameraAddedEventCommand();
            commandSent.add(command);
            CanonFactory.commandDispatcher().scheduleCommand(command);
        }

        if (!cameraAddedListeners.isEmpty()) {
            cameraAddedListeners.forEach(CanonFactory.cameraAddedEventLogic()::addCameraAddedListener);
        }

        cameraAddedListeners.clear();

        return commandSent;
    }

    private boolean shouldInitializeSdk() {
        // In fact we could always return true, because this a class to initialize the framework !
        return useEventFetcher || registerCameraAddedEvent || !cameraAddedListeners.isEmpty();
    }

    private void validate() {

    }
}
