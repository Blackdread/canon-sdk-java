package org.blackdread.cameraframework.api.helper.factory;

import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.helper.logic.CameraLogic;
import org.blackdread.cameraframework.api.helper.logic.LiveViewLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyDescLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyDescShortcutLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyGetLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyGetShortcutLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertyLogic;
import org.blackdread.cameraframework.api.helper.logic.PropertySetLogic;
import org.blackdread.cameraframework.api.helper.logic.ShootLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2018/10/21.<p>
 *
 * @author Yoann CAPLAIN
 */
class CanonFactoryTest {

    @Test
    void getCanonFactory() {
        final CanonFactory canonFactory = CanonFactory.getCanonFactory();
        assertNotNull(canonFactory);
    }

    @Test
    void setCanonFactory() {
        final NoneFactory factory = new NoneFactory();
        CanonFactory.setCanonFactory(factory);
        assertSame(factory, CanonFactory.getCanonFactory());
    }

    @Test
    void setCanonFactoryThrowsIfNull() {
        assertThrows(NullPointerException.class, () -> CanonFactory.setCanonFactory(null));
    }

    @Test
    void getCanonLibrary() {
        final CanonLibrary canonLibrary = CanonFactory.getCanonFactory().getCanonLibrary();
        assertNotNull(canonLibrary);

        final CanonLibrary shortcut = CanonFactory.canonLibrary();
        assertSame(canonLibrary, shortcut);
    }

    @Test
    void getCameraLogic() {
        final CameraLogic cameraLogic = CanonFactory.getCanonFactory().getCameraLogic();
        assertNotNull(cameraLogic);

        final CameraLogic shortcut = CanonFactory.cameraLogic();
        assertSame(cameraLogic, shortcut);
    }

    @Test
    void getPropertyLogic() {
        final PropertyLogic propertyLogic = CanonFactory.getCanonFactory().getPropertyLogic();
        assertNotNull(propertyLogic);

        final PropertyLogic shortcut = CanonFactory.propertyLogic();
        assertSame(propertyLogic, shortcut);
    }

    @Test
    void getPropertyDescLogic() {
        final PropertyDescLogic propertyDescLogic = CanonFactory.getCanonFactory().getPropertyDescLogic();
        assertNotNull(propertyDescLogic);

        final PropertyDescLogic shortcut = CanonFactory.propertyDescLogic();
        assertSame(propertyDescLogic, shortcut);
    }

    @Test
    void getPropertyDescShortcutLogic() {
        final PropertyDescShortcutLogic descShortcutLogic = CanonFactory.getCanonFactory().getPropertyDescShortcutLogic();
        assertNotNull(descShortcutLogic);

        final PropertyDescShortcutLogic shortcut = CanonFactory.propertyDescShortcutLogic();
        assertSame(descShortcutLogic, shortcut);
    }

    @Test
    void getPropertySetLogic() {
        final PropertySetLogic setLogic = CanonFactory.getCanonFactory().getPropertySetLogic();
        assertNotNull(setLogic);

        final PropertySetLogic shortcut = CanonFactory.propertySetLogic();
        assertSame(setLogic, shortcut);
    }

    @Test
    void getPropertyGetLogic() {
        final PropertyGetLogic getLogic = CanonFactory.getCanonFactory().getPropertyGetLogic();
        assertNotNull(getLogic);

        final PropertyGetLogic shortcut = CanonFactory.propertyGetLogic();
        assertSame(getLogic, shortcut);
    }

    @Test
    void getPropertyGetShortcutLogic() {
        final PropertyGetShortcutLogic getShortcutLogic = CanonFactory.getCanonFactory().getPropertyGetShortcutLogic();
        assertNotNull(getShortcutLogic);

        final PropertyGetShortcutLogic shortcut = CanonFactory.propertyGetShortcutLogic();
        assertSame(getShortcutLogic, shortcut);
    }

    @Test
    void getLiveViewLogic() {
        final LiveViewLogic liveViewLogic = CanonFactory.getCanonFactory().getLiveViewLogic();
        assertNotNull(liveViewLogic);

        final LiveViewLogic shortcut = CanonFactory.liveViewLogic();
        assertSame(liveViewLogic, shortcut);
    }

    @Test
    void getShootLogic() {
        final ShootLogic shootLogic = CanonFactory.getCanonFactory().getShootLogic();
        assertNotNull(shootLogic);

        final ShootLogic shortcut = CanonFactory.shootLogic();
        assertSame(shootLogic, shortcut);
    }


    public static class NoneFactory extends CanonFactory {

    }
}
