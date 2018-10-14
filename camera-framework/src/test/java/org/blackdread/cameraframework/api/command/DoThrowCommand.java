package org.blackdread.cameraframework.api.command;

/**
 * Command that always throw when ran
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
public class DoThrowCommand extends AbstractCanonCommand<String> {

    @Override
    protected void runInternal() {
        throw new RuntimeException("I always throw");
    }

}
