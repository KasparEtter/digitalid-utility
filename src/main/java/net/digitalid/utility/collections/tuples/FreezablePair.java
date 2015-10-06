package net.digitalid.utility.collections.tuples;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.reference.Capturable;
import net.digitalid.utility.collections.annotations.freezable.Frozen;
import net.digitalid.utility.collections.annotations.freezable.NonFrozen;
import net.digitalid.utility.collections.annotations.freezable.NonFrozenRecipient;
import net.digitalid.utility.collections.annotations.elements.NullableElements;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.collections.freezable.Freezable;
import net.digitalid.utility.collections.freezable.FreezableObject;

/**
 * This class models a {@link Freezable freezable} pair.
 * 
 * @see FreezableTriplet
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
 */
public class FreezablePair<E0, E1> extends FreezableObject implements ReadOnlyPair<E0, E1> {
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Fields –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Stores the first element of this tuple.
     */
    private @Nullable E0 element0;
    
    /**
     * Stores the second element of this tuple.
     */
    private @Nullable E1 element1;
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Constructors –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Creates a new pair with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     */
    protected FreezablePair(@Nullable E0 element0, @Nullable E1 element1) {
        this.element0 = element0;
        this.element1 = element1;
    }
    
    /**
     * Creates a new pair with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * 
     * @return a new pair with the given elements.
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E0, E1> FreezablePair<E0, E1> get(@Nullable E0 element0, @Nullable E1 element1) {
        return new FreezablePair<>(element0, element1);
    }
    
    /**
     * Creates a new pair from the given pair.
     * 
     * @param pair the pair containing the elements.
     * 
     * @return a new pair from the given pair.
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E0, E1> FreezablePair<E0, E1> getNonNullable(@Nonnull @NullableElements ReadOnlyPair<E0, E1> pair) {
        return get(pair.getNullableElement0(), pair.getNullableElement1());
    }
    
    /**
     * Creates a new pair from the given pair.
     * 
     * @param pair the pair containing the elements.
     * 
     * @return a new pair from the given pair.
     */
    @Pure
    public static @Capturable @Nullable @NonFrozen <E0, E1> FreezablePair<E0, E1> getNullable(@Nullable @NullableElements ReadOnlyPair<E0, E1> pair) {
        return pair == null ? null : getNonNullable(pair);
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Getters –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    public final @Nullable E0 getNullableElement0() {
        return element0;
    }
    
    @Pure
    @Override
    public final @Nonnull E0 getNonNullableElement0() {
        assert element0 != null : "The element is not null.";
        
        return element0;
    }
    
    @Pure
    @Override
    public final @Nullable E1 getNullableElement1() {
        return element1;
    }
    
    @Pure
    @Override
    public final @Nonnull E1 getNonNullableElement1() {
        assert element1 != null : "The element is not null.";
        
        return element1;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Setters –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Sets the first element of this tuple.
     * 
     * @param element0 the element to be set.
     */
    @NonFrozenRecipient
    public final void setElement0(@Nullable E0 element0) {
        assert !isFrozen() : "This object is not frozen.";
        
        this.element0 = element0;
    }
    
    /**
     * Sets the second element of this tuple.
     * 
     * @param element1 the element to be set.
     */
    @NonFrozenRecipient
    public final void setElement1(@Nullable E1 element1) {
        assert !isFrozen() : "This object is not frozen.";
        
        this.element1 = element1;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Freezable –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Override
    public @Nonnull @Frozen ReadOnlyPair<E0, E1> freeze() {
        super.freeze();
        return this;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Cloneable –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezablePair<E0, E1> clone() {
        return FreezablePair.getNonNullable(this);
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Object –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(@Nullable Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (!(object instanceof FreezablePair)) return object.equals(this);
        final @Nonnull FreezablePair other = (FreezablePair) object;
        return Objects.equals(this.element0, other.element0) && Objects.equals(this.element1, other.element1);
    }
    
    @Pure
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(element0);
        hash = 83 * hash + Objects.hashCode(element1);
        return hash;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return element0 + ", " + element1;
    }
    
}
