package magma.collect.map;

import magma.collect.stream.Stream;
import magma.option.Option;
import magma.option.Tuple;

public interface Map_<K, V> {
    Map_<K, V> with(K propertyKey, V propertyValue);

    Option<V> find(K propertyKey);

    Stream<Tuple<K, V>> stream();
}
