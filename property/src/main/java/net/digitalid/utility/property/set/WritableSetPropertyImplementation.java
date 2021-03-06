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
package net.digitalid.utility.property.set;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.annotations.type.ThreadSafe;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * This class implements the {@link WritableSetProperty}.
 * 
 * @see WritableVolatileSetProperty
 */
@Mutable
@ThreadSafe
public abstract class WritableSetPropertyImplementation<@Unspecifiable VALUE, @Unspecifiable READONLY_SET extends ReadOnlySet<@Nonnull @Valid VALUE>, @Unspecifiable EXCEPTION1 extends Exception, @Unspecifiable EXCEPTION2 extends Exception, @Unspecifiable OBSERVER extends SetObserver<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>, @Unspecifiable PROPERTY extends ReadOnlySetProperty<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY>> extends ReadOnlySetPropertyImplementation<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> implements WritableSetProperty<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> {
    
    /* -------------------------------------------------- Notification -------------------------------------------------- */
    
    /**
     * Notifies the registered observers that the given value has been added to or removed from this property.
     * 
     * @param added {@code true} if the given value has been added to or {@code false} if it has been removed from this property.
     * 
     * @require !added || get().contains(value) : "If the value was added, this property has to contain it now.";
     * @require added || !get().contains(value) : "If the value was removed, this property may no longer contain it.";
     */
    @Impure
    @SuppressWarnings("unchecked")
    protected void notifyObservers(@NonCaptured @Unmodified @Nonnull @Valid VALUE value, boolean added) throws EXCEPTION1, EXCEPTION2 {
        Require.that(!added || get().contains(value)).orThrow("If the value $ was added, this property has to contain it now.", value);
        Require.that(added || !get().contains(value)).orThrow("If the value $ was removed, this property may no longer contain it.", value);
        
        if (!observers.isEmpty()) {
            for (@Nonnull SetObserver<VALUE, READONLY_SET, EXCEPTION1, EXCEPTION2, OBSERVER, PROPERTY> observer : observers.values()) {
                observer.notify((PROPERTY) this, value, added);
            }
        }
    }
    
}
