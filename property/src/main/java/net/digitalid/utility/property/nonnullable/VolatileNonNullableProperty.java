package net.digitalid.utility.property.nonnullable;

import javax.annotation.Nonnull;
import net.digitalid.utility.validation.state.Pure;
import net.digitalid.utility.validation.state.Validated;

/**
 * This property stores a replaceable value that cannot be null.
 */
public final class VolatileNonNullableProperty<V> extends WritableNonNullableProperty<V> {
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Stores the value of this property.
     */
    private @Nonnull @Validated V value;
    
    @Pure
    @Override
    public @Nonnull @Validated V get() {
        return value;
    }
    
    @Override
    public void set(@Nonnull @Validated V newValue) {
        final @Nonnull V oldValue = this.value;
        this.value = newValue;
        
        if (!newValue.equals(oldValue)) { notify(oldValue, newValue); }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new non-nullable replaceable property.
     * 
     * @param value the initial value of the new non-nullable replaceable property.
     */
    private VolatileNonNullableProperty(@Nonnull @Validated V value) {
        super();

        this.value = value;
    }

    /**
     * Returns a new non-nullable replaceable property with the given initial value.
     * 
     * @param value the initial value of the new non-nullable replaceable property.
     * 
     * @return a new non-nullable replaceable property with the given initial value.
     */
    @Pure
    public static @Nonnull <V> VolatileNonNullableProperty<V> get(@Nonnull V value) {
        return new VolatileNonNullableProperty<>(value);
    }
    
}
