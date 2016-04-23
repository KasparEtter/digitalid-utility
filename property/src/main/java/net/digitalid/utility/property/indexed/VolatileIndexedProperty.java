package net.digitalid.utility.property.indexed;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.collections.collection.ReadOnlyCollection;
import net.digitalid.utility.collections.map.FreezableMap;
import net.digitalid.utility.collections.map.ReadOnlyMap;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.freezable.annotations.NonFrozen;
import net.digitalid.utility.generator.annotations.GenerateNoBuilder;
import net.digitalid.utility.generator.annotations.GenerateNoSubclass;
import net.digitalid.utility.property.Validated;
import net.digitalid.utility.property.ValueValidator;
import net.digitalid.utility.annotations.method.Pure;

/**
 * The property stores indexed values in volatile memory.
 * 
 * <em>Important:</em> Make sure that {@code F} is a sub-type of {@code R}!
 * Unfortunately, this cannot be enforced with the limited Java generics.
 * 
 * [used for the hosts in the Server class and modules in the Service class]
 * 
 * @param <K> the type of the key that indexes the value.
 * @param <V> the type of the value.
 * @param <R> the type of the read-only map to which the map is casted to when retrieved with getMap().
 * @param <F> the type of the map that is used to store the key-value pairs.
 */
@GenerateNoBuilder
@GenerateNoSubclass
public class VolatileIndexedProperty<K, V, R extends ReadOnlyMap<K, V>, F extends FreezableMap<K, V>> extends WritableIndexedProperty<K, V, R, F> {
    
    /* -------------------------------------------------- Map -------------------------------------------------- */
    
    /**
     * Stores the indexed value of the property.
     */
    private final @Nonnull @Validated F map;
    
    @Pure
    @Override
    @SuppressWarnings("unchecked")
    public @Nonnull @Validated @NonFrozen R getMap() {
        return (R) map;
    }
    
    /* -------------------------------------------------- Values -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nullable @Validated V get(@Nonnull K key) {
        return map.get(key);
    }
    
    @Pure
    @Override
    public @Validated @Nonnull ReadOnlyCollection<V> getAll() {
        return map.values();
    }
    
    /* -------------------------------------------------- Modifiers -------------------------------------------------- */
    
    @Override
    public void add(@Nonnull K key, @Nonnull @Validated V value) {
        Require.that(!map.containsKey(key)).orThrow("The key may not already be indexed.");
        
        map.put(key, value);
        
        notifyAdded(key, value);
    }
    
    @Override
    public void remove(@Nonnull @Validated K key) {
        V removedValue = map.remove(key);
        
        if (removedValue != null) {
            notifyRemoved(key, removedValue);
        }
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new volatile indexed property object, which is used to store indexed property values in the given map.
     * 
     * @param keyValidator the validator used to validate the key of this property.
     * @param valueValidator the validator used to validate the value of this property
     * @param map the map that stores the indexed value of the property.
     * 
     * @require keysAndValuesValid(keyValidator, valueValidator, map) : "The keys and values are valid.";
     */
    protected VolatileIndexedProperty(@Nonnull ValueValidator<? super K> keyValidator, @Nonnull ValueValidator<? super V> valueValidator, @Nonnull @Validated F map) {
        super(keyValidator, valueValidator);

        Require.that(keysAndValuesValid(keyValidator, valueValidator, map)).orThrow("The keys and values are valid.");

        this.map = map;
    }
    
    /**
     * Creates a new volatile indexed property with the given map and the given key- and value validators.
     *
     * @param keyValidator the validator used to validate the key of this property.
     * @param valueValidator the validator used to validate the value of this property
     * @param map the map that stores the indexed value of the property.
     *
     * @return a new volatile indexed property object.
     */
    @Pure
    public static @Nullable <K, V, R extends ReadOnlyMap<K, V>, F extends FreezableMap<K, V>> VolatileIndexedProperty<K, V, R, F> get(@Nonnull ValueValidator<? super K> keyValidator, @Nonnull ValueValidator<? super V> valueValidator, @Nonnull @Validated F map) {
        return new VolatileIndexedProperty<K, V, R, F>(keyValidator, valueValidator, map);
    }

    /**
     * Creates a new volatile indexed property with the given map.
     * 
     * @param map the map that stores the indexed value of the property.
     * 
     * @return a new volatile indexed property object.
     */
    @Pure
    public static @Nullable <K, V, R extends ReadOnlyMap<K, V>, F extends FreezableMap<K, V>> VolatileIndexedProperty<K, V, R, F> get(@Nonnull @Validated F map) {
        return get(ValueValidator.DEFAULT, ValueValidator.DEFAULT, map);
    }

    /* -------------------------------------------------- Validator Checks -------------------------------------------------- */

    /**
     * Validates that the contents of the map are valid by checking whether the keys and values can be validated with the given
     * key- and value validators.
     *
     * @param keyValidator the validator used to validate the key of this property.
     * @param valueValidator the validator used to validate the value of this property
     * @param map the map that stores the indexed value of the property.
     *
     * @return true if the keys and values are valid; false otherwise.
     */
    private boolean keysAndValuesValid(@Nonnull ValueValidator<? super K> keyValidator, @Nonnull ValueValidator<? super V> valueValidator, @Nonnull @Validated F map) { // TODO: Make it static? @NonNullableElements?
        for (final Map.@Nonnull Entry<K, V> entry : map.entrySet()) {
            if (!keyValidator.isValid(entry.getKey())) { return false; }
            if (!valueValidator.isValid(entry.getValue())) { return false; }
        }
        return true;
    }

}
