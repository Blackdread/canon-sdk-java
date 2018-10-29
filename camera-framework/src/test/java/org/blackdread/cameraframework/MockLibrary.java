package org.blackdread.cameraframework;

import org.junit.jupiter.api.condition.EnabledIf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to enable tests only if library is mocked
 * <p>Created on 2018/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated not sure to keep as it might not be possible unless JNA can also be mocked
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnabledIf("systemProperty.get('mockLibrary') == 'true' && systemProperty.get('canonCameraConnected') == 'false' && systemProperty.get('canonLibIsOnPath') == 'false'")
public @interface MockLibrary {
    // works but is not repeatable so not very useful when need to test many things
    // if camera is connected then we suppose that DLL are present
}
