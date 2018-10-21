package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.helper.logic.LiveViewLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
@NotThreadSafe
public class CanonFactory {

    private static final Logger log = LoggerFactory.getLogger(CanonFactory.class);

    private static final AtomicInteger factoryChangedCount = new AtomicInteger(0);

    /**
     * Mutable to allow user to define its own factory and overrides logic.
     * If different factory is desired, it should be set as soon as possible in the main class then never changed again (can but not desirable and not volatile)
     */
    private static CanonFactory canonFactory = new CanonFactory();


    // TODO if factory is changed, we should give protected method to let user modify or set to null those variables
    private static final CanonLibrary canonLibrary = new CanonLibraryImpl();

    private static final PropertyLogic propertyLogic = new PropertyLogicDefault();

    private static final LiveViewLogic liveViewLogic = new LiveViewLogicDefault();

    private static final ShootLogic shootLogic = new ShootLogicDefault();

    /**
     * Should never be called directly, is protected to allow to extend
     */
    protected CanonFactory() {
    }

    public static CanonFactory getCanonFactory() {
        return canonFactory;
    }

    public static void setCanonFactory(final CanonFactory canonFactory) {
        CanonFactory.canonFactory = Objects.requireNonNull(canonFactory);
        log.info("Canon factory has been modified {} time(s)", factoryChangedCount.incrementAndGet());
    }

    public CanonLibrary getCanonLibrary() {
        return canonLibrary;
    }

    public PropertyLogic getPropertyLogic() {
        return propertyLogic;
    }

    public LiveViewLogic getLiveViewLogic() {
        return liveViewLogic;
    }

    public ShootLogic getShootLogic() {
        return shootLogic;
    }

}
