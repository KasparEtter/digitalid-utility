package net.digitalid.utility.functional.iterable.old;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.digitalid.utility.functional.iterable.zip.ZipToNonNullableQuartetIterator;
import net.digitalid.utility.tupless.quartet.NonNullableQuartet;
import net.digitalid.utility.validation.annotations.elements.NonNullableElements;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * The zip-to-non-nullable-quartet iterable implements an iterable that combines four non-nullable-elements iterables and returns an iterator that returns quartets of the elements of the source iterables.
 */
@Immutable
class ZipToNonNullableQuartetNonNullIterable<T1, T2, T3, T4> extends NonNullableIterable<NonNullableQuartet<T1, T2, T3, T4>> {
    
    /**
     * The first iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T1> iterable1;
    
    /**
     * The second iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T2> iterable2;
    
    /**
     * The third iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T3> iterable3;
    
    /**
     * The forth iterable with non-nullable elements.
     */
    private final @Nonnull NonNullableIterable<T4> iterable4;
    
    /**
     * Creates a zip-to-non-nullable-quartet iterable by combining four non-nullable-elements iterables.
     */
    ZipToNonNullableQuartetNonNullIterable(@Nonnull NonNullableIterable<T1> iterable1, @Nonnull NonNullableIterable<T2> iterable2, @Nonnull NonNullableIterable<T3> iterable3, @Nonnull NonNullableIterable<T4> iterable4) {
        this.iterable1 = iterable1;
        this.iterable2 = iterable2;
        this.iterable3 = iterable3;
        this.iterable4 = iterable4;
    }
    
    /* -------------------------------------------------- Iterable -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull @NonNullableElements Iterator<NonNullableQuartet<T1, T2, T3, T4>> iterator() {
        return new ZipToNonNullableQuartetIterator<>(iterable1.iterator(), iterable2.iterator(), iterable3.iterator(), iterable4.iterator());
    }
    
    /* -------------------------------------------------- Size -------------------------------------------------- */
    
    /**
     * Since only the {@link net.digitalid.utility.functional.iterable.zip.ZipStrategy#SHORTEST_SEQUENCE SHORTEST_SEQUENCE}
     * zip strategy can be applied, the size is equal to the shortest source iterable.
     */
    @Override
    public int size() {
        final int sizeIterable1 = iterable1.size() == -1 ? Integer.MAX_VALUE : iterable1.size();
        final int sizeIterable2 = iterable2.size() == -1 ? Integer.MAX_VALUE : iterable2.size();
        final int sizeIterable3 = iterable3.size() == -1 ? Integer.MAX_VALUE : iterable3.size();
        final int sizeIterable4 = iterable4.size() == -1 ? Integer.MAX_VALUE : iterable4.size();
        final int size = Math.min(Math.min(Math.min(sizeIterable1, sizeIterable2), sizeIterable3), sizeIterable4);
        return size == Integer.MAX_VALUE ? -1 : size;
    }
    
}