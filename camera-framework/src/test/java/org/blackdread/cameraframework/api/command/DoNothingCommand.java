package org.blackdread.cameraframework.api.command;

/**
 * Command that does nothing when ran
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
public class DoNothingCommand extends AbstractCanonCommand<String> {

    @Override
    protected void runInternal() {
    }

}
