package magma.java;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.collect.stream.Collector;

import java.util.Collections;

public record JavaMap<K, V>(java.util.Map<K, V> internal) implements Map<K, V> {
    public JavaMap() {
        this(Collections.emptyMap());
    }

    public static <K, V> Collector<Tuple<K, V>, Map<K, V>> collecting() {
        return new Collector<Tuple<K, V>, Map<K, V>>() {
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
}
