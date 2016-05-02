package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the objects of the annotated class are mutable.
 * It is not safe to share mutable objects between various instances and threads,
 * unless the annotated class is also thread-safe.
 * 
 * @see Immutable
 * @see ReadOnly
 * @see Stateless
 * @see Utility
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mutable {}
