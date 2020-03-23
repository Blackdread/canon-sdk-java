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
package org.blackdread.cameraframework.api.helper.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Created on 2019/03/29.</p>
 *
 * @author Yoann CAPLAIN
 */
class WeakReferenceUtilTest {

    private List<WeakReference<Object>> list;

    private Object data1;
    private Object data2;
    private Object data3;

    private WeakReference<Object> weak1;
    private WeakReference<Object> weak2;
    private WeakReference<Object> weak3;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();

        data1 = "1"; // String are handled differently for JVM memory
        data2 = new Object();
        data3 = new String("3"); // different for JVM

        weak1 = new WeakReference<>(data1);
        weak2 = new WeakReference<>(data2);
        weak3 = new WeakReference<>(data3);
    }

    @Test
    void contains() {
        list.add(weak1);
        list.add(weak2);
        list.add(weak3);

        Assertions.assertTrue(WeakReferenceUtil.contains(list, data1));
        Assertions.assertTrue(WeakReferenceUtil.contains(list, data2));
        Assertions.assertTrue(WeakReferenceUtil.contains(list, data3));
        Assertions.assertFalse(WeakReferenceUtil.contains(list, weak1));
        Assertions.assertFalse(WeakReferenceUtil.contains(list, weak2));
        Assertions.assertFalse(WeakReferenceUtil.contains(list, weak3));

        list.remove(weak1);

        Assertions.assertFalse(WeakReferenceUtil.contains(list, data1));
        Assertions.assertTrue(WeakReferenceUtil.contains(list, data2));
        Assertions.assertFalse(WeakReferenceUtil.contains(list, weak1));
        Assertions.assertFalse(WeakReferenceUtil.contains(list, weak2));

        list.remove(0);

        Assertions.assertFalse(WeakReferenceUtil.contains(list, data2));
    }

    @Test
    void remove() {
        list.add(weak1);
        list.add(weak2);
        list.add(weak3);

        WeakReferenceUtil.remove(list, data1);
        WeakReferenceUtil.remove(list, data2);
        WeakReferenceUtil.remove(list, weak3);

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(weak3, list.get(0));
    }

    @Test
    void cleanNullReferences() throws InterruptedException {
        list.add(weak1);
        list.add(weak2);
        list.add(weak3);

        data2 = null;
        data3 = null;

        for (int i = 0; i < 50; i++) {
            System.gc();
            Thread.sleep(50);
            System.gc();
        }

        // Let's hope gc actually happened...

        WeakReferenceUtil.cleanNullReferences(list);

        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(weak1, list.get(0));
    }
}
