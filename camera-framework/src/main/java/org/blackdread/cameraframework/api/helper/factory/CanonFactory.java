package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.helper.logic.*;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.EventFetcherLogic;
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

    // TODO this is not lazy...
    private static final CommandDispatcher commandDispatcher = SingleCommandDispatcher.getInstance();

    private static final EventFetcherLogic eventFetcherLogic = new EventFetcherLogicDefault();

    // TODO if factory is changed, we should give protected method to let user modify or set to null those variables
    private static final CanonLibrary canonLibrary = new CanonLibraryImpl();

    private static final CameraLogic cameraLogic = new CameraLogicDefault();

    private static final CameraAddedEventLogic cameraAddedEventLogic = new CameraAddedEventLogicDefault();

    private static final CameraObjectEventLogic cameraObjectEventLogic = new CameraObjectEventLogicDefault();

    private static final CameraPropertyEventLogic cameraPropertyEventLogic = new CameraPropertyEventLogicDefault();

    private static final CameraStateEventLogic cameraStateEventLogic = new CameraStateEventLogicDefault();

    private static final PropertyLogic propertyLogic = new PropertyLogicDefault();

    private static final PropertyDescLogic propertyDescLogic = new PropertyDescLogicDefault();

    private static final PropertyDescShortcutLogic propertyDescShortcutLogic = new PropertyDescShortcutLogicDefault();

    private static final PropertyGetLogic propertyGetLogic = new PropertyGetLogicDefault();

    private static final PropertyGetShortcutLogic propertyGetShortcutLogic = new PropertyGetShortcutLogicDefault();

    private static final PropertySetLogic propertySetLogic = new PropertySetLogicDefault();

    private static final LiveViewLogic liveViewLogic = new LiveViewLogicDefault();

    private static final ShootLogic shootLogic = new ShootLogicDefault();

    private static final FileLogic fileLogic = new FileLogicDefault();

    /**
     * Should never be called directly, is protected to allow to extend
     */
    protected CanonFactory() {
    }

    /**
     * @return instance of factory, never null
     */
    public static CanonFactory getCanonFactory() {
        return canonFactory;
    }

    /**
     * Allow to set a custom factory to return your own implementation of the different interfaces that this factory returns.
     * <p>It should be called as soon as possible, better first lines of main</p>
     * <p>It is not thread-safe</p>
     *
     * @param canonFactory factory to set (non null)
     */
    public static void setCanonFactory(final CanonFactory canonFactory) {
        CanonFactory.canonFactory = Objects.requireNonNull(canonFactory);
        log.info("Canon factory has been modified {} time(s)", factoryChangedCount.incrementAndGet());
    }

    /**
     * Shortcut for {@link CanonFactory#getCommandDispatcher()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCommandDispatcher() }
     * </pre>
     *
     * @return command dispatcher instance, never null
     */
    public static CommandDispatcher commandDispatcher() {
        return CanonFactory.getCanonFactory().getCommandDispatcher();
    }


    /**
     * Shortcut for {@link CanonFactory#getEventFetcherLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getEventFetcherLogic(); }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static EventFetcherLogic eventFetcherLogic() {
        return CanonFactory.getCanonFactory().getEventFetcherLogic();
    }

    /**
     * Shortcut for {@link CanonLibrary#edsdkLibrary()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCanonLibrary().edsdkLibrary(); }
     * </pre>
     *
     * @return library instance, never null, throws if failed to init library
     */
    public static EdsdkLibrary edsdkLibrary() {
        return CanonFactory.getCanonFactory().getCanonLibrary().edsdkLibrary();
    }

    /**
     * Shortcut for {@link CanonFactory#getCanonLibrary()} ()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCanonLibrary() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static CanonLibrary canonLibrary() {
        return CanonFactory.getCanonFactory().getCanonLibrary();
    }

    /**
     * Shortcut for {@link CanonFactory#getCameraLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCameraLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static CameraLogic cameraLogic() {
        return CanonFactory.getCanonFactory().getCameraLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getCameraAddedEventLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCameraAddedEventLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static CameraAddedEventLogic cameraAddedEventLogic() {
        return CanonFactory.getCanonFactory().getCameraAddedEventLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getCameraObjectEventLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCameraObjectEventLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static CameraObjectEventLogic cameraObjectEventLogic() {
        return CanonFactory.getCanonFactory().getCameraObjectEventLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getCameraPropertyEventLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCameraPropertyEventLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static CameraPropertyEventLogic cameraPropertyEventLogic() {
        return CanonFactory.getCanonFactory().getCameraPropertyEventLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getCameraStateEventLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getCameraStateEventLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static CameraStateEventLogic cameraStateEventLogic() {
        return CanonFactory.getCanonFactory().getCameraStateEventLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getPropertyLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getPropertyLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static PropertyLogic propertyLogic() {
        return CanonFactory.getCanonFactory().getPropertyLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getPropertyDescLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getPropertyDescLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static PropertyDescLogic propertyDescLogic() {
        return CanonFactory.getCanonFactory().getPropertyDescLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getPropertyDescShortcutLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getPropertyDescShortcutLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static PropertyDescShortcutLogic propertyDescShortcutLogic() {
        return CanonFactory.getCanonFactory().getPropertyDescShortcutLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getPropertyGetLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getPropertyGetLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static PropertyGetLogic propertyGetLogic() {
        return CanonFactory.getCanonFactory().getPropertyGetLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getPropertyGetShortcutLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getPropertyGetShortcutLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static PropertyGetShortcutLogic propertyGetShortcutLogic() {
        return CanonFactory.getCanonFactory().getPropertyGetShortcutLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getPropertySetLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getPropertySetLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static PropertySetLogic propertySetLogic() {
        return CanonFactory.getCanonFactory().getPropertySetLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getLiveViewLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getLiveViewLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static LiveViewLogic liveViewLogic() {
        return CanonFactory.getCanonFactory().getLiveViewLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getShootLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getShootLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static ShootLogic shootLogic() {
        return CanonFactory.getCanonFactory().getShootLogic();
    }

    /**
     * Shortcut for {@link CanonFactory#getFileLogic()}
     * <pre>
     * {@code CanonFactory.getCanonFactory().getFileLogic() }
     * </pre>
     *
     * @return logic instance, never null
     */
    public static FileLogic fileLogic() {
        return CanonFactory.getCanonFactory().getFileLogic();
    }


    /**
     * @return command dispatcher
     * @see CommandDispatcher
     */
    public CommandDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    /**
     * @return event fetcher logic
     * @see EventFetcherLogic
     */
    public EventFetcherLogic getEventFetcherLogic() {
        return eventFetcherLogic;
    }

    /**
     * @return canon library
     * @see CanonLibrary
     */
    public CanonLibrary getCanonLibrary() {
        return canonLibrary;
    }

    /**
     * @return camera logic
     * @see CameraLogic
     */
    public CameraLogic getCameraLogic() {
        return cameraLogic;
    }

    /**
     * @return camera added event logic
     * @see CameraAddedEventLogic
     */
    public CameraAddedEventLogic getCameraAddedEventLogic() {
        return cameraAddedEventLogic;
    }

    /**
     * @return camera object event logic
     * @see CameraObjectEventLogic
     */
    public CameraObjectEventLogic getCameraObjectEventLogic() {
        return cameraObjectEventLogic;
    }

    /**
     * @return camera property event logic
     * @see CameraPropertyEventLogic
     */
    public CameraPropertyEventLogic getCameraPropertyEventLogic() {
        return cameraPropertyEventLogic;
    }

    /**
     * @return camera state event logic
     * @see CameraStateEventLogic
     */
    public CameraStateEventLogic getCameraStateEventLogic() {
        return cameraStateEventLogic;
    }

    /**
     * @return property logic
     * @see PropertyLogic
     */
    public PropertyLogic getPropertyLogic() {
        return propertyLogic;
    }

    /**
     * @return property desc logic
     * @see PropertyDescLogic
     */
    public PropertyDescLogic getPropertyDescLogic() {
        return propertyDescLogic;
    }

    /**
     * @return property desc shortcut logic
     * @see PropertyDescShortcutLogic
     */
    public PropertyDescShortcutLogic getPropertyDescShortcutLogic() {
        return propertyDescShortcutLogic;
    }

    /**
     * @return property get logic
     * @see PropertyGetLogic
     */
    public PropertyGetLogic getPropertyGetLogic() {
        return propertyGetLogic;
    }

    /**
     * @return property get shortcut logic
     * @see PropertyGetShortcutLogic
     */
    public PropertyGetShortcutLogic getPropertyGetShortcutLogic() {
        return propertyGetShortcutLogic;
    }

    /**
     * @return property set logic
     * @see PropertySetLogic
     */
    public PropertySetLogic getPropertySetLogic() {
        return propertySetLogic;
    }

    /**
     * @return live view logic
     * @see LiveViewLogic
     */
    public LiveViewLogic getLiveViewLogic() {
        return liveViewLogic;
    }

    /**
     * @return shoot logic
     * @see ShootLogic
     */
    public ShootLogic getShootLogic() {
        return shootLogic;
    }

    /**
     * @return file logic
     * @see FileLogic
     */
    public FileLogic getFileLogic() {
        return fileLogic;
    }

}
