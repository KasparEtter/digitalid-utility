package net.digitalid.utility.castable;

import javax.annotation.Nonnull;

import net.digitalid.utility.castable.exceptions.InvalidClassCastException;
import net.digitalid.utility.validation.state.Pure;

/**
 * This class implements an easy way to cast an object to a subclass.
 */
public abstract class CastableObject implements Castable {
    
    /* -------------------------------------------------- Casting -------------------------------------------------- */
    
    @Pure
    @Override
    public final @Nonnull <T> T castTo(@Nonnull Class<T> targetClass) throws InvalidClassCastException {
        if (targetClass.isInstance(this)) { return targetClass.cast(this); }
        else { throw InvalidClassCastException.get(this, targetClass); }
    }
    
}
