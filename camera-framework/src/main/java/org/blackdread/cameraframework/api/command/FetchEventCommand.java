package org.blackdread.cameraframework.api.command;

import org.blackdread.cameraframework.api.constant.EdsdkError;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

import static org.blackdread.cameraframework.util.ErrorUtil.toEdsdkError;

/**
 * Make the dispatcher fetch events
 *
 * <p>Created on 2018/12/12.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public class FetchEventCommand extends AbstractCanonCommand<Void> {

    private final boolean throwOnGetEventError;
    private final int fetchCount;

    public FetchEventCommand() {
        this.throwOnGetEventError = true;
        this.fetchCount = 1;
    }

    public FetchEventCommand(final boolean throwOnGetEventError, final int fetchCount) {
        if (fetchCount <= 0) {
            throw new IllegalArgumentException("Fetch count must > 0");
        }
        this.throwOnGetEventError = throwOnGetEventError;
        this.fetchCount = fetchCount;
    }

    public FetchEventCommand(final FetchEventCommand toCopy) {
        super(toCopy);
        this.throwOnGetEventError = toCopy.throwOnGetEventError;
        this.fetchCount = toCopy.fetchCount;
    }

    @Override
    protected Void runInternal() {
        for (int i = 0; i < fetchCount; i++) {
            final EdsdkError edsdkError = toEdsdkError(CanonFactory.edsdkLibrary().EdsGetEvent());
            if (throwOnGetEventError && edsdkError != EdsdkError.EDS_ERR_OK) {
                throw edsdkError.getException();
            }
        }
        return null;
    }
}
