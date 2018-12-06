package org.blackdread.cameraframework.api.helper.logic;

import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.cameraframework.api.command.CanonCommand;

/**
 * <p>Created on 2018/11/03</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CommandDispatcher {

    void execute(final CanonCommand<?> command);

    void execute(final EdsCameraRef owner, final CanonCommand<?> command);

}
