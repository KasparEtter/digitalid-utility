package net.digitalid.utility.validation.annotations.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;

/**
 * This annotation indicates that the objects of the annotated type cannot be modified through
 * their methods but can still change because they represent other objects in a read-only mode.
 * It should always be safe to share read-only objects between various instances and threads.
 * 
 * @see Mutable
 * @see Immutable
 * @see Stateless
 * @see Utility
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(ReadOnly.Validator.class)
public @interface ReadOnly {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Returns the type to which the annotated type provides read-only access (or the type of this annotation as a default because null is not allowed).
     */
    @Nonnull Class<?> value() default ReadOnly.class;
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator extends TypeAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror) {
            for (@Nonnull ExecutableElement method : ElementFilter.methodsIn(StaticProcessingEnvironment.getElementUtils().getAllMembers((TypeElement) element))) {
                if (ProcessingUtility.isDeclaredInDigitalIDLibrary(method) && !method.getModifiers().contains(Modifier.STATIC) && !ProcessingUtility.hasAnnotation(element, Pure.class)) {
                    ProcessingLog.error("The read-only type $ may only contain non-static methods that are pure.", SourcePosition.of(method), element);
                }
            }
        }
        
    }
    
}
