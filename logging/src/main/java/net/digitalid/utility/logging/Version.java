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
package net.digitalid.utility.logging;

import javax.annotation.Nonnull;

import net.digitalid.utility.configuration.Configuration;
import net.digitalid.utility.validation.annotations.type.Utility;

/**
 * The version of this library is used for logging.
 */
@Utility
public abstract class Version {
    
    /* -------------------------------------------------- Configuration -------------------------------------------------- */
    
    /**
     * Stores the version of this library.
     */
    public static final @Nonnull Configuration<String> string = Configuration.with("1.0.0");
    
}
