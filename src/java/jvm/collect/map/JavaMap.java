package jvm.collect.map;

import jvm.collect.stream.Streams;
import magma.collect.map.Map_;
import magma.collect.stream.Stream;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Override
    public Stream<Tuple<K, V>> stream() {
        return Streams.fromNativeList(map.entrySet()
                .stream()
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()))
                .toList());
    }

    @Override
    public Map_<K, V> ensure(K propertyKey, Function<V, V> ifPresent, Supplier<V> ifEmpty) {
        if (map.containsKey(propertyKey)) {
            return with(propertyKey, ifPresent.apply(map.get(propertyKey)));
        } else {
            return with(propertyKey, ifEmpty.get());
        }
    }

    @Override
    public Map_<K, V> withAll(Map_<K, V> other) {
        return other.stream().<Map_<K, V>>foldWithInitial(this,
                (current, entry) -> current.with(entry.left(), entry.right()));
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
}
