package magmac.api.collect.map;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.iter.Iter;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Immutable map abstraction.
 */

public interface Map<K, V> {
    /**
     * Retrieves a value by key or returns {@code other} when absent.
     */
    V getOrDefault(K key, V other);

    /**
     * Creates an iterator over entries of this map.
     */
    Iter<Tuple2<K, V>> iter();

    /**
     * Associates {@code key} with {@code value} and returns the updated map.
     */
    Map<K, V> put(K key, V value);

    /**
     * Checks whether {@code key} exists.
     */
    boolean containsKey(K key);

    /**
     * Retrieves the value mapped to {@code key}.
     */
    V get(K key);

    /**
     * Tests whether the map has no entries.
     */
    boolean isEmpty();

    /**
     * Updates an existing key with {@code mapper} or puts a supplied value if missing.
     */
    Map<K, V> mapOrPut(K key, Function<V, V> mapper, Supplier<V> supplier);

    /**
     * Removes a key returning the updated map and removed value.
     */
    Option<Tuple2<Map<K, V>, V>> removeByKey(K key);
}
