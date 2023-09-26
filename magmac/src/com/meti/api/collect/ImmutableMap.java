package com.meti.api.collect;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Map;

import java.util.HashMap;

public record ImmutableMap<K, V>(java.util.Map<K, V> map) implements Map<K, V> {
    public ImmutableMap() {
        this(new HashMap<>());
    }

    public static <K, V> Map<K, V> empty() {
        return new ImmutableMap<>();
    }

    @Override
    public boolean hasKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public Map<K, V> put(K key, V value) {
        var copy = new HashMap<>(map);
        copy.put(key, value);
        return new ImmutableMap<>(copy);
    }

    @Override
    public Option<V> apply(K key) {
        if (map.containsKey(key)) {
            return Some.apply(map.get(key));
        } else {
            return None.apply();
        }
    }
}
