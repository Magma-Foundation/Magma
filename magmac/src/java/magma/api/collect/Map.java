package magma.api.collect;

import magma.api.Tuple;
import magma.api.collect.stream.Stream;
import magma.api.option.Option;

public interface Map<K, V> {
    Map<K, V> putAll(Map<K, V> other);

    Option<V> get(K key);

    Stream<Tuple<K, V>> streamEntries();

    Map<K, V> put(K key, V value);

    Stream<K> keyStream();
}
