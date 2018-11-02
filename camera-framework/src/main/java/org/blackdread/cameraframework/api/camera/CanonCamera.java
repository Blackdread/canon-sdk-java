package org.blackdread.cameraframework.api.camera;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsCameraRef;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsObjectEventHandler;
import org.blackdread.camerabinding.jna.EdsdkLibrary.EdsPropertyEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created on 2018/11/01.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CanonCamera {

    private EdsCameraRef cameraRef;

    private final List<EdsdkLibrary.EdsStateEventHandler> stateEventHandlers = new ArrayList<>();

    private final List<EdsPropertyEventHandler> propertyEventHandlers = new ArrayList<>();

    private final List<EdsObjectEventHandler> objectEventHandlers = new ArrayList<>();

    private final Shoot shoot = new Shoot();

    private final Property property = new Property();


    public static final class Shoot {

    }

    public static final class Property {

    }
}
