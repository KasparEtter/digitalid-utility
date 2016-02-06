package net.digitalid.utility.collections.freezable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.readonly.ReadOnlyList;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.validation.annotations.index.Index;
import net.digitalid.utility.validation.annotations.index.IndexForInsertion;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class extends the {@link LinkedList} and makes it {@link Freezable}.
 * Be careful when treating instances of this class as normal {@link List lists} because all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * <em>Important:</em> Only use freezable or immutable types for the elements!
 * (The type is not restricted to {@link Freezable} or {@link Immutable} so that library types can also be used.)
 */
public class FreezableLinkedList<E> extends LinkedList<E> implements FreezableList<E> {
    
    /* -------------------------------------------------- Freezable -------------------------------------------------- */
    
    /**
     * Stores whether this object is frozen.
     */
    private boolean frozen = false;
    
    @Pure
    @Override
    public boolean isFrozen() {
        return frozen;
    }
    
    @Override
    public @Nonnull @Frozen ReadOnlyList<E> freeze() {
        if (!frozen) {
            frozen = true;
            for (final @Nullable E element : this) {
                if (element instanceof Freezable) {
                    ((Freezable) element).freeze();
                } else {
                    break;
                }
            }
        }
        return this;
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * @see LinkedList#LinkedList()
     */
    protected FreezableLinkedList() {
        super();
    }
    
    /**
     * @see LinkedList#LinkedList()
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableLinkedList<E> get() {
        return new FreezableLinkedList<>();
    }
    
    /**
     * Creates a new freezable linked list with the given element.
     * 
     * @param element the element to add to the newly created list.
     * 
     * @return a new freezable linked list with the given element.
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableLinkedList<E> get(@Nullable E element) {
        final @Nonnull FreezableLinkedList<E> list = get();
        list.add(element);
        return list;
    }
    
    /**
     * Creates a new freezable linked list with the given elements.
     * 
     * @param elements the elements to add to the newly created list.
     */
    @Pure
    @SuppressWarnings("unchecked")
    public static @Capturable @Nonnull @NonFrozen <E> FreezableLinkedList<E> get(@Nonnull E... elements) {
        final @Nonnull FreezableLinkedList<E> list = get();
        list.addAll(Arrays.asList(elements));
        return list;
    }
    
    /**
     * @see LinkedList#LinkedList(java.util.Collection)
     */
    protected FreezableLinkedList(@Nonnull Collection<? extends E> collection) {
        super(collection);
    }
    
    /**
     * @see LinkedList#LinkedList(java.util.Collection)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableLinkedList<E> getNonNullable(@Nonnull Collection<? extends E> collection) {
        return new FreezableLinkedList<>(collection);
    }
    
    /**
     * @see LinkedList#LinkedList(java.util.Collection)
     */
    @Pure
    public static @Capturable @Nullable @NonFrozen <E> FreezableLinkedList<E> getNullable(@Nullable Collection<? extends E> collection) {
        return collection == null ? null : getNonNullable(collection);
    }
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isSingle() {
        return size() == 1;
    }
    
    /* -------------------------------------------------- List -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable E getNullable(@Index int index) {
        return get(index);
    }
    
    @Pure
    @Override
    public boolean isNull(@Index int index) {
        return get(index) == null;
    }
    
    @Pure
    @Override
    public @Nonnull E getNonNullable(@Index int index) {
        @Nullable E element = get(index);
        Require.that(element != null).orThrow("The element at the given index is not null.");
        
        return element;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull FreezableIterator<E> iterator() {
        return FreezableIterableIterator.get(this, super.iterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull FreezableIterator<E> descendingIterator() {
        return FreezableIterableIterator.get(this, super.descendingIterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull FreezableListIterator<E> listIterator() {
        return FreezableListIterator.get(this, super.listIterator());
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull FreezableListIterator<E> listIterator(@IndexForInsertion int index) {
        return FreezableListIterator.get(this, super.listIterator(index));
    }
    
    @Pure
    @Override
    public @Nonnull FreezableList<E> subList(@Index int fromIndex, @IndexForInsertion int toIndex) {
        return BackedFreezableList.get(this, super.subList(fromIndex, toIndex));
    }
    
    /* -------------------------------------------------- Add -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public boolean add(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.add(element);
    }
    
    @Override
    @NonFrozenRecipient
    public void add(@IndexForInsertion int index, @Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.add(index, element);
    }
    
    @Override
    @NonFrozenRecipient
    public void addFirst(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.addFirst(element);
    }
    
    @Override
    @NonFrozenRecipient
    public void addLast(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.addLast(element);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean addAll(@Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.addAll(collection);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean addAll(@IndexForInsertion int index, @Nonnull Collection<? extends E> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.addAll(index, collection);
    }
    
    /* -------------------------------------------------- Offer -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public boolean offer(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.offer(element);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean offerFirst(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.offerFirst(element);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean offerLast(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.offerLast(element);
    }
    
    /* -------------------------------------------------- Remove -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public @Nullable E remove() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove();
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E removeFirst() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeFirst();
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E removeLast() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeLast();
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E remove(@Index int index) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(index);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean remove(@Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(object);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean removeFirstOccurrence(@Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeFirstOccurrence(object);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean removeLastOccurrence(@Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeLastOccurrence(object);
    }
    
    @Override
    @NonFrozenRecipient
    protected void removeRange(@Index int fromIndex, @IndexForInsertion int toIndex) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.removeRange(fromIndex, toIndex);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean removeAll(@Nonnull Collection<?> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeAll(collection);
    }
    
    /* -------------------------------------------------- Poll -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public @Nullable E poll() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.poll();
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E pollFirst() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.pollFirst();
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E pollLast() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.pollLast();
    }
    
    /* -------------------------------------------------- Stack -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public void push(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.push(element);
    }
    
    @Override
    @NonFrozenRecipient
    public @Nullable E pop() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.pop();
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public @Nullable E set(@Index int index, @Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.set(index, element);
    }
    
    @Override
    @NonFrozenRecipient
    public void clear() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.clear();
    }
    
    @Override
    @NonFrozenRecipient
    public boolean retainAll(@Nonnull Collection<?> collection) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.retainAll(collection);
    }
    
    /* -------------------------------------------------- Conditions -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean containsNull() {
        for (final @Nullable E element : this) {
            if (element == null) { return true; }
        }
        return false;
    }
    
    @Pure
    @Override
    public boolean containsDuplicates() {
        final @Nonnull HashSet<E> set = new HashSet<>(size());
        for (final @Nullable E element : this) {
            if (set.contains(element)) { return true; }
            else { set.add(element); }
        }
        return false;
    }
    
    /* -------------------------------------------------- Ordering -------------------------------------------------- */
    
    /**
     * Returns whether the elements in this list are ordered (excluding null values).
     * 
     * @param strictly whether the ordering is strict (i.e. without equal values).
     * @param ascending whether the ordering is ascending (true) or descending (false).
     * 
     * @return {@code true} if the elements in this list are ordered, {@code false} otherwise.
     */
    @Pure
    @SuppressWarnings("unchecked")
    private boolean isOrdered(boolean strictly, boolean ascending) {
        @Nullable E lastElement = null;
        for (final @Nullable E element : this) {
            if (element == null) { continue; }
            if (lastElement != null) {
                if (element instanceof Comparable<?>) {
                    if (((Comparable<E>) element).compareTo(lastElement) * (ascending ? 1 : -1) < (strictly ? 1 : 0)) { return false; }
                }
            }
            lastElement = element;
        }
        return true;
    }
    
    @Pure
    @Override
    public boolean isAscending() {
        return isOrdered(false, true);
    }
    
    @Pure
    @Override
    public boolean isStrictlyAscending() {
        return isOrdered(true, true);
    }
    
    @Pure
    @Override
    public boolean isDescending() {
        return isOrdered(false, false);
    }
    
    @Pure
    @Override
    public boolean isStrictlyDescending() {
        return isOrdered(true, false);
    }
    
    /* -------------------------------------------------- Conversions -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableLinkedList<E> clone() {
        return new FreezableLinkedList<>(this);
    }
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Capturable @Nonnull @NonFrozen FreezableArray<E> toFreezableArray() {
        return FreezableArray.getNonNullable(toArray((E[]) new Object[size()]));
    }
    
}
