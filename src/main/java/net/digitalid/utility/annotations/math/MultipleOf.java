package net.digitalid.utility.annotations.math;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;
import net.digitalid.utility.annotations.meta.TargetType;

/**
 * This annotation indicates that a numeric value is a multiple of the given value.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TargetType({long.class, int.class, short.class, byte.class, BigInteger.class})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface MultipleOf {
    
    /**
     * Returns the value of which the annotated numeric value is a multiple of.
     * 
     * @return the value of which the annotated numeric value is a multiple of.
     */
    long value();
    
}