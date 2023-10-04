package com.meti.api.collect.map;

import com.meti.api.Tuple;
import com.meti.api.collect.ImmutableList;
import com.meti.api.collect.Iterator;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.ArrayList;
import java.util.HashMap;

public record ImmutableMap<K, V>(java.util.Map<K, V> map) implements Map<K, V> {
    public ImmutableMap() {
        this(new HashMap<>());
    }

    public static <K, V> Map<K, V> empty() {
        return new ImmutableMap<>();
    }

    @Override
    public Option<V> get(K key) {
        if (map.containsKey(key)) {
            return Some.apply(map.get(key));
        } else {
            return None.apply();
        }
    }

    @Override
    public Map<K, V> put(K key, V value) {
        var copy = new HashMap<>(map);
        copy.put(key, value);
        return new ImmutableMap<>(copy);
    }

    @Override
    public Iterator<Tuple<K, V>> iter() {
        return new ImmutableList<>(new ArrayList<>(map.entrySet()))
                .iter()
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public boolean hasKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public Map<K, V> putAll(Map<K, V> others) {
        var copy = new HashMap<>(this.map);
        var kvHashMap = others.iter().foldRight(copy, (kvHashMap1, kvTuple) -> {
            kvHashMap1.put(kvTuple.a(), kvTuple.b());
            return kvHashMap1;
        });
        return new ImmutableMap<>(kvHashMap);
    }
}
