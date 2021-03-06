/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.method.PureWithSideEffects;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.processing.logging.ErrorLogger;
import net.digitalid.utility.processing.logging.SourcePosition;
import net.digitalid.utility.processing.utility.ProcessingUtility;
import net.digitalid.utility.validation.annotations.meta.TypeValidator;
import net.digitalid.utility.validation.validator.TypeAnnotationValidator;

/**
 * This annotation indicates that the objects of the annotated class are immutable.
 * <p>
 * An object is considered immutable, if its representation (usually the data that is included in its block) is fixed.
 * Other objects that are not fully part of its representation but can nonetheless be reached through its fields may still be mutable.
 * <p>
 * It should always be safe to share immutable objects between various instances and threads.
 * However, it is in general not guaranteed that the hash of immutable objects stays the same.
 * In other words, an immutable object is only conceptually immutable but its values may change.
 * (This is the case with references to persons, which remain constant but can still be merged.)
 * 
 * @see Mutable
 * @see ReadOnly
 * @see Stateless
 * @see Utility
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TypeValidator(Immutable.Validator.class)
public @interface Immutable {
    
    /* -------------------------------------------------- Validator -------------------------------------------------- */
    
    /**
     * This class checks the use of the surrounding annotation.
     */
    @Stateless
    public static class Validator implements TypeAnnotationValidator {
        
        @Pure
        @Override
        public void checkUsage(@Nonnull Element element, @Nonnull AnnotationMirror annotationMirror, @NonCaptured @Modified @Nonnull ErrorLogger errorLogger) {
            for (@Nonnull ExecutableElement method : ProcessingUtility.getAllMethods((TypeElement) element).filter(ProcessingUtility::isDeclaredInDigitalIDLibrary).filterNot(method -> method.getModifiers().contains(Modifier.STATIC))) {
                if (!ProcessingUtility.hasAnnotation(method, Pure.class) && !ProcessingUtility.hasAnnotation(method, PureWithSideEffects.class)) {
                    errorLogger.log("The immutable type $ may only contain pure non-static methods.", SourcePosition.of(method), element);
                }
            }
            for (@Nonnull VariableElement field : ProcessingUtility.getAllFields((TypeElement) element).filter(ProcessingUtility::isDeclaredInDigitalIDLibrary).filterNot(field -> field.getModifiers().contains(Modifier.STATIC))) {
                if (!field.getModifiers().contains(Modifier.FINAL)) {
                    errorLogger.log("The immutable type $ may only contain final non-static fields.", SourcePosition.of(field), element);
                }
            }
        }
        
    }
    
}
