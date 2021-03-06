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
package net.digitalid.utility.conversion.interfaces;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.generics.Specifiable;
import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.ownership.Shared;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.ConnectionException;
import net.digitalid.utility.conversion.exceptions.RecoveryException;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.size.NonEmpty;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.string.DomainName;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * A converter stores type-specific information and allows to convert and recover objects from that type without reflection.
 */
@Immutable
public interface Converter<@Unspecifiable TYPE, @Specifiable PROVIDED> {
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    /**
     * Returns the type which is modeled and whose instances are converted.
     * The upper bound has to be left unspecified in order to support generic types.
     */
    @Pure
    public @Nonnull Class<? super TYPE> getType();
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of the type which is modeled.
     */
    @Pure
    public @Nonnull @CodeIdentifier @MaxSize(63) String getTypeName();
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    /**
     * Returns the name of the package in which the type is declared.
     */
    @Pure
    public @Nonnull @DomainName String getTypePackage();
    
    /* -------------------------------------------------- Primitive Converter -------------------------------------------------- */
    
    /**
     * Returns true if the converter is one of those under net.digitalid.utility.conversion.converters.
     */
    @Pure
    public default boolean isPrimitiveConverter() {
        return false;
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    /**
     * Returns the fields of the modeled type for the given representation.
     */
    @Pure
    public @Nonnull @NonNullableElements ImmutableList<CustomField> getFields(@Nonnull Representation representation);
    
    /* -------------------------------------------------- Inheritance -------------------------------------------------- */
    
    /**
     * Returns the converter of the supertype or null if the modeled type has no convertible supertype.
     */
    @Pure
    public default @Nullable Converter<? super TYPE, PROVIDED> getSupertypeConverter() { return null; }
    
    /**
     * Returns the converters of the subtypes or null if the modeled type has no convertible subtypes.
     */
    @Pure
    public default @Nullable @NonNullableElements @NonEmpty ImmutableList<Converter<? extends TYPE, PROVIDED>> getSubtypeConverters() { return null; }
    
    /* -------------------------------------------------- Convert -------------------------------------------------- */
    
    /**
     * Converts the given object of the modeled type with the given encoder.
     */
    @Pure
    public <@Unspecifiable EXCEPTION extends ConnectionException> void convert(@NonCaptured @Unmodified @Nonnull TYPE object, @NonCaptured @Modified @Nonnull Encoder<EXCEPTION> encoder) throws EXCEPTION;
    
    /* -------------------------------------------------- Recover -------------------------------------------------- */
    
    /**
     * Recovers an object of the modeled type from the decoder with the provided object.
     */
    @Pure
    public @Capturable <@Unspecifiable EXCEPTION extends ConnectionException> @Nonnull TYPE recover(@NonCaptured @Modified @Nonnull Decoder<EXCEPTION> decoder, @Shared PROVIDED provided) throws EXCEPTION, RecoveryException;
    
}
