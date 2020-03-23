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
package org.blackdread.cameraframework;

import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to enable tests only if DLL is on path
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnabledIfSystemProperty(named = "canonCameraConnected", matches = "true")
@EnabledIfSystemProperty(named = "canonLibIsOnPath", matches = "true")
//@EnabledIf("systemProperty.get('canonCameraConnected') == 'true' || systemProperty.get('canonLibIsOnPath') == 'true'")
public @interface DllOnPath {
    // works but is not repeatable so not very useful when need to test many things
    // if camera is connected then we suppose that DLL are present
}
