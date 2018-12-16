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

import com.google.common.collect.Lists;
import org.blackdread.camerabinding.jna.EdsdkLibrary;

import java.util.List;

/**
 * <p>Created on 2018/10/05.<p>
 *
 * @author Yoann CAPLAIN
 */
class EdsBatteryLevel2Test extends ConstantValueFromLibraryTest<EdsBatteryLevel2> {

    @Override
    EdsBatteryLevel2 getOneEnumValue() {
        return EdsBatteryLevel2.kEdsBatteryLevel2_AC;
    }

    @Override
    EdsBatteryLevel2[] getAllEnumValues() {
        return EdsBatteryLevel2.values();
    }

    @Override
    Class<?> getLibraryClass() {
        return EdsdkLibrary.EdsBatteryLevel2.class;
    }

    @Override
    List<EdsBatteryLevel2> skipCheckDuplicateValues() {
        return Lists.newArrayList(EdsBatteryLevel2.kEdsBatteryLevel2_Empty, EdsBatteryLevel2.kEdsBatteryLevel2_BCLevel);
    }
}
