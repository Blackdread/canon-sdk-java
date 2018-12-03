package org.blackdread.cameraframework.api.command.contract;

import org.blackdread.cameraframework.api.command.CanonCommand;

/**
 * General contract to copy a command to be able to send it (re-send the original one but its copy)
 * <p>Created on 2018/12/03.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CopyCommand<R> {

    /*
     * If instance on which copy() is called is a "normal" command meaning it has no decorators then it returns the copy (without decorators of course as there was none).
     *
     * If instance is a decorator then user may use getRoot() from RootDecoratorCommand class to copy only the "normal" command without the decorators.
     * Otherwise decorators will be copied and applied to the returned command of copy() method
     */

    /**
     * Allow to re-send a command as a command cannot be re-sent without making a copy.
     * <p>Not all fields need to be copied</p>
     *
     * @return copied command
     */
    CanonCommand<R> copy();

}
