package org.blackdread.cameraframework.api.helper.factory;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * <p>Created on 2018/11/08.</p>
 *
 * @author Yoann CAPLAIN
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
