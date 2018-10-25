package org.blackdread.camerabinding.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When a parameter is marked with this annotation, the reference counter is implicitly 1 for the retrieved object/reference. When the object is not needed, you must use EdsRelease to decrease the reference counter of that variable.
 *
 * <p>Created on 2018/10/24.</p>
 *
 * @author Yoann CAPLAIN
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER})
public @interface ImplicitRetain {
}
