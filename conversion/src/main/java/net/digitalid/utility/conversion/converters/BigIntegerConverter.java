package net.digitalid.utility.conversion.converters;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.generics.Unspecifiable;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.enumerations.Representation;
import net.digitalid.utility.conversion.exceptions.ConnectionException;
import net.digitalid.utility.conversion.interfaces.Converter;
import net.digitalid.utility.conversion.interfaces.Decoder;
import net.digitalid.utility.conversion.interfaces.Encoder;
import net.digitalid.utility.conversion.model.CustomField;
import net.digitalid.utility.conversion.model.CustomType;
import net.digitalid.utility.immutable.ImmutableList;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.string.DomainName;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * This class implements the conversion of big integers.
 */
@Stateless
public class BigIntegerConverter implements Converter<BigInteger, Void> {
    
    /* -------------------------------------------------- Instance -------------------------------------------------- */
    
    public static final @Nonnull BigIntegerConverter INSTANCE = new BigIntegerConverter();
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull Class<BigInteger> getType() {
        return BigInteger.class;
    }
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @CodeIdentifier @MaxSize(63) String getTypeName() {
        return "BigInteger";
    }
    
    /* -------------------------------------------------- Package -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @DomainName String getTypePackage() {
        return "java.math";
    }
    
    /* -------------------------------------------------- Primitive Converter -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isPrimitiveConverter() {
        return true;
    }
    
    /* -------------------------------------------------- Fields -------------------------------------------------- */
    
    private static final @Nonnull ImmutableList<@Nonnull CustomField> fields = ImmutableList.withElements(CustomField.with(CustomType.INTEGER, "value"));
    
    @Pure
    @Override
    public @Nonnull ImmutableList<@Nonnull CustomField> getFields(@Nonnull Representation representation) {
        return fields;
    }
    
    /* -------------------------------------------------- Convert -------------------------------------------------- */
    
    @Pure
    @Override
    public <@Unspecifiable EXCEPTION extends ConnectionException> void convert(@NonCaptured @Unmodified @Nonnull BigInteger number, @NonCaptured @Modified @Nonnull Encoder<EXCEPTION> encoder) throws EXCEPTION {
        encoder.encodeInteger(number);
    }
    
    /* -------------------------------------------------- Recover -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable <@Unspecifiable EXCEPTION extends ConnectionException> @Nonnull BigInteger recover(@NonCaptured @Modified @Nonnull Decoder<EXCEPTION> decoder, Void provided) throws EXCEPTION {
        return decoder.decodeInteger();
    }
    
}