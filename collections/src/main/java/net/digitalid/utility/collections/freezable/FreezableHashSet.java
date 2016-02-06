package net.digitalid.utility.collections.freezable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.readonly.ReadOnlySet;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.Freezable;
import net.digitalid.utility.freezable.annotations.Frozen;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.freezable.annotations.NonFrozenRecipient;
import net.digitalid.utility.string.iterable.Brackets;
import net.digitalid.utility.string.iterable.IterableConverter;
import net.digitalid.utility.validation.annotations.math.NonNegative;
import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.reference.Capturable;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class extends the {@link HashSet} and makes it {@link Freezable}.
 * Be careful when treating instances of this class as normal {@link Set sets} because all modifying methods may fail with an {@link AssertionError}.
 * <p>
 * <em>Important:</em> Only use {@link Immutable immutable} or {@link Freezable frozen} objects as elements!
 */
public class FreezableHashSet<E> extends HashSet<E> implements FreezableSet<E> {
    
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
    public @Nonnull @Frozen ReadOnlySet<E> freeze() {
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
     * @see HashSet#HashSet(int, float)
     */
    protected FreezableHashSet(@NonNegative int initialCapacity, @Positive float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    /**
     * @see HashSet#HashSet(int, float)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableHashSet<E> get(@NonNegative int initialCapacity, @Positive float loadFactor) {
        return new FreezableHashSet<>(initialCapacity, loadFactor);
    }
    
    /**
     * @see HashSet#HashSet(int)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableHashSet<E> get(@NonNegative int initialCapacity) {
        return get(initialCapacity, 0.75f);
    }
    
    /**
     * @see HashSet#HashSet()
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableHashSet<E> get() {
        return get(16);
    }
    
    /**
     * Creates a new freezable hash set with the given element.
     * 
     * @param element the element to add to the newly created hash set.
     * 
     * @return a new freezable hash set with the given element.
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableHashSet<E> get(@Nullable E element) {
        final @Nonnull FreezableHashSet<E> set = get();
        set.add(element);
        return set;
    }
    
    /**
     * @see HashSet#HashSet(java.util.Collection)
     */
    protected FreezableHashSet(@Nonnull Collection<? extends E> collection) {
        super(collection);
    }
    
    /**
     * @see HashSet#HashSet(java.util.Collection)
     */
    @Pure
    public static @Capturable @Nonnull @NonFrozen <E> FreezableHashSet<E> getNonNullable(@Nonnull Collection<? extends E> collection) {
        return new FreezableHashSet<>(collection);
    }
    
    /**
     * @see HashSet#HashSet(java.util.Collection)
     */
    @Pure
    public static @Capturable @Nullable @NonFrozen <E> FreezableHashSet<E> getNullable(@Nullable Collection<? extends E> collection) {
        return collection == null ? null : getNonNullable(collection);
    }
    
    /* -------------------------------------------------- Collection -------------------------------------------------- */
    
    @Pure
    @Override
    public boolean isSingle() {
        return size() == 1;
    }
    
    @Pure
    @Override
    public @Nonnull FreezableIterator<E> iterator() {
        return FreezableIterableIterator.get(this, super.iterator());
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
        return false;
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    @Override
    @NonFrozenRecipient
    public boolean add(@Nullable E element) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.add(element);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.addAll(c);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean remove(@Nullable Object object) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.remove(object);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean removeAll(@Nonnull Collection<?> c) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.removeAll(c);
    }
    
    @Override
    @NonFrozenRecipient
    public boolean retainAll(@Nonnull Collection<?> c) {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        return super.retainAll(c);
    }
    
    @Override
    @NonFrozenRecipient
    public void clear() {
        Require.that(!isFrozen()).orThrow("This object is not frozen.");
        
        super.clear();
    }
    
    /* -------------------------------------------------- ReadOnlySet -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> add(@Nonnull ReadOnlySet<E> set) {
        final @Nonnull FreezableSet<E> clone = clone();
        clone.addAll((Collection<E>) set);
        return clone;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> subtract(@Nonnull ReadOnlySet<E> set) {
        final @Nonnull FreezableSet<E> clone = clone();
        clone.removeAll((Collection<E>) set);
        return clone;
    }
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableSet<E> intersect(@Nonnull ReadOnlySet<E> set) {
        final @Nonnull FreezableSet<E> clone = clone();
        clone.retainAll((Collection<E>) set);
        return clone;
    }
    
    /* -------------------------------------------------- Conversions -------------------------------------------------- */
    
    @Pure
    @Override
    public @Capturable @Nonnull @NonFrozen FreezableHashSet<E> clone() {
        return new FreezableHashSet<>(this);
    }
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Capturable @Nonnull @NonFrozen FreezableArray<E> toFreezableArray() {
        return FreezableArray.getNonNullable(toArray((E[]) new Object[size()]));
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return IterableConverter.toString(this, Brackets.CURLY);
    }
    
}
