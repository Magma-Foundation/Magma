package com.meti.collect;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class JavaMap<K, V> {
    private final Map<K, V> inner;

    public JavaMap(Map<K, V> inner) {
        this.inner = inner;
    }

    public JavaMap() {
        this(new HashMap<>());
    }

    public Stream<Pair<K, V>> stream() {
        return Streams.fromSet(inner.entrySet()).map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public String toString() {
        JavaString collect = stream().map(pair -> {
            return JavaString.from(pair.left() + ", " + pair.right());
        }).collect(Collectors.joining(JavaString.from(", "))).orElse(JavaString.Empty);

        return "new JavaMap<>(Map.of(" + collect.inner() + "))";
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
        if (copy.containsKey(key)) {
            copy.put(key, merger.apply(copy.get(key), value));
        } else {
            copy.put(key, value);
        }
        return new JavaMap<>(copy);
    }

    public boolean isEmpty() {
        return this.inner.isEmpty();
    }

    public JavaMap<K, V> putAll(JavaMap<K, V> other) {
        var copy = new HashMap<>(inner);
        copy.putAll(other.inner);
        return new JavaMap<>(copy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaMap<?, ?> javaMap = (JavaMap<?, ?>) o;
        return Objects.equals(inner, javaMap.inner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner);
    }
}
