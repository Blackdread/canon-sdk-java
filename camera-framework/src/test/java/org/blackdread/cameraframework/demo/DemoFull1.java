package org.blackdread.cameraframework.demo;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.CameraIsConnected;
import org.blackdread.cameraframework.api.camera.CanonCamera;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.TerminateSdkCommand;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.constant.EdsSaveTo;
import org.blackdread.cameraframework.api.helper.Initialisation.FrameworkInitialisation;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyListener;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateListener;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/12/16.</p>
 *
 * @author Yoann CAPLAIN
 */
public class DemoFull1 {

    private static final Logger log = LoggerFactory.getLogger(DemoFull1.class);

    CameraAddedListener cameraAddedListener;
    CameraObjectListener cameraObjectListener;
    CameraPropertyListener cameraPropertyListener;
    CameraStateListener cameraStateListener;

    @Test
    @CameraIsConnected
    void demo() {
        cameraAddedListener = event -> {
            log.info("Camera added detected: {}", event);
        };
        cameraObjectListener = event -> {
            log.info("Object event: {}", event);
        };
        cameraPropertyListener = event -> {
            log.info("Property event: {}", event);
        };
        cameraStateListener = event -> {
            log.info("State event: {}", event);
        };
        new FrameworkInitialisation()
            .registerCameraAddedEvent()
            .withEventFetcherLogic()
            .withCameraAddedListener(cameraAddedListener)
            .initialize();

        final CanonCamera camera = new CanonCamera();

        final EdsdkLibrary.EdsCameraRef edsCameraRef = get(camera.openSession());

        get(camera.getEvent().registerObjectEventCommand());
        get(camera.getEvent().registerPropertyEventCommand());
        get(camera.getEvent().registerStateEventCommand());

        CanonFactory.cameraObjectEventLogic().addCameraObjectListener(cameraObjectListener);
        CanonFactory.cameraPropertyEventLogic().addCameraPropertyListener(edsCameraRef, cameraPropertyListener);
        CanonFactory.cameraStateEventLogic().addCameraStateListener(edsCameraRef, cameraStateListener);

        final String artist = get(camera.getProperty().getArtistAsync());
        log.info("artist: {}", artist);

        final String serial = get(camera.getProperty().getBodyIDExAsync());
        log.info("serial: {}", serial);

        final EdsISOSpeed isoSpeed = get(camera.getProperty().getIsoSpeedAsync());
        log.info("isoSpeed: {}", isoSpeed);

        final EdsSaveTo saveTo = get(camera.getProperty().getSaveToAsync());
        log.info("saveTo: {}", saveTo);

        get(camera.closeSession());

        CanonFactory.commandDispatcher().scheduleCommand(new TerminateSdkCommand());
    }

    private static <R> R get(final CanonCommand<R> command) {
        try {
            return command.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }
}
