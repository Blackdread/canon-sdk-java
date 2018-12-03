package org.blackdread.cameraframework.api.command;

import java.io.File;
import java.util.List;

/**
 * <p>Created on 2018/10/02.<p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class ShootCommand extends AbstractCanonCommand<List<File>> {

    public ShootCommand() {
    }

    public ShootCommand(final ShootCommand toCopy) {
        super(toCopy);
    }
}
