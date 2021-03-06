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
package net.digitalid.utility.collections.map;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.collections.collection.ReadOnlyCollection;
import net.digitalid.utility.collections.set.ReadOnlySet;
import net.digitalid.utility.freezable.ReadOnlyInterface;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.immutable.entry.ReadOnlyEntrySet;
import net.digitalid.utility.interfaces.Countable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.type.ReadOnly;

/**
 * This interface provides read-only access to {@link Map maps} and should <em>never</em> be cast away (unless external code requires it).
 * It is recommended to use only {@link ReadOnly} or {@link Immutable} types for the elements.
 */
@ReadOnly(FreezableMap.class)
public interface ReadOnlyMap<K, V> extends ReadOnlyInterface, Countable {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * @see Map#containsKey(java.lang.Object)
     */
    @Pure
    public boolean containsKey(@NonCaptured @Unmodified @Nullable Object key);
    
    /**
     * @see Map#containsValue(java.lang.Object)
     */
    @Pure
    public boolean containsValue(@NonCaptured @Unmodified @Nullable Object value);
    
    /**
     * @see Map#get(java.lang.Object)
     */
    @Pure
    public @NonCapturable @Nullable V get(@NonCaptured @Unmodified @Nullable Object key);
    
    /**
     * @see Map#keySet()
     */
    @Pure
    public @NonCapturable @Nonnull ReadOnlySet<K> keySet();
    
    /**
     * @see Map#values()
     */
    @Pure
    public @NonCapturable @Nonnull ReadOnlyCollection<V> values();
    
    /**
     * @see Map#entrySet()
     */
    @Pure
    public @NonCapturable @Nonnull ReadOnlyEntrySet<K, V> entrySet();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableMap<K,V> clone();
    
}
