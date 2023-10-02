package com.meti.compile;

import com.meti.api.Tuple;
import com.meti.api.collect.Iterator;
import com.meti.api.option.Option;

public interface Map<K, V> {
    Option<V> get(K key);

    Map<K, V> put(K key, V value);

    Iterator<Tuple<K, V>> iter();
}
