package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Property Event
 * <p>Created on 2018/10/07.<p>
 *
 * @author Yoann CAPLAIN
 */
public enum EdsPropertyEvent implements NativeEnum<Integer> {
    /**
     * Notifies all property events.
     */
    kEdsPropertyEvent_All("Notify All"),

    /**
     * Notifies that a camera property value has been changed.
     * <br>
     * The changed property can be retrieved from event data.
     * <br>
     * The changed value can be retrieved by means of EdsGetPropertyData.
     * <br>
     * In the case of type 1 protocol standard cameras,
     * notification of changed properties can only be issued for custom
     * functions (CFn).
     * <br>
     * If the property type is ""/0x0000FFFF, the changed property cannot be
     * identified. Thus, retrieve all required properties repeatedly.
     * <br>
     * See API Reference - 4.2.2 kEdsPropertyEvent_PropertyChanged
     */
    kEdsPropertyEvent_PropertyChanged("Notification of property state changes"),

    /**
     * Notifies of changes in the list of camera properties with
     * configurable values.
     * <br>
     * The list of configurable values for property IDs indicated in event
     * data can be retrieved by means of EdsGetPropertyDesc.
     * <br>
     * For type 1 protocol standard cameras, the property ID is identified
     * as "Unknown" (0x0000FFFF) during notification.
     * <br>
     * Thus, you must retrieve a list of configurable values for all
     * properties and retrieve the property values repeatedly.
     * <br>
     * (For details on properties for which you can retrieve a list of
     * configurable properties, see the description of EdsGetPropertyDesc).
     * <br>
     * See API Reference - 4.2.3 kEdsPropertyEvent_PropertyDescChanged
     */
    kEdsPropertyEvent_PropertyDescChanged("Notification of state changes in configurable property values");

    private final int value;
    private final String description;

    EdsPropertyEvent(final String description) {
        value = LibraryFieldUtil.getNativeIntValue(EdsdkLibrary.class, name());
        this.description = description;
    }

    @Override
    public final Integer value() {
        return value;
    }

    @Override
    public final String description() {
        return description;
    }

}
