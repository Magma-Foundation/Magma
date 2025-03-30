package magma.collect.map;

import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Map_<K, V> {
    Map_<K, V> with(K propertyKey, V propertyValue);

    Option<V> find(K propertyKey);

    Stream<Tuple<K, V>> stream();

    Map_<K, V> ensure(K propertyKey, Function<V, V> whenPresent, Supplier<V> whenEmpty);

    Map_<K,V> withAll(Map_<K, V> other);

    boolean isEmpty();
}
