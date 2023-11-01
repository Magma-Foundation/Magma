package com.meti.api.collect;

import com.meti.api.iterator.Collector;
import com.meti.api.iterator.Iterator;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;

public class JavaMap<K, V> implements Map<K, V> {
    private final java.util.Map<K, V> inner;

    public JavaMap(java.util.Map<K, V> inner) {
        this.inner = inner;
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

    public static <K, V> Collector<Tuple<K, V>, Map<K, V>> collect() {
        return new Collector<Tuple<K, V>, Map<K, V>>() {
            @Override
            public Map<K, V> initial() {
                return new JavaMap<>(new HashMap<>());
            }

            @Override
            public Map<K, V> fold(Map<K, V> current, Tuple<K, V> element) {
                return current.put(element.a(), element.b());
            }
        };
    }

    public static <K, V> Map<K, V> empty() {
        return new JavaMap<>(Collections.emptyMap());
    }

    @Override
    public Iterator<Tuple<K, V>> iter() {
        return new NativeListIterator<>(new ArrayList<>(inner.entrySet()))
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public Option<V> get(K key) {
        if (inner.containsKey(key)) {
            return Some.apply(inner.get(key));
        } else {
            return None.apply();
        }
    }

    @Override
    public Map<K, V> putAll(Map<K, V> text) {
        return text.iter().foldRight(this, (BiFunction<Map<K, V>, Tuple<K, V>, Map<K, V>>) (kvJavaMap, vvTuple) -> kvJavaMap.put(vvTuple.a(), vvTuple.b()));
    }

    @Override
    public boolean hasKey(K key) {
        return inner.containsKey(key);
    }

    @Override
    public Map<K, V> put(K key, V value) {
        var copy = new HashMap<>(this.inner);
        copy.put(key, value);
        return new JavaMap<>(copy);
    }
}
