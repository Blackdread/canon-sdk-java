package org.blackdread.cameraframework.api.helper.initialisation;

import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.CommandDispatcher;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.EventFetcherLogic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

/**
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class FrameworkInitialisationMockTest {

    private static final CanonFactory initialCanonFactory = CanonFactory.getCanonFactory();

    private final AtomicInteger eventCount = new AtomicInteger(0);

    private final CameraAddedListener cameraAddedListener = event -> eventCount.getAndIncrement();

    // Mock
    private CanonLibrary canonLibrary;
    private CommandDispatcher commandDispatcher;
    private EventFetcherLogic eventFetcherLogic;
    private CameraAddedEventLogic cameraAddedEventLogic;

    @BeforeAll
    static void setUpClass() {
    }

    @AfterAll
    static void tearDownClass() {
        CanonFactory.setCanonFactory(initialCanonFactory);
    }

    @BeforeEach
    void setUp() {
        final CanonFactory canonFactory = Mockito.mock(CanonFactory.class);
        CanonFactory.setCanonFactory(canonFactory);

        canonLibrary = Mockito.mock(CanonLibrary.class);
        commandDispatcher = Mockito.mock(CommandDispatcher.class);
        eventFetcherLogic = Mockito.mock(EventFetcherLogic.class);
        cameraAddedEventLogic = Mockito.mock(CameraAddedEventLogic.class);

        when(canonFactory.getCanonLibrary()).thenReturn(canonLibrary);
        when(canonFactory.getCommandDispatcher()).thenReturn(commandDispatcher);
        when(canonFactory.getEventFetcherLogic()).thenReturn(eventFetcherLogic);
        when(canonFactory.getCameraAddedEventLogic()).thenReturn(cameraAddedEventLogic);

        eventCount.set(0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initializeDefault() throws ExecutionException, InterruptedException {
        final List<CanonCommand> commands = new FrameworkInitialisation()
            .initialize();

        Assertions.assertEquals(0, commands.size());

        for (CanonCommand canonCommand : commands) {
            canonCommand.get();
        }
    }

    @Test
    void initializeAll() throws ExecutionException, InterruptedException {
        final CameraAddedListener customListener = event -> {
        };
        final List<CanonCommand> commands = new FrameworkInitialisation()
            .registerCameraAddedEvent()
            .withArchLibrary(CanonLibrary.ArchLibrary.FORCE_32)
            .withoutEventFetcherLogic()
            .withEventFetcherLogic()
            .withCameraAddedListener(this.cameraAddedListener)
            .withCameraAddedListener(customListener)
            .initialize();

        verify(canonLibrary, times(1)).setArchLibraryToUse(CanonLibrary.ArchLibrary.FORCE_32);
        verify(commandDispatcher, times(2)).scheduleCommand(any());
        verify(eventFetcherLogic, times(1)).start();
        verify(cameraAddedEventLogic, times(1)).addCameraAddedListener(this.cameraAddedListener);
        verify(cameraAddedEventLogic, times(1)).addCameraAddedListener(customListener);
        verify(cameraAddedEventLogic, times(2)).addCameraAddedListener(any());
        verify(cameraAddedEventLogic, times(0)).addCameraAddedListener(event -> {});

        Assertions.assertEquals(2, commands.size());

        for (CanonCommand canonCommand : commands) {
            // infinite wait as commands are not actually processed for now
//            canonCommand.get();
        }
    }
}
