package magma.collect.map;

import magma.option.Option;

public interface Map_<K, V> {
    Map_<K, V> with(K propertyKey, V propertyValue);

    Option<V> find(K propertyKey);
}
