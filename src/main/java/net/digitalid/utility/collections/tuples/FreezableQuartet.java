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

/**
 * This class models a {@link Freezable freezable} quartet.
 */
public class FreezableQuartet<E0, E1, E2, E3> extends FreezableTriplet<E0, E1, E2> implements ReadOnlyQuartet<E0, E1, E2, E3> {
    
    /* -------------------------------------------------- Field -------------------------------------------------- */
    
    /**
     * Stores the fourth element of this tuple.
     */
    private @Nullable E3 element3;
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new quartet with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * @param element2 the third element of this tuple.
     * @param element3 the fourth element of this tuple.
     */
    protected FreezableQuartet(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        super(element0, element1, element2);
        
        this.element3 = element3;
    }
    
    /**
     * Creates a new quartet with the given elements.
     * 
     * @param element0 the first element of this tuple.
     * @param element1 the second element of this tuple.
     * @param element2 the third element of this tuple.
     * @param element3 the fourth element of this tuple.
     * 
     * @return a new quartet with the given elements.
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E0, E1, E2, E3> FreezableQuartet<E0, E1, E2, E3> get(@Nullable E0 element0, @Nullable E1 element1, @Nullable E2 element2, @Nullable E3 element3) {
        return new FreezableQuartet<>(element0, element1, element2, element3);
    }
    
    /**
     * Creates a new quartet from the given quartet.
     * 
     * @param quartet the quartet containing the elements.
     * 
     * @return a new quartet from the given quartet.
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E0, E1, E2, E3> FreezableQuartet<E0, E1, E2, E3> getNonNullable(@Nonnull @NullableElements ReadOnlyQuartet<E0, E1, E2, E3> quartet) {
        return get(quartet.getNullableElement0(), quartet.getNullableElement1(), quartet.getNullableElement2(), quartet.getNullableElement3());
    }
    
    /* -------------------------------------------------- Getter -------------------------------------------------- */
    
    @Pure
    @Override
    public final @Nullable E3 getNullableElement3() {
        return element3;
    }
    
    @Pure
    @Override
    public final @Nonnull E3 getNonNullableElement3() {
        assert element3 != null : "The element is not null.";
        
        return element3;
    }
    
    /* -------------------------------------------------- Setter -------------------------------------------------- */
    
    /**
     * Sets the fourth element of this tuple.
     * 
     * @param element3 the element to be set.
     */
    @NonFrozenRecipient
    public final void setElement3(@Nullable E3 element3) {
        assert !isFrozen() : "This object is not frozen.";
        
        this.element3 = element3;
    }
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    @Override
    public @Nonnull @Frozen ReadOnlyQuartet<E0, E1, E2, E3> freeze() {
        super.freeze();
        return this;
    }
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableQuartet<E0, E1, E2, E3> clone() {
        return FreezableQuartet.getNonNullable(this);
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null) { return false; }
        if (!(object instanceof FreezableQuartet)) { return object.equals(this); }
        final @Nonnull FreezableQuartet other = (FreezableQuartet) object;
        return super.equals(object) && Objects.equals(this.element3, other.element3);
    }
    
    @Pure
    @Override
    public int hashCode() {
        return 83 * super.hashCode() + Objects.hashCode(element3);
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return super.toString() + ", " + element3;
    }
    
}
