package com.meti.java;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.iterate.Iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class JavaMap<K, V> implements com.meti.java.Map<K, V> {
    private final Map<K, V> map;

    public JavaMap(Map<K, V> map) {
        this.map = map;
    }

    public static <K, V> com.meti.java.Map<K, V> empty() {
        return new JavaMap<>(new HashMap<>());
    }

    @Override
    public String toString() {
        return "JavaMap{" +
               "map=" + map +
               '}';
    }

    @Override
    public com.meti.java.Map<K, V> insertOrMap(K key, Function<V, V> onPresent, Supplier<V> onAbsent) {
        if (this.map.containsKey(key)) {
            var oldValue = this.map.get(key);
            var newValue = onPresent.apply(oldValue);
            this.map.put(key, newValue);
        } else {
            this.map.put(key, onAbsent.get());
        }

        return this;
    }

    @Override
    public Iterator<Tuple<K, V>> iter() {
        return new JavaList<>(new ArrayList<>(this.map.entrySet()))
                .iter().map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public com.meti.java.Map<K, V> mapValue(K first, Function<V, V> mapper) {
        var previous = this.map.get(first);
        var next = mapper.apply(previous);
        this.map.put(first, next);
        return this;
    }

    @Override
    public boolean hasKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public Option<V> applyOptionally(K key) {
        if (this.map.containsKey(key)) {
            return new Some<>(this.map.get(key));
        } else {
            return None.apply();
        }
    }

    @Override
    public V apply(Key<K> key) {
        return key.peek(this.map::get);
    }

    @Override
    public com.meti.java.Map<K, V> insert(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    @Override
    public Iterator<Key<K>> keys() {
        return new JavaSet<>(this.map.keySet())
                .iter()
                .map(ImmutableKey::new);
    }

}
