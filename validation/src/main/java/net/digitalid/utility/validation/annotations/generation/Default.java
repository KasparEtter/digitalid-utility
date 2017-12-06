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
package net.digitalid.utility.validation.annotations.generation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.meta.ValueValidator;
import net.digitalid.utility.validation.annotations.string.JavaExpression;
import net.digitalid.utility.validation.validators.GenerationValidator;

/**
 * This annotation indicates that the annotated value is initialized with the given default value.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ValueValidator(GenerationValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface Default {
    
    /**
     * Returns the name that describes the default value.
     */
    @Nonnull String name() default "";
    
    /**
     * Returns the default value as a Java expression.
     */
    @Nonnull @JavaExpression String value();
    
}
