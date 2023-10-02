package com.meti.compile;

import com.meti.api.option.Option;

public interface Map<K, V> {
    Option<V> get(K key);

    boolean hasKey(K key);

    Map<K, V> put(K key, V value);
}
