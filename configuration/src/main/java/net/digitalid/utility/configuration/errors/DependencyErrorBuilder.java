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
package net.digitalid.utility.configuration.errors;

import javax.annotation.Generated;
import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.method.Chainable;

@SuppressWarnings("null")
@Generated(value = "net.digitalid.utility.processor.generator.JavaFileGenerator")
public class DependencyErrorBuilder {
    
    public interface DependencyDependencyErrorBuilder {
        
        @Chainable
        public @Nonnull InnerDependencyErrorBuilder withDependency(@Nonnull Configuration<?> dependency);
        
    }
    
    public static class InnerDependencyErrorBuilder implements DependencyDependencyErrorBuilder {
        
        private InnerDependencyErrorBuilder() {
        }
        
        /* -------------------------------------------------- Configuration -------------------------------------------------- */
        
        private @Nonnull Configuration<?> configuration = null;
        
        @Chainable
        public @Nonnull InnerDependencyErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
            this.configuration = configuration;
            return this;
        }
        
        /* -------------------------------------------------- Dependency -------------------------------------------------- */
        
        private @Nonnull Configuration<?> dependency = null;
        
        @Chainable
        public @Nonnull InnerDependencyErrorBuilder withDependency(@Nonnull Configuration<?> dependency) {
            this.dependency = dependency;
            return this;
        }
        
        /* -------------------------------------------------- Build -------------------------------------------------- */
        
        public DependencyError build() {
            return new DependencyErrorSubclass(configuration, dependency);
        }
        
    }
    
    public static DependencyDependencyErrorBuilder withConfiguration(@Nonnull Configuration<?> configuration) {
        return new InnerDependencyErrorBuilder().withConfiguration(configuration);
    }
    
}
