package org.blackdread.cameraframework.api.command;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.builder.ShootOption;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import java.io.File;
import java.util.List;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public class ShootCommand extends AbstractCanonCommand<List<File>> {

    private final ShootOption shootOption;

    public ShootCommand() {
        shootOption = null;
    }

    public ShootCommand(final ShootOption shootOption) {
        this.shootOption = shootOption;
    }

    public ShootCommand(final ShootCommand toCopy) {
        super(toCopy);
        this.shootOption = toCopy.shootOption;
    }

    @Override
    protected List<File> runInternal() throws InterruptedException {
        final EdsCameraRef ref = (EdsCameraRef) getTargetRefInternal();
        if (shootOption == null) {
            return CanonFactory.shootLogic().shoot(ref);
        } else {
            return CanonFactory.shootLogic().shoot(ref, shootOption);
        }
    }
}
