package magmac.api.collect.map;

import magmac.api.Tuple2;
import magmac.api.iter.Iter;

public interface Map<K, V> {
    V getOrDefault(K key, V other);

    Iter<Tuple2<K, V>> iterEntries();

    Map<K, V> put(K key, V value);

    boolean containsKey(K key);

    V get(K key);

    boolean isEmpty();
}
