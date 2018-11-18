package org.blackdread.cameraframework.api.command.contract;

/**
 * <p>Created on 2018/10/08.<p>
 *
 * @author Yoann CAPLAIN
 * @deprecated not meaningful and not usable
 */
@Deprecated
public enum ErrorLogic {
    /**
     * Errors are not rethrown but result of command will be set to null or an empty optional.
     * There should be no cases where an error will result in a different "correct"/"default" result unless specified.
     */
    SKIP_ERRORS,
    /**
     * Throw only on critical errors (might be 95% of errors) that does
     * not allow to continue logic of command execution.
     *
     * @deprecated not sure
     */
    THROW_CRITICAL_ERRORS,
    /**
     * Any error will result in an exception in command and will be rethrown when result is requested
     */
    THROW_ALL_ERRORS
}
