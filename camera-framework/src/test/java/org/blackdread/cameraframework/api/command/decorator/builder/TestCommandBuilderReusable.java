package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.AbstractCanonCommand;

/**
 * To test and provide extra functions for tests
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
public class TestCommandBuilderReusable<R> extends CommandBuilderReusable<TestCommandBuilderReusable<R>, R> {

    public TestCommandBuilderReusable() {
        super();
    }

    public TestCommandBuilderReusable(final AbstractCanonCommand<R> canonCommand) {
        super(canonCommand);
    }

}
