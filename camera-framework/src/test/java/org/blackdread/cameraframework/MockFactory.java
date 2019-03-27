/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
package org.blackdread.cameraframework;

import org.blackdread.cameraframework.api.CanonLibrary;
import org.blackdread.cameraframework.api.helper.factory.CanonFactory;
import org.blackdread.cameraframework.api.helper.logic.*;
import org.blackdread.cameraframework.api.helper.logic.event.CameraAddedEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraObjectEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraPropertyEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.CameraStateEventLogic;
import org.blackdread.cameraframework.api.helper.logic.event.EventFetcherLogic;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
//@ExtendWith(MockitoExtension.class) // No use, can be specified directly by the implementing test if needed
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
     * To be called in method annotated with {@code org.junit.jupiter.api.BeforeEach}.
     * <br>
     * Better when need to recreate mock before each test which makes not possible to use below (without reset())
     * <pre>
     * <code>@</code>Mock
     * private CanonLibrary canonLibrary;
     * <code>@</code>Mock
     * private CommandDispatcher commandDispatcher;
     * <code>@</code>Mock
     * private EventFetcherLogic eventFetcherLogic;
     * <code>@</code>Mock
     * private CameraAddedEventLogic cameraAddedEventLogic;
     * </pre>
     */
    default void setUpCanonFactoryMock() {
        final CanonFactory canonFactory = Mockito.mock(CanonFactory.class);
        CanonFactory.setCanonFactory(canonFactory);

        // Might want to use lenient

        when(canonFactory.getCanonLibrary()).thenReturn(Mockito.mock(CanonLibrary.class));

        when(canonFactory.getCommandDispatcher()).thenReturn(Mockito.mock(CommandDispatcher.class));
        when(canonFactory.getEventFetcherLogic()).thenReturn(Mockito.mock(EventFetcherLogic.class));

        when(canonFactory.getCameraAddedEventLogic()).thenReturn(Mockito.mock(CameraAddedEventLogic.class));
        when(canonFactory.getCameraObjectEventLogic()).thenReturn(Mockito.mock(CameraObjectEventLogic.class));
        when(canonFactory.getCameraPropertyEventLogic()).thenReturn(Mockito.mock(CameraPropertyEventLogic.class));
        when(canonFactory.getCameraStateEventLogic()).thenReturn(Mockito.mock(CameraStateEventLogic.class));

        when(canonFactory.getCameraLogic()).thenReturn(Mockito.mock(CameraLogic.class));
        when(canonFactory.getLiveViewLogic()).thenReturn(Mockito.mock(LiveViewLogic.class));

        when(canonFactory.getPropertyLogic()).thenReturn(Mockito.mock(PropertyLogic.class));
        when(canonFactory.getPropertyGetLogic()).thenReturn(Mockito.mock(PropertyGetLogic.class));
        when(canonFactory.getPropertyDescLogic()).thenReturn(Mockito.mock(PropertyDescLogic.class));

        when(canonFactory.getPropertySetLogic()).thenReturn(Mockito.mock(PropertySetLogic.class));

        when(canonFactory.getShootLogic()).thenReturn(Mockito.mock(ShootLogic.class));
        when(canonFactory.getFileLogic()).thenReturn(Mockito.mock(FileLogic.class));

        when(canonFactory.getPropertyGetShortcutLogic()).thenReturn(Mockito.mock(PropertyGetShortcutLogic.class));
        when(canonFactory.getPropertyDescShortcutLogic()).thenReturn(Mockito.mock(PropertyDescShortcutLogic.class));

    }
}
