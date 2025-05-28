package magmac.api.collect.map;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.iter.Iter;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Map<K, V> {
    V getOrDefault(K key, V other);

    Iter<Tuple2<K, V>> iter();

    Map<K, V> put(K key, V value);

    boolean containsKey(K key);

    V get(K key);

    boolean isEmpty();

    Map<K, V> mapOrPut(K key, Function<V, V> mapper, Supplier<V> supplier);

    Option<Tuple2<Map<K, V>, V>> removeByKey(K key);
}
