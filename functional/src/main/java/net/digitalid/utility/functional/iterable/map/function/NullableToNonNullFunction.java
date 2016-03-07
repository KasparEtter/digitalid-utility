package net.digitalid.utility.functional.iterable.map.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Stateless;

/**
 * Maps nullable elements to a non-null result by applying the implemented function.
 */
@Stateless
public abstract class NullableToNonNullFunction<I, O> implements ToNonNullFunction<I, O> {
    
    /**
     * Applies the function on nullable elements to produce a non-null result.
     */
    @Pure
    @Override
    public abstract @Nonnull O apply(@Nullable I... element);
    
}