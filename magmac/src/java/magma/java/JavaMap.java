package magma.java;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.collect.stream.AbstractStream;
import magma.api.collect.stream.Collector;
import magma.api.collect.stream.Stream;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public record JavaMap<K, V>(java.util.Map<K, V> internal) implements Map<K, V> {
    public JavaMap() {
        this(Collections.emptyMap());
    }

    public static <K, V> Collector<Tuple<K, V>, Map<K, V>> collecting() {
        return new Collector<>() {
            @Override
            public Map<K, V> createInitial() {
                return new JavaMap<>();
            }

            @Override
            public Map<K, V> fold(Map<K, V> current, Tuple<K, V> next) {
                return current.put(next.left(), next.right());
            }
        };
    }

    @Override
    public Map<K, V> putAll(Map<K, V> other) {
        return other.streamEntries().<Map<K, V>>foldLeft(this, (current, next) -> current.put(next.left(), next.right()));
    }

    @Override
    public Option<V> get(K key) {
        if (internal.containsKey(key)) {
            return new Some<>(internal.get(key));
        } else {
            return new None<>();
        }
    }

    @Override
    public Stream<Tuple<K, V>> streamEntries() {
        return new AbstractStream<>(new NativeListHead<>(new ArrayList<>(internal.entrySet())))
                .map(inner -> new Tuple<>(inner.getKey(), inner.getValue()));
    }

    @Override
    public Map<K, V> put(K key, V value) {
        var copy = new HashMap<>(internal);
        copy.put(key, value);
        return new JavaMap<>(copy);
    }

    @Override
    public Stream<K> keyStream() {
        return new AbstractStream<>(new NativeListHead<>(new ArrayList<>(internal.keySet())));
    }
}
