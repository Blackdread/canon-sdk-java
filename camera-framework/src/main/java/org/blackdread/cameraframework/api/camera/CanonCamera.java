package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsObjectEventHandler;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsPropertyEventHandler;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.GetPropertyCommand.ProductName;
import org.blackdread.cameraframework.api.command.SetPropertyCommand;
import org.blackdread.cameraframework.api.command.ShootCommand;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.command.decorator.builder.CommandBuilderReusable;
import org.blackdread.cameraframework.api.constant.EdsISOSpeed;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CanonCamera {

    private EdsCameraRef cameraRef;

    private final List<EdsdkLibrary.EdsStateEventHandler> stateEventHandlers = new ArrayList<>();

    private final List<EdsPropertyEventHandler> propertyEventHandlers = new ArrayList<>();

    private final List<EdsObjectEventHandler> objectEventHandlers = new ArrayList<>();

    /**
     * Default decorator builder for all commands created by this canon camera.
     * If null then is not applied.
     */
    private CommandBuilderReusable commandBuilderReusable;

    private final Shoot shoot = new Shoot();

    private final Property property = new Property();

    protected <T extends CanonCommand<R>, R> T applyTarget(final T command) {
        if (!command.getTargetRef().isPresent()) {
            command.setTargetRef(cameraRef);
        }
        return command;
    }

    protected <T extends CanonCommand<R>, R> T applyExtraOptions(final T command) {

        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T extends CanonCommand<R>, R> T applyDefaultCommandDecoration(final T command) {
        if (commandBuilderReusable != null) {
            return (T) commandBuilderReusable.setCanonCommand(command)
                .build();
        }
        return command;
    }

    protected <T extends CanonCommand<R>, R> T dispatchCommand(T command) {
        command = applyTarget(command);
        command = applyDefaultCommandDecoration(command);
        CanonFactory.commandDispatcher().scheduleCommand(cameraRef, command);
        return command;
    }

    public Shoot getShoot() {
        return shoot;
    }

    public Property getProperty() {
        return property;
    }

    public final class Shoot {

        public List<File> shoot() throws ExecutionException, InterruptedException {
            return dispatchCommand(new ShootCommand()).get();
        }

        public ShootCommand shootAsync() {
            return dispatchCommand(new ShootCommand());
        }

        public ShootCommand shootAsync(final ShootOption shootOption) {
            return dispatchCommand(new ShootCommand(shootOption));
        }

    }

    public final class Property {

        public String getProductName() throws ExecutionException, InterruptedException {
            return dispatchCommand(new ProductName()).get();
        }

        public ProductName getProductNameAsync() {
            return dispatchCommand(new ProductName());
        }


        public SetPropertyCommand.IsoSpeed setIsoSpeedAsync(final EdsISOSpeed value) {
            return dispatchCommand(new SetPropertyCommand.IsoSpeed(value));
        }
    }
}
