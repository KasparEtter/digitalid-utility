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
package net.digitalid.utility.property.map;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.property.Property;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Mutable;
import net.digitalid.utility.validation.annotations.value.Valid;

/**
 * Objects that implement this interface can be used to {@link Property#register(net.digitalid.utility.property.Observer) observe} {@link ReadOnlyVolatileMapProperty volatile map properties}.
 */
@Mutable
@Functional
public interface VolatileMapObserver<@Unspecifiable KEY, @Unspecifiable VALUE, @Unspecifiable READONLY_MAP extends ReadOnlyMap<@Nonnull @Valid("key") KEY, @Nonnull @Valid VALUE>> extends MapObserver<KEY, VALUE, READONLY_MAP, RuntimeException, RuntimeException, VolatileMapObserver<KEY, VALUE, READONLY_MAP>, ReadOnlyVolatileMapProperty<KEY, VALUE, READONLY_MAP>> {}
