package magma.api.collect;

import magma.api.option.Option;
import magma.api.result.Tuple;

public interface Map_<K, V> {
    Option<V> get(K propertyKey);

    Map_<K, V> put(K propertyKey, V propertyValue);

    Map_<K, V> putAll(Map_<K, V> other);

    Stream<Tuple<K, V>> stream();
}
