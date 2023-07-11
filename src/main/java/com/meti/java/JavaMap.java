package com.meti.java;

import com.meti.core.Tuple;
import com.meti.iterate.Iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class JavaMap<K, V> {
    private final Map<K, V> map;

    public JavaMap() {
        this(new HashMap<>());
    }

    public JavaMap(Map<K, V> map) {
        this.map = map;
    }


    public JavaMap<K, V> insertOrMap(K key, Function<V, V> onPresent, Supplier<V> onAbsent) {
        if (this.map.containsKey(key)) {
            var oldValue = this.map.get(key);
            var newValue = onPresent.apply(oldValue);
            this.map.put(key, newValue);
        } else {
            this.map.put(key, onAbsent.get());
        }

        return this;
    }

    public Iterator<Tuple<K, V>> iter() {
        return new JavaList<>(new ArrayList<>(this.map.entrySet()))
                .iter().map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }
}
