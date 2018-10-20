package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.cameraframework.api.helper.logic.LiveViewLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Objects;

/**
 * <p>Created on 2018/10/09.<p>
 *
 * @author Yoann CAPLAIN
 */
@NotThreadSafe
public class HelperFactory {

    /**
     * Mutable to allow user to define its own factory and overrides logic.
     * If different factory is desired, it should be set as soon as possible in the main class then never changed again (can but not desirable and not volatile)
     */
    private static HelperFactory helperFactory = new HelperFactory();

    protected HelperFactory() {
    }

    public static HelperFactory getHelperFactory() {
        return helperFactory;
    }

    public static void setHelperFactory(final HelperFactory helperFactory) {
        HelperFactory.helperFactory = Objects.requireNonNull(helperFactory);
    }

    PropertyLogic getPropertyLogic() {
        return PropertyLogicDefault.DEFAULT;
    }

    LiveViewLogic getLiveViewLogic() {
//        return LiveViewLogicDefault.DEFAULT;
        return null;
    }

    ShootLogic getShootLogic() {
//        return ShootLogicDefault.DEFAULT;
        return null;
    }
}
