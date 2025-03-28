package jvm.api.collect;

import magma.api.collect.Map_;
import magma.api.collect.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Tuple;

import java.util.HashMap;
import java.util.Map;

public record JavaMap<K, V>(Map<K, V> map) implements Map_<K, V> {
    public JavaMap() {
        this(new HashMap<>());
    }

    @Override
    public Option<V> get(K propertyKey) {
        return map.containsKey(propertyKey)
                ? new Some<>(map.get(propertyKey))
                : new None<>();
    }

    @Override
    public Map_<K, V> put(K propertyKey, V propertyValue) {
        map.put(propertyKey, propertyValue);
        return this;
    }

    @Override
    public Map_<K, V> putAll(Map_<K, V> other) {
        return other.stream().<Map_<K, V>>foldWithInitial(this, (kvJavaMap, kvTuple) -> kvJavaMap.put(kvTuple.left(), kvTuple.right()));
    }

    @Override
    public Stream<Tuple<K, V>> stream() {
        return Streams.streamList(map.entrySet()
                .stream()
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()))
                .toList());
    }
}
