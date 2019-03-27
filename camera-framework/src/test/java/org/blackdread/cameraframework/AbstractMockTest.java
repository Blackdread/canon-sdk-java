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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allow to give access to mocked instance directly in more intuitive way and reduce duplicate code
 * <p>Created on 2019/03/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class AbstractMockTest implements MockFactory {

    private static final Logger log = LoggerFactory.getLogger(AbstractMockTest.class);

    protected CanonLibrary canonLibrary;
    protected CommandDispatcher commandDispatcher;
    protected EventFetcherLogic eventFetcherLogic;

    protected CameraAddedEventLogic cameraAddedEventLogic;
    protected CameraObjectEventLogic cameraObjectEventLogic;
    protected CameraPropertyEventLogic cameraPropertyEventLogic;
    protected CameraStateEventLogic cameraStateEventLogic;

    protected CameraLogic cameraLogic;
    protected LiveViewLogic liveViewLogic;

    protected PropertyLogic propertyLogic;
    protected PropertyGetLogic propertyGetLogic;
    protected PropertyDescLogic propertyDescLogic;

    protected PropertySetLogic propertySetLogic;

    protected ShootLogic shootLogic;
    protected FileLogic fileLogic;

    protected PropertyGetShortcutLogic propertyGetShortcutLogic;
    protected PropertyDescShortcutLogic propertyDescShortcutLogic;


    @AfterAll
    static void tearDownClassMocks() {
        MockFactory.onTearDownClass();
    }

    @BeforeEach
    void setUpMocks() {
        setUpCanonFactoryMock();

        canonLibrary = CanonFactory.canonLibrary();
        commandDispatcher = CanonFactory.commandDispatcher();
        eventFetcherLogic = CanonFactory.eventFetcherLogic();
        cameraAddedEventLogic = CanonFactory.cameraAddedEventLogic();
        cameraObjectEventLogic = CanonFactory.cameraObjectEventLogic();
        cameraPropertyEventLogic = CanonFactory.cameraPropertyEventLogic();
        cameraStateEventLogic = CanonFactory.cameraStateEventLogic();
        cameraLogic = CanonFactory.cameraLogic();
        liveViewLogic = CanonFactory.liveViewLogic();
        propertyLogic = CanonFactory.propertyLogic();
        propertyGetLogic = CanonFactory.propertyGetLogic();
        propertyDescLogic = CanonFactory.propertyDescLogic();
        propertySetLogic = CanonFactory.propertySetLogic();
        shootLogic = CanonFactory.shootLogic();
        fileLogic = CanonFactory.fileLogic();
        propertyGetShortcutLogic = CanonFactory.propertyGetShortcutLogic();
        propertyDescShortcutLogic = CanonFactory.propertyDescShortcutLogic();
    }

}
