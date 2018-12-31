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

import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 * @deprecated Not useful
 */
final class LibraryUtil {

    private List<String> getConstantInterfaceNames() {
        final Class<?>[] classes = EdsdkLibrary.class.getClasses();

        if (classes.length == 0)
            throw new IllegalStateException("");

        return Arrays.stream(classes)
            .filter(aClass -> !Structure.class.isAssignableFrom(aClass))
            .filter(aClass -> !PointerType.class.isAssignableFrom(aClass))
            .filter(aClass -> !Structure.ByReference.class.isAssignableFrom(aClass))
            .filter(aClass -> !StdCallLibrary.StdCallCallback.class.isAssignableFrom(aClass))
            .map(Class::getSimpleName)
            .collect(Collectors.toList());
    }
}
