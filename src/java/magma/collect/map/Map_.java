package magma.collect.map;

import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Map_<K, V> {
    Map_<K, V> with(K key, V value);

    Option<V> find(K key);

    Stream<Tuple<K, V>> stream();

    Map_<K, V> ensure(K key, Function<V, V> whenPresent, Supplier<V> whenEmpty);

    Map_<K, V> withAll(Map_<K, V> other);

    boolean isEmpty();

    Map_<K, V> remove(K key);

    boolean containsKey(K key);

    Stream<V> streamValues();

    Stream<K> streamKeys();
}
