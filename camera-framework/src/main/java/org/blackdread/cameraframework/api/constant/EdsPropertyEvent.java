/*
 * MIT License
 *
 * Copyright (c) 2018 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.constant;

import org.blackdread.camerabinding.jna.EdsdkLibrary;
import org.blackdread.cameraframework.util.LibraryFieldUtil;

/**
 * Property Event
 * <p>Created on 2018/10/07.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
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

    /**
     * @param value value to search
     * @return enum having same value as passed
     * @throws IllegalArgumentException if value was not found
     */
    public static EdsPropertyEvent ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsPropertyEvent.class, value);
    }

}
