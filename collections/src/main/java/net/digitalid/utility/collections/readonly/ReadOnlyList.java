package net.digitalid.utility.collections.readonly;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.collections.freezable.FreezableList;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This interface provides read-only access to {@link List lists} and should <em>never</em> be cast away (unless external code requires it).
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 * 
 * @see FreezableList
 */
public interface ReadOnlyList<E> extends ReadOnlyCollection<E> {
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    /**
     * @see List#get(int)
     */
    @Pure
    public @Nullable E getNullable(@Index int index);
    
    /**
     * Returns whether the element at the given index is null.
     * 
     * @param index the index of the element to be checked.
     * 
     * @return whether the element at the given index is null.
     */
    @Pure
    public boolean isNull(@Index int index);
    
    /**
     * Returns the element at the given index.
     * 
     * @param index the index of the element to be returned.
     * 
     * @return the element at the given index.
     * 
     * @require !isNull(index) : "The element at the given index is not null.";
     */
    @Pure
    public @Nonnull E getNonNullable(@Index int index);
    
    /**
     * @see List#indexOf(java.lang.Object)
     */
    @Pure
    public int indexOf(@Nullable Object object);
    
    /**
     * @see List#lastIndexOf(java.lang.Object)
     */
    @Pure
    public int lastIndexOf(@Nullable Object object);
    
    /**
     * @see List#listIterator()
     */
    @Pure
    public @Nonnull ReadOnlyListIterator<E> listIterator();
    
    /**
     * @see List#listIterator(int)
     */
    @Pure
    public @Nonnull ReadOnlyListIterator<E> listIterator(@IndexForInsertion int index);
    
    /**
     * @see List#subList(int, int)
     */
    @Pure
    public @Nonnull ReadOnlyList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex);
    
    /* -------------------------------------------------- Ordering -------------------------------------------------- */
    
    /**
     * Returns whether the elements in this list are ascending (excluding null values).
     * 
     * @return {@code true} if the elements in this list are ascending, {@code false} otherwise.
     */
    @Pure
    public boolean isAscending();
    
    /**
     * Returns whether the elements in this list are strictly ascending (excluding null values).
     * 
     * @return {@code true} if the elements in this list are strictly ascending, {@code false} otherwise.
     */
    @Pure
    public boolean isStrictlyAscending();
    
    /**
     * Returns whether the elements in this list are descending (excluding null values).
     * 
     * @return {@code true} if the elements in this list are descending, {@code false} otherwise.
     */
    @Pure
    public boolean isDescending();
    
    /**
     * Returns whether the elements in this list are strictly descending (excluding null values).
     * 
     * @return {@code true} if the elements in this list are strictly descending, {@code false} otherwise.
     */
    @Pure
    public boolean isStrictlyDescending();
    
    /* -------------------------------------------------- Cloneable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableList<E> clone();
    
}
