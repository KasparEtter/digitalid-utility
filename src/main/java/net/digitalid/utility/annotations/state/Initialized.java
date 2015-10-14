package net.digitalid.utility.annotations.state;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a static method may only be called when its class has been initialized.
 * 
 * @author Kaspar Etter
 * @version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface Initialized {}
