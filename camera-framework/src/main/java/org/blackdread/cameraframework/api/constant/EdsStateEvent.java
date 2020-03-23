/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
 * State Event
 * <p>Created on 2018/10/07.<p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
public enum EdsStateEvent implements NativeEnum<Integer> {
    /**
     * Notifies all state events.
     */
    kEdsStateEvent_All("Notify All"),

    /**
     * Indicates that a camera is no longer connected to a computer,
     * whether it was disconnected by unplugging a cord, opening
     * the compact flash compartment, turning the camera off, auto shut-off, or by other means.
     * <br>
     * See API Reference - 4.2.1 kEdsStateEvent_Shutdown
     */
    kEdsStateEvent_Shutdown("Notification of camera disconnection"),

    /**
     * Notifies of whether or not there are objects waiting to
     * be transferred to a host computer.
     * <br>
     * This is useful when ensuring all shot images have been transferred
     * when the application is closed.
     * <br>
     * Notification of this event is not issued for type 1 protocol
     * standard cameras.
     * <br>
     * See API Reference - 4.2.11 kEdsStateEvent_JobStatusChanged
     */
    kEdsStateEvent_JobStatusChanged("Notification of changes in job states"),

    /**
     * Notifies that the camera will shut down after a specific period.
     * Generated only if auto shut-off is set.
     * <br>
     * Exactly when notification is issued (that is, the number of
     * seconds until shutdown) varies depending on the camera model.
     * <br>
     * To continue operation without having the camera shut down,
     * use EdsSendCommand to extend the auto shut-off timer.
     * <br>
     * The time in seconds until the camera shuts down is returned
     * as the initial value.
     * <br>
     * See API Reference - 4.2.15 kEdsStateEvent_WillSoonShutDown
     */
    kEdsStateEvent_WillSoonShutDown("Notification of warnings when the camera will shut off soon"),

    /**
     * As the counterpart event to {@link #kEdsStateEvent_WillSoonShutDown},
     * this event notifies of updates to the number of seconds until
     * a camera shuts down.
     * <br>
     * After the update, the period until shutdown is model-dependent.
     * <br>
     * See API Reference - 4.2.16 kEdsStateEvent_ShutDownTimerUpdate
     */
    kEdsStateEvent_ShutDownTimerUpdate("Notification that the camera will remain on for a longer period"),

    /**
     * Notifies that a requested release has failed, due to focus
     * failure or similar factors.
     * <br>
     * <ul>
     * <li>0x00000001 - Shooting failure</li>
     * <li>0x00000002 - The lens was closed</li>
     * <li>0x00000003 - General errors from the shooting mode, such as errors from the bulb or mirror-up mechanism</li>
     * <li>0x00000004 - Sensor cleaning</li>
     * <li>0x00000005 - Error because the camera was set for silent operation</li>
     * <li>0x00000006 - Prohibited settings using CFn-2, and no card inserted</li>
     * <li>0x00000007 - Card error (including CARD-FULL/No.-FULL)</li>
     * <li>0x00000008 - Write-protected</li>
     * </ul>
     * <br>
     * See API Reference - 4.2.17 kEdsStateEvent_CaptureError
     */
    kEdsStateEvent_CaptureError("Notification of remote release failure"),

    /**
     * Notifies of the exposure time during bulb shooting.
     * <br>
     * Events are issued in about one-second intervals during bulb shooting.
     * <br>
     * However, this event is only issued when bulb shooting is started remotely. (kEdsCameraCommand_BulbStart)
     * <br>
     * See API Reference - 4.2.18 kEdsStateEvent_BulbExposureTime
     */
    kEdsStateEvent_BulbExposureTime("Bulb Exposure Time"),

    /**
     * Notifies of internal SDK errors.
     * <br>
     * If this error event is received, the issuing device will probably
     * not be able to continue working properly, so cancel the remote connection.
     * <br>
     * See API Reference - 4.2.19 kEdsStateEvent_InternalError
     */
    kEdsStateEvent_InternalError("Notification of internal SDK errors"),

    /**
     * Not specified in API Reference
     */
    kEdsStateEvent_AfResult("AF Result");

    private final int value;
    private final String description;

    EdsStateEvent(final String description) {
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
    public static EdsStateEvent ofValue(final Integer value) {
        return ConstantUtil.ofValue(EdsStateEvent.class, value);
    }

}
