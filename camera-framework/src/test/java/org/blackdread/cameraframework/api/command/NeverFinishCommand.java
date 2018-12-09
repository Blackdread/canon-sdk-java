package org.blackdread.cameraframework.api.command;

/**
 * Command that does not finish when ran (60 sec is like infinite time)
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
public class NeverFinishCommand extends AbstractCanonCommand<String> {

    public NeverFinishCommand() {
    }

    public NeverFinishCommand(final NeverFinishCommand toCopy) {
        super(toCopy);
    }

    @Override
    protected void runInternal() throws InterruptedException {
        Thread.sleep(60_000);
    }

}
