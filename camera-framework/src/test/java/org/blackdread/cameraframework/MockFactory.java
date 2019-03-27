package org.blackdread.cameraframework;

import org.blackdread.cameraframework.api.helper.factory.CanonFactory;

/**
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface MockFactory {

    /**
     * Initial factory that should be set back at end of test class to not impact next test classes
     */
    CanonFactory initialCanonFactory = CanonFactory.getCanonFactory();

    /**
     * To be called in method annotated with {@code org.junit.jupiter.api.AfterAll}
     */
    static void onTearDownClass() {
        CanonFactory.setCanonFactory(initialCanonFactory);
    }

    /**
     * To be called in method annotated with {@code org.junit.jupiter.api.BeforeEach}
     */
    default void setUpMock() {
        // TODO later can create all mocks for each element of CanonFactory
    }
}
