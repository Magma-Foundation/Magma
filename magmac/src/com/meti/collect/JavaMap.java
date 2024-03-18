package com.meti.collect;

import com.meti.collect.option.Option;

import java.util.HashMap;
import java.util.Map;

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
}
