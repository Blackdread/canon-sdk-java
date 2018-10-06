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
