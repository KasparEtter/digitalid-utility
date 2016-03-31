package net.digitalid.utility.collections.readonly;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.freezable.FreezableArrayIterator;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.annotations.method.Pure;

/**
 * This interface provides read-only access to {@link FreezableArrayIterator array iterators} and should not be lost by assigning its objects to a supertype.
 * Never call {@link Iterator#remove()} on a read-only array iterator! Unfortunately, this method cannot be undeclared again.
 * (Please note that only the underlying array and not the iterator itself is read-only (and possibly frozen).)
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableArrayIterator
 */
public interface ReadOnlyArrayIterator<E> extends ReadOnlyIterator<E> {
    
    /* -------------------------------------------------- Iteration -------------------------------------------------- */
    
    /**
     * Returns whether this iterator has a previous element.
     * 
     * @return whether this iterator has a previous element.
     */
    @Pure
    public boolean hasPrevious();
    
    /**
     * Returns the previous element of this iterator.
     * 
     * @return the previous element of this iterator.
     */
    public @Nullable E previous();
    
    /**
     * Returns the index of the next element.
     * 
     * @return the index of the next element.
     */
    @Pure
    public int nextIndex();
    
    /**
     * Returns the index of the previous element.
     * 
     * @return the index of the previous element.
     */
    @Pure
    public int previousIndex();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableArrayIterator<E> clone();
    
}
