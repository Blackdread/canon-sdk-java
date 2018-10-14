package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.AbstractCanonCommand;

/**
 * To test and provide extra functions for tests
 * <p>Created on 2018/10/14.<p>
 *
 * @author Yoann CAPLAIN
 */
public class TestCommandBuilder<R> extends CommandBuilder<TestCommandBuilder<R>, R> {

    public TestCommandBuilder(final AbstractCanonCommand<R> canonCommand) {
        super(canonCommand);
    }

}
