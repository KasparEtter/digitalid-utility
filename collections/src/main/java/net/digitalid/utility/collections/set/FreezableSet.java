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
package net.digitalid.utility.collections.set;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.collection.FreezableCollection;
import net.digitalid.utility.freezable.FreezableInterface;
import net.digitalid.utility.freezable.annotations.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface models a {@link Set set} that can be {@link FreezableInterface frozen}.
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 * 
 * @see BackedFreezableSet
 * @see FreezableHashSet
 * @see FreezableLinkedHashSet
 */
@Freezable(ReadOnlySet.class)
public interface FreezableSet<E> extends ReadOnlySet<E>, Set<E>, FreezableCollection<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Impure
    @Override
    @NonFrozenRecipient
    public @Chainable @Nonnull @Frozen ReadOnlySet<E> freeze();
    
    /* -------------------------------------------------- Conflicts -------------------------------------------------- */
    
    @Pure
    @Override
    public default boolean isEmpty() {
        return FreezableCollection.super.isEmpty();
    }
    
    @Pure
    @Override
    public default boolean contains(@NonCaptured @Unmodified @Nullable Object object) {
        return FreezableCollection.super.contains(object);
    }
    
    @Pure
    @Override
    public default boolean containsAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return FreezableCollection.super.containsAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public default boolean removeAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return FreezableCollection.super.removeAll(collection);
    }
    
    @Impure
    @Override
    @NonFrozenRecipient
    public default boolean retainAll(@NonCaptured @Unmodified @Nonnull Collection<?> collection) {
        return FreezableCollection.super.retainAll(collection);
    }
    
    @Pure
    @Override
    public default @Capturable @Nonnull Object[] toArray() {
        return FreezableCollection.super.toArray();
    }
    
    @Pure
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public default @Capturable <T> @Nonnull T[] toArray(@NonCaptured @Modified @Nonnull T[] array) {
        return FreezableCollection.super.toArray(array);
    }
    
}
