package net.digitalid.utility.validation.annotations.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.circumfixes.Quotes;
import net.digitalid.utility.interfaces.Locking;
import net.digitalid.utility.processing.utility.TypeImporter;
import net.digitalid.utility.validation.annotations.meta.MethodValidator;
import net.digitalid.utility.validation.annotations.type.Stateless;
import net.digitalid.utility.validation.contract.Contract;
import net.digitalid.utility.validation.validator.MethodAnnotationValidator;

/**
 * This annotation indicates that a method may only be called when the corresponding lock is not held by the current thread.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MethodValidator(LockNotHeldByCurrentThread.Validator.class)
public @interface LockNotHeldByCurrentThread {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of and generates the contract for the surrounding annotation.
     */
    @Stateless
    public static class Validator implements MethodAnnotationValidator {
        
        @Pure
        @Override
        public @Nonnull Class<?> getReceiverType() {
            return Locking.class;
        }
        
        @Pure
        @Override
        public @Nonnull Contract generateContract(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull TypeImporter typeImporter) {
            return Contract.with("!isLockHeldByCurrentThread()", "The method " + Quotes.inSingle(element.getSimpleName().toString()) + " may only be called by a thread that does not hold the lock.");
        }
        
    }
    
}
