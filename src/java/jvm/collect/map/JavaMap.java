package jvm.collect.map;

import magma.collect.map.Map_;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record JavaMap<K, V>(Map<K, V> map) implements Map_<K, V> {
    public JavaMap() {
        this(Collections.emptyMap());
    }

    @Override
    public Map_<K, V> with(K propertyKey, V propertyValue) {
        HashMap<K, V> copy = new HashMap<>(map);
        copy.put(propertyKey, propertyValue);
        return new JavaMap<>(copy);
    }

    @Override
    public Option<V> find(K propertyKey) {
        return map.containsKey(propertyKey)
                ? new Some<>(map.get(propertyKey))
                : new None<>();
    }
}
