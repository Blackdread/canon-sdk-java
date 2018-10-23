package org.blackdread.cameraframework;

import org.junit.jupiter.api.condition.EnabledIf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to enable tests only if canon camera is connected to PC
 * <p>Created on 2018/10/22.</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnabledIf("systemProperty.get('canonCameraConnected') == 'true'")
//@EnabledIfSystemProperty(named = "canonCameraConnected", matches = "true")
public @interface CameraIsConnected {
    // works but is not repeatable so not very useful when need to test many things
    // if camera is connected then we suppose that DLL are present
}
