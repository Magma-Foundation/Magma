package com.meti.collect;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class JavaMap<K, V> {
    private final Map<K, V> inner;


    public JavaMap(Map<K, V> inner) {
        this.inner = inner;
    }

    public Stream<Tuple<K, V>> stream() {
        return Streams.fromSet(inner.entrySet()).map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }

    public JavaMap() {
        this(new HashMap<>());
    }

    @Override
    public String toString() {
        return "JavaMap{" +
               "inner=" + inner +
               '}';
    }

    public Option<V> apply(K key) {
        return inner.containsKey(key)
                ? Some(inner.get(key))
                : None();
    }

    public Option<JavaMap<K, V>> replaceValue(K key, V value) {
        if (!inner.containsKey(key)) return None();
        var copy = new HashMap<>(inner);
        copy.put(key, value);
        return Some(new JavaMap<>(copy));
    }

    public JavaMap<K, V> put(K key, V value) {
        var copy = new HashMap<>(inner);
        copy.put(key, value);
        return new JavaMap<>(copy);
    }

    public JavaMap<K, V> merge(K key, V value, BiFunction<V, V, V> merger) {
        var copy = new HashMap<>(inner);
        if(copy.containsKey(key)) {
            copy.put(key, merger.apply(copy.get(key), value));
        } else {
            copy.put(key, value);
        }
        return new JavaMap<>(copy);
    }
}
