package net.digitalid.utility.generator.annotations.generators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.digitalid.utility.generator.generators.BuilderGenerator;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;

/**
 * Marks a class such that the {@link BuilderGenerator builder generator} generates a builder for the annotated class.
 * 
 * @see GenerateSubclass
 * @see GenerateConverter
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(GenerateAnnotationValidator.class)
public @interface GenerateBuilder {}
