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

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * <p>Created on 2018/11/08.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 */
final class WeakReferenceUtil {

    /**
     * @param list   list to search in
     * @param object object to find
     * @param <T>    type of Object
     * @return true if the object is found in the list of WeakReference
     */
    static <T> boolean contains(final List<WeakReference<T>> list, final T object) {
        for (final WeakReference<T> weakReference : list) {
            final T ref = weakReference.get();
            if (ref != null && ref.equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param weakReferences list to remove from
     * @param object         object to remove if found
     * @param <T>            type of Object
     */
    static <T> void remove(final List<WeakReference<T>> weakReferences, final T object) {
        weakReferences.removeIf(weakReference -> {
            final T ref = weakReference.get();
            if (weakReference.isEnqueued() || ref == null) {
                return true;
            }
            return ref.equals(object);
        });
    }

    /**
     * Remove all WeakReference that are enqueued or that get method returns null
     *
     * @param weakReferences list to clean
     */
    static <T> void cleanNullReferences(final List<WeakReference<T>> weakReferences) {
        weakReferences.removeIf(weakReference -> weakReference.isEnqueued() || weakReference.get() == null);
    }

    private WeakReferenceUtil() {
    }
}
